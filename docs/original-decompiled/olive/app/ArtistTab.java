package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
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
import java.util.ArrayList;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class ArtistTab extends Activity {
    private LazyAdapter adapter;
    private ListView artists;
    ArrayList<String> title_arr;
    private String devIP = null;
    private Items items = null;
    private ProgressDialog myDialog = null;
    private String query = null;
    private int startindex = 0;
    private String url = EXTHeader.DEFAULT_VALUE;
    private boolean processing = false;
    AsyncTask<Object[], Void, Items> myAsynTask = null;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artisttab);
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        this.artists = (ListView) findViewById(R.id.ListViewArtists);
        this.artists.setFastScrollEnabled(true);
        Intent intent = getIntent();
        if ("android.intent.action.SEARCH".equals(intent.getAction())) {
            this.query = intent.getStringExtra("query");
            this.url = "http://" + this.devIP + "/server/getSearch.php?id=ROOT_ALLAR&xmlType=noAttributes&searchterm=" + this.query;
            if (this.myDialog == null || !this.myDialog.isShowing()) {
                this.myDialog = ProgressDialog.show(this, "Working...", "Loading all artists...", true, false);
            }
            this.myAsynTask = new LoadingArtist().execute(new Object[]{this, EXTHeader.DEFAULT_VALUE});
            return;
        }
        if (this.devIP != "none") {
            int ip = ((WifiManager) getSystemService("wifi")).getConnectionInfo().getIpAddress();
            if (ip != 0) {
                if (this.myDialog == null || !this.myDialog.isShowing()) {
                    this.myDialog = ProgressDialog.show(this, "Working...", "Loading all artists...", true, false);
                }
                this.url = "http://" + this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true";
                this.myAsynTask = new LoadingArtist().execute(new Object[]{this, EXTHeader.DEFAULT_VALUE});
                return;
            }
            new AlertDialog.Builder(this).setTitle("Error!").setMessage("You are currently not connected to a wireless network. Please connect your phone to a network and try again.").setPositiveButton("Try Again", new DialogInterface.OnClickListener() { // from class: olive.app.ArtistTab.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    if (ArtistTab.this.myDialog == null || !ArtistTab.this.myDialog.isShowing()) {
                        ArtistTab.this.myDialog = ProgressDialog.show(ArtistTab.this, "Working...", "Loading all artists...", true, false);
                    }
                    ArtistTab.this.url = "http://" + ArtistTab.this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true";
                    ArtistTab.this.myAsynTask = new LoadingArtist().execute(new Object[]{ArtistTab.this, EXTHeader.DEFAULT_VALUE});
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: olive.app.ArtistTab.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            return;
        }
        startActivityForResult(new Intent(this, (Class<?>) LoadDevices.class), 185);
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
            if (this.artists.getAdapter() == null) {
                this.adapter = new LazyAdapter(this, this.items, this.devIP, R.layout.artist_item);
                this.artists.setAdapter((ListAdapter) this.adapter);
            } else {
                ((BaseAdapter) this.artists.getAdapter()).notifyDataSetChanged();
            }
            this.artists.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: olive.app.ArtistTab.3
                @Override // android.widget.AbsListView.OnScrollListener
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem != 0 && firstVisibleItem > ArtistTab.this.startindex - 10 && !ArtistTab.this.processing) {
                        ArtistTab.this.processing = true;
                        ArtistTab.this.startindex += 64;
                        ArtistTab.this.url = "http://" + ArtistTab.this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true&startIndex=" + ArtistTab.this.startindex;
                        ArtistTab.this.myAsynTask = new LoadingArtist().execute(new Object[]{ArtistTab.this, EXTHeader.DEFAULT_VALUE});
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }
            });
            this.artists.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.ArtistTab.4
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                    Bundle bundle = new Bundle();
                    bundle.putString("objId", ArtistTab.this.items.getObjId().get(position));
                    bundle.putString("albumTitle", ArtistTab.this.items.getTitle().get(position));
                    Intent intent = new Intent(ArtistTab.this, (Class<?>) TrackListTab.class);
                    intent.putExtras(bundle);
                    ArtistTab.this.startActivity(intent);
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
            new AlertDialog.Builder(this).setTitle(errorTitle).setMessage(errorMessage).setPositiveButton("Try Again", new DialogInterface.OnClickListener() { // from class: olive.app.ArtistTab.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    if (ArtistTab.this.myDialog == null || !ArtistTab.this.myDialog.isShowing()) {
                        ArtistTab.this.myDialog = ProgressDialog.show(ArtistTab.this, "Working...", "Loading all artists...", true, false);
                    }
                    ArtistTab.this.url = "http://" + ArtistTab.this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true";
                    ArtistTab.this.myAsynTask = new LoadingArtist().execute(new Object[]{ArtistTab.this, EXTHeader.DEFAULT_VALUE});
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: olive.app.ArtistTab.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    ArtistTab.this.myDialog.dismiss();
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search /* 2131296368 */:
                searchArtists();
                return true;
            case R.id.refresh /* 2131296369 */:
                reloadArtists();
                return true;
            case R.id.quit /* 2131296370 */:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchArtists() {
        Intent foo = new Intent(this, (Class<?>) SearchEntry.class);
        startActivityForResult(foo, 183);
    }

    public void showSearchResult(Items items) {
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 183:
                try {
                    String value = data.getStringExtra("value");
                    if (value != null && value.length() > 0) {
                        this.query = value;
                        this.url = "http://" + this.devIP + "/server/getSearch.php?id=ROOT_ALLAR&xmlType=noAttributes&searchterm=" + this.query;
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
            case 185:
                try {
                    if (data.getStringExtra("reload").equals("yes")) {
                        this.startindex = 0;
                        if (this.myDialog == null || !this.myDialog.isShowing()) {
                            this.myDialog = ProgressDialog.show(this, "Working...", "Loading all artists...", true, false);
                        }
                        if (this.devIP == "none") {
                            SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
                            this.devIP = settings.getString("devIP", "none");
                        }
                        LoadTabs.setIP(this.devIP);
                        this.url = "http://" + this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true";
                        this.myAsynTask = new LoadingArtist().execute(new Object[]{this, EXTHeader.DEFAULT_VALUE});
                        break;
                    }
                } catch (Exception e2) {
                    return;
                }
                break;
        }
    }

    public void displayError(String errorMsg) {
        this.myDialog.dismiss();
        Toast.makeText(this, R.string.connectionError, 10000);
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.ArtistTab.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    private void reloadArtists() {
        this.items = null;
        ((ListView) findViewById(R.id.ListViewArtists)).setAdapter((ListAdapter) null);
        this.startindex = 0;
        this.processing = true;
        if (this.myDialog == null || !this.myDialog.isShowing()) {
            this.myDialog = ProgressDialog.show(this, "Working...", "Loading all artists...", true, false);
        }
        this.url = "http://" + this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true&startIndex=0";
        this.myAsynTask = new LoadingArtist().execute(new Object[]{this, EXTHeader.DEFAULT_VALUE});
    }

    @Override // android.app.Activity
    public void onResume() {
        if (LoadTabs.getReload(0)) {
            SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
            this.devIP = settings.getString("devIP", "none");
            reloadArtists();
            LoadTabs.reload(false, 0);
        }
        super.onResume();
    }

    public void onDestroy(Bundle savedInstanceState) {
        this.myAsynTask.cancel(true);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Information:").setMessage("Do you want to quit the application?").setNeutralButton("YES", new DialogInterface.OnClickListener() { // from class: olive.app.ArtistTab.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() { // from class: olive.app.ArtistTab.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
