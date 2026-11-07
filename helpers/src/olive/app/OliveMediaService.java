package olive.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Publishes what the Olive is playing as a media session, so Android shows it in the lock-screen and
 * quick-settings media player with artwork, title/artist and play/pause/skip controls, and the phone's
 * volume keys control the Olive's volume. Commands are the same requests the original player screen sends.
 */
public class OliveMediaService extends Service {
    private static final String CHANNEL = "olive_playback";
    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_PREVIOUS = "olive.app.PREVIOUS";
    private static final String ACTION_TOGGLE = "olive.app.TOGGLE";
    private static final String ACTION_NEXT = "olive.app.NEXT";
    /** Stop publishing after the player has been stopped this long with no app screen open. */
    private static final long IDLE_STOP_MS = 10 * 60 * 1000;

    static volatile boolean appVisible;

    private final Handler main = new Handler(Looper.getMainLooper());
    private final ExecutorService io = Executors.newSingleThreadExecutor();
    private MediaSession session;
    private MediaSession.Callback callback;
    private boolean haveState;
    private String pendingAction;
    private String widgetArtUrl;
    private VolumeProvider volume;
    // What was last published to the session (also lets tests check it without a live system service).
    private MediaMetadata lastMetadata;
    private PlaybackState lastState;
    private BroadcastReceiver screenOn;

    // Last known player state (main thread).
    private String devIP;
    private String transport = "STOPPED";
    private String objId = "";
    private String title = "";
    private String artist = "";
    private String album = "";
    private String artUrl = "";
    private Bitmap art;
    private long durationMs;
    private long positionMs;
    private int volumePercent = -1;
    private long stoppedSince;

    private final Runnable poll = new Runnable() {
        public void run() {
            fetch();
        }
    };

