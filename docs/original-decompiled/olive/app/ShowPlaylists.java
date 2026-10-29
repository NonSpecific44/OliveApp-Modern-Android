package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class ShowPlaylists extends Activity {
    String devIP;
    ProgressDialog myDialog;
    String objId;
    ListView pls;
    Items items = null;
    EditText plName = null;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add2playlist);
        this.plName = (EditText) findViewById(R.id.newPLname);
        Bundle bundle = getIntent().getExtras();
        this.objId = bundle.getString("objId");
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        this.pls = (ListView) findViewById(R.id.ListViewPLs);
        this.pls.setFastScrollEnabled(true);
        InputFilter filter = new InputFilter() { // from class: olive.app.ShowPlaylists.1
            @Override // android.text.InputFilter
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i)) && String.valueOf(source.charAt(i)).equals("\n") && !String.valueOf(source.charAt(i)).equals(" ")) {
                        new AlertDialog.Builder(ShowPlaylists.this).setTitle("Error!").setMessage("Please only use characters a-z, A-Z or 0-9.").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.ShowPlaylists.1.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                        return EXTHeader.DEFAULT_VALUE;
                    }
                }
                return null;
            }
        };
        this.plName.setFilters(new InputFilter[]{filter});
        findViewById(R.id.btnNewPL).setOnClickListener(new View.OnClickListener() { // from class: olive.app.ShowPlaylists.2
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                if (ShowPlaylists.this.plName.getText().length() > 0) {
                    Object[] data = {"createPlaylist", ShowPlaylists.this.devIP, ShowPlaylists.this, ShowPlaylists.this.plName.getText()};
                    new RequestCommand().execute(data);
                    ShowPlaylists.this.finish();
                } else {
                    Context context = ShowPlaylists.this.getApplicationContext();
                    Toast toast = Toast.makeText(context, "Please enter the name of the playlist you want to add this song to.", 1);
                    toast.show();
                }
            }
        });
        if (this.devIP != "none") {
            try {
                this.myDialog = ProgressDialog.show(this, "Working...", "Loading playlists...", true, false);
                Object[] data = {"add2playlist", this.devIP, this};
                new RequestCommand().execute(data);
                return;
            } catch (Exception e) {
                if (e.toString().indexOf("java.io.FileNotFoundException") > -1) {
                    new AlertDialog.Builder(this).setTitle("Warning!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.ShowPlaylists.3
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                    return;
                }
                return;
            }
        }
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.forceClose).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.ShowPlaylists.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    public void setItems(Object items) {
        if (items != null) {
            if (this.items == null) {
                this.items = (Items) items;
            } else {
                this.items.addItems((Items) items);
            }
            this.items.setError(((Items) items).getError());
        }
    }

    public void showPlaylists() {
        this.myDialog.dismiss();
        if (this.items != null) {
            ArrayList<String> title_arr = this.items.getTitle();
            this.pls = (ListView) findViewById(R.id.ListViewPLs);
            this.pls.setAdapter((ListAdapter) new ArrayAdapter(this, android.R.layout.simple_list_item_1, title_arr));
            this.pls.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.ShowPlaylists.5
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                    Object[] data = {"addTrack2Playlist", ShowPlaylists.this.devIP, ShowPlaylists.this, ShowPlaylists.this.items.getObjId().get(position), ShowPlaylists.this.objId};
                    new RequestCommand().execute(data);
                    ShowPlaylists.this.finish();
                }
            });
            return;
        }
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setPositiveButton("Try Again", new DialogInterface.OnClickListener() { // from class: olive.app.ShowPlaylists.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                ShowPlaylists.this.myDialog = ProgressDialog.show(ShowPlaylists.this, "Working...", "Loading all playlists...", true, false);
            }
        }).show();
    }

    public void displayError(String errorMsg) {
        this.myDialog.dismiss();
        Toast.makeText(this, R.string.connectionError, 10000);
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.ShowPlaylists.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    public void onDestroy(Bundle savedInstanceState) {
    }

    public void add2playlist(Object response) {
        if (response.toString().indexOf("nt_refresh_playlist") >= 0) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Playlist created", 1);
            toast.show();
            String[] plId = response.toString().split("'");
            Object[] data = {"addTrack2Playlist", this.devIP, this, plId[1], this.objId};
            new RequestCommand().execute(data);
            return;
        }
        if (response.toString().indexOf("exists already") >= 0) {
            Context context2 = getApplicationContext();
            Toast toast2 = Toast.makeText(context2, "Playlist already exists. Please try again", 1);
            toast2.show();
        } else if (response.toString().indexOf("inf_show_PLcreated();") >= 0) {
            Context context3 = getApplicationContext();
            Toast toast3 = Toast.makeText(context3, "Successfully added track to playlist", 1);
            toast3.show();
        } else {
            CharSequence text = "An unknown error occured. Please try again";
            if (response.toString().indexOf("Could not add all items to playlist") >= 0) {
                text = "Could not add all items to playlist. Please try again.";
            }
            Context context4 = getApplicationContext();
            Toast toast4 = Toast.makeText(context4, text, 1);
            toast4.show();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
