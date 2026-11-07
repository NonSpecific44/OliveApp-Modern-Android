package olive.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

/**
 * Adaptive and gesture UI added on top of the original screens:
 * - pull-to-refresh on lists whose screen has a Refresh action, with an M3 indicator that also replaces the
 *   original modal "Working..." progress dialogs where the screen has one;
 * - phones stay portrait, tablets rotate freely;
 * - the player switches between stacked and side-by-side panes with the window's shape.
 */
public final class OliveUi {
    private OliveUi() {
    }

    static int id(Context c, String name) {
        return c.getResources().getIdentifier(name, "id", c.getPackageName());
    }

    static float dp(Context c, float v) {
        return v * c.getResources().getDisplayMetrics().density;
    }

    // --- Orientation ------------------------------------------------------------------------------

    /** Phones (smallest width under 600dp) stay portrait as before; tablets and unfolded foldables rotate. */
    public static void orientationPolicy(Activity activity) {
        if (activity.getParent() != null || activity.getResources().getConfiguration().smallestScreenWidthDp >= 600) {
            return;
        }
        TypedArray a = activity.obtainStyledAttributes(new int[] {android.R.attr.windowIsFloating});
        boolean floating = a.getBoolean(0, false);
        a.recycle();
        if (floating) {
            return; // dialog-style screens follow the screen beneath them
        }
        try {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch (RuntimeException ignored) {
        }
    }

    /** Called whenever a screen resumes: wires up pull-to-refresh and the adaptive layout. */
    public static void attach(Activity activity) {
        Pull.attach(activity);
        Adaptive.attach(activity);
        OliveUpdater.attachSettings(activity);
        OliveArt.attach(activity);
        OliveTheme.attachSettings(activity);
    }

    // --- Progress -----------------------------------------------------------------------------------

    /**
     * Replacement for ProgressDialog.show(). Where the screen has the inline indicator, it spins there
     * instead of a modal dialog; the returned dialog is never shown and stands in for it, so the original
     * code's isShowing()/dismiss() calls drive the indicator.
     */
    public static ProgressDialog progress(Context context, CharSequence title, CharSequence message,
                                          boolean indeterminate, boolean cancelable) {
        Pull pull = context instanceof Activity ? Pull.attach((Activity) context) : null;
        if (pull == null) {
            return ProgressDialog.show(context, title, message, indeterminate, cancelable);
        }
        pull.startLoading();
        return new InlineProgress(context, pull);
    }

    static final class InlineProgress extends ProgressDialog {
        private final Pull pull;

        InlineProgress(Context context, Pull pull) {
            super(context);
            this.pull = pull;
        }

        @Override
        public boolean isShowing() {
            return pull.loading;
        }

        @Override
        public void show() {
            pull.startLoading();
        }

        @Override
        public void dismiss() {
            pull.finishLoading();
        }

        @Override
        public void cancel() {
            pull.finishLoading();
        }

        @Override
        public void hide() {
            pull.finishLoading();
        }
    }

    // --- Pull to refresh ----------------------------------------------------------------------------

    static final class Pull implements View.OnTouchListener {
        private final Activity activity;
        private final ListView list;
        private final View indicator;
        private final View arrow;
        private final View spinner;
        private final boolean canRefresh;
        private final float hiddenY;
        private final float threshold;
        private final int slop;
        private final Handler main = new Handler(Looper.getMainLooper());
        private float startY;
        private boolean pulling;
        private float distance;
        boolean loading;

        private final Runnable timeout = new Runnable() {
            public void run() {
                finishLoading();
            }
        };

        private Pull(Activity activity, ListView list, View indicator) {
            this.activity = activity;
            this.list = list;
            this.indicator = indicator;
            this.arrow = indicator.findViewById(id(activity, "pullArrow"));
            this.spinner = indicator.findViewById(id(activity, "pullSpinner"));
            this.canRefresh = menuItem("refresh") != null;
            this.hiddenY = -dp(activity, 72);
            this.threshold = dp(activity, 80);
            this.slop = ViewConfiguration.get(activity).getScaledTouchSlop();
            indicator.setTranslationY(hiddenY);
            indicator.setAlpha(0f);
            if (canRefresh) {
                list.setOnTouchListener(this);
            }
        }

        /** The screen's indicator controller, created on first use; null if the screen has no indicator. */
        static Pull attach(Activity activity) {
            View indicator = activity.findViewById(id(activity, "pullIndicator"));
            if (indicator == null) {
                return null;
            }
            int key = id(activity, "olive_pull");
            Object existing = indicator.getTag(key);
            if (existing instanceof Pull) {
                return (Pull) existing;
            }
            ViewGroup parent = (ViewGroup) indicator.getParent();
            ListView list = null;
            for (int i = 0; i < parent.getChildCount() && list == null; i++) {
                if (parent.getChildAt(i) instanceof ListView) {
                    list = (ListView) parent.getChildAt(i);
                }
            }
            if (list == null) {
                return null;
            }
            Pull pull = new Pull(activity, list, indicator);
            indicator.setTag(key, pull);
            return pull;
        }

        private MenuItem menuItem(String name) {
            try {
                Menu menu = new PopupMenu(activity, list).getMenu();
                activity.onCreateOptionsMenu(menu);
                return menu.findItem(id(activity, name));
            } catch (RuntimeException e) {
                return null;
            }
        }

        private boolean atTop() {
            if (list.getChildCount() == 0) {
                return true;
            }
            return list.getFirstVisiblePosition() == 0 && list.getChildAt(0).getTop() >= list.getPaddingTop();
        }

        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    startY = e.getRawY();
                    pulling = false;
                    return false;
                case MotionEvent.ACTION_MOVE:
                    if (loading) {
                        return false;
                    }
                    float dy = e.getRawY() - startY;
                    if (!pulling) {
                        if (dy > slop && atTop()) {
                            pulling = true;
                            startY = e.getRawY();
                            MotionEvent cancel = MotionEvent.obtain(e);
                            cancel.setAction(MotionEvent.ACTION_CANCEL);
                            list.onTouchEvent(cancel); // stop the list's own scroll / press / stretch
                            cancel.recycle();
                            indicator.animate().cancel();
                        } else {
                            if (dy < 0) {
                                startY = e.getRawY();
                            }
                            return false;
                        }
                    }
                    drag(Math.max(0f, e.getRawY() - startY));
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (!pulling) {
                        return false;
                    }
                    pulling = false;
                    if (e.getActionMasked() == MotionEvent.ACTION_UP && distance >= threshold) {
                        refresh();
                    } else {
                        retract();
                    }
                    return true;
                default:
                    return pulling;
            }
        }

