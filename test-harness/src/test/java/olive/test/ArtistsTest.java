package olive.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.robolectric.shadows.ShadowLooper;

/** The Artists tab: one row per artist, opening an artist's albums, then an album's tracks. */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34, qualifiers = "w411dp-h891dp-xhdpi")
@LooperMode(LooperMode.Mode.PAUSED)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class ArtistsTest {
    static final String SERVER = "127.0.0.1:8765";
    static final File OUT = new File(System.getProperty("shots.dir", "shots"));

    static Context app() {
        return RuntimeEnvironment.getApplication();
    }

    static int id(String name) {
        return app().getResources().getIdentifier(name, "id", "olive.app");
    }

    static void settle() throws Exception {
        for (int i = 0; i < 25; i++) {
            ShadowLooper.idleMainLooper(100, java.util.concurrent.TimeUnit.MILLISECONDS);
            Thread.sleep(120);
        }
    }

    static String serverLog() throws Exception {
        InputStream in = new URL("http://" + SERVER + "/__log").openStream();
        Scanner s = new Scanner(in, "UTF-8").useDelimiter("\\A");
        String log = s.hasNext() ? s.next() : "";
        in.close();
        return log;
    }

    static String text(View row, String viewId) {
        TextView t = (TextView) row.findViewById(id(viewId));
        return t.getVisibility() == View.VISIBLE ? t.getText().toString() : "";
    }

    static void shot(Activity a, String name) throws Exception {
        OUT.mkdirs();
        DisplayMetrics dm = app().getResources().getDisplayMetrics();
        View v = a.getWindow().getDecorView();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, dm.widthPixels, dm.heightPixels);
        Bitmap b = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        v.draw(new Canvas(b));
        FileOutputStream out = new FileOutputStream(new File(OUT, name + ".png"));
        b.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.close();
    }

    @Before
    public void device() {
        app().getSharedPreferences("Olive_Settings", 0).edit().putString("devIP", SERVER).commit();
    }

    @Test
    public void groupingIsCaseInsensitiveAndKeepsOrder() throws Exception {
        Method group = Class.forName("olive.app.OliveArtists").getDeclaredMethod("group",
                List.class, List.class, List.class, List.class, String.class);
        group.setAccessible(true);
        List<?> artists = (List<?>) group.invoke(null,
                Arrays.asList("Air", "AIR ", "Bonobo", ""),
                Arrays.asList("Moon Safari", "Talkie Walkie", "Black Sands", "Mystery"),
                Arrays.asList("a1", "a2", "b1", "x1"),
                Arrays.asList("http://127.0.0.1/art/1.png", "", "", ""), "10.50.10.5");
        assertEquals(3, artists.size());
        Object air = artists.get(0);
        assertEquals("Air", field(air, "name"));
        assertEquals(Arrays.asList("a1", "a2"), field(air, "ids"));
        assertEquals("artwork address points at the Olive", "http://10.50.10.5/art/1.png", ((List<?>) field(air, "art")).get(0));
        assertEquals("Unknown artist", field(artists.get(2), "name"));
    }

    @Test
    public void oneRowPerArtistAcrossAllPages() throws Exception {
        Activity tab = Robolectric.buildActivity(Class.forName("olive.app.ArtistTab").asSubclass(Activity.class)).setup().get();
        settle();
        assertTrue("the second page was fetched automatically", serverLog().contains("searchCriteria=artist&xml=true&startIndex=64"));
        ListView list = (ListView) tab.findViewById(id("ListViewArtists"));
        ListAdapter adapter = list.getAdapter();
        // 70 albums by 26 artists (6 named + 20 session-player groups of 3).
        assertEquals(26, adapter.getCount());
        View air = adapter.getView(0, null, list);
        assertEquals("Air", text(air, "artist"));
        assertEquals("3 albums", text(air, "album"));
        View fleetwood = adapter.getView(3, null, list);
        assertEquals("Fleetwood Mac", text(fleetwood, "artist"));
        assertEquals("single album shows its title", "Rumours", text(fleetwood, "album"));
        shot(tab, "50-artists-grouped");
    }

    @Test
    public void tappingAnArtistShowsTheirAlbumsThenTracks() throws Exception {
        Activity tab = Robolectric.buildActivity(Class.forName("olive.app.ArtistTab").asSubclass(Activity.class)).setup().get();
        settle();
        ListView list = (ListView) tab.findViewById(id("ListViewArtists"));
        list.performItemClick(list.getAdapter().getView(0, null, list), 0, 0);
        Intent toArtist = Shadows.shadowOf(tab).getNextStartedActivity();
        assertNotNull(toArtist);
        assertEquals("olive.app.OliveArtistActivity", toArtist.getComponent().getClassName());

        Activity artist = Robolectric.buildActivity(Class.forName("olive.app.OliveArtistActivity").asSubclass(Activity.class), toArtist).setup().get();
        settle();
        assertEquals("Air", ((TextView) artist.findViewById(id("ListViewTitle"))).getText().toString());
        ListView albums = (ListView) artist.findViewById(id("ListView"));
        assertEquals(3, albums.getAdapter().getCount());
        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            titles.add(text(albums.getAdapter().getView(i, null, albums), "album"));
        }
        assertEquals(Arrays.asList("Moon Safari", "Talkie Walkie", "The Virgin Suicides"), titles);
        shot(artist, "51-artist-albums");

        albums.performItemClick(albums.getAdapter().getView(1, null, albums), 1, 1);
        Intent toTracks = Shadows.shadowOf(artist).getNextStartedActivity();
        assertEquals("olive.app.TrackListTab", toTracks.getComponent().getClassName());
        assertEquals("obj1", toTracks.getStringExtra("objId"));
        assertEquals("Talkie Walkie", toTracks.getStringExtra("albumTitle"));
    }

    static Object field(Object target, String name) throws Exception {
        java.lang.reflect.Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }
}
