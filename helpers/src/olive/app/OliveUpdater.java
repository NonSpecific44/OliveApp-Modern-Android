package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * In-app updates from the project's GitHub releases. Checks the latest release (at most daily on launch, or
 * from Settings), shows its notes, downloads the APK, verifies its SHA-256 and that it is signed with the same
 * key as the installed app, then hands it to Android's package installer, which asks the user to confirm.
 */
public final class OliveUpdater {
    static String latestReleaseUrl = "https://api.github.com/repos/NonSpecific44/OliveApp-Modern-Android/releases/latest";
    private static final String PREFS = "Olive_Settings";
    private static final long DAY_MS = 24L * 60 * 60 * 1000;
    private static volatile boolean busy;

    private OliveUpdater() {
    }

    static final class Release {
        String version;
        String notes;
        String pageUrl;
        String apkUrl;
        String apkName;
        String shaUrl;
    }

    // --- Versions -------------------------------------------------------------------------------------

    static String installedVersion(Context c) {
        try {
            return c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /** Compares dotted numeric versions ("1.2.10" > "1.2.9"); a leading "v" is ignored. Non-numeric parts count as 0. */
    static int compare(String a, String b) {
        String[] x = a.replaceFirst("^[vV]", "").split("[.\\-+]");
        String[] y = b.replaceFirst("^[vV]", "").split("[.\\-+]");
        for (int i = 0; i < Math.max(x.length, y.length); i++) {
            int p = i < x.length ? number(x[i]) : 0;
            int q = i < y.length ? number(y[i]) : 0;
            if (p != q) {
                return p < q ? -1 : 1;
            }
        }
        return 0;
    }

    private static int number(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** True for plain "x.y.z" releases; older "1.0-modern.N" builds predate in-app updates and are ignored. */
    static boolean isRelease(String version) {
        return version != null && version.replaceFirst("^[vV]", "").matches("\\d+\\.\\d+\\.\\d+");
    }

    // --- Checking ---------------------------------------------------------------------------------------

    /** On launch of the main screen: checks at most once a day, quietly unless an update is found. */
    public static void checkOnLaunch(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFS, 0);
        long last = prefs.getLong("update_last_check", 0);
        if (System.currentTimeMillis() - last < DAY_MS) {
            return;
        }
        prefs.edit().putLong("update_last_check", System.currentTimeMillis()).apply();
        check(activity, false);
    }

    /** Checks now; when manual, also reports "up to date" or errors. */
    public static void check(final Activity activity, final boolean manual) {
        if (busy) {
            return;
        }
        busy = true;
        final Handler main = new Handler(Looper.getMainLooper());
        if (manual) {
            Toast.makeText(activity, "Checking for updates…", Toast.LENGTH_SHORT).show();
        }
        new Thread(new Runnable() {
            public void run() {
                final Release release = fetchLatest();
                main.post(new Runnable() {
                    public void run() {
                        busy = false;
                        if (activity.isFinishing()) {
                            return;
                        }
                        String installed = installedVersion(activity);
                        if (release == null) {
                            if (manual) {
                                Toast.makeText(activity, "Couldn't check for updates. Check your connection.", Toast.LENGTH_LONG).show();
                            }
                        } else if (compare(release.version, installed) > 0 && release.apkUrl != null) {
                            String skipped = activity.getSharedPreferences(PREFS, 0).getString("update_skipped", "");
                            if (manual || !release.version.equals(skipped)) {
                                offer(activity, release, installed);
                            }
                        } else if (manual) {
                            Toast.makeText(activity, "You have the latest version (" + installed + ").", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }

    static Release fetchLatest() {
        HttpURLConnection c = null;
        try {
            c = (HttpURLConnection) new URL(latestReleaseUrl).openConnection();
            c.setConnectTimeout(8000);
            c.setReadTimeout(8000);
            c.setRequestProperty("Accept", "application/vnd.github+json");
            c.setRequestProperty("User-Agent", "OliveApp-modern");
            if (c.getResponseCode() != 200) {
                return null;
            }
            return parse(readAll(c.getInputStream()));
        } catch (Exception e) {
            return null;
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
    }

    /** Reads a GitHub "latest release" response; null unless it's an x.y.z release. */
    static Release parse(String json) throws Exception {
        JSONObject o = new JSONObject(json);
        Release r = new Release();
        r.version = o.optString("tag_name", "").replaceFirst("^[vV]", "");
        if (!isRelease(r.version) || o.optBoolean("draft") || o.optBoolean("prerelease")) {
            return null;
        }
        r.notes = o.optString("body", "");
        r.pageUrl = o.optString("html_url", "");
        JSONArray assets = o.optJSONArray("assets");
        for (int i = 0; assets != null && i < assets.length(); i++) {
            JSONObject a = assets.getJSONObject(i);
            String name = a.optString("name");
            if (name.endsWith(".apk")) {
                r.apkName = name;
                r.apkUrl = a.optString("browser_download_url");
            }
        }
        for (int i = 0; assets != null && i < assets.length(); i++) {
            JSONObject a = assets.getJSONObject(i);
            if (r.apkName != null && a.optString("name").equals(r.apkName + ".sha256")) {
                r.shaUrl = a.optString("browser_download_url");
            }
        }
        return r;
    }

    private static void offer(final Activity activity, final Release release, String installed) {
        String notes = release.notes.replace("\r", "").replaceAll("(?m)^#+\\s*", "").replace("**", "").replace("`", "").trim();
        if (notes.length() > 1500) {
            notes = notes.substring(0, 1500) + "…";
        }
        new AlertDialog.Builder(activity)
                .setTitle("Update available: " + release.version)
                .setMessage("You have " + installed + ".\n\n" + notes)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int which) {
                        startUpdate(activity, release);
                    }
                })
                .setNegativeButton("Later", null)
                .setNeutralButton("Skip this version", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int which) {
                        activity.getSharedPreferences(PREFS, 0).edit().putString("update_skipped", release.version).apply();
                    }
                })
                .show();
    }

    // --- Downloading and installing -------------------------------------------------------------------

    private static void startUpdate(final Activity activity, final Release release) {
        if (Build.VERSION.SDK_INT >= 26 && !activity.getPackageManager().canRequestPackageInstalls()) {
            new AlertDialog.Builder(activity)
                    .setTitle("Allow updates")
                    .setMessage("To install updates, allow Olive to install apps. Turn on \"Allow from this source\", then come back and tap Update again.")
                    .setPositiveButton("Open settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface d, int which) {
                            activity.startActivity(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                    Uri.parse("package:" + activity.getPackageName())));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return;
        }
        LinearLayout box = new LinearLayout(activity);
        int pad = Math.round(24 * activity.getResources().getDisplayMetrics().density);
        box.setPadding(pad, pad / 2, pad, 0);
        final ProgressBar bar = new ProgressBar(activity, null, android.R.attr.progressBarStyleHorizontal);
        bar.setMax(100);
        box.addView(bar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        final AlertDialog progress = new AlertDialog.Builder(activity)
                .setTitle("Downloading " + release.version + "…")
                .setView(box)
                .setCancelable(false)
                .show();
        final Handler main = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            public void run() {
                String error = null;
                File apk = new File(new File(activity.getCacheDir(), "updates"), release.apkName);
                try {
                    apk.getParentFile().mkdirs();
                    download(release.apkUrl, apk, new Progress() {
                        public void update(final int percent) {
                            main.post(new Runnable() {
                                public void run() {
                                    bar.setProgress(percent);
                                }
                            });
                        }
                    });
                    error = verify(activity, apk, release);
                    if (error == null) {
                        install(activity, apk);
                    }
                } catch (Exception e) {
                    error = "The download failed. Check your connection and try again.";
                }
                final String message = error;
                main.post(new Runnable() {
                    public void run() {
                        try {
                            progress.dismiss();
                        } catch (RuntimeException ignored) {
                        }
                        if (message != null && !activity.isFinishing()) {
                            new AlertDialog.Builder(activity).setTitle("Update not installed").setMessage(message)
                                    .setPositiveButton("OK", null).show();
                        }
                    }
                });
            }
        }).start();
    }

    interface Progress {
        void update(int percent);
    }

    static void download(String url, File to, Progress progress) throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        c.setConnectTimeout(10000);
        c.setReadTimeout(20000);
        c.setRequestProperty("User-Agent", "OliveApp-modern");
        c.setInstanceFollowRedirects(true);
        try {
            if (c.getResponseCode() != 200) {
                throw new Exception("HTTP " + c.getResponseCode());
            }
            long total = c.getContentLength();
            InputStream in = c.getInputStream();
            OutputStream out = new FileOutputStream(to);
            byte[] buf = new byte[65536];
            long done = 0;
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
                done += n;
                if (total > 0 && progress != null) {
                    progress.update((int) (done * 100 / total));
                }
            }
            out.close();
            in.close();
        } finally {
            c.disconnect();
        }
    }

    /** Null if the APK matches its published checksum, is this app, newer, and signed by the same key. */
    static String verify(Context context, File apk, Release release) throws Exception {
        if (release.shaUrl == null) {
            return "The release has no checksum, so the download can't be verified.";
        }
        String published = readUrl(release.shaUrl).trim().split("\\s+")[0].toLowerCase();
        if (!published.equals(sha256(apk))) {
            return "The download didn't match its published checksum. Try again later.";
        }
        PackageManager pm = context.getPackageManager();
        int flags = Build.VERSION.SDK_INT >= 28 ? PackageManager.GET_SIGNING_CERTIFICATES : PackageManager.GET_SIGNATURES;
        PackageInfo update = pm.getPackageArchiveInfo(apk.getPath(), flags);
        PackageInfo installed = pm.getPackageInfo(context.getPackageName(), flags);
        if (update == null || !context.getPackageName().equals(update.packageName)) {
            return "The download isn't an Olive app update.";
        }
        if (!sameSigner(signers(update), signers(installed))) {
            return "The update isn't signed with the same key as this app, so it wasn't installed.";
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    private static Signature[] signers(PackageInfo info) {
        if (Build.VERSION.SDK_INT >= 28 && info.signingInfo != null) {
            return info.signingInfo.getApkContentsSigners();
        }
        return info.signatures;
    }

    private static boolean sameSigner(Signature[] a, Signature[] b) {
        if (a == null || b == null || a.length == 0 || a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) {
                return false;
            }
        }
        return true;
    }

    static String sha256(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        InputStream in = new FileInputStream(file);
        byte[] buf = new byte[65536];
        int n;
        while ((n = in.read(buf)) > 0) {
            digest.update(buf, 0, n);
        }
        in.close();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest.digest()) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /** Hands the APK to Android's package installer; Android asks the user to confirm the update. */
    static void install(Context context, File apk) throws Exception {
        PackageInstaller installer = context.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        params.setAppPackageName(context.getPackageName());
        int sessionId = installer.createSession(params);
        PackageInstaller.Session session = installer.openSession(sessionId);
        try {
            OutputStream out = session.openWrite("update.apk", 0, apk.length());
            InputStream in = new FileInputStream(apk);
            byte[] buf = new byte[65536];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
            in.close();
            session.fsync(out);
            out.close();
            Intent result = new Intent(context, OliveUpdateReceiver.class);
            int flags = PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= 31 ? PendingIntent.FLAG_MUTABLE : 0);
            session.commit(PendingIntent.getBroadcast(context, sessionId, result, flags).getIntentSender());
        } finally {
            session.close();
        }
    }

    private static String readUrl(String url) throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        c.setConnectTimeout(8000);
        c.setReadTimeout(8000);
        c.setRequestProperty("User-Agent", "OliveApp-modern");
        try {
            return readAll(c.getInputStream());
        } finally {
            c.disconnect();
        }
    }

    private static String readAll(InputStream in) throws Exception {
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) {
            out.write(buf, 0, n);
        }
        in.close();
        return out.toString("UTF-8");
    }

    // --- Settings screen -----------------------------------------------------------------------------

    /** Shows the installed version and a "Check for updates" row on the Settings screen. */
    public static void attachSettings(final Activity activity) {
        View row = activity.findViewById(OliveUi.id(activity, "updateRow"));
        if (row == null) {
            return;
        }
        TextView version = (TextView) activity.findViewById(OliveUi.id(activity, "updateVersion"));
        if (version != null) {
            version.setText("Version " + installedVersion(activity) + " • Tap to check for updates");
        }
        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                check(activity, true);
            }
        });
    }
}
