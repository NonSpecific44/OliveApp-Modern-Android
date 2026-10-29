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
public class SearchListTab extends Activity {
    TrackAdapter adapter;
    private String devIP;
    ListView searchList;
    private ProgressDialog myDialog = null;
    private String url = EXTHeader.DEFAULT_VALUE;
    private String objId = null;
    private Items items = null;
    boolean processing = false;
    private Runnable startShufflePlayback = new Runnable() { // from class: olive.app.SearchListTab.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                URL url = new URL("http://" + SearchListTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + SearchListTab.this.objId);
                url.getContent();
                URL url2 = new URL("http://" + SearchListTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=SHUFFLE&id=" + SearchListTab.this.objId);
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
        setContentView(R.layout.searchresult);
        this.searchList = (ListView) findViewById(R.id.ListViewSearchList);
        Bundle bundle = getIntent().getExtras();
        this.url = bundle.getString("url");
        String title = bundle.getString("title");
        ((TextView) findViewById(R.id.ListViewSearchTitle)).setText(title);
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        Intent intent = getIntent();
        if (!"android.intent.action.SEARCH".equals(intent.getAction())) {
            if (this.devIP != "none") {
                try {
                    this.myDialog = ProgressDialog.show(this, "Working...", "Loading search result...", true, false);
                    new LoadingSearchResult().execute(this);
                } catch (Exception e) {
                    if (e.toString().indexOf("java.io.FileNotFoundException") > -1) {
                        new AlertDialog.Builder(this).setTitle("Warning!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.SearchListTab.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }
            } else {
                new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.forceClose).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.SearchListTab.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        }
        View.OnClickListener l1 = new View.OnClickListener() { // from class: olive.app.SearchListTab.4
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                SearchListTab.this.finish();
            }
        };
        ((LinearLayout) findViewById(R.id.back)).setOnClickListener(l1);
        ((ImageView) findViewById(R.id.backImg)).setOnClickListener(l1);
        View.OnClickListener l2 = new View.OnClickListener() { // from class: olive.app.SearchListTab.5
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                if (SearchListTab.this.url.indexOf("ROOT_ALL") > -1 && SearchListTab.this.url.indexOf("search") < 0) {
                    String[] objIdArr = SearchListTab.this.url.split("ROOT");
                    SearchListTab.this.objId = "ROOT" + objIdArr[1];
                    if (SearchListTab.this.objId.indexOf("&") > -1) {
                        SearchListTab.this.objId = SearchListTab.this.objId.split("&")[0];
                    }
                    if (SearchListTab.this.objId.lastIndexOf("_AL") > 4 || SearchListTab.this.objId.indexOf("_AR") > -1 || SearchListTab.this.objId.indexOf("ALLPL") > -1 || SearchListTab.this.objId.equals("ROOT_ALLAL") || SearchListTab.this.objId.equals("ROOT_ALLAU")) {
                        SearchListTab.this.startActivity(new Intent(SearchListTab.this, (Class<?>) Playscreen.class));
                        new Thread(SearchListTab.this.startShufflePlayback).start();
                        return;
                    } else {
                        Context context = SearchListTab.this.getApplicationContext();
                        Toast toast = Toast.makeText(context, "Function not available in this context.", 1);
                        toast.show();
                        return;
                    }
                }
                Context context2 = SearchListTab.this.getApplicationContext();
                Toast toast2 = Toast.makeText(context2, "Function not available in this context.", 1);
                toast2.show();
            }
        };
        ((LinearLayout) findViewById(R.id.shuffleAll)).setOnClickListener(l2);
        ((ImageView) findViewById(R.id.shuffleImg)).setOnClickListener(l2);
        View.OnClickListener l3 = new View.OnClickListener() { // from class: olive.app.SearchListTab.6
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                SearchListTab.this.startActivity(new Intent(SearchListTab.this, (Class<?>) LoadTabs.class));
                SearchListTab.this.finish();
            }
        };
        ((LinearLayout) findViewById(R.id.home)).setOnClickListener(l3);
        ((ImageView) findViewById(R.id.homeImg)).setOnClickListener(l3);
        findViewById(R.id.playerbar).setOnClickListener(new View.OnClickListener() { // from class: olive.app.SearchListTab.7
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                SearchListTab.this.startActivity(new Intent(SearchListTab.this, (Class<?>) Playscreen.class));
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
            this.adapter = new TrackAdapter(this.items, this);
            this.searchList.setAdapter((ListAdapter) this.adapter);
            this.searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.SearchListTab.8
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                    String objId = SearchListTab.this.items.getObjId().get(position);
                    if (!SearchListTab.this.items.getIsDir().get(position).equals("0")) {
                        SearchListTab.this.url = "http://" + SearchListTab.this.devIP + "/server/iGetCompilationsAndTracks.php?xmlType=noAttribute&id=" + objId;
                        Bundle bundle = new Bundle();
                        bundle.putString("url", SearchListTab.this.url);
                        bundle.putString("title", SearchListTab.this.items.getTitle().get(position));
                        Intent intent = new Intent(SearchListTab.this, (Class<?>) SearchListTab.class);
                        intent.putExtras(bundle);
                        SearchListTab.this.startActivity(intent);
                        return;
                    }
                    new SendCommand().execute("http://" + SearchListTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + SearchListTab.this.items.getObjId().get(position));
                    SearchListTab.this.startActivity(new Intent(SearchListTab.this, (Class<?>) Playscreen.class));
                }
            });
            this.processing = false;
            return;
        }
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "No entries found.", 1);
        toast.show();
    }

    public void displayError(String errorMsg) {
        this.myDialog.dismiss();
        Toast.makeText(this, R.string.connectionError, 10000);
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.SearchListTab.9
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
    }
}
