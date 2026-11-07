package olive.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.GraphicsMode;
import org.robolectric.annotation.LooperMode;
import org.robolectric.shadows.ShadowAppWidgetManager;
import org.robolectric.shadows.ShadowLooper;

/** Album art in the now-playing bar, and the home-screen widget. */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34, qualifiers = "w411dp-h891dp-xhdpi")
@LooperMode(LooperMode.Mode.PAUSED)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class WidgetAndArtTest {
    static final String SERVER = "127.0.0.1:8765";
    static final File OUT = new File(System.getProperty("shots.dir", "shots"));

    static Class<?> app(String n) throws Exception {
        return Class.forName("olive.app." + n);
    }

    static int id(String type, String name) {
        Context c = RuntimeEnvironment.getApplication();
        return c.getResources().getIdentifier(name, type, "olive.app");
    }

    static void settle() throws Exception {
        for (int i = 0; i < 20; i++) {
            ShadowLooper.idleMainLooper(200, java.util.concurrent.TimeUnit.MILLISECONDS);
            Thread.sleep(150);
        }
    }

    static String serverLog() throws Exception {
        InputStream in = new URL("http://" + SERVER + "/__log").openStream();
        Scanner s = new Scanner(in, "UTF-8").useDelimiter("\\A");
        String log = s.hasNext() ? s.next() : "";
        in.close();
        return log;
    }

    static int count(String haystack, String needle) {
        int n = 0;
        for (int i = haystack.indexOf(needle); i >= 0; i = haystack.indexOf(needle, i + 1)) {
            n++;
        }
        return n;
    }

    static void shot(View v, int widthDp, int heightDp, String name) throws Exception {
        OUT.mkdirs();
        float d = RuntimeEnvironment.getApplication().getResources().getDisplayMetrics().density;
        int w = Math.round(widthDp * d);
        int h = Math.round(heightDp * d);
        v.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, w, h);
        Bitmap b = Bitmap.createBitmap(w + 40, h + 40, Bitmap.Config.ARGB_8888);
        b.eraseColor(0xff6d7f99); // a wallpaper-ish backdrop
        Canvas canvas = new Canvas(b);
        canvas.translate(20, 20);
        v.draw(canvas);
        FileOutputStream out = new FileOutputStream(new File(OUT, name + ".png"));
        b.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.close();
    }

    @Before
    public void device() {
        RuntimeEnvironment.getApplication().getSharedPreferences("Olive_Settings", 0).edit().putString("devIP", SERVER).commit();
    }

    @Test
    public void nowPlayingBarShowsAlbumArt() throws Exception {
        Activity tabs = Robolectric.buildActivity(app("LoadTabs").asSubclass(Activity.class)).setup().get();
        ImageView art = (ImageView) tabs.findViewById(id("id", "miniArt"));
        assertNotNull(art);
        // Through the original app's own update method, which now hands the artwork to OliveArt first.
        Object metadata = app("Metadata").getConstructor().newInstance();
        app("Metadata").getMethod("setAlbumart", String.class).invoke(metadata, "http://127.0.0.1/art/0.png");
        app("Metadata").getMethod("setAlbum", String.class).invoke(metadata, "Moon Safari");
        app("Metadata").getMethod("setDctitle", String.class).invoke(metadata, "Sexy Boy");
        app("Metadata").getMethod("setTransportState", String.class).invoke(metadata, "PLAYING");
        app("Metadata").getMethod("setPlayType", String.class).invoke(metadata, "LOCAL");
        app("Metadata").getMethod("setStationTitle", String.class).invoke(metadata, "");
        Method update = app("LoadTabs").getMethod("updateMetadata", app("Metadata"));
        update.invoke(tabs, metadata);
        settle();
        assertTrue("artwork shown", art.getDrawable() instanceof BitmapDrawable);
        assertEquals("artwork fills the tile", 0, art.getPaddingLeft());
        assertEquals("http://127.0.0.1:8765/art/0.png",
                tabs.getSharedPreferences("Olive_Settings", 0).getString("miniArtUrl", ""));
    }

    @Test
    public void widgetShowsNowPlayingAndButtonsWaitForPlayerState() throws Exception {
        Context c = RuntimeEnvironment.getApplication();
        ShadowAppWidgetManager widgets = Shadows.shadowOf(AppWidgetManager.getInstance(c));
        int widgetId = widgets.createWidget(app("OliveWidget").asSubclass(android.appwidget.AppWidgetProvider.class), id("layout", "olive_widget_dyn"));
        int before = count(serverLog(), "sortCrit=+upnp:originalTrackNumber&index=0");

        // A widget play/pause tap starting the service from cold: it learns the player is playing, then pauses.
        Intent toggle = new Intent(c, app("OliveMediaService")).setAction("olive.app.TOGGLE");
        Service service = Robolectric.buildService(app("OliveMediaService").asSubclass(Service.class), toggle).create().startCommand(0, 1).get();
        settle();
        assertEquals("the play/pause toggle was sent once", before + 1, count(serverLog(), "sortCrit=+upnp:originalTrackNumber&index=0"));

        View view = widgets.getViewFor(widgetId);
        TextView title = (TextView) view.findViewById(id("id", "widgetTitle"));
        TextView artist = (TextView) view.findViewById(id("id", "widgetArtist"));
        assertEquals("Sexy Boy", title.getText().toString());
        assertEquals("Air • Moon Safari", artist.getText().toString());
        assertTrue("artwork saved for the widget", new File(c.getFilesDir(), "widget_art.png").exists());
        shot(view, 340, 72, "30-widget-4x1");

        View large = android.view.LayoutInflater.from(c).inflate(id("layout", "olive_widget_large_dyn"), null);
        android.widget.RemoteViews remote = (android.widget.RemoteViews) buildLarge(c);
        remote.reapply(c, large);
        shot(large, 340, 190, "31-widget-4x2");
        service.onDestroy();
    }

    static Object buildLarge(Context c) throws Exception {
        Method build = app("OliveWidget").getDeclaredMethod("build", Context.class, String.class);
        build.setAccessible(true);
        return build.invoke(null, c, "olive_widget_large");
    }
}