        /** The indicator follows the finger with resistance; the arrow turns and fills in as it nears the threshold. */
        private void drag(float raw) {
            distance = raw * 0.5f;
            float progress = Math.min(1f, distance / threshold);
            indicator.setTranslationY(hiddenY + Math.min(distance, threshold * 1.4f) + dp(activity, 8));
            indicator.setAlpha(Math.min(1f, progress * 1.5f));
            float scale = 0.6f + 0.4f * progress;
            indicator.setScaleX(scale);
            indicator.setScaleY(scale);
            if (arrow != null) {
                arrow.setVisibility(View.VISIBLE);
                arrow.setRotation(progress * 270f);
                arrow.setAlpha(progress >= 1f ? 1f : 0.55f);
            }
            if (spinner != null) {
                spinner.setVisibility(View.GONE);
            }
        }

        private void refresh() {
            startLoading();
            MenuItem item = menuItem("refresh");
            if (item != null) {
                activity.onOptionsItemSelected(item);
            }
        }

        void startLoading() {
            loading = true;
            main.removeCallbacks(timeout);
            main.postDelayed(timeout, 30000);
            if (arrow != null) {
                arrow.setVisibility(View.GONE);
            }
            if (spinner != null) {
                spinner.setVisibility(View.VISIBLE);
            }
            indicator.animate().translationY(dp(activity, 16)).alpha(1f).scaleX(1f).scaleY(1f)
                    .setDuration(250).setInterpolator(Compat.EMPHASIZED).start();
        }

        void finishLoading() {
            loading = false;
            main.removeCallbacks(timeout);
            retract();
        }

        private void retract() {
            distance = 0;
            indicator.animate().translationY(hiddenY).alpha(0f).scaleX(0.6f).scaleY(0.6f)
                    .setDuration(250).setInterpolator(Compat.EMPHASIZED).start();
        }
    }

    // --- Adaptive panes -------------------------------------------------------------------------------

    /**
     * A LinearLayout with id "adaptivePanes" holding "paneArt" and "paneInfo" is laid out side by side when
     * the window is wide (landscape tablet) and stacked otherwise, and "artwork" is sized as a square that fits.
     * The original screens keep their activity across rotation (configChanges), so this adapts in place.
     */
    static final class Adaptive implements View.OnLayoutChangeListener {
        private final Activity activity;
        private final LinearLayout panes;
        private int lastW;
        private int lastH;

        private Adaptive(Activity activity, LinearLayout panes) {
            this.activity = activity;
            this.panes = panes;
        }

        static void attach(Activity activity) {
            View v = activity.findViewById(id(activity, "adaptivePanes"));
            if (!(v instanceof LinearLayout)) {
                return;
            }
            int key = id(activity, "olive_adaptive");
            if (v.getTag(key) != null) {
                return;
            }
            Adaptive adaptive = new Adaptive(activity, (LinearLayout) v);
            v.setTag(key, adaptive);
            v.addOnLayoutChangeListener(adaptive);
            if (v.getWidth() > 0) {
                adaptive.apply(v.getWidth(), v.getHeight());
            }
        }

        public void onLayoutChange(View v, int l, int t, int r, int b, int ol, int ot, int or, int ob) {
            final int w = r - l;
            final int h = b - t;
            if (w == lastW && h == lastH) {
                return;
            }
            v.post(new Runnable() {
                public void run() {
                    apply(w, h);
                }
            });
        }

        void apply(int w, int h) {
            if (w <= 0 || h <= 0) {
                return;
            }
            lastW = w;
            lastH = h;
            View art = panes.findViewById(id(activity, "paneArt"));
            View info = panes.findViewById(id(activity, "paneInfo"));
            View artwork = panes.findViewById(id(activity, "artwork"));
            if (art == null || info == null || artwork == null) {
                return;
            }
            boolean wide = w > h * 1.15f;
            panes.setOrientation(wide ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
            float max = dp(activity, activity.getResources().getConfiguration().smallestScreenWidthDp >= 600 ? 520 : 360);
            float pad = dp(activity, 48);
            int size;
            if (wide) {
                art.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                info.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                size = (int) Math.min(max, Math.min(w / 2f - pad, h - pad));
            } else {
                art.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0f));
                info.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));
                size = (int) Math.min(max, Math.min(w - pad, h * 0.42f));
            }
            ViewGroup.LayoutParams lp = artwork.getLayoutParams();
            if (lp.width != size || lp.height != size) {
                lp.width = size;
                lp.height = size;
                artwork.setLayoutParams(lp);
            }
            panes.requestLayout();
        }
    }
}
