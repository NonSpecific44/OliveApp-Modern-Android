package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class TrackListTab extends Activity {
    TrackAdapter adapter;
    private String devIP;
    private ListView trackList;
    private ProgressDialog myDialog = null;
    private String url = EXTHeader.DEFAULT_VALUE;
    private Items items = null;
    private TrackListTab myTrackList = null;
    private int startindex = 0;
    private boolean processing = false;
    private String albumTitle = EXTHeader.DEFAULT_VALUE;
    private String objId = null;
    int holyColor = 0;
    private Runnable startShufflePlayback = new Runnable() { // from class: olive.app.TrackListTab.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                URL url = new URL("http://" + TrackListTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + TrackListTab.this.objId);
                url.getContent();
                URL url2 = new URL("http://" + TrackListTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=SHUFFLE&id=" + TrackListTab.this.objId);
                url2.getContent();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    };
    private Runnable pullUpdateMetadata = new Runnable() { // from class: olive.app.TrackListTab.2
        @Override // java.lang.Runnable
        public void run() {
            new CurrentlyPlaying().execute(TrackListTab.this);
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracklisttab);
        Bundle bundle = getIntent().getExtras();
        this.objId = bundle.getString("objId");
        this.trackList = (ListView) findViewById(R.id.ListViewTrackList);
        this.albumTitle = bundle.getString("albumTitle");
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        Intent intent = getIntent();
        if (!"android.intent.action.SEARCH".equals(intent.getAction())) {
            if (this.devIP != "none") {
                try {
                    this.myDialog = ProgressDialog.show(this, "Working...", "Loading track list...", true, false);
                    this.url = "http://" + this.devIP + "/server/iGetCompilationsAndTracks.php?xmlType=noAttribute&id=" + this.objId;
                    new LoadingXMLParserData().execute(this);
                } catch (Exception e) {
                    if (e.toString().indexOf("java.io.FileNotFoundException") > -1) {
                        new AlertDialog.Builder(this).setTitle("Warning!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.TrackListTab.3
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }
            } else {
                new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.forceClose).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.TrackListTab.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        }
        View.OnClickListener homeListener = new View.OnClickListener() { // from class: olive.app.TrackListTab.5
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                TrackListTab.this.finish();
            }
        };
        LinearLayout back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(homeListener);
        ((ImageView) findViewById(R.id.backImg)).setOnClickListener(homeListener);
        View.OnClickListener listener = new View.OnClickListener() { // from class: olive.app.TrackListTab.6
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                if (TrackListTab.this.url.indexOf("ROOT_ALL") > -1 && TrackListTab.this.url.indexOf("search") < 0) {
                    String[] objIdArr = TrackListTab.this.url.split("ROOT");
                    TrackListTab.this.objId = "ROOT" + objIdArr[1];
                    if (TrackListTab.this.objId.indexOf("&") > -1) {
                        TrackListTab.this.objId = TrackListTab.this.objId.split("&")[0];
                    }
                    if (TrackListTab.this.objId.lastIndexOf("_AL") > 4 || TrackListTab.this.objId.indexOf("_AR") > -1 || TrackListTab.this.objId.indexOf("ALLPL") > -1 || TrackListTab.this.objId.equals("ROOT_ALLAL") || TrackListTab.this.objId.equals("ROOT_ALLAU")) {
                        TrackListTab.this.startActivity(new Intent(TrackListTab.this, (Class<?>) Playscreen.class));
                        new Thread(TrackListTab.this.startShufflePlayback).start();
                        return;
                    } else {
                        Context context = TrackListTab.this.getApplicationContext();
                        Toast toast = Toast.makeText(context, "Function not available in this context.", 1);
                        toast.show();
                        return;
                    }
                }
                Context context2 = TrackListTab.this.getApplicationContext();
                Toast toast2 = Toast.makeText(context2, "Function not available in this context.", 1);
                toast2.show();
            }
        };
        LinearLayout shuffleAll = (LinearLayout) findViewById(R.id.shuffleAll);
        shuffleAll.setOnClickListener(listener);
        ((ImageView) findViewById(R.id.shuffleImg)).setOnClickListener(listener);
        View.OnClickListener listenerH = new View.OnClickListener() { // from class: olive.app.TrackListTab.7
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                TrackListTab.this.startActivity(new Intent(TrackListTab.this, (Class<?>) LoadTabs.class));
                TrackListTab.this.finish();
            }
        };
        LinearLayout home = (LinearLayout) findViewById(R.id.home);
        home.setOnClickListener(listenerH);
        ((ImageView) findViewById(R.id.homeImg)).setOnClickListener(listenerH);
        findViewById(R.id.playerbar).setOnClickListener(new View.OnClickListener() { // from class: olive.app.TrackListTab.8
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                TrackListTab.this.startActivity(new Intent(TrackListTab.this, (Class<?>) Playscreen.class));
            }
        });
        updatePlayer();
    }

    private void updatePlayer() {
        SharedPreferences share = getSharedPreferences("Olive_Settings", 0);
        String albumTitle = share.getString("albumTitle", "none");
        String trackTitle = share.getString("trackTitle", "none");
        if (albumTitle != "none") {
            ((TextView) findViewById(R.id.playerAlbum)).setText(albumTitle);
        }
        if (trackTitle != "none") {
            ((TextView) findViewById(R.id.playerTitle)).setText(trackTitle);
        }
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return this.url;
    }

    public void setItems(Items items) {
        if (items != null) {
            if (this.items == null) {
                this.items = items;
            } else {
                this.items.addItems(items);
            }
            this.items.setError(items.getError());
        }
    }

    public void displayItems() {
        this.myDialog.dismiss();
        if (this.items != null) {
            TextView albumTitle = (TextView) findViewById(R.id.ListViewAlbumTitle);
            albumTitle.setText(this.albumTitle);
            this.adapter = new TrackAdapter(this.items, this);
            this.trackList.setAdapter((ListAdapter) this.adapter);
            this.trackList.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: olive.app.TrackListTab.9
                @Override // android.widget.AbsListView.OnScrollListener
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem > TrackListTab.this.startindex - 10) {
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }
            });
            this.trackList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.TrackListTab.10
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                    TrackListTab.this.startActivity(new Intent(TrackListTab.this, (Class<?>) Playscreen.class));
                    try {
                        URL url = new URL("http://" + TrackListTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + TrackListTab.this.items.getObjId().get(position));
                        url.getContent();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            });
            this.processing = false;
            return;
        }
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setPositiveButton("Try Again", new DialogInterface.OnClickListener() { // from class: olive.app.TrackListTab.11
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                TrackListTab.this.myDialog = ProgressDialog.show(TrackListTab.this.myTrackList, "Working...", "Loading track list...", true, false);
                TrackListTab.this.myTrackList.url = "http://" + TrackListTab.this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true";
                new LoadingXMLParserData().execute(TrackListTab.this.myTrackList);
            }
        }).show();
    }

    public void updateMetadata(Metadata metadata) {
        if (metadata != null) {
            TextView album = (TextView) findViewById(R.id.playerAlbum);
            TextView title = (TextView) findViewById(R.id.playerTitle);
            if (metadata.getAlbum().equals(EXTHeader.DEFAULT_VALUE) && metadata.getTransportState().equals("STOPPED")) {
                album.setText(EXTHeader.DEFAULT_VALUE);
                title.setText("No track currently playing...");
            } else {
                album.setText(metadata.getAlbum());
                title.setText(metadata.getDctitle());
            }
        }
        Handler mHandler = new Handler();
        mHandler.removeCallbacks(this.pullUpdateMetadata);
        mHandler.postDelayed(this.pullUpdateMetadata, 5000L);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:1:0x0000. Please report as an issue. */
    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        }
    }

    public void displayError() {
        this.myDialog.dismiss();
        Toast.makeText(this, R.string.connectionError, 10000);
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.TrackListTab.12
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        updatePlayer();
    }

    public void onDestroy(Bundle savedInstanceState) {
        super.onDestroy();
    }
}
