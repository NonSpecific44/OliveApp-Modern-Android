package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.LinkedList;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class ZonesTab extends Activity {
    TrackAdapter adapter;
    ListView list;
    String devIP = null;
    boolean processing = false;
    String objId = null;
    int startindex = 0;
    LinkedList<String> devices = new LinkedList<>();
    TextView tf_devIP = null;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.list = (ListView) findViewById(R.id.ListViewDevices);
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        this.devIP = this.devIP.substring(0, this.devIP.lastIndexOf(".") + 1);
        this.tf_devIP = (TextView) findViewById(R.id.tf_DevIP);
        this.tf_devIP.setText(this.devIP);
        if (!settings.equals("none")) {
            for (int i = 0; settings.getString("device" + i, null) != null; i++) {
                if (!this.devices.contains(settings.getString("device" + i, null))) {
                    this.devices.add(settings.getString("device" + i, null));
                }
            }
        }
        this.list.setAdapter((ListAdapter) new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.devices));
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.ZonesTab.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                ZonesTab.this.loadDevice(ZonesTab.this.list.getItemAtPosition(position).toString());
            }
        });
        ((Button) findViewById(R.id.connect)).setOnClickListener(new View.OnClickListener() { // from class: olive.app.ZonesTab.2
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                if (!ZonesTab.this.tf_devIP.getText().toString().equals(EXTHeader.DEFAULT_VALUE)) {
                    ZonesTab.this.loadDevice(ZonesTab.this.tf_devIP.getText().toString());
                } else {
                    new AlertDialog.Builder(ZonesTab.this).setTitle("Error!").setMessage("Please enter an IP address first.").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.ZonesTab.2.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });
    }

    public void loadDevice(String ip) {
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("devIP", ip);
        if (!this.devices.contains(ip)) {
            this.devices.addFirst(ip);
        }
        int i = 0;
        while (this.devices.size() > 0) {
            prefEditor.putString("device" + i, this.devices.getFirst());
            this.devices.removeFirst();
            i++;
        }
        prefEditor.commit();
        new SendCommand().execute("http://" + this.tf_devIP.getText().toString() + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
        startActivity(new Intent(this, (Class<?>) LoadTabs.class));
        finish();
        super.finish();
    }
}
