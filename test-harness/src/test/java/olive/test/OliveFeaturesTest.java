package olive.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
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

/** Network scan across subnets (device picker) and the lock-screen media session. */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
@LooperMode(LooperMode.Mode.PAUSED)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class OliveFeaturesTest {
    static final String SERVER = "127.0.0.1:8765";

    static Class<?> app(String name) throws Exception {
        return Class.forName("olive.app." + name);
    }

    static Object call(String cls, String method, Class<?>[] types, Object... args) throws Exception {
        Method m = app(cls).getDeclaredMethod(method, types);
        m.setAccessible(true);
        return m.invoke(null, args);
    }

    static void settle() throws Exception {
        for (int i = 0; i < 20; i++) {
            ShadowLooper.idleMainLooper(200, java.util.concurrent.TimeUnit.MILLISECONDS);
            Thread.sleep(150);
        }
    }

    static Object field(Object target, String name) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }

    static String serverLog() throws Exception {
        InputStream in = new URL("http://" + SERVER + "/__log").openStream();
        Scanner s = new Scanner(in, "UTF-8").useDelimiter("\\A");
        String log = s.hasNext() ? s.next() : "";
        in.close();
        return log;
    }

    // --- Scanning -------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    static List<String> hosts(String subnet) throws Exception {
        return (List<String>) call("OliveNet", "hostsIn", new Class<?>[] {String.class}, subnet);
    }

    @Test
    public void subnetNotations() throws Exception {
        List<String> h = hosts("10.50.10.0/24");
        assertEquals(254, h.size());
        assertEquals("10.50.10.1", h.get(0));
        assertEquals("10.50.10.254", h.get(253));
        assertEquals(h, hosts("10.50.10.*"));
        assertEquals(h, hosts("10.50.10"));
        assertEquals(h, hosts("10.50.10."));
        assertEquals(h, hosts("10.50.10.77"));
        assertEquals(1022, hosts("10.50.8.0/22").size());
        assertEquals(2, hosts("10.50.10.4/30").size());
        assertNull("too big", hosts("10.50.0.0/16"));
        assertNull(hosts("olive.local"));
        assertNull(hosts("10.50.300.0/24"));
        assertEquals("10.50.10.0/24", call("OliveNet", "subnetOf", new Class<?>[] {String.class}, "10.50.10.7"));
        assertEquals("10.50.10.0/24", call("OliveNet", "subnetOf", new Class<?>[] {String.class}, "10.50.10.7:80"));
        assertNull(call("OliveNet", "subnetOf", new Class<?>[] {String.class}, "olive.tailnet.ts.net"));
    }

    @Test
    public void probeIdentifiesAnOliveAndItsName() throws Exception {
        Class<?>[] t = {String.class, int.class, int.class, int.class};
        assertEquals("Olive 4HD", call("OliveNet", "probe", t, "127.0.0.1", 8765, 1900, 400));
        assertNull("nothing listening", call("OliveNet", "probe", t, "127.0.0.1", 9, 1900, 400));
    }

    @Test
    public void scanResultAppearsInPickerAndConnects() throws Exception {
        Context c = RuntimeEnvironment.getApplication();
        Activity picker = Robolectric.buildActivity(app("LoadDevices").asSubclass(Activity.class)).setup().get();
        settle();
        View scanButton = picker.findViewById(c.getResources().getIdentifier("scanNetwork", "id", "olive.app"));
        assertEquals("Scan a network button shown", View.VISIBLE, scanButton.getVisibility());

        call("OliveNet", "addResult", new Class<?>[] {Activity.class, String.class, String.class}, picker, "Olive 4HD", "10.50.10.5");
        call("OliveNet", "addResult", new Class<?>[] {Activity.class, String.class, String.class}, picker, "Olive 4HD", "10.50.10.5");
        ListView list = (ListView) picker.findViewById(c.getResources().getIdentifier("ListViewDevices", "id", "olive.app"));
        assertEquals("added once", 1, list.getAdapter().getCount());
        assertEquals("Olive 4HD (10.50.10.5)", list.getAdapter().getItem(0));

        // The original picker's own click handler parses "Name (address)" and saves the address.
        View row = list.getAdapter().getView(0, null, list);
        list.performItemClick(row, 0, 0);
        assertEquals("10.50.10.5", c.getSharedPreferences("Olive_Settings", 0).getString("devIP", "none"));
    }

    // --- Lock-screen media session ----------------------------------------------------------------

    @Test
    public void lockScreenSessionShowsNowPlayingAndSendsOriginalCommands() throws Exception {
        Context c = RuntimeEnvironment.getApplication();
        c.getSharedPreferences("Olive_Settings", 0).edit().putString("devIP", SERVER).commit();
        Service service = Robolectric.buildService(app("OliveMediaService").asSubclass(Service.class)).create().startCommand(0, 1).get();
        settle();

        // Robolectric doesn't relay a session to MediaControllers, so read what the service published to it.
        MediaMetadata meta = (MediaMetadata) field(service, "lastMetadata");
        PlaybackState state = (PlaybackState) field(service, "lastState");
        assertNotNull("metadata published", meta);
        assertEquals("Sexy Boy", meta.getString(MediaMetadata.METADATA_KEY_TITLE));
        assertEquals("Air", meta.getString(MediaMetadata.METADATA_KEY_ARTIST));
        assertEquals("Moon Safari", meta.getString(MediaMetadata.METADATA_KEY_ALBUM));
        assertEquals(298000, meta.getLong(MediaMetadata.METADATA_KEY_DURATION));
        assertNotNull("artwork loaded", meta.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART));
        assertEquals(PlaybackState.STATE_PLAYING, state.getState());
        assertEquals(113000, state.getPosition());
        assertTrue("play/pause/skip offered", (state.getActions() & (PlaybackState.ACTION_PLAY_PAUSE
                | PlaybackState.ACTION_SKIP_TO_NEXT | PlaybackState.ACTION_SKIP_TO_PREVIOUS)) != 0);
        android.media.VolumeProvider volume = (android.media.VolumeProvider) field(service, "volume");
        assertNotNull("volume keys control the Olive (remote volume)", volume);
        assertEquals(45, volume.getCurrentVolume());

        NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        List<Notification> posted = Shadows.shadowOf(nm).getAllNotifications();
        assertTrue("media notification posted", !posted.isEmpty());
        Notification n = posted.get(posted.size() - 1);
        assertTrue("media-style notification linked to the session", n.extras.containsKey(Notification.EXTRA_MEDIA_SESSION));
        assertEquals("Sexy Boy", n.extras.getCharSequence(Notification.EXTRA_TITLE).toString());

        // The same callbacks the lock screen, quick settings and headset buttons invoke.
        MediaSession.Callback controls = (MediaSession.Callback) field(service, "callback");
        controls.onPause();
        controls.onSkipToNext();
        controls.onSkipToPrevious();
        volume.onSetVolumeTo(30);
        settle();
        String log = serverLog();
        assertTrue("pause sends the original toggle", log.contains(
                "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=null&upnpid=null&sortCrit=+upnp:originalTrackNumber&index=0"));
        assertTrue("next", log.contains("action=right_skip"));
        assertTrue("previous", log.contains("action=left_skip"));
        assertTrue("volume", log.contains("action=volumeSet&volume=30&mutestat=1"));
        service.onDestroy();
    }

    @Test
    public void parsesOliveTimes() throws Exception {
        Class<?>[] t = {String.class};
        assertEquals(298000L, call("OliveMediaService", "parseTime", t, "0:04:58"));
        assertEquals(298000L, call("OliveMediaService", "parseTime", t, "04:58"));
        assertEquals(0L, call("OliveMediaService", "parseTime", t, ""));
        assertEquals("Fleetwood & Mac", call("OliveMediaService", "tag", new Class<?>[] {String.class, String.class},
                "<x><artist> Fleetwood &amp; Mac </artist></x>", "artist"));
    }
}
