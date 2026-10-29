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
public class SourcesTab extends Activity {
    TrackAdapter adapter;
    private String devIP;
    ListView list;
    private ProgressDialog myDialog = null;
    private String url = EXTHeader.DEFAULT_VALUE;
    private Items items = null;
    boolean processing = false;
    String objId = null;
    int startindex = 0;
    private Runnable startShufflePlayback = new Runnable() { // from class: olive.app.SourcesTab.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                URL url = new URL("http://" + SourcesTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + SourcesTab.this.objId);
                url.getContent();
                URL url2 = new URL("http://" + SourcesTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=SHUFFLE&id=" + SourcesTab.this.objId);
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
        this.startindex = 0;
        this.list = (ListView) findViewById(R.id.ListView);
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        Bundle bundle = getIntent().getExtras();
        this.objId = bundle.getString("objId");
        String title = bundle.getString("title");
        this.url = bundle.getString("url");
        ((TextView) findViewById(R.id.ListViewTitle)).setText(title);
        if (this.devIP != "none") {
            this.myDialog = ProgressDialog.show(this, "Working...", "Loading content...", true, false);
            new LoadingXMLParserData().execute(this);
        } else {
            new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.forceClose).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.SourcesTab.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
        View.OnClickListener l1 = new View.OnClickListener() { // from class: olive.app.SourcesTab.3
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                SourcesTab.this.onBackPressed();
            }
        };
        ((LinearLayout) findViewById(R.id.back)).setOnClickListener(l1);
        ((ImageView) findViewById(R.id.backImg)).setOnClickListener(l1);
        View.OnClickListener l2 = new View.OnClickListener() { // from class: olive.app.SourcesTab.4
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                if (SourcesTab.this.url.indexOf("ROOT_ALL") > -1) {
                    String[] objIdArr = SourcesTab.this.url.split("ROOT");
                    SourcesTab.this.objId = "ROOT" + objIdArr[1];
                    if (SourcesTab.this.objId.indexOf("&") > -1) {
                        SourcesTab.this.objId = SourcesTab.this.objId.split("&")[0];
                    }
                    if (SourcesTab.this.objId.lastIndexOf("_AL") > 4 || SourcesTab.this.objId.indexOf("_AR") > -1 || SourcesTab.this.objId.indexOf("ALLPL") > -1 || SourcesTab.this.objId.equals("ROOT_ALLAL") || SourcesTab.this.objId.equals("ROOT_ALLAU")) {
                        SourcesTab.this.startActivity(new Intent(SourcesTab.this, (Class<?>) Playscreen.class));
                        new Thread(SourcesTab.this.startShufflePlayback).start();
                        return;
                    } else {
                        Context context = SourcesTab.this.getApplicationContext();
                        Toast toast = Toast.makeText(context, "Function not available in this context.", 1);
                        toast.show();
                        return;
                    }
                }
                Context context2 = SourcesTab.this.getApplicationContext();
                Toast toast2 = Toast.makeText(context2, "Function not available in this context.", 1);
                toast2.show();
            }
        };
        ((LinearLayout) findViewById(R.id.shuffleAll)).setOnClickListener(l2);
        ((ImageView) findViewById(R.id.shuffleImg)).setOnClickListener(l2);
        View.OnClickListener l3 = new View.OnClickListener() { // from class: olive.app.SourcesTab.5
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                new SendCommand().execute("http://" + SourcesTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
                SourcesTab.this.startActivity(new Intent(SourcesTab.this, (Class<?>) LoadTabs.class));
                SourcesTab.this.finish();
            }
        };
        ((LinearLayout) findViewById(R.id.home)).setOnClickListener(l3);
        ((ImageView) findViewById(R.id.homeImg)).setOnClickListener(l3);
        findViewById(R.id.playerbar).setOnClickListener(new View.OnClickListener() { // from class: olive.app.SourcesTab.6
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                SourcesTab.this.startActivity(new Intent(SourcesTab.this, (Class<?>) Playscreen.class));
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
            if (this.items.getTitle().size() == 0) {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "No entries found.", 1);
                toast.show();
            }
            if (this.objId != null) {
                this.list.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: olive.app.SourcesTab.7
                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (SourcesTab.this.items != null && SourcesTab.this.items.getTotalItems() != null && SourcesTab.this.items.getItemsReturned() != null && !SourcesTab.this.items.getTotalItems().equals(EXTHeader.DEFAULT_VALUE) && !SourcesTab.this.items.getItemsReturned().equals(EXTHeader.DEFAULT_VALUE)) {
                            if (Integer.parseInt(SourcesTab.this.items.getTotalItems()) > Integer.parseInt(SourcesTab.this.items.getItemsReturned())) {
                                SourcesTab.this.processing = true;
                                SourcesTab.this.startindex += 64;
                                if (SourcesTab.this.url.indexOf("startindex") > -1) {
                                    SourcesTab.this.url = SourcesTab.this.url.substring(0, SourcesTab.this.url.lastIndexOf("=") + 1);
                                    SourcesTab sourcesTab = SourcesTab.this;
                                    sourcesTab.url = String.valueOf(sourcesTab.url) + SourcesTab.this.startindex;
                                } else {
                                    SourcesTab sourcesTab2 = SourcesTab.this;
                                    sourcesTab2.url = String.valueOf(sourcesTab2.url) + "&startindex=" + SourcesTab.this.startindex;
                                }
                                new LoadingXMLParserData().execute(SourcesTab.this);
                                return;
                            }
                            return;
                        }
                        if (firstVisibleItem > SourcesTab.this.startindex - 10 && !SourcesTab.this.processing) {
                            SourcesTab.this.processing = true;
                            SourcesTab.this.startindex += 64;
                            if (SourcesTab.this.url.indexOf("startindex") > -1) {
                                SourcesTab.this.url = SourcesTab.this.url.substring(0, SourcesTab.this.url.lastIndexOf("=") + 1);
                                SourcesTab sourcesTab3 = SourcesTab.this;
                                sourcesTab3.url = String.valueOf(sourcesTab3.url) + SourcesTab.this.startindex;
                            } else {
                                SourcesTab sourcesTab4 = SourcesTab.this;
                                sourcesTab4.url = String.valueOf(sourcesTab4.url) + "&startindex=" + SourcesTab.this.startindex;
                            }
                            new LoadingXMLParserData().execute(SourcesTab.this);
                        }
                    }

                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                    }
                });
            }
            this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.SourcesTab.8
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                    String objId = SourcesTab.this.items.getObjId().get(position);
                    if (SourcesTab.this.items.getIsDir().size() > 0 && SourcesTab.this.items.getIsDir().get(position).equals("0")) {
                        SourcesTab.this.startActivity(new Intent(SourcesTab.this, (Class<?>) Playscreen.class));
                        new SendCommand().execute("http://" + SourcesTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + SourcesTab.this.items.getObjId().get(position));
                        return;
                    }
                    if (SourcesTab.this.objId == null) {
                        new SendCommand().execute("http://" + SourcesTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=" + objId + "&uuid=" + SourcesTab.this.items.getUuid().get(position));
                    }
                    if (SourcesTab.this.objId == null) {
                        SourcesTab.this.url = "http://" + SourcesTab.this.devIP + "/server/iGetCompilationsAndTracks.php?xmlType=noAttribute&id=0";
                    } else {
                        SourcesTab.this.url = "http://" + SourcesTab.this.devIP + "/server/iGetCompilationsAndTracks.php?xmlType=noAttribute&id=" + objId;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("url", SourcesTab.this.url);
                    bundle.putString("title", SourcesTab.this.items.getTitle().get(position));
                    bundle.putString("objId", objId);
                    Intent intent = new Intent(SourcesTab.this, (Class<?>) SourcesTab.class);
                    intent.putExtras(bundle);
                    SourcesTab.this.startActivity(intent);
                }
            });
            this.processing = false;
            return;
        }
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setPositiveButton("Try Again", new DialogInterface.OnClickListener() { // from class: olive.app.SourcesTab.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                SourcesTab.this.myDialog = ProgressDialog.show(SourcesTab.this, "Working...", "Loading content...", true, false);
                new LoadingXMLParserData().execute(SourcesTab.this);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: olive.app.SourcesTab.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                SourcesTab.this.myDialog.dismiss();
            }
        }).show();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        TextView title = (TextView) findViewById(R.id.ListViewTitle);
        String myTitle = title.getText().toString();
        if (myTitle.equals("Music Sources:")) {
            new SendCommand().execute("http://" + this.devIP + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
        }
        finish();
    }

    public void onDestroy(Bundle savedInstanceState) {
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        updatePlayer();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
