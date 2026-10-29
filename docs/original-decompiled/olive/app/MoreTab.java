package olive.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/* loaded from: classes.dex */
public class MoreTab extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moretab);
        ListView more = (ListView) findViewById(R.id.ListViewMore);
        String[] more_arr = {"Playlists", "Genre", "Internet Radio", "Music Sources", "Zones", "Settings"};
        more.setAdapter((ListAdapter) new ArrayAdapter(this, android.R.layout.simple_list_item_1, more_arr));
        more.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.MoreTab.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                TextView textView = (TextView) itemClicked;
                String task = textView.getText().toString();
                if (task == "Playlists") {
                    Bundle bundle = new Bundle();
                    bundle.putString("objId", "ROOT_ALLPL");
                    bundle.putString("title", "My Playlists:");
                    Intent intent = new Intent(MoreTab.this, (Class<?>) PlaylistTab.class);
                    intent.putExtras(bundle);
                    MoreTab.this.startActivity(intent);
                    return;
                }
                if (task == "Genre") {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("objId", "ROOT_ALLGE");
                    bundle2.putString("title", "Genres:");
                    Intent intent2 = new Intent(MoreTab.this, (Class<?>) PlaylistTab.class);
                    intent2.putExtras(bundle2);
                    MoreTab.this.startActivity(intent2);
                    return;
                }
                if (task == "Internet Radio") {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("objId", "ROOT_ALLIR");
                    bundle3.putString("title", "Internet Radio:");
                    bundle3.putString("url", "http://" + MoreTab.this.getSharedPreferences("Olive_Settings", 0).getString("devIP", "none") + "/server/iGetRadio.php?command=initRadio");
                    Intent intent3 = new Intent(MoreTab.this, (Class<?>) IRadioTab.class);
                    intent3.putExtras(bundle3);
                    MoreTab.this.startActivity(intent3);
                    return;
                }
                if (task == "Music Sources") {
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("title", "Music Sources:");
                    bundle4.putString("url", "http://" + MoreTab.this.getSharedPreferences("Olive_Settings", 0).getString("devIP", "none") + "/server/getMusicSources.php?xmlType=noAttribute");
                    Intent intent4 = new Intent(MoreTab.this, (Class<?>) SourcesTab.class);
                    intent4.putExtras(bundle4);
                    MoreTab.this.startActivity(intent4);
                    return;
                }
                if (task == "Zones") {
                    MoreTab.this.startActivityForResult(new Intent(MoreTab.this, (Class<?>) LoadDevices.class), 184);
                } else if (task == "Settings") {
                    MoreTab.this.startActivity(new Intent(MoreTab.this, (Class<?>) SettingsTab.class));
                }
            }
        });
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 184:
                try {
                    if (data.getStringExtra("reload").equals("yes")) {
                        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
                        new SendCommand().execute("http://" + settings.getString("devIP", "none") + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
                        LoadTabs.reload(true, 0);
                        LoadTabs.reload(true, 1);
                        LoadTabs.reload(true, 2);
                        startActivity(new Intent(this, (Class<?>) LoadTabs.class));
                        break;
                    }
                } catch (Exception e) {
                    return;
                }
                break;
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }
}
