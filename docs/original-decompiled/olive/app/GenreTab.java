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
public class GenreTab extends Activity {
    TrackAdapter adapter;
    private String devIP;
    ListView list;
    private ProgressDialog myDialog = null;
    private String url = EXTHeader.DEFAULT_VALUE;
    private Items items = null;
    boolean processing = false;
    String objId = null;
    int startindex = 0;

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
            this.myDialog = ProgressDialog.show(this, "Working...", "Loading playlists...", true, false);
            this.url = "http://" + this.devIP + "/server/iGetCompilationsAndTracks.php?xmlType=noAttribute&id=" + this.objId;
            new LoadingXMLParserData().execute(this);
        } else {
            new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.forceClose).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.GenreTab.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
        LinearLayout back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() { // from class: olive.app.GenreTab.2
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                GenreTab.this.finish();
            }
        });
        LinearLayout shuffleAll = (LinearLayout) findViewById(R.id.shuffleAll);
        shuffleAll.setOnClickListener(new View.OnClickListener() { // from class: olive.app.GenreTab.3
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                Context context = GenreTab.this.getApplicationContext();
                Toast toast = Toast.makeText(context, "Function not available in this context.", 1);
                toast.show();
            }
        });
        LinearLayout home = (LinearLayout) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() { // from class: olive.app.GenreTab.4
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                GenreTab.this.startActivity(new Intent(GenreTab.this, (Class<?>) LoadTabs.class));
                GenreTab.this.finish();
            }
        });
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
            this.list.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: olive.app.GenreTab.5
                @Override // android.widget.AbsListView.OnScrollListener
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem > GenreTab.this.startindex - 10 && !GenreTab.this.processing) {
                        GenreTab.this.processing = true;
                        GenreTab.this.startindex += 64;
                        if (GenreTab.this.url.indexOf("startindex") > -1) {
                            GenreTab.this.url = GenreTab.this.url.substring(0, GenreTab.this.url.lastIndexOf("=") + 1);
                            GenreTab genreTab = GenreTab.this;
                            genreTab.url = String.valueOf(genreTab.url) + GenreTab.this.startindex;
                        } else {
                            GenreTab genreTab2 = GenreTab.this;
                            genreTab2.url = String.valueOf(genreTab2.url) + "&startindex=" + GenreTab.this.startindex;
                        }
                        new LoadingXMLParserData().execute(GenreTab.this);
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }
            });
            this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.GenreTab.6
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                    String objId = GenreTab.this.items.getObjId().get(position);
                    if (objId.indexOf("AU") > -1 || objId.indexOf("TR") > -1) {
                        try {
                            URL url = new URL("http://" + GenreTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + GenreTab.this.items.getObjId().get(position));
                            url.getContent();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        GenreTab.this.startActivity(new Intent(GenreTab.this, (Class<?>) Playscreen.class));
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("objId", objId);
                    bundle.putString("albumTitle", GenreTab.this.items.getTitle().get(position));
                    Intent intent = new Intent(GenreTab.this, (Class<?>) TrackListTab.class);
                    intent.putExtras(bundle);
                    GenreTab.this.startActivity(intent);
                }
            });
            this.processing = false;
            return;
        }
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setPositiveButton("Try Again", new DialogInterface.OnClickListener() { // from class: olive.app.GenreTab.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                GenreTab.this.myDialog = ProgressDialog.show(GenreTab.this, "Working...", "Loading content...", true, false);
                GenreTab.this.url = "http://" + GenreTab.this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true";
                new LoadingXMLParserData().execute(GenreTab.this);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: olive.app.GenreTab.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                GenreTab.this.myDialog.dismiss();
            }
        }).show();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
