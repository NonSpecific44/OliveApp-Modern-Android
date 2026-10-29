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
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
public class PlaylistTab extends Activity {
    TrackAdapter adapter;
    private String devIP;
    ListView list;
    private ProgressDialog myDialog = null;
    private String url = EXTHeader.DEFAULT_VALUE;
    private Items items = null;
    boolean processing = false;
    String objId = null;
    int startindex = 0;
    private Runnable startShufflePlayback = new Runnable() { // from class: olive.app.PlaylistTab.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                URL url = new URL("http://" + PlaylistTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + PlaylistTab.this.objId);
                url.getContent();
                URL url2 = new URL("http://" + PlaylistTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=SHUFFLE&id=" + PlaylistTab.this.objId);
                url2.getContent();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        this.list = (ListView) findViewById(R.id.ListView);
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        Bundle bundle = getIntent().getExtras();
        this.objId = bundle.getString("objId");
        String title = bundle.getString("title");
        ((TextView) findViewById(R.id.ListViewTitle)).setText(title);
        if (this.devIP != "none") {
            this.myDialog = ProgressDialog.show(this, "Working...", "Loading content...", true, false);
            this.url = "http://" + this.devIP + "/server/iGetCompilationsAndTracks.php?xmlType=noAttribute&id=" + this.objId;
            new LoadingXMLParserData().execute(this);
        } else {
            new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.forceClose).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.PlaylistTab.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
        View.OnClickListener l1 = new View.OnClickListener() { // from class: olive.app.PlaylistTab.3
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                PlaylistTab.this.finish();
            }
        };
        ((LinearLayout) findViewById(R.id.back)).setOnClickListener(l1);
        ((ImageView) findViewById(R.id.backImg)).setOnClickListener(l1);
        View.OnClickListener l2 = new View.OnClickListener() { // from class: olive.app.PlaylistTab.4
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                if (PlaylistTab.this.objId.indexOf("&") > -1) {
                    PlaylistTab.this.objId = PlaylistTab.this.objId.split("&")[0];
                }
                if (!PlaylistTab.this.objId.equals("ROOT_ALLGE")) {
                    PlaylistTab.this.startActivity(new Intent(PlaylistTab.this, (Class<?>) Playscreen.class));
                    new Thread(PlaylistTab.this.startShufflePlayback).start();
                } else {
                    Context context = PlaylistTab.this.getApplicationContext();
                    Toast toast = Toast.makeText(context, "Function not available in this context.", 1);
                    toast.show();
                }
            }
        };
        ((LinearLayout) findViewById(R.id.shuffleAll)).setOnClickListener(l2);
        ((ImageView) findViewById(R.id.shuffleImg)).setOnClickListener(l2);
        View.OnClickListener l3 = new View.OnClickListener() { // from class: olive.app.PlaylistTab.5
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                PlaylistTab.this.startActivity(new Intent(PlaylistTab.this, (Class<?>) LoadTabs.class));
                PlaylistTab.this.finish();
            }
        };
        ((LinearLayout) findViewById(R.id.home)).setOnClickListener(l3);
        ((ImageView) findViewById(R.id.homeImg)).setOnClickListener(l3);
        findViewById(R.id.playerbar).setOnClickListener(new View.OnClickListener() { // from class: olive.app.PlaylistTab.6
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                PlaylistTab.this.startActivity(new Intent(PlaylistTab.this, (Class<?>) Playscreen.class));
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
            if (this.list.getAdapter() == null) {
                this.adapter = new TrackAdapter(this.items, this);
                this.list.setAdapter((ListAdapter) this.adapter);
            } else {
                ((BaseAdapter) this.list.getAdapter()).notifyDataSetChanged();
            }
            this.list.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: olive.app.PlaylistTab.7
                @Override // android.widget.AbsListView.OnScrollListener
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem > PlaylistTab.this.startindex - 10 && !PlaylistTab.this.processing) {
                        PlaylistTab.this.processing = true;
                        PlaylistTab.this.startindex += 64;
                        if (PlaylistTab.this.url.indexOf("startindex") > -1) {
                            PlaylistTab.this.url = PlaylistTab.this.url.substring(0, PlaylistTab.this.url.lastIndexOf("=") + 1);
                            PlaylistTab playlistTab = PlaylistTab.this;
                            playlistTab.url = String.valueOf(playlistTab.url) + PlaylistTab.this.startindex;
                        } else {
                            PlaylistTab playlistTab2 = PlaylistTab.this;
                            playlistTab2.url = String.valueOf(playlistTab2.url) + "&startindex=" + PlaylistTab.this.startindex;
                        }
                        new LoadingXMLParserData().execute(PlaylistTab.this);
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }
            });
            this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.PlaylistTab.8
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                    String objId = PlaylistTab.this.items.getObjId().get(position);
                    if (!PlaylistTab.this.items.getIsDir().get(position).equals("0")) {
                        PlaylistTab.this.url = "http://" + PlaylistTab.this.devIP + "/server/iGetCompilationsAndTracks.php?xmlType=noAttribute&id=" + objId;
                        Bundle bundle = new Bundle();
                        bundle.putString("url", PlaylistTab.this.url);
                        bundle.putString("title", PlaylistTab.this.items.getTitle().get(position));
                        Intent intent = new Intent(PlaylistTab.this, (Class<?>) SearchListTab.class);
                        intent.putExtras(bundle);
                        PlaylistTab.this.startActivity(intent);
                        return;
                    }
                    new SendCommand().execute("http://" + PlaylistTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + PlaylistTab.this.items.getObjId().get(position));
                    PlaylistTab.this.startActivity(new Intent(PlaylistTab.this, (Class<?>) Playscreen.class));
                }
            });
            this.processing = false;
            return;
        }
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setPositiveButton("Try Again", new DialogInterface.OnClickListener() { // from class: olive.app.PlaylistTab.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PlaylistTab.this.myDialog = ProgressDialog.show(PlaylistTab.this, "Working...", "Loading content...", true, false);
                PlaylistTab.this.url = "http://" + PlaylistTab.this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true";
                new LoadingXMLParserData().execute(PlaylistTab.this);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: olive.app.PlaylistTab.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PlaylistTab.this.myDialog.dismiss();
            }
        }).show();
    }

    public void displayError() {
        this.myDialog.dismiss();
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.PlaylistTab.11
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onDestroy(Bundle savedInstanceState) {
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        updatePlayer();
    }
}
