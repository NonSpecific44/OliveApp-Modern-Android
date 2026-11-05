package olive.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Album art in the now-playing bar. The screens that poll the player (main tabs, album/track list) pass each
 * metadata update here; other screens show the last artwork when they open.
 */
public final class OliveArt {
    private static final String PREFS = "Olive_Settings";
    private static final LruCache<String, Bitmap> CACHE = new LruCache<String, Bitmap>(8);
    private static final ExecutorService IO = Executors.newSingleThreadExecutor();
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    private OliveArt() {
    }

    /** Called with the original app's Metadata object at the start of each now-playing update. */
    public static void onMetadata(Activity activity, Object metadata) {
        if (metadata == null) {
            return;
        }
        String url = "";
        try {
            Method albumart = metadata.getClass().getMethod("getAlbumart");
            Object value = albumart.invoke(metadata);
            url = value == null ? "" : value.toString().trim();
        } catch (Exception ignored) {
        }
        String devIP = activity.getSharedPreferences(PREFS, 0).getString("devIP", "none");
        if (url.length() > 0 && devIP != null && !devIP.equals("none")) {
            // The Olive reports its own artwork URLs as 127.0.0.1, as the original artwork loader also handles.
            url = url.replace("127.0.0.1", devIP);
        }
        activity.getSharedPreferences(PREFS, 0).edit().putString("miniArtUrl", url).apply();
        show(activity, url);
    }

    /** Shows the last known artwork when a screen with a now-playing bar opens. */
    public static void attach(Activity activity) {
        if (activity.findViewById(OliveUi.id(activity, "miniArt")) != null) {
            show(activity, activity.getSharedPreferences(PREFS, 0).getString("miniArtUrl", ""));
        }
    }

    private static void show(final Activity activity, final String url) {
        final ImageView view = (ImageView) activity.findViewById(OliveUi.id(activity, "miniArt"));
        if (view == null) {
            return;
        }
        if (url == null || url.length() == 0) {
            setIcon(activity, view);
            return;
        }
        Bitmap cached = CACHE.get(url);
        if (cached != null) {
            setArt(view, cached, url);
            return;
        }
        if (url.equals(view.getTag(OliveUi.id(activity, "olive_art_loading")))) {
            return;
        }
        view.setTag(OliveUi.id(activity, "olive_art_loading"), url);
        IO.execute(new Runnable() {
            public void run() {
                final Bitmap bitmap = load(url, Math.round(96 * activity.getResources().getDisplayMetrics().density));
                MAIN.post(new Runnable() {
                    public void run() {
                        view.setTag(OliveUi.id(activity, "olive_art_loading"), null);
                        if (bitmap != null) {
                            CACHE.put(url, bitmap);
                            setArt(view, bitmap, url);
                        } else {
                            setIcon(activity, view);
                        }
                    }
                });
            }
        });
    }

    private static void setArt(ImageView view, Bitmap bitmap, String url) {
        if (url.equals(view.getTag())) {
            return;
        }
        view.setTag(url);
        view.setPadding(0, 0, 0, 0);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setClipToOutline(true);
        view.setImageBitmap(bitmap);
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(250).start();
    }

    private static void setIcon(Context c, ImageView view) {
        if ("icon".equals(view.getTag())) {
            return;
        }
        view.setTag("icon");
        int pad = Math.round(12 * c.getResources().getDisplayMetrics().density);
        view.setPadding(pad, pad, pad, pad);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.setImageResource(c.getResources().getIdentifier("ic_m3_now_playing", "drawable", c.getPackageName()));
    }

    /** Downloads and downsamples artwork to about maxPx on its longer side. */
    static Bitmap load(String url, int maxPx) {
        HttpURLConnection c = null;
        try {
            c = (HttpURLConnection) new URL(url).openConnection();
            c.setConnectTimeout(5000);
            c.setReadTimeout(8000);
            Bitmap full = BitmapFactory.decodeStream(c.getInputStream());
            if (full == null) {
                return null;
            }
            int max = Math.max(full.getWidth(), full.getHeight());
            if (max <= maxPx) {
                return full;
            }
            float s = (float) maxPx / max;
            return Bitmap.createScaledBitmap(full, Math.max(1, Math.round(full.getWidth() * s)), Math.max(1, Math.round(full.getHeight() * s)), true);
        } catch (Exception e) {
            return null;
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
    }
}
