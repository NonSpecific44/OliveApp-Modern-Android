package olive.app;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class LoadTabs extends TabActivity {
    static boolean reloadArtists = false;
    static boolean reloadAlbums = false;
    static boolean reloadTracks = false;
    private static String devIP = "none";
    String url = EXTHeader.DEFAULT_VALUE;
    AsyncTask<Object, Void, Metadata> myAsync = null;
    TabHost tabHost = null;
    Handler mHandler = new Handler();
    private Runnable pullUpdateMetadata = new Runnable() { // from class: olive.app.LoadTabs.1
        @Override // java.lang.Runnable
        public void run() {
            Object[] data = {"LoadTabs", LoadTabs.this};
            LoadTabs.this.myAsync = new CurrentlyPlaying().execute(data);
        }
    };

    @Override // android.app.ActivityGroup, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadtabs);
        Resources res = getResources();
        this.tabHost = getTabHost();
        Intent intent = new Intent().setClass(this, ArtistTab.class);
        TabHost.TabSpec spec = this.tabHost.newTabSpec("artists").setIndicator("Artists", res.getDrawable(R.drawable.artist)).setContent(intent);
        this.tabHost.addTab(spec);
        Intent intent2 = new Intent().setClass(this, AlbumTab.class);
        TabHost.TabSpec spec2 = this.tabHost.newTabSpec("albums").setIndicator("Albums", res.getDrawable(R.drawable.album)).setContent(intent2);
        this.tabHost.addTab(spec2);
        Intent intent3 = new Intent().setClass(this, TrackTab.class);
        TabHost.TabSpec spec3 = this.tabHost.newTabSpec("tracks").setIndicator("Tracks", res.getDrawable(R.drawable.tracks_t)).setContent(intent3);
        this.tabHost.addTab(spec3);
        Intent intent4 = new Intent().setClass(this, MoreTab.class);
        TabHost.TabSpec spec4 = this.tabHost.newTabSpec("more").setIndicator("More", res.getDrawable(R.drawable.more2)).setContent(intent4);
        this.tabHost.addTab(spec4);
        this.tabHost.setCurrentTab(0);
        findViewById(R.id.playerbar).setOnClickListener(new View.OnClickListener() { // from class: olive.app.LoadTabs.2
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                LoadTabs.this.startActivity(new Intent(LoadTabs.this, (Class<?>) Playscreen.class));
            }
        });
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        devIP = settings.getString("devIP", "none");
        if (devIP != "none") {
            new SendCommand().execute("http://" + devIP + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
            new SendCommand().execute("http://" + devIP + "/includes/ajax/a_executeOperation.php?action=setController&var=iPhone");
            String url_tmp = "http://" + devIP + "/server/getMetadata.php";
            setUrl(url_tmp);
        }
        Object[] data = {"LoadTabs", this};
        this.myAsync = new CurrentlyPlaying().execute(data);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        new SendCommand().execute("http://" + devIP + "/includes/ajax/a_executeOperation.php?action=setController&var=iPhone");
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        devIP = settings.getString("devIP", "none");
        if (reloadArtists) {
            this.tabHost.setCurrentTab(0);
        }
    }

    public static void reload(boolean flag, int type) {
        switch (type) {
            case 0:
                reloadArtists = flag;
                break;
            case 1:
                reloadAlbums = flag;
                break;
            case 2:
                reloadTracks = flag;
                break;
        }
    }

    public static boolean getReload(int type) {
        switch (type) {
            case 0:
                return reloadArtists;
            case 1:
                return reloadAlbums;
            case 2:
                return reloadTracks;
            default:
                return false;
        }
    }

    public LoadTabs getHost() {
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return "http://" + devIP + "/server/getMetadata.php";
    }

    public void updateMetadata(Metadata metadata) {
        if (metadata != null) {
            TextView album = (TextView) findViewById(R.id.playerAlbum);
            TextView title = (TextView) findViewById(R.id.playerTitle);
            SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
            SharedPreferences.Editor prefEditor = settings.edit();
            if (metadata.getAlbum().equals(EXTHeader.DEFAULT_VALUE) && metadata.getTransportState().equals("STOPPED")) {
                album.setText(EXTHeader.DEFAULT_VALUE);
                title.setText("No track currently playing...");
                prefEditor.putString("albumTitle", EXTHeader.DEFAULT_VALUE);
                prefEditor.putString("trackTitle", "No track currently playing...");
            } else if (metadata.getPlayType().equals("INTERNET_RADIO") && !metadata.getStationTitle().equals(EXTHeader.DEFAULT_VALUE)) {
                album.setText(metadata.getStationTitle());
                title.setText(metadata.getDctitle());
                prefEditor.putString("albumTitle", metadata.getStationTitle());
                prefEditor.putString("trackTitle", metadata.getDctitle());
            } else {
                album.setText(metadata.getAlbum());
                title.setText(metadata.getDctitle());
                prefEditor.putString("albumTitle", metadata.getAlbum());
                prefEditor.putString("trackTitle", metadata.getDctitle());
            }
            prefEditor.commit();
        }
        this.mHandler.removeCallbacks(this.pullUpdateMetadata);
        this.mHandler.postDelayed(this.pullUpdateMetadata, 5000L);
    }

    public static void setIP(String newIP) {
        devIP = newIP;
    }

    @Override // android.app.ActivityGroup, android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mHandler.removeCallbacks(this.pullUpdateMetadata);
    }

    @Override // android.app.ActivityGroup, android.app.Activity
    protected void onResume() {
        super.onResume();
        SharedPreferences share = getSharedPreferences("Olive_Settings", 0);
        String albumTitle = share.getString("albumTitle", "none");
        String trackTitle = share.getString("trackTitle", "none");
        if (albumTitle != "none") {
            ((TextView) findViewById(R.id.playerAlbum)).setText(albumTitle);
        }
        if (trackTitle != "none") {
            ((TextView) findViewById(R.id.playerTitle)).setText(trackTitle);
        }
        this.mHandler.removeCallbacks(this.pullUpdateMetadata);
        this.mHandler.postDelayed(this.pullUpdateMetadata, 5000L);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override // android.app.ActivityGroup, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacks(this.pullUpdateMetadata);
        finish();
    }
}
