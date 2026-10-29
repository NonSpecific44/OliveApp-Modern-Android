package olive.test;

import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
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
import org.robolectric.shadows.ShadowWifiInfo;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34, qualifiers = "w411dp-h891dp-xhdpi")
@LooperMode(LooperMode.Mode.PAUSED)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class ScreenshotTest {
    static final File OUT = new File(System.getProperty("shots.dir", "shots"));
    static final String SERVER = System.getProperty("olive.server", "127.0.0.1:8765");

    @SuppressWarnings("unchecked")
    static Class<? extends Activity> cls(String n) throws Exception {
        return (Class<? extends Activity>) Class.forName("olive.app." + n);
    }

    static void settle() throws Exception {
        for (int i = 0; i < 12; i++) {
            ShadowLooper.idleMainLooper(100, java.util.concurrent.TimeUnit.MILLISECONDS);
            Thread.sleep(150);
        }
        ShadowLooper.idleMainLooper();
    }

    static void dump(View v, String indent, StringBuilder sb) {
        sb.append(indent).append(v.getClass().getSimpleName()).append(' ').append(v.getWidth()).append('x').append(v.getHeight())
          .append(" vis=").append(v.getVisibility());
        if (v instanceof android.widget.TextView) sb.append(" '").append(((android.widget.TextView) v).getText()).append("'");
        if (v instanceof android.widget.AdapterView) sb.append(" count=").append(((android.widget.AdapterView<?>) v).getCount());
        sb.append('\n');
        if (v instanceof android.view.ViewGroup) {
            android.view.ViewGroup g = (android.view.ViewGroup) v;
            for (int i = 0; i < g.getChildCount() && indent.length() < 40; i++) dump(g.getChildAt(i), indent + "  ", sb);
        }
    }

    static void shot(View v, String name) throws Exception { shot(v, name, true); }

    static void shot(View v, String name, boolean full) throws Exception {
        OUT.mkdirs();
        int mode = full ? View.MeasureSpec.EXACTLY : View.MeasureSpec.AT_MOST;
        v.measure(View.MeasureSpec.makeMeasureSpec(822, full ? View.MeasureSpec.EXACTLY : View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(1782, mode));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        b.eraseColor(0xff808080);
        v.draw(new Canvas(b));
        FileOutputStream out = new FileOutputStream(new File(OUT, name + ".png"));
        b.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.close();
    }

    static void shotDialog(String name) throws Exception {
        Dialog d = ShadowDialog.getLatestDialog();
        if (d != null && d.isShowing()) {
            shot(d.getWindow().getDecorView(), name, false);
        }
    }

    @Before
    public void network() throws Exception {
        if (Boolean.getBoolean("shots.night")) {
            RuntimeEnvironment.setQualifiers("+night");
        }
        Context c = RuntimeEnvironment.getApplication();
        WifiManager wm = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = ShadowWifiInfo.newInstance();
        Shadows.shadowOf(info).setInetAddress(InetAddress.getByName("192.168.1.50"));
        Shadows.shadowOf(wm).setConnectionInfo(info);
    }

    @Test
    public void mainScreens() throws Exception {
        Context c = RuntimeEnvironment.getApplication();
        c.getSharedPreferences("Olive_Settings", 0).edit().putString("devIP", SERVER).commit();
        TabActivity tabs = (TabActivity) Robolectric.buildActivity(cls("LoadTabs")).setup().get();
        settle();
        // Robolectric stubs TabHost, so add the real tab indicator views by hand to preview the shell.
        android.widget.TabWidget widget = tabs.getTabHost().getTabWidget();
        String[] labels = {"Artists", "Albums", "Tracks", "More"};
        java.lang.reflect.Method ind = Class.forName("olive.app.Compat").getMethod("tabIndicator", Activity.class,
                android.widget.TabHost.TabSpec.class, CharSequence.class, android.graphics.drawable.Drawable.class);
        for (String l : labels) {
            android.widget.TabHost.TabSpec spec = tabs.getTabHost().newTabSpec(l);
            ind.invoke(null, tabs, spec, l, null);
            widget.addView(Shadows.shadowOf(spec).getIndicatorAsView());
        }
        widget.getChildTabViewAt(0).setSelected(true);
        ((android.widget.TextView) tabs.findViewById(c.getResources().getIdentifier("screenTitle", "id", "olive.app"))).setText("Artists");
        settle();
        shot(tabs.getWindow().getDecorView(), "00-tabs-shell");
        String[][] screens = {{"ArtistTab", "01-artists"}, {"AlbumTab", "02-albums"}, {"TrackTab", "03-tracks"},
                {"MoreTab", "04-more"}, {"Playscreen", "05-playscreen"}};
        for (String[] sc : screens) {
            Activity a = Robolectric.buildActivity(cls(sc[0])).setup().get();
            settle();
            Dialog d = ShadowDialog.getLatestDialog();
            if (d != null && d.isShowing()) d.dismiss();
            settle();
            shot(a.getWindow().getDecorView(), sc[1]);
            if (sc[0].equals("Playscreen")) {
                View art = a.findViewById(c.getResources().getIdentifier("artwork", "id", "olive.app"));
                org.junit.Assert.assertTrue("artwork is clipped to its rounded outline", art.getClipToOutline());
            }
        }
    }

    @Test
    public void secondaryScreens() throws Exception {
        Context c = RuntimeEnvironment.getApplication();
        c.getSharedPreferences("Olive_Settings", 0).edit().putString("devIP", SERVER).commit();
        String[] names = {"TrackListTab", "PlaylistTab", "SourcesTab", "IRadioTab", "ZonesTab", "SettingsTab", "AddURLTab", "ShowPlaylists", "SearchListTab"};
        for (int i = 0; i < names.length; i++) {
            android.content.Intent in = new android.content.Intent(c, cls(names[i]));
            in.putExtra("objId", "obj0").putExtra("albumTitle", "Moon Safari").putExtra("title", "Moon Safari")
              .putExtra("url", "http://" + SERVER + "/server/iGetCompilationsAndTracks.php?xmlType=noAttribute&id=obj0")
              .putExtra("name", "Moon Safari").putExtra("genre", "Electronic");
            Activity a = Robolectric.buildActivity(cls(names[i]), in).setup().get();
            settle();
            Dialog d = ShadowDialog.getLatestDialog();
            if (d != null && d.isShowing() && d instanceof android.app.ProgressDialog) d.dismiss();
            settle();
            shot(a.getWindow().getDecorView(), String.format("1%d-%s", i, names[i]), !names[i].equals("ShowPlaylists"));
        }
    }

    @Test
    public void firstRunPicker() throws Exception {
        Activity picker = Robolectric.buildActivity(cls("LoadDevices")).setup().get();
        settle();
        shot(picker.getWindow().getDecorView(), "06-device-picker", false);
        shotDialog("06b-device-picker-dialog");
    }

    @Test
    public void noWifi() throws Exception {
        Context c = RuntimeEnvironment.getApplication();
        WifiManager wm = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        Shadows.shadowOf(wm).setConnectionInfo(ShadowWifiInfo.newInstance());
        Activity picker = Robolectric.buildActivity(cls("LoadDevices")).setup().get();
        settle();
        org.junit.Assert.assertNull("no error dialog when not on Wi-Fi", ShadowDialog.getLatestDialog());
        shot(picker.getWindow().getDecorView(), "08-picker-no-wifi", false);
        c.getSharedPreferences("Olive_Settings", 0).edit().putString("devIP", SERVER).commit();
        Activity artists = Robolectric.buildActivity(cls("ArtistTab")).setup().get();
        settle();
        Dialog d = ShadowDialog.getLatestDialog();
        org.junit.Assert.assertTrue("artists load without Wi-Fi", d == null || d instanceof android.app.ProgressDialog);
        android.widget.ListView list = (android.widget.ListView) artists.findViewById(c.getResources().getIdentifier("ListViewArtists", "id", "olive.app"));
        org.junit.Assert.assertTrue("artists listed", list.getAdapter() != null && list.getAdapter().getCount() > 0);
        if (d != null && d.isShowing()) d.dismiss();
        settle();
        shot(artists.getWindow().getDecorView(), "09-artists-no-wifi");
    }

    @Test
    public void search() throws Exception {
        Activity s = Robolectric.buildActivity(cls("SearchEntry")).setup().get();
        settle();
        shot(s.getWindow().getDecorView(), "07-search", false);
    }
}
