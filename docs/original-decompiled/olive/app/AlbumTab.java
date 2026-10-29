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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class AlbumTab extends Activity {
    private LazyAdapter adapter;
    private ListView albums;
    ArrayList<String> title_arr;
    private String devIP = null;
    private Items items = null;
    private ProgressDialog myDialog = null;
    private String query = null;
    private int startindex = 0;
    private String url = EXTHeader.DEFAULT_VALUE;
    private boolean processing = false;
    private Runnable startShufflePlayback = new Runnable() { // from class: olive.app.AlbumTab.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                URL url = new URL("http://" + AlbumTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=ROOT_ALLAL");
                url.getContent();
                URL url2 = new URL("http://" + AlbumTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=SHUFFLE&id=ROOT_ALLAL");
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
        setContentView(R.layout.albumtab);
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        this.albums = (ListView) findViewById(R.id.ListViewAlbums);
        this.albums.setFastScrollEnabled(true);
        Intent intent = getIntent();
        if ("android.intent.action.SEARCH".equals(intent.getAction())) {
            this.query = intent.getStringExtra("query");
            this.url = "http://" + this.devIP + "/server/getSearch.php?id=ROOT_ALLAL&xmlType=noAttributes&searchterm=" + this.query;
            if (this.myDialog == null || !this.myDialog.isShowing()) {
                this.myDialog = ProgressDialog.show(this, "Working...", "Loading all albums...", true, false);
            }
            new LoadingAlbum().execute(this);
            return;
        }
        if (this.devIP != "none") {
            try {
                if (this.myDialog == null || !this.myDialog.isShowing()) {
                    this.myDialog = ProgressDialog.show(this, "Working...", "Loading all albums...", true, false);
                }
                this.url = "http://" + this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=album&xml=true";
                new LoadingAlbum().execute(this);
                return;
            } catch (Exception e) {
                if (e.toString().indexOf("java.io.FileNotFoundException") > -1) {
                    new AlertDialog.Builder(this).setTitle("Warning!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.AlbumTab.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                    return;
                }
                return;
            }
        }
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.forceClose).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.AlbumTab.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
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
        if (this.items != null && this.items.getError().equals(EXTHeader.DEFAULT_VALUE)) {
            this.title_arr = this.items.getTitle();
            if (this.albums.getAdapter() == null) {
                this.adapter = new LazyAdapter(this, this.items, this.devIP, R.layout.album_item);
                this.albums.setAdapter((ListAdapter) this.adapter);
            } else {
                ((BaseAdapter) this.albums.getAdapter()).notifyDataSetChanged();
            }
            this.albums.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: olive.app.AlbumTab.4
                @Override // android.widget.AbsListView.OnScrollListener
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem != 0 && firstVisibleItem > AlbumTab.this.startindex - 10 && !AlbumTab.this.processing) {
                        AlbumTab.this.processing = true;
                        AlbumTab.this.startindex += 64;
                        AlbumTab.this.url = "http://" + AlbumTab.this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=album&xml=true&startIndex=" + AlbumTab.this.startindex;
                        new LoadingAlbum().execute(AlbumTab.this);
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }
            });
            this.albums.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.AlbumTab.5
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                    Bundle bundle = new Bundle();
                    bundle.putString("objId", AlbumTab.this.items.getObjId().get(position));
                    bundle.putString("albumTitle", AlbumTab.this.items.getTitle().get(position));
                    Intent intent = new Intent(AlbumTab.this, (Class<?>) TrackListTab.class);
                    intent.putExtras(bundle);
                    AlbumTab.this.startActivity(intent);
                }
            });
            this.processing = false;
            return;
        }
        this.processing = false;
        this.startindex -= 64;
        String errorMessage = EXTHeader.DEFAULT_VALUE;
        String errorTitle = "Error!";
        if (this.items != null && this.items.getError().indexOf("Failed to execute") > -1) {
            errorMessage = "Please make sure your Olive is not navigating an external music source.";
        } else if (this.items != null && this.items.getError().indexOf("The operation timed out") > -1) {
            errorTitle = "Connection could not be established.";
            errorMessage = "Please make sure your Olive and your mobile device is connected with the network.";
        } else if (this.items != null && this.items.getError().indexOf("Invalid argument") > -1) {
            errorMessage = "Please make sure the IP you are trying to connect to is using a valid format: xxx.xxx.xxx.xxx";
        }
        if (!errorMessage.equals(EXTHeader.DEFAULT_VALUE)) {
            new AlertDialog.Builder(this).setTitle(errorTitle).setMessage(errorMessage).setPositiveButton("Try Again", new DialogInterface.OnClickListener() { // from class: olive.app.AlbumTab.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    if (AlbumTab.this.myDialog == null || !AlbumTab.this.myDialog.isShowing()) {
                        AlbumTab.this.myDialog = ProgressDialog.show(AlbumTab.this, "Working...", "Loading all albums...", true, false);
                    }
                    AlbumTab.this.url = "http://" + AlbumTab.this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=album&xml=true";
                    new LoadingAlbum().execute(AlbumTab.this);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: olive.app.AlbumTab.7
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    AlbumTab.this.myDialog.dismiss();
                }
            }).show();
            return;
        }
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "No entries found.", 1);
        toast.show();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu_shuffle, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shuffleAll /* 2131296282 */:
                startActivity(new Intent(this, (Class<?>) Playscreen.class));
                new Thread(this.startShufflePlayback).start();
                return true;
            case R.id.search /* 2131296368 */:
                searchAlbums();
                return true;
            case R.id.refresh /* 2131296369 */:
                reloadAlbums();
                return true;
            case R.id.quit /* 2131296370 */:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchAlbums() {
        Intent foo = new Intent(this, (Class<?>) SearchEntry.class);
        foo.putExtra("value", "old value to edit");
        startActivityForResult(foo, 183);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 183:
                try {
                    String value = data.getStringExtra("value");
                    if (value != null && value.length() > 0) {
                        this.query = value;
                        this.url = "http://" + this.devIP + "/server/getSearch.php?id=ROOT_ALLAL&xmlType=noAttributes&searchterm=" + this.query;
                        Bundle bundle = new Bundle();
                        bundle.putString("url", this.url);
                        bundle.putString("title", "Search result:");
                        Intent intent = new Intent(this, (Class<?>) SearchListTab.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }
                } catch (Exception e) {
                    return;
                }
                break;
        }
    }

    public void displayError(String errorMsg) {
        this.myDialog.dismiss();
        Toast.makeText(this, R.string.connectionError, 10000);
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.AlbumTab.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    private void reloadAlbums() {
        this.items = null;
        ((ListView) findViewById(R.id.ListViewAlbums)).setAdapter((ListAdapter) null);
        this.startindex = 0;
        this.processing = true;
        if (this.myDialog != null) {
            this.myDialog.dismiss();
        }
        this.myDialog = ProgressDialog.show(this, "Working...", "Loading all albums...", true, false);
        this.url = "http://" + this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=album&xml=true&startIndex=0";
        new LoadingAlbum().execute(this);
    }

    @Override // android.app.Activity
    public void onResume() {
        if (LoadTabs.getReload(1)) {
            SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
            this.devIP = settings.getString("devIP", "none");
            reloadAlbums();
            LoadTabs.reload(false, 1);
        }
        super.onResume();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.myDialog != null) {
            this.myDialog.dismiss();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onDestroy(Bundle savedInstanceState) {
        super.onDestroy();
    }
}
