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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URLEncoder;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class IRadioTab extends Activity {
    RadioAdapter adapter;
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
        this.url = bundle.getString("url");
        ((TextView) findViewById(R.id.ListViewTitle)).setText(title);
        if (this.devIP != "none") {
            this.myDialog = ProgressDialog.show(this, "Working...", "Loading content...", true, false);
            new LoadingXMLParserData().execute(this);
        } else {
            new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.forceClose).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.IRadioTab.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
        View.OnClickListener l1 = new View.OnClickListener() { // from class: olive.app.IRadioTab.2
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                IRadioTab.this.finish();
            }
        };
        ((LinearLayout) findViewById(R.id.back)).setOnClickListener(l1);
        ((ImageView) findViewById(R.id.backImg)).setOnClickListener(l1);
        View.OnClickListener l2 = new View.OnClickListener() { // from class: olive.app.IRadioTab.3
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                Context context = IRadioTab.this.getApplicationContext();
                Toast toast = Toast.makeText(context, "Function not available in this context.", 1);
                toast.show();
            }
        };
        ((LinearLayout) findViewById(R.id.shuffleAll)).setOnClickListener(l2);
        ((ImageView) findViewById(R.id.shuffleImg)).setOnClickListener(l2);
        View.OnClickListener l3 = new View.OnClickListener() { // from class: olive.app.IRadioTab.4
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                IRadioTab.this.startActivity(new Intent(IRadioTab.this, (Class<?>) LoadTabs.class));
                IRadioTab.this.finish();
            }
        };
        ((LinearLayout) findViewById(R.id.home)).setOnClickListener(l3);
        ((ImageView) findViewById(R.id.homeImg)).setOnClickListener(l3);
        findViewById(R.id.playerbar).setOnClickListener(new View.OnClickListener() { // from class: olive.app.IRadioTab.5
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                IRadioTab.this.startActivity(new Intent(IRadioTab.this, (Class<?>) Playscreen.class));
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
                this.adapter = new RadioAdapter(this, this.items, R.layout.radioitem);
                this.list.setAdapter((ListAdapter) this.adapter);
            } else {
                ((BaseAdapter) this.list.getAdapter()).notifyDataSetChanged();
            }
            if (this.items.getTitle().size() == 0) {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "No entries found.", 1);
                toast.show();
            }
            this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.IRadioTab.6
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                    String objId = IRadioTab.this.items.getObjId().get(position);
                    if (!IRadioTab.this.items.getAction().get(position).equals("playURLFav") && !IRadioTab.this.items.getAction().get(position).equals("playURL") && (IRadioTab.this.items.getIsDir().size() <= 0 || !IRadioTab.this.items.getIsDir().get(position).equals("0"))) {
                        if (IRadioTab.this.items.getAction().get(position).equals("internetRadioAddURL")) {
                            Intent searchIR = new Intent(IRadioTab.this, (Class<?>) AddURLTab.class);
                            searchIR.putExtra("newStation", EXTHeader.DEFAULT_VALUE);
                            IRadioTab.this.startActivityForResult(searchIR, 184);
                            return;
                        }
                        if (!objId.equals("searchIR")) {
                            if (!IRadioTab.this.items.getAction().get(position).equals(EXTHeader.DEFAULT_VALUE)) {
                                IRadioTab.this.url = "http://" + IRadioTab.this.devIP + "/server/iGetRadio.php?command=" + IRadioTab.this.items.getAction().get(position) + "&id=" + objId;
                            } else {
                                IRadioTab.this.url = "http://" + IRadioTab.this.devIP + "/server/iGetRadio.php?command=browseRadio&id=" + objId;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("url", IRadioTab.this.url);
                            bundle.putString("title", IRadioTab.this.items.getTitle().get(position));
                            bundle.putString("objId", objId);
                            Intent intent = new Intent(IRadioTab.this, (Class<?>) IRadioTab.class);
                            intent.putExtras(bundle);
                            IRadioTab.this.startActivity(intent);
                            return;
                        }
                        Intent searchIR2 = new Intent(IRadioTab.this, (Class<?>) SearchEntry.class);
                        searchIR2.putExtra("title", EXTHeader.DEFAULT_VALUE);
                        IRadioTab.this.startActivityForResult(searchIR2, 183);
                        return;
                    }
                    IRadioTab.this.startActivity(new Intent(IRadioTab.this, (Class<?>) Playscreen.class));
                    String url = "http://" + IRadioTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=playURL&id=" + IRadioTab.this.items.getObjId().get(position);
                    if (IRadioTab.this.items.getAction().get(position).equals("playURLFav")) {
                        url = "http://" + IRadioTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=playURLFav&id=" + IRadioTab.this.items.getObjId().get(position);
                    }
                    new SendCommand().execute(url);
                }
            });
            this.processing = false;
            return;
        }
        Context context2 = getApplicationContext();
        Toast toast2 = Toast.makeText(context2, "No entries found.", 1);
        toast2.show();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, (Class<?>) IRadioTab.class);
        switch (requestCode) {
            case 183:
                if (!data.getStringExtra("value").equals(EXTHeader.DEFAULT_VALUE)) {
                    this.url = "http://" + this.devIP + "/server/iGetRadio.php?id=ROOT_ALLIR&command=search&searchField=" + URLEncoder.encode(data.getStringExtra("value"));
                    bundle.putString("url", this.url);
                    bundle.putString("title", "Search result:");
                    bundle.putString("objId", this.objId);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                }
                break;
            case 184:
                if (data.getExtras() != null) {
                    String name = URLEncoder.encode(data.getExtras().getString("name"));
                    String urlFav = URLEncoder.encode(data.getExtras().getString("url"));
                    String genre = data.getExtras().getString("genre");
                    this.url = "http://" + this.devIP + "/includes/ajax/a_executeOperation.php?action=addRadioToFavs&name=" + name + "&url=" + urlFav + "&genre=" + genre;
                    Object[] request = {"addURL", this, this.url};
                    new RequestCommand().execute(request);
                    break;
                }
                break;
        }
    }

    public void urlAdded(Object response) {
        if (response.equals("1")) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Successfully added radio station", 1);
            toast.show();
            return;
        }
        new AlertDialog.Builder(this).setTitle("Error!").setMessage("Error: Favorite could not be saved. Please try again.").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.IRadioTab.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
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

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        updatePlayer();
    }
}