    /** Starts (or refreshes) the service while an app screen is in the foreground. */
    public static void ensureRunning(Activity activity) {
        String ip = activity.getSharedPreferences("Olive_Settings", 0).getString("devIP", "none");
        if (ip == null || ip.equals("none") || ip.length() == 0) {
            return;
        }
        try {
            Intent intent = new Intent(activity, OliveMediaService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                activity.startForegroundService(intent);
            } else {
                activity.startService(intent);
            }
        } catch (RuntimeException e) {
            // Background-start restrictions or a stopped process: it starts next time a screen opens.
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL, "Now playing", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("Playback controls for your Olive");
            channel.setShowBadge(false);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);
        }
        session = new MediaSession(this, "Olive");
        callback = new MediaSession.Callback() {
            public void onPlay() {
                command(isStopped() ? "controlPlayer&root=playItem&upnpid=" + objId : (isPaused() ? TOGGLE : null), "PLAYING");
            }

            public void onPause() {
                command(isPlaying() ? TOGGLE : null, "PAUSED_PLAYBACK");
            }

            public void onSkipToNext() {
                command("right_skip", null);
            }

            public void onSkipToPrevious() {
                command("left_skip", null);
            }

            public void onStop() {
                command("controlPlayer&id=stop", "STOPPED");
            }
        };
        session.setCallback(callback);
        session.setSessionActivity(openPlayer());
        session.setActive(true);
        screenOn = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                pollSoon(0);
            }
        };
        registerReceiver(screenOn, new IntentFilter(Intent.ACTION_SCREEN_ON));
        startInForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startInForeground();
        String action = intent == null ? null : intent.getAction();
        if (action != null && !haveState) {
            // Started by a widget button: learn whether the Olive is playing before acting on play/pause.
            pendingAction = action;
            pollSoon(0);
        } else {
            perform(action);
        }
        // Not sticky: a restart from the background may not start a foreground service on newer Android.
        return START_NOT_STICKY;
    }

    private void perform(String action) {
        // Straight to the session's callback: the same handlers the lock screen and media buttons reach.
        if (ACTION_PREVIOUS.equals(action)) {
            callback.onSkipToPrevious();
        } else if (ACTION_NEXT.equals(action)) {
            callback.onSkipToNext();
        } else if (ACTION_TOGGLE.equals(action)) {
            if (isPlaying()) {
                callback.onPause();
            } else {
                callback.onPlay();
            }
        } else {
            pollSoon(0);
        }
    }

    @Override
    public void onDestroy() {
        main.removeCallbacksAndMessages(null);
        io.shutdownNow();
        try {
            unregisterReceiver(screenOn);
        } catch (RuntimeException ignored) {
        }
        session.setActive(false);
        session.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String TOGGLE = "controlPlayer&root=null&upnpid=null&sortCrit=+upnp:originalTrackNumber&index=0";

    private boolean isPlaying() {
        return "PLAYING".equals(transport);
    }

    private boolean isPaused() {
        return "PAUSED_PLAYBACK".equals(transport);
    }

    private boolean isStopped() {
        return !isPlaying() && !isPaused();
    }

    /** Sends one of the Olive's a_executeOperation actions, updates the shown state optimistically, then re-polls. */
    private void command(final String action, String expectedState) {
        if (action == null || devIP == null) {
            return;
        }
        if (expectedState != null) {
            transport = expectedState;
            publish();
        }
        final String url = "http://" + devIP + "/includes/ajax/a_executeOperation.php?action=" + action;
        io.execute(new Runnable() {
            public void run() {
                read(url, 4000);
            }
        });
        pollSoon(700);
    }

    private void pollSoon(long delayMs) {
        main.removeCallbacks(poll);
        main.postDelayed(poll, delayMs);
    }

    private void fetch() {
        SharedPreferences prefs = getSharedPreferences("Olive_Settings", 0);
        final String ip = prefs.getString("devIP", "none");
        if (ip == null || ip.equals("none")) {
            stopSelf();
            return;
        }
        devIP = ip;
        io.execute(new Runnable() {
            public void run() {
                final String xml = read("http://" + ip + "/server/getMetadata.php", 5000);
                Bitmap newArt = null;
                String newArtUrl = null;
                if (xml != null) {
                    newArtUrl = tag(xml, "albumart").replace("127.0.0.1", ip);
                    if (newArtUrl.length() > 0 && !newArtUrl.equals(artUrl)) {
                        newArt = loadArt(newArtUrl);
                    }
                }
                final Bitmap fetchedArt = newArt;
                final String fetchedArtUrl = newArtUrl;
                main.post(new Runnable() {
                    public void run() {
                        if (xml != null) {
                            apply(xml, fetchedArtUrl, fetchedArt);
                        }
                        scheduleNext(xml != null);
                    }
                });
            }
        });
    }

    private void apply(String xml, String newArtUrl, Bitmap newArt) {
        transport = tag(xml, "transportState");
        objId = tag(xml, "objId");
        boolean radio = "INTERNET_RADIO".equals(tag(xml, "PlayType")) && tag(xml, "stationTitle").length() > 0;
        title = tag(xml, "dctitle");
        artist = radio ? tag(xml, "stationTitle") : tag(xml, "artist");
        album = radio ? "" : tag(xml, "album");
        durationMs = radio ? 0 : parseTime(tag(xml, "currentTrackDuration"));
        positionMs = parseTime(tag(xml, "relativeTimePosition"));
        if (newArtUrl != null && !newArtUrl.equals(artUrl)) {
            artUrl = newArtUrl;
            art = newArt;
        }
        try {
            volumePercent = Integer.parseInt(tag(xml, "volVal").trim());
        } catch (NumberFormatException e) {
            volumePercent = -1;
        }
        haveState = true;
        publish();
        if (pendingAction != null) {
            String action = pendingAction;
            pendingAction = null;
            perform(action);
        }
    }

    private void scheduleNext(boolean reachable) {
        boolean visibleSoon = ((PowerManager) getSystemService(POWER_SERVICE)).isInteractive();
        if (!reachable && !appVisible) {
            // Olive out of reach (e.g. away from home without a VPN): don't leave an empty player behind.
            stopSelf();
            return;
        }
        if (isStopped() && !appVisible) {
            if (stoppedSince == 0) {
                stoppedSince = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - stoppedSince > IDLE_STOP_MS) {
                stopSelf();
                return;
            }
        } else {
            stoppedSince = 0;
        }
        long delay = !reachable ? 15000 : !visibleSoon ? 30000 : isPlaying() ? 3000 : 6000;
        pollSoon(delay);
    }

    /** Pushes the current state to the media session (what the lock screen shows) and the notification. */
    private void publish() {
        MediaMetadata.Builder meta = new MediaMetadata.Builder()
                .putString(MediaMetadata.METADATA_KEY_TITLE, title.length() > 0 ? title : "Olive")
                .putString(MediaMetadata.METADATA_KEY_ARTIST, artist)
                .putString(MediaMetadata.METADATA_KEY_ALBUM, album)
                .putLong(MediaMetadata.METADATA_KEY_DURATION, durationMs > 0 ? durationMs : -1);
        if (art != null) {
            meta.putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, art);
        }
        lastMetadata = meta.build();
        session.setMetadata(lastMetadata);
        int state = isPlaying() ? PlaybackState.STATE_PLAYING : isPaused() ? PlaybackState.STATE_PAUSED : PlaybackState.STATE_STOPPED;
        long actions = PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PAUSE | PlaybackState.ACTION_PLAY_PAUSE
                | PlaybackState.ACTION_SKIP_TO_NEXT | PlaybackState.ACTION_SKIP_TO_PREVIOUS | PlaybackState.ACTION_STOP;
        lastState = new PlaybackState.Builder()
                .setActions(actions)
                .setState(state, positionMs, isPlaying() ? 1f : 0f)
                .build();
        session.setPlaybackState(lastState);
        updateVolumeProvider();
        boolean artChanged = widgetArtUrl == null || !widgetArtUrl.equals(artUrl);
        widgetArtUrl = artUrl;
        OliveWidget.update(this, title, album.length() > 0 ? artist + " \u2022 " + album : artist, isPlaying(), art, artChanged);
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NOTIFICATION_ID, buildNotification());
    }

    /** Hardware volume keys (also on the lock screen) adjust the Olive's volume. */
    private void updateVolumeProvider() {
        if (volumePercent < 0) {
            return;
        }
        if (volume == null) {
            volume = new VolumeProvider(VolumeProvider.VOLUME_CONTROL_ABSOLUTE, 100, volumePercent) {
                public void onSetVolumeTo(int value) {
                    setOliveVolume(value);
                }

                public void onAdjustVolume(int direction) {
                    setOliveVolume(getCurrentVolume() + direction * 5);
                }
            };
            session.setPlaybackToRemote(volume);
        } else if (volume.getCurrentVolume() != volumePercent) {
            volume.setCurrentVolume(volumePercent);
        }
    }

    private void setOliveVolume(int value) {
        int v = Math.max(0, Math.min(100, value));
        volumePercent = v;
        volume.setCurrentVolume(v);
        command("volumeSet&volume=" + v + "&mutestat=1", null);
    }

    private void startInForeground() {
        // Called for every start request: each startForegroundService() must be answered with startForeground().
        Notification notification = buildNotification();
        if (Build.VERSION.SDK_INT >= 29) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
        } else {
            startForeground(NOTIFICATION_ID, notification);
        }
    }

    private Notification buildNotification() {
        Notification.Builder b = Build.VERSION.SDK_INT >= 26 ? new Notification.Builder(this, CHANNEL) : new Notification.Builder(this);
        int icon = getResources().getIdentifier("ic_notif", "drawable", getPackageName());
        b.setSmallIcon(icon != 0 ? icon : android.R.drawable.ic_media_play)
                .setContentTitle(title.length() > 0 ? title : "Olive")
                .setContentText(album.length() > 0 ? artist + " • " + album : artist)
                .setLargeIcon(art)
                .setContentIntent(openPlayer())
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setShowWhen(false)
                .setOngoing(isPlaying())
                .addAction(new Notification.Action(android.R.drawable.ic_media_previous, "Previous", serviceAction(ACTION_PREVIOUS)))
                .addAction(new Notification.Action(isPlaying() ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play,
                        isPlaying() ? "Pause" : "Play", serviceAction(ACTION_TOGGLE)))
                .addAction(new Notification.Action(android.R.drawable.ic_media_next, "Next", serviceAction(ACTION_NEXT)))
                .setStyle(new Notification.MediaStyle().setMediaSession(session.getSessionToken()).setShowActionsInCompactView(0, 1, 2));
        if (Build.VERSION.SDK_INT >= 31) {
            b.setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE);
        }
        return b.build();
    }

    private PendingIntent serviceAction(String action) {
        Intent intent = new Intent(this, OliveMediaService.class).setAction(action);
        return PendingIntent.getService(this, action.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /** Tapping the player opens the app on the playback screen. */
    private PendingIntent openPlayer() {
        Intent home = new Intent().setClassName(this, "olive.app.LoadTabs").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent player = new Intent().setClassName(this, "olive.app.Playscreen");
        return PendingIntent.getActivities(this, 0, new Intent[] {home, player}, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // --- helpers ---------------------------------------------------------------------------------

    static String read(String url, int timeoutMs) {
        HttpURLConnection c = null;
        try {
            c = (HttpURLConnection) new URL(url).openConnection();
            c.setConnectTimeout(timeoutMs);
            c.setReadTimeout(timeoutMs);
            InputStream in = c.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0 && out.size() < 1 << 20) {
                out.write(buf, 0, n);
            }
            in.close();
            return out.toString("UTF-8");
        } catch (Exception e) {
            return null;
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
    }

    private static Bitmap loadArt(String url) {
        HttpURLConnection c = null;
        try {
            c = (HttpURLConnection) new URL(url).openConnection();
            c.setConnectTimeout(5000);
            c.setReadTimeout(5000);
            Bitmap full = BitmapFactory.decodeStream(c.getInputStream());
            if (full == null) {
                return null;
            }
            int max = Math.max(full.getWidth(), full.getHeight());
            if (max <= 512) {
                return full;
            }
            float s = 512f / max;
            return Bitmap.createScaledBitmap(full, Math.round(full.getWidth() * s), Math.round(full.getHeight() * s), true);
        } catch (Exception e) {
            return null;
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
    }

    /** Text of the first <name>...</name> element (the Olive's metadata XML is flat). */
    static String tag(String xml, String name) {
        Matcher m = Pattern.compile("<" + name + ">([^<]*)</" + name + ">", Pattern.CASE_INSENSITIVE).matcher(xml);
        if (!m.find()) {
            return "";
        }
        return m.group(1).trim().replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">")
                .replace("&quot;", "\"").replace("&apos;", "'").replace("&#39;", "'");
    }

    /** "0:04:58" or "04:58" to milliseconds. */
    static long parseTime(String text) {
        String[] parts = text.trim().split(":");
        long seconds = 0;
        try {
            for (String p : parts) {
                seconds = seconds * 60 + Long.parseLong(p.trim().split("\\.")[0]);
            }
        } catch (NumberFormatException e) {
            return 0;
        }
        return seconds * 1000;
    }
}
