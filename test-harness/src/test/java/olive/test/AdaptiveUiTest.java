package olive.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowLooper;

/** Pull-to-refresh, the app bar after the menu clean-up, orientation policy and tablet layouts. */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34, qualifiers = "w411dp-h891dp-xhdpi")
@LooperMode(LooperMode.Mode.PAUSED)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class AdaptiveUiTest {
    static final String SERVER = "127.0.0.1:8765";
    static final File OUT = new File(System.getProperty("shots.dir", "shots"));

    @SuppressWarnings("unchecked")
    static Class<? extends Activity> cls(String n) throws Exception {
        return (Class<? extends Activity>) Class.forName("olive.app." + n);
    }

    static int id(String name) {
        Context c = RuntimeEnvironment.getApplication();
        return c.getResources().getIdentifier(name, "id", "olive.app");
    }

    static void settle() throws Exception {
        for (int i = 0; i < 15; i++) {
            ShadowLooper.idleMainLooper(100, java.util.concurrent.TimeUnit.MILLISECONDS);
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

    static void shot(View v, String name) throws Exception {
        OUT.mkdirs();
        DisplayMetrics dm = RuntimeEnvironment.getApplication().getResources().getDisplayMetrics();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, dm.widthPixels, dm.heightPixels);
        ShadowLooper.idleMainLooper();
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
        RuntimeEnvironment.getApplication().getSharedPreferences("Olive_Settings", 0).edit().putString("devIP", SERVER).commit();
    }

    // --- Menu clean-up --------------------------------------------------------------------------------

    @Test
    public void appBarHasSearchAndShuffleButtonsButNoOverflowMenu() throws Exception {
        Activity tabs = Robolectric.buildActivity(cls("LoadTabs")).setup().get();
        assertNull("no ⋮ button", tabs.findViewById(id("menuButton")));
        assertNotNull(tabs.findViewById(id("searchButton")));
        assertNotNull(tabs.findViewById(id("shuffleButton")));
        // The buttons run the tabs' own menu actions; shuffle is offered where the tab has "Shuffle all".
        Activity albums = Robolectric.buildActivity(cls("AlbumTab")).setup().get();
        android.view.Menu menu = new android.widget.PopupMenu(albums, albums.getWindow().getDecorView()).getMenu();
        albums.onCreateOptionsMenu(menu);
        assertNotNull(menu.findItem(id("shuffleAll")));
        Activity artists = Robolectric.buildActivity(cls("ArtistTab")).setup().get();
        menu = new android.widget.PopupMenu(artists, artists.getWindow().getDecorView()).getMenu();
        artists.onCreateOptionsMenu(menu);
        assertNull(menu.findItem(id("shuffleAll")));
    }

    // --- Pull to refresh --------------------------------------------------------------------------------

    @Test
    public void pullingTheListDownRefreshesItWithoutAModalDialog() throws Exception {
        Activity artists = Robolectric.buildActivity(cls("ArtistTab")).setup().get();
        settle();
        Dialog d = ShadowDialog.getLatestDialog();
        assertTrue("initial load uses the inline indicator, not a dialog", d == null || !d.isShowing());
        ListView list = (ListView) artists.findViewById(id("ListViewArtists"));
        assertTrue("artists loaded", list.getAdapter() != null && list.getAdapter().getCount() > 0);
        View indicator = artists.findViewById(id("pullIndicator"));
        assertEquals("indicator hidden when idle", 0f, indicator.getAlpha(), 0.01f);
        int before = count(serverLog(), "searchCriteria=artist&xml=true&startIndex=0");

        // Drag down from the top of the list and release past the threshold.
        long t = SystemClock.uptimeMillis();
        list.dispatchTouchEvent(MotionEvent.obtain(t, t, MotionEvent.ACTION_DOWN, 300, 200, 0));
        for (int y = 220; y <= 800; y += 40) {
            list.dispatchTouchEvent(MotionEvent.obtain(t, t + y, MotionEvent.ACTION_MOVE, 300, y, 0));
        }
        assertTrue("indicator follows the finger", indicator.getAlpha() > 0.9f);
        list.dispatchTouchEvent(MotionEvent.obtain(t, t + 900, MotionEvent.ACTION_UP, 300, 800, 0));
        settle();

        assertEquals("the original Refresh reload ran", before + 1, count(serverLog(), "searchCriteria=artist&xml=true&startIndex=0"));
        d = ShadowDialog.getLatestDialog();
        assertTrue("no modal progress dialog", d == null || !d.isShowing());
        assertTrue("list reloaded", list.getAdapter() != null && list.getAdapter().getCount() > 0);
        assertEquals("indicator hidden again after loading", 0f, indicator.getAlpha(), 0.01f);
    }

    @Test
    public void shortPullDoesNotRefresh() throws Exception {
        Activity artists = Robolectric.buildActivity(cls("ArtistTab")).setup().get();
        settle();
        ListView list = (ListView) artists.findViewById(id("ListViewArtists"));
        int before = count(serverLog(), "startIndex=0");
        long t = SystemClock.uptimeMillis();
        list.dispatchTouchEvent(MotionEvent.obtain(t, t, MotionEvent.ACTION_DOWN, 300, 200, 0));
        list.dispatchTouchEvent(MotionEvent.obtain(t, t + 50, MotionEvent.ACTION_MOVE, 300, 260, 0));
        list.dispatchTouchEvent(MotionEvent.obtain(t, t + 100, MotionEvent.ACTION_UP, 300, 260, 0));
        settle();
        assertEquals(before, count(serverLog(), "startIndex=0"));
    }

    // --- Orientation --------------------------------------------------------------------------------------

    @Test
    public void phonesStayPortrait() throws Exception {
        Activity tabs = Robolectric.buildActivity(cls("LoadTabs")).setup().get();
        assertEquals(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, tabs.getRequestedOrientation());
    }

    @Test
    @Config(qualifiers = "sw800dp-w1280dp-h800dp-land-xhdpi")
    public void tabletsRotateFreely() throws Exception {
        Activity tabs = Robolectric.buildActivity(cls("LoadTabs")).setup().get();
        assertEquals(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED, tabs.getRequestedOrientation());
        assertNotNull("tablet layout has a navigation rail", tabs.findViewById(id("navRail")));
    }

    // --- Tablet and adaptive screenshots -------------------------------------------------------------------

    static void shell(String name) throws Exception {
        TabActivity tabs = (TabActivity) Robolectric.buildActivity(cls("LoadTabs")).setup().get();
        settle();
        // Robolectric stubs TabHost, so add the real tab indicators by hand (as the app's tabIndicator does).
        android.widget.TabWidget widget = tabs.getTabHost().getTabWidget();
        java.lang.reflect.Method ind = Class.forName("olive.app.Compat").getMethod("tabIndicator", Activity.class,
                android.widget.TabHost.TabSpec.class, CharSequence.class, android.graphics.drawable.Drawable.class);
        for (String l : new String[] {"Artists", "Albums", "Tracks", "More"}) {
            android.widget.TabHost.TabSpec spec = tabs.getTabHost().newTabSpec(l);
            ind.invoke(null, tabs, spec, l, null);
            widget.addView(Shadows.shadowOf(spec).getIndicatorAsView());
        }
        Class.forName("olive.app.Compat").getMethod("buildRail", TabActivity.class).invoke(null, tabs);
        View rail = tabs.findViewById(id("navRail"));
        if (rail != null) {
            ((LinearLayout) rail).getChildAt(1).setSelected(true);
        } else {
            widget.getChildTabViewAt(1).setSelected(true);
        }
        ((android.widget.TextView) tabs.findViewById(id("screenTitle"))).setText("Albums");
        tabs.findViewById(id("shuffleButton")).setVisibility(View.VISIBLE);
        // Show the Albums tab's content in the shell's content area.
        Activity albums = Robolectric.buildActivity(cls("AlbumTab")).setup().get();
        settle();
        View content = albums.getWindow().getDecorView().findViewById(android.R.id.content);
        ((android.view.ViewGroup) content.getParent()).removeView(content);
        android.widget.FrameLayout area = (android.widget.FrameLayout) tabs.findViewById(android.R.id.tabcontent);
        area.addView(content, new android.widget.FrameLayout.LayoutParams(-1, -1));
        settle();
        shot(tabs.getWindow().getDecorView(), name);
    }

    static void player(String name) throws Exception {
        Activity play = Robolectric.buildActivity(cls("Playscreen")).setup().get();
        settle();
        shot(play.getWindow().getDecorView(), name);
        Class.forName("olive.app.OliveUi").getMethod("attach", Activity.class).invoke(null, play);
        settle();
        shot(play.getWindow().getDecorView(), name);
    }

    @Test
    public void phoneScreens() throws Exception {
        shell("20-phone-albums");
        player("21-phone-player");
        LinearLayout panes = (LinearLayout) Robolectric.buildActivity(cls("Playscreen")).setup().get().findViewById(id("adaptivePanes"));
        assertNotNull(panes);
    }

    @Test
    @Config(qualifiers = "sw800dp-w1280dp-h800dp-land-mdpi")
    public void tabletLandscapeScreens() throws Exception {
        shell("22-tablet-land-albums");
        player("23-tablet-land-player");
        Activity play = Robolectric.buildActivity(cls("Playscreen")).setup().get();
        settle();
        Class.forName("olive.app.OliveUi").getMethod("attach", Activity.class).invoke(null, play);
        View decor = play.getWindow().getDecorView();
        decor.measure(View.MeasureSpec.makeMeasureSpec(1280, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.EXACTLY));
        decor.layout(0, 0, 1280, 800);
        settle();
        LinearLayout panes = (LinearLayout) play.findViewById(id("adaptivePanes"));
        assertEquals("side by side when wide", LinearLayout.HORIZONTAL, panes.getOrientation());
    }

    @Test
    @Config(qualifiers = "sw800dp-w800dp-h1280dp-port-mdpi")
    public void tabletPortraitScreens() throws Exception {
        shell("24-tablet-port-albums");
        player("25-tablet-port-player");
        Activity play = Robolectric.buildActivity(cls("Playscreen")).setup().get();
        settle();
        Class.forName("olive.app.OliveUi").getMethod("attach", Activity.class).invoke(null, play);
        View decor = play.getWindow().getDecorView();
        decor.measure(View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1280, View.MeasureSpec.EXACTLY));
        decor.layout(0, 0, 800, 1280);
        settle();
        LinearLayout panes = (LinearLayout) play.findViewById(id("adaptivePanes"));
        assertEquals("stacked when tall", LinearLayout.VERTICAL, panes.getOrientation());
        assertFalse(panes.getOrientation() == LinearLayout.HORIZONTAL);
    }
}
