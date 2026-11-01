package olive.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.widget.TextView;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.shadows.ShadowToast;

/** In-app updates from GitHub releases, against a local stand-in for the GitHub API. */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
@LooperMode(LooperMode.Mode.PAUSED)
public class UpdaterTest {
    private HttpServer github;
    private final Map<String, byte[]> files = new HashMap<String, byte[]>();
    private String base;

    static Class<?> updater() throws Exception {
        return Class.forName("olive.app.OliveUpdater");
    }

    static Object call(String method, Class<?>[] types, Object... args) throws Exception {
        Method m = updater().getDeclaredMethod(method, types);
        m.setAccessible(true);
        return m.invoke(null, args);
    }

    static Object field(Object target, String name) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }

    static void settle() throws Exception {
        for (int i = 0; i < 20; i++) {
            ShadowLooper.idleMainLooper(100, java.util.concurrent.TimeUnit.MILLISECONDS);
            Thread.sleep(100);
        }
    }

    static String hex(byte[] bytes) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (byte b : MessageDigest.getInstance("SHA-256").digest(bytes)) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    String releaseJson(String tag, boolean prerelease) {
        String apk = "OliveApp-modern-" + tag.substring(1) + ".apk";
        return "{\"tag_name\":\"" + tag + "\",\"draft\":false,\"prerelease\":" + prerelease + ","
                + "\"html_url\":\"https://github.com/x/y/releases/tag/" + tag + "\",\"body\":\"### Fixed\\n- **Things** work\","
                + "\"assets\":[{\"name\":\"" + apk + "\",\"browser_download_url\":\"" + base + "/download/" + apk + "\"},"
                + "{\"name\":\"" + apk + ".sha256\",\"browser_download_url\":\"" + base + "/download/" + apk + ".sha256\"}]}";
    }

    @Before
    public void startGithub() throws Exception {
        github = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        github.createContext("/", new HttpHandler() {
            public void handle(HttpExchange ex) throws java.io.IOException {
                byte[] body = files.get(ex.getRequestURI().getPath());
                ex.sendResponseHeaders(body == null ? 404 : 200, body == null ? -1 : body.length);
                if (body != null) {
                    OutputStream out = ex.getResponseBody();
                    out.write(body);
                    out.close();
                }
                ex.close();
            }
        });
        github.start();
        base = "http://127.0.0.1:" + github.getAddress().getPort();
        Field url = updater().getDeclaredField("latestReleaseUrl");
        url.setAccessible(true);
        url.set(null, base + "/releases/latest");
    }

    @After
    public void stopGithub() {
        github.stop(0);
    }

    @Test
    public void versionOrdering() throws Exception {
        Class<?>[] t = {String.class, String.class};
        assertEquals(1, call("compare", t, "1.0.1", "1.0.0"));
        assertEquals(1, call("compare", t, "v1.2.10", "1.2.9"));
        assertEquals(0, call("compare", t, "v1.0.0", "1.0.0"));
        assertEquals(-1, call("compare", t, "1.0.0", "1.1.0"));
        assertTrue((Boolean) call("isRelease", new Class<?>[] {String.class}, "v1.0.0"));
        assertFalse("pre-1.0.0 builds are ignored", (Boolean) call("isRelease", new Class<?>[] {String.class}, "1.0-modern.7"));
    }

    @Test
    public void parsesLatestReleaseAndIgnoresOldOrPrereleaseTags() throws Exception {
        Object r = call("parse", new Class<?>[] {String.class}, releaseJson("v1.2.3", false));
        assertEquals("1.2.3", field(r, "version"));
        assertEquals("OliveApp-modern-1.2.3.apk", field(r, "apkName"));
        assertEquals(base + "/download/OliveApp-modern-1.2.3.apk", field(r, "apkUrl"));
        assertEquals(base + "/download/OliveApp-modern-1.2.3.apk.sha256", field(r, "shaUrl"));
        assertNull(call("parse", new Class<?>[] {String.class}, releaseJson("v1.2.4", true)));
        assertNull(call("parse", new Class<?>[] {String.class},
                "{\"tag_name\":\"v1.0-modern.7\",\"assets\":[]}"));
    }

    @Test
    public void offersANewerReleaseWithItsNotes() throws Exception {
        files.put("/releases/latest", releaseJson("v9.9.9", false).getBytes("UTF-8"));
        Activity a = Robolectric.buildActivity(Activity.class).setup().get();
        call("check", new Class<?>[] {Activity.class, boolean.class}, a, true);
        settle();
        AlertDialog d = ShadowAlertDialog.getLatestAlertDialog();
        assertNotNull("update dialog shown", d);
        assertEquals("Update available: 9.9.9", org.robolectric.Shadows.shadowOf(d).getTitle().toString());
        String message = org.robolectric.Shadows.shadowOf(d).getMessage().toString();
        assertTrue(message.startsWith("You have 1.0.0."));
        assertTrue("notes shown without markdown", message.contains("Fixed") && message.contains("- Things work") && !message.contains("**"));
    }

    @Test
    public void reportsUpToDateWhenLatestIsTheInstalledVersion() throws Exception {
        files.put("/releases/latest", releaseJson("v1.0.0", false).getBytes("UTF-8"));
        Activity a = Robolectric.buildActivity(Activity.class).setup().get();
        call("check", new Class<?>[] {Activity.class, boolean.class}, a, true);
        settle();
        assertEquals("You have the latest version (1.0.0).", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void launchCheckRunsAtMostDailyAndRespectsSkip() throws Exception {
        files.put("/releases/latest", releaseJson("v9.9.9", false).getBytes("UTF-8"));
        Context c = RuntimeEnvironment.getApplication();
        c.getSharedPreferences("Olive_Settings", 0).edit().putString("update_skipped", "9.9.9").commit();
        Activity a = Robolectric.buildActivity(Activity.class).setup().get();
        call("checkOnLaunch", new Class<?>[] {Activity.class}, a);
        settle();
        assertNull("skipped version not offered on launch", ShadowAlertDialog.getLatestAlertDialog());
        long last = c.getSharedPreferences("Olive_Settings", 0).getLong("update_last_check", 0);
        assertTrue(last > 0);
        c.getSharedPreferences("Olive_Settings", 0).edit().remove("update_skipped").commit();
        call("checkOnLaunch", new Class<?>[] {Activity.class}, a);
        settle();
        assertNull("not checked again within a day", ShadowAlertDialog.getLatestAlertDialog());
    }

    @Test
    public void downloadIsVerifiedAgainstItsChecksum() throws Exception {
        byte[] apk = "not really an apk".getBytes("UTF-8");
        files.put("/download/OliveApp-modern-9.9.9.apk", apk);
        files.put("/download/OliveApp-modern-9.9.9.apk.sha256", (hex(apk) + "  OliveApp-modern-9.9.9.apk\n").getBytes("UTF-8"));
        Object release = call("parse", new Class<?>[] {String.class}, releaseJson("v9.9.9", false));
        Context c = RuntimeEnvironment.getApplication();
        File to = new File(c.getCacheDir(), "dl.apk");
        Class<?> progress = Class.forName("olive.app.OliveUpdater$Progress");
        call("download", new Class<?>[] {String.class, File.class, progress}, field(release, "apkUrl"), to, null);
        assertEquals(hex(apk), call("sha256", new Class<?>[] {File.class}, to));
        Class<?> rel = Class.forName("olive.app.OliveUpdater$Release");
        String ok = (String) call("verify", new Class<?>[] {Context.class, File.class, rel}, c, to, release);
        assertFalse("checksum accepted", ok != null && ok.contains("checksum"));
        assertNotNull("but it isn't a real APK of this app", ok);

        FileOutputStream tamper = new FileOutputStream(to, true);
        tamper.write(1);
        tamper.close();
        String bad = (String) call("verify", new Class<?>[] {Context.class, File.class, rel}, c, to, release);
        assertEquals("The download didn't match its published checksum. Try again later.", bad);
    }

    @Test
    public void installHandsTheApkToThePackageInstaller() throws Exception {
        Context c = RuntimeEnvironment.getApplication();
        File apk = new File(c.getCacheDir(), "update.apk");
        FileOutputStream out = new FileOutputStream(apk);
        out.write(new byte[1024]);
        out.close();
        call("install", new Class<?>[] {Context.class, File.class}, c, apk);
        PackageInstaller installer = c.getPackageManager().getPackageInstaller();
        assertEquals("one install session committed", 1, installer.getAllSessions().size());
        assertEquals("olive.app", installer.getAllSessions().get(0).getAppPackageName());
    }

    @Test
    public void settingsShowsVersionAndChecks() throws Exception {
        Activity settings = Robolectric.buildActivity(Class.forName("olive.app.SettingsTab").asSubclass(Activity.class)).setup().get();
        call("attachSettings", new Class<?>[] {Activity.class}, settings);
        int id = c(settings).getResources().getIdentifier("updateVersion", "id", "olive.app");
        assertEquals("Version 1.0.0 • Tap to check for updates", ((TextView) settings.findViewById(id)).getText().toString());
        assertNotEquals(0, c(settings).getResources().getIdentifier("updateRow", "id", "olive.app"));
    }

    static Context c(Activity a) {
        return a;
    }
}
