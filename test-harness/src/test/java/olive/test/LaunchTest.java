package olive.test;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import java.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;
import org.robolectric.shadows.ShadowLooper;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
@LooperMode(LooperMode.Mode.PAUSED)
public class LaunchTest {
    @SuppressWarnings("unchecked")
    static Class<? extends Activity> cls(String n) throws Exception {
        return (Class<? extends Activity>) Class.forName("olive.app." + n);
    }

    @Test
    public void freshInstallFlow() throws Exception {
        System.out.println("thread policy after app onCreate: " + StrictMode.getThreadPolicy());
        ActivityController<? extends Activity> tabs = Robolectric.buildActivity(cls("LoadTabs")).setup();
        ShadowLooper.idleMainLooper();
        // Follow whatever activity the tabs started (device picker on first run).
        Intent next = Shadows.shadowOf(tabs.get()).getNextStartedActivity();
        System.out.println("started from LoadTabs: " + next);
        if (next != null) {
            ActivityController<? extends Activity> picker = Robolectric.buildActivity(cls("LoadDevices"), next).setup();
            for (int i = 0; i < 5; i++) ShadowLooper.idleMainLooper(3, java.util.concurrent.TimeUnit.SECONDS);
            picker.pause().stop().destroy();
        }
    }

    @Test
    public void savedDeviceFlow() throws Exception {
        android.content.Context c = org.robolectric.RuntimeEnvironment.getApplication();
        c.getSharedPreferences("Olive_Settings", 0).edit().putString("devIP", "127.0.0.1:9").commit();
        Robolectric.buildActivity(cls("LoadTabs")).setup();
        for (int i = 0; i < 5; i++) ShadowLooper.idleMainLooper(3, java.util.concurrent.TimeUnit.SECONDS);
        Robolectric.buildActivity(cls("Playscreen")).setup();
        for (int i = 0; i < 3; i++) ShadowLooper.idleMainLooper(3, java.util.concurrent.TimeUnit.SECONDS);
    }

    @Test
    public void crashReportShownOnNextLaunch() throws Exception {
        android.content.Context c = org.robolectric.RuntimeEnvironment.getApplication();
        java.io.FileOutputStream f = new java.io.FileOutputStream(new java.io.File(c.getFilesDir(), "last_crash.txt"));
        f.write("java.lang.SecurityException: test".getBytes()); f.close();
        Robolectric.buildActivity(cls("LoadTabs")).setup();
        ShadowLooper.idleMainLooper();
        android.app.Dialog d = org.robolectric.shadows.ShadowDialog.getLatestDialog();
        System.out.println("crash dialog shown: " + (d != null && d.isShowing()));
        org.junit.Assert.assertTrue(d != null && d.isShowing());
        org.junit.Assert.assertFalse(new java.io.File(c.getFilesDir(), "last_crash.txt").exists());
    }

    @Test
    public void devicePickerDirect() throws Exception {
        ActivityController<? extends Activity> picker = Robolectric.buildActivity(cls("LoadDevices")).setup();
        for (int i = 0; i < 5; i++) ShadowLooper.idleMainLooper(3, java.util.concurrent.TimeUnit.SECONDS);
        picker.pause().stop().destroy();
    }
}
