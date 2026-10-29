package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import olive.app.upnp.ControlPoint;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class LoadDevices extends Activity {
    private ArrayAdapter<String> listAdapter;
    private AlertDialog myDialog;
    Handler mHandler = new Handler();
    byte[] d = new byte[1024];
    DatagramPacket received = new DatagramPacket(this.d, this.d.length);
    ControlPoint cp = null;
    int searchSent_count = 0;
    LinkedList<String> devices = new LinkedList<>();
    EditText tf_devIP = null;
    AsyncTask<Object[], Void, String> loading = null;
    private String url = EXTHeader.DEFAULT_VALUE;
    ArrayList<String> listDev = new ArrayList<>();
    Hashtable<String, String> deviceList = new Hashtable<>();
    private Runnable startSearch = new Runnable() { // from class: olive.app.LoadDevices.1
        @Override // java.lang.Runnable
        public void run() {
            LoadDevices.this.searchUPnPDevices();
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.tf_devIP = (EditText) findViewById(R.id.tf_DevIP);
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        String devIP = settings.getString("devIP", "none");
        if (devIP != "none") {
            this.tf_devIP.setText(devIP);
        }
        for (int i = 0; settings.getString("device" + i, null) != null && i < 5; i++) {
            if (!this.devices.contains(settings.getString("device" + i, null))) {
                this.devices.add(settings.getString("device" + i, null));
            }
        }
        Button connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() { // from class: olive.app.LoadDevices.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String ip = LoadDevices.this.tf_devIP.getText().toString();
                if (ip.equals(EXTHeader.DEFAULT_VALUE)) {
                    new AlertDialog.Builder(LoadDevices.this).setTitle("Error!").setMessage("Please enter an IP address.").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.LoadDevices.2.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                    return;
                }
                LoadDevices.this.cancelSearch();
                SharedPreferences settings2 = LoadDevices.this.getSharedPreferences("Olive_Settings", 0);
                SharedPreferences.Editor prefEditor = settings2.edit();
                prefEditor.putString("devIP", ip);
                if (!LoadDevices.this.devices.contains(ip)) {
                    LoadDevices.this.devices.addFirst(ip);
                }
                int i2 = 0;
                while (LoadDevices.this.devices.size() > 0) {
                    prefEditor.putString("device" + i2, LoadDevices.this.devices.getFirst());
                    LoadDevices.this.devices.removeFirst();
                    i2++;
                }
                prefEditor.commit();
                new SendCommand().execute("http://" + ip + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reload", "yes");
                LoadDevices.this.setResult(-1, resultIntent);
                LoadDevices.this.finish();
            }
        });
        ListView deviceList = (ListView) findViewById(R.id.ListViewDevices);
        this.listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceList.setAdapter((ListAdapter) this.listAdapter);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.LoadDevices.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                LoadDevices.this.cancelSearch();
                TextView textView = (TextView) itemClicked;
                String ip = textView.getText().toString();
                String ip2 = ip.substring(ip.indexOf("(") + 1, ip.length() - 1);
                new SendCommand().execute("http://" + ip2 + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
                SharedPreferences settings2 = LoadDevices.this.getSharedPreferences("Olive_Settings", 0);
                SharedPreferences.Editor prefEditor = settings2.edit();
                prefEditor.putString("devIP", ip2);
                prefEditor.commit();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reload", "yes");
                LoadDevices.this.setResult(-1, resultIntent);
                LoadDevices.this.finish();
            }
        });
        Handler mHandler = new Handler();
        mHandler.removeCallbacks(this.startSearch);
        mHandler.postDelayed(this.startSearch, 0L);
    }

    public String getURL() {
        return this.url;
    }

    public void displayDevices() {
        if (this.cp != null) {
            Map<String, String> devices = this.cp.getDevices();
            this.listAdapter.clear();
            Object[] keys = devices.keySet().toArray();
            for (int i = 0; i < keys.length; i++) {
                this.listAdapter.add(String.valueOf(devices.get(keys[i]).toString().replace("Renderer", EXTHeader.DEFAULT_VALUE)) + " (" + keys[i] + ")");
            }
            this.listAdapter.notifyDataSetChanged();
        }
        this.mHandler.removeCallbacks(this.startSearch);
        this.mHandler.postDelayed(this.startSearch, 2000L);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        cancelSearch();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("reload", "no");
        setResult(-1, resultIntent);
        finish();
    }

    public void cancelSearch() {
        ((ProgressBar) findViewById(R.id.searchLoadingIcon)).setVisibility(8);
        ((TextView) findViewById(R.id.searchDevicesText)).setVisibility(8);
        this.mHandler.removeCallbacks(this.startSearch);
        if (this.cp != null) {
            this.cp.close();
        }
    }

    public void onDestroy(Bundle savedInstanceState) {
        this.cp.close();
        super.onDestroy();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void searchUPnPDevices() {
        try {
            if (this.searchSent_count == 0 || this.searchSent_count % 6 == 0 || this.searchSent_count % 7 == 0) {
                WifiManager wifiManager = (WifiManager) getSystemService("wifi");
                WifiInfo wifiinfo = wifiManager.getConnectionInfo();
                int intaddr = wifiinfo.getIpAddress();
                if (this.searchSent_count == 0) {
                    wifiManager.disconnect();
                    wifiManager.reconnect();
                }
                String errorMessage = EXTHeader.DEFAULT_VALUE;
                try {
                    if (this.cp == null) {
                        this.cp = new ControlPoint(intaddr);
                    }
                    if (this.cp != null) {
                        if (this.searchSent_count % 6 == 0) {
                            this.cp.search();
                        } else {
                            this.cp.search("urn:schemas-upnp-org:device:MediaRenderer:1");
                        }
                    }
                } catch (Exception a) {
                    if (a.getMessage().equals("No such device") && this.searchSent_count > 6) {
                        errorMessage = "Unable to establish a network interface. Please re-connect your Android device to the network.";
                    }
                }
                if (errorMessage != EXTHeader.DEFAULT_VALUE || intaddr == 0) {
                    if (intaddr == 0) {
                        errorMessage = "Please connect your Android device to the network.";
                    }
                    if (this.myDialog == null || !this.myDialog.isShowing()) {
                        this.myDialog = new AlertDialog.Builder(this).setTitle("Error!").setMessage(errorMessage).setNeutralButton("OK", new DialogInterface.OnClickListener() { // from class: olive.app.LoadDevices.4
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                    if (intaddr == 0) {
                        cancelSearch();
                        return;
                    }
                }
            }
            this.searchSent_count++;
            displayDevices();
        } catch (UnsupportedOperationException e) {
            if (this.myDialog != null) {
                this.myDialog.dismiss();
            }
            this.myDialog = new AlertDialog.Builder(this).setTitle("Error!").setMessage("Unable to find devices. Please type in the IP-Address manually.").setNeutralButton("OK", new DialogInterface.OnClickListener() { // from class: olive.app.LoadDevices.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    ((TextView) LoadDevices.this.findViewById(R.id.searchDevicesText)).setVisibility(8);
                    ((ProgressBar) LoadDevices.this.findViewById(R.id.searchLoadingIcon)).setVisibility(8);
                }
            }).show();
        }
    }
}
