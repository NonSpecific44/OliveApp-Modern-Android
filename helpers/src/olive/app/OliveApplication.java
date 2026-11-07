package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Added for modern Android. The original app (written for Android 1.6) performs
 * some HTTP requests directly from click handlers on the UI thread. Since
 * Android 3.0 that throws NetworkOnMainThreadException for apps targeting a
 * newer SDK, so restore the permissive behaviour the app was designed for.
 *
 * Also records any crash and shows it on the next launch, so it can be reported.
 */
public class OliveApplication extends Application {
    private static final String CRASH_FILE = "last_crash.txt";
    private boolean crashChecked;
    private int resumed;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

        final Thread.UncaughtExceptionHandler previous = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable error) {
                try {
                    StringWriter trace = new StringWriter();
                    error.printStackTrace(new PrintWriter(trace));
                    String report = "Olive " + versionName() + " on Android " + Build.VERSION.RELEASE
                            + " (API " + Build.VERSION.SDK_INT + "), " + Build.MANUFACTURER + " " + Build.MODEL
                            + "\nThread: " + thread.getName() + "\n\n" + trace;
                    FileOutputStream out = new FileOutputStream(new File(getFilesDir(), CRASH_FILE));
                    out.write(report.getBytes("UTF-8"));
                    out.close();
                } catch (Throwable ignored) {
                }
                if (previous != null) {
                    previous.uncaughtException(thread, error);
                }
            }
        });

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            public void onActivityResumed(Activity activity) {
                resumed++;
                OliveTheme.resumed(activity);
                OliveMediaService.appVisible = true;
                OliveMediaService.ensureRunning(activity);
                OliveUi.attach(activity);
                if (activity.getParent() == null && "olive.app.LoadTabs".equals(activity.getClass().getName())) {
                    OliveUpdater.checkOnLaunch(activity);
                }
                Compat.animateLists(activity);
                if (!crashChecked) {
                    crashChecked = true;
                    showLastCrash(activity);
                }
            }

            public void onActivityCreated(Activity activity, Bundle state) {
                OliveTheme.apply(activity);
                OliveUi.orientationPolicy(activity);
            }

            public void onActivityStarted(Activity activity) {
            }

            public void onActivityPaused(Activity activity) {
                resumed = Math.max(0, resumed - 1);
                OliveMediaService.appVisible = resumed > 0;
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle state) {
            }

            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    private String versionName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            return "?";
        }
    }

    private void showLastCrash(Activity activity) {
        File file = new File(getFilesDir(), CRASH_FILE);
        if (!file.exists()) {
            return;
        }
        final String report;
        try {
            FileInputStream in = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            int read = 0;
            while (read < data.length) {
                int n = in.read(data, read, data.length - read);
                if (n < 0) {
                    break;
                }
                read += n;
            }
            in.close();
            report = new String(data, 0, read, "UTF-8");
        } catch (Exception e) {
            return;
        } finally {
            file.delete();
        }
        final Context app = getApplicationContext();
        try {
            new AlertDialog.Builder(activity)
                    .setTitle("Olive closed unexpectedly last time")
                    .setMessage("Tap Copy and paste this report to whoever is fixing the app:\n\n"
                            + (report.length() > 3000 ? report.substring(0, 3000) + "\n…" : report))
                    .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ClipboardManager clipboard = (ClipboardManager) app.getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setPrimaryClip(ClipData.newPlainText("Olive crash report", report));
                            Toast.makeText(app, "Crash report copied", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Close", null)
                    .show();
        } catch (RuntimeException ignored) {
        }
    }
}
