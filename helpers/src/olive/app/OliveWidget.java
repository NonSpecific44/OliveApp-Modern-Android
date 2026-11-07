package olive.app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.util.SizeF;
import android.widget.RemoteViews;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Home-screen widget: artwork, title and artist with previous / play-pause / next. OliveMediaService pushes
 * each now-playing update here; the last state is kept so the widget still shows it when nothing is running.
 * Buttons start the media service with the same actions as its notification.
 */
public class OliveWidget extends AppWidgetProvider {
    private static final String PREFS = "Olive_Settings";
    private static final String ART_FILE = "widget_art.png";

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        render(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager manager, int id, Bundle options) {
        render(context);
    }

    /** Saves the current state (artwork only when it changed) and refreshes every placed widget. */
    static void update(Context context, String title, String artist, boolean playing, Bitmap art, boolean artChanged) {
        SharedPreferences.Editor e = context.getSharedPreferences(PREFS, 0).edit()
                .putString("widget_title", title)
                .putString("widget_artist", artist)
                .putBoolean("widget_playing", playing);
        if (artChanged) {
            File file = new File(context.getFilesDir(), ART_FILE);
            if (art == null) {
                file.delete();
            } else {
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    rounded(art, 0.12f).compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.close();
                } catch (Exception ignored) {
                }
            }
        }
        e.apply();
        render(context);
    }

    static void render(Context context) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(new ComponentName(context, OliveWidget.class));
        if (ids == null || ids.length == 0) {
            return;
        }
        RemoteViews small = build(context, "olive_widget");
        // (build() picks the "_dyn" or "_olive" variant of each layout and drawable for the Colours setting.)
        if (Build.VERSION.SDK_INT >= 31) {
            // Android 12+: the launcher picks the layout that fits the widget's current size.
            Map<SizeF, RemoteViews> sizes = new HashMap<SizeF, RemoteViews>();
            sizes.put(new SizeF(180f, 40f), small);
            sizes.put(new SizeF(180f, 150f), build(context, "olive_widget_large"));
            manager.updateAppWidget(ids, new RemoteViews(sizes));
        } else {
            manager.updateAppWidget(ids, small);
        }
    }

    private static RemoteViews build(Context context, String layout) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
        boolean configured = !"none".equals(prefs.getString("devIP", "none"));
        String title = prefs.getString("widget_title", "");
        String artist = prefs.getString("widget_artist", "");
        boolean playing = prefs.getBoolean("widget_playing", false);
        String palette = OliveTheme.olive(context) ? "_olive" : "_dyn";
        RemoteViews views = new RemoteViews(context.getPackageName(), res(context, "layout", layout + palette));
        views.setTextViewText(res(context, "id", "widgetTitle"),
                !configured ? "Olive" : title.length() > 0 ? title : "Nothing playing");
        views.setTextViewText(res(context, "id", "widgetArtist"),
                !configured ? "Open the app to connect to your Olive" : artist);
        File art = new File(context.getFilesDir(), ART_FILE);
        Bitmap bitmap = art.exists() ? BitmapFactory.decodeFile(art.getPath()) : null;
        if (bitmap != null) {
            views.setImageViewBitmap(res(context, "id", "widgetArt"), bitmap);
        } else {
            views.setImageViewResource(res(context, "id", "widgetArt"), res(context, "drawable", "w_art_placeholder" + palette));
        }
        views.setImageViewResource(res(context, "id", "widgetPlay"), res(context, "drawable", (playing ? "w_pause" : "w_play") + palette));
        views.setContentDescription(res(context, "id", "widgetPlay"), playing ? "Pause" : "Play");
        views.setOnClickPendingIntent(res(context, "id", "widgetPrevious"), action(context, "olive.app.PREVIOUS"));
        views.setOnClickPendingIntent(res(context, "id", "widgetPlay"), action(context, "olive.app.TOGGLE"));
        views.setOnClickPendingIntent(res(context, "id", "widgetNext"), action(context, "olive.app.NEXT"));
        views.setOnClickPendingIntent(res(context, "id", "widgetBody"), openApp(context));
        return views;
    }

    private static PendingIntent action(Context context, String action) {
        Intent intent = new Intent(context, OliveMediaService.class).setAction(action);
        int flags = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= 26) {
            // Tapping a widget button may start a foreground service even while the app is in the background.
            return PendingIntent.getForegroundService(context, action.hashCode(), intent, flags);
        }
        return PendingIntent.getService(context, action.hashCode(), intent, flags);
    }

    private static PendingIntent openApp(Context context) {
        Intent home = new Intent().setClassName(context, "olive.app.LoadTabs").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent player = new Intent().setClassName(context, "olive.app.Playscreen");
        return PendingIntent.getActivities(context, 1, new Intent[] {home, player},
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /** Square-cropped copy with rounded corners (radius as a fraction of the side); widgets can't clip images. */
    static Bitmap rounded(Bitmap source, float radiusFraction) {
        int side = Math.min(source.getWidth(), source.getHeight());
        Bitmap square = Bitmap.createBitmap(source, (source.getWidth() - side) / 2, (source.getHeight() - side) / 2, side, side);
        Bitmap out = Bitmap.createBitmap(side, side, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new BitmapShader(square, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        float r = side * radiusFraction;
        new Canvas(out).drawRoundRect(new RectF(0, 0, side, side), r, r, paint);
        return out;
    }

    private static int res(Context c, String type, String name) {
        return c.getResources().getIdentifier(name, type, c.getPackageName());
    }
}
