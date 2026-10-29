package olive.app;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;

/** Helpers patched into the original bytecode to run on current Android versions. */
public final class Compat {
    private static WifiManager.MulticastLock multicastLock;

    /** M3 emphasized-decelerate easing. */
    static final Interpolator EMPHASIZED = new PathInterpolator(0.05f, 0.7f, 0.1f, 1f);

    private Compat() {
    }

    /**
     * Replacement for AsyncTask.execute(). On Android 1.6 tasks ran in parallel;
     * on 3.0+ execute() is serial, so one stalled request (e.g. the 5 second
     * "now playing" poll) would block every other command to the player.
     */
    public static AsyncTask exec(AsyncTask task, Object[] params) {
        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    }

    /** Many Wi-Fi drivers drop multicast packets unless a lock is held; needed for SSDP discovery. */
    public static synchronized void multicast(Context context, boolean enable) {
        try {
            if (enable) {
                if (multicastLock == null) {
                    WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    multicastLock = wifi.createMulticastLock("olive-ssdp");
                    multicastLock.setReferenceCounted(false);
                }
                multicastLock.acquire();
            } else if (multicastLock != null && multicastLock.isHeld()) {
                multicastLock.release();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modern devices have no hardware MENU key, so the tabs' option menus
     * (Search, Refresh, Shuffle all, Quit) were unreachable. Show the current
     * tab's options menu as a popup anchored to an on-screen button instead.
     */
    public static void showTabMenu(TabActivity host, View anchor) {
        final Activity tab = host.getCurrentActivity();
        if (tab == null) {
            return;
        }
        PopupMenu popup = new PopupMenu(host, anchor);
        Menu menu = popup.getMenu();
        tab.onCreateOptionsMenu(menu);
        tab.onPrepareOptionsMenu(menu);
        if (!menu.hasVisibleItems()) {
            Toast.makeText(host, "No options for this tab", Toast.LENGTH_SHORT).show();
            return;
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return tab.onOptionsItemSelected(item);
            }
        });
        popup.show();
    }

    private static int id(Context context, String type, String name) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    /**
     * Replacement for WifiInfo.getIpAddress(). The original app refused to work unless
     * the phone had a Wi-Fi address; this also accepts any other active network
     * (mobile data, VPNs such as Tailscale), in the same little-endian format.
     */
    public static int ipAddress(WifiInfo info) {
        if (info != null && info.getIpAddress() != 0) {
            return info.getIpAddress();
        }
        try {
            for (NetworkInterface nif : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (!nif.isUp() || nif.isLoopback()) {
                    continue;
                }
                for (InetAddress address : Collections.list(nif.getInetAddresses())) {
                    if (address instanceof Inet4Address) {
                        byte[] b = address.getAddress();
                        return (b[0] & 255) | (b[1] & 255) << 8 | (b[2] & 255) << 16 | (b[3] & 255) << 24;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Automatic discovery uses SSDP multicast, which only works on the Olive's own Wi-Fi.
     * When not on Wi-Fi (e.g. mobile data + Tailscale) skip it and explain, instead of
     * showing an error, so the address can simply be typed in.
     */
    public static boolean skipDiscovery(Activity activity) {
        WifiInfo info = null;
        try {
            WifiManager wifi = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            info = wifi.getConnectionInfo();
        } catch (RuntimeException ignored) {
        }
        if (info != null && info.getIpAddress() != 0) {
            return false;
        }
        View spinner = activity.findViewById(id(activity, "id", "searchLoadingIcon"));
        if (spinner != null) {
            spinner.setVisibility(View.GONE);
        }
        TextView text = (TextView) activity.findViewById(id(activity, "id", "searchDevicesText"));
        if (text != null) {
            text.setVisibility(View.VISIBLE);
            text.setText("Automatic search only works on the same Wi-Fi as the Olive. Not on Wi-Fi right now, so enter the address above.");
        }
        return true;
    }

    /** Hides a list row's second line when it just repeats the first (e.g. artist names). */
    public static void dedupeSubtitle(TextView subtitle) {
        View row = (View) subtitle.getParent();
        TextView title = (TextView) row.findViewById(id(subtitle.getContext(), "id", "artist"));
        if (title == subtitle) {
            title = (TextView) row.findViewById(id(subtitle.getContext(), "id", "album"));
        }
        boolean same = title != null && title.getText().toString().trim().equalsIgnoreCase(subtitle.getText().toString().trim());
        subtitle.setVisibility(same ? View.GONE : View.VISIBLE);
    }

    /** Replacement for TabSpec.setIndicator(label, icon): bottom-navigation style tab with icon and label. */
    public static TabHost.TabSpec tabIndicator(Activity activity, TabHost.TabSpec spec, CharSequence label, Drawable icon) {
        View tab = activity.getLayoutInflater().inflate(id(activity, "layout", "olive_tab"), null);
        int vector = id(activity, "drawable", "ic_nav_" + label.toString().toLowerCase());
        ImageView image = (ImageView) tab.findViewById(id(activity, "id", "tabIcon"));
        if (vector != 0) {
            image.setImageResource(vector);
        } else {
            image.setImageDrawable(icon);
        }
        ((TextView) tab.findViewById(id(activity, "id", "tabLabel"))).setText(label);
        tab.setContentDescription(label);
        return spec.setIndicator(tab);
    }

    /** Keeps the app bar title and search button in step with the selected tab. */
    public static void setupShell(final TabActivity host) {
        final TabHost tabs = host.getTabHost();
        final TextView title = (TextView) host.findViewById(id(host, "id", "screenTitle"));
        final View search = host.findViewById(id(host, "id", "searchButton"));
        final boolean[] animate = {false};
        TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tag) {
                View tab = tabs.getCurrentTabView();
                TextView label = tab == null ? null : (TextView) tab.findViewById(id(host, "id", "tabLabel"));
                if (title != null && label != null) {
                    title.setText(label.getText());
                }
                if (search != null) {
                    search.setVisibility(findTabMenuItem(host, search, "search") != null ? View.VISIBLE : View.GONE);
                }
                if (animate[0]) {
                    View pill = tab == null ? null : tab.findViewById(id(host, "id", "tabPill"));
                    if (pill != null) {
                        pill.setScaleX(0.4f);
                        pill.animate().scaleX(1f).setDuration(350).setInterpolator(EMPHASIZED).start();
                    }
                    View content = tabs.getCurrentView();
                    if (content != null) {
                        // M3 "fade through" between top-level destinations.
                        content.setAlpha(0f);
                        content.setScaleX(0.96f);
                        content.setScaleY(0.96f);
                        content.animate().alpha(1f).scaleX(1f).scaleY(1f).setStartDelay(60).setDuration(300).setInterpolator(EMPHASIZED).start();
                    }
                }
            }
        };
        tabs.setOnTabChangedListener(listener);
        listener.onTabChanged(tabs.getCurrentTabTag());
        animate[0] = true;
    }

    private static MenuItem findTabMenuItem(TabActivity host, View anchor, String name) {
        Activity tab = host.getCurrentActivity();
        if (tab == null) {
            return null;
        }
        Menu menu = new PopupMenu(host, anchor).getMenu();
        tab.onCreateOptionsMenu(menu);
        tab.onPrepareOptionsMenu(menu);
        return menu.findItem(id(host, "id", name));
    }

    /** Runs one of the current tab's option-menu actions (used by the app bar's search button). */
    public static void tabAction(TabActivity host, View anchor, String name) {
        MenuItem item = findTabMenuItem(host, anchor, name);
        if (item != null) {
            host.getCurrentActivity().onOptionsItemSelected(item);
        }
    }

    /**
     * Replacement for the player's play/pause icon changes: the new state's drawable
     * (a differently shaped container) pops in with an expressive squish-and-overshoot.
     */
    public static void setPlayIcon(final ImageButton button, final int resId) {
        swapPlayIcon(button, button.getResources().getDrawable(resId), resId);
    }

    public static void setPlayIcon(ImageButton button, Drawable drawable) {
        swapPlayIcon(button, drawable, drawable == null ? 0 : drawable.getConstantState() == null ? drawable.hashCode() : drawable.getConstantState().hashCode());
    }

    private static void swapPlayIcon(final ImageButton button, final Drawable drawable, int key) {
        int tagKey = id(button.getContext(), "id", "olive_play_icon");
        Object previous = tagKey == 0 ? null : button.getTag(tagKey);
        if (tagKey != 0) {
            button.setTag(tagKey, key);
        }
        if (previous == null || ((Integer) previous) == key || !button.isAttachedToWindow()) {
            button.setImageDrawable(drawable);
            return;
        }
        button.animate().cancel();
        button.animate().scaleX(0.82f).scaleY(0.82f).setDuration(90).setInterpolator(EMPHASIZED).withEndAction(new Runnable() {
            public void run() {
                button.setImageDrawable(drawable);
                button.animate().scaleX(1f).scaleY(1f).setDuration(320).setInterpolator(new OvershootInterpolator(2.5f)).start();
            }
        }).start();
    }

    /** The player coloured its title and progress bar per genre; the M3 theme colours are used instead. */
    public static void themeTextColor(TextView view, int color) {
    }

    public static void keepProgressStyle(ProgressBar bar, Drawable drawable) {
    }

    /**
     * Lists load asynchronously, after their first layout, so the theme's layout animation
     * would never be seen. Replay it whenever a list goes from empty to populated.
     */
    public static void animateLists(Activity activity) {
        View root = activity.getWindow() == null ? null : activity.getWindow().peekDecorView();
        if (root != null) {
            watchLists(root);
        }
    }

    private static void watchLists(View view) {
        if (view instanceof ListView) {
            final ListView list = (ListView) view;
            int key = id(list.getContext(), "id", "olive_list_watch");
            if (key == 0 || list.getTag(key) != null || list.getLayoutAnimation() == null) {
                return;
            }
            final int[] lastCount = {list.getAdapter() == null ? 0 : list.getAdapter().getCount()};
            list.setTag(key, Boolean.TRUE);
            list.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    int count = list.getAdapter() == null ? 0 : list.getAdapter().getCount();
                    if (lastCount[0] == 0 && count > 0) {
                        list.scheduleLayoutAnimation();
                        list.invalidate();
                    }
                    lastCount[0] = count;
                }
            });
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                watchLists(group.getChildAt(i));
            }
        }
    }
}
