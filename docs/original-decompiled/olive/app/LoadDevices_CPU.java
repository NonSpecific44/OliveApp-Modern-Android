package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import olive.app.upnp.ControlPoint;
import olive.app.upnp.ControlPointListener;
import org.teleal.cling.android.AndroidUpnpService;
import org.teleal.cling.android.AndroidUpnpServiceImpl;
import org.teleal.cling.model.ServiceReference;
import org.teleal.cling.model.message.header.EXTHeader;
import org.teleal.cling.model.message.header.UDADeviceTypeHeader;
import org.teleal.cling.model.meta.Device;
import org.teleal.cling.model.types.UDADeviceType;
import org.teleal.cling.registry.RegistryListener;

/* loaded from: classes.dex */
public class LoadDevices_CPU extends Activity {
    private ArrayAdapter<String> listAdapter;
    private AndroidUpnpService upnpService;
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
    private RegistryListener registryListener = new BrowseRegistryListener();
    private ServiceConnection serviceConnection = new ServiceConnection() { // from class: olive.app.LoadDevices_CPU.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            LoadDevices_CPU.this.upnpService = (AndroidUpnpService) service;
            UDADeviceType udaType = new UDADeviceType("MediaServer");
            LoadDevices_CPU.this.upnpService.getControlPoint().search(new UDADeviceTypeHeader(udaType));
            UDADeviceType udaType2 = new UDADeviceType("MediaRenderer");
            LoadDevices_CPU.this.upnpService.getControlPoint().search(new UDADeviceTypeHeader(udaType2));
            for (Device device : LoadDevices_CPU.this.upnpService.getRegistry().getDevices()) {
                ((BrowseRegistryListener) LoadDevices_CPU.this.registryListener).deviceAdded(device);
            }
            LoadDevices_CPU.this.upnpService.getRegistry().addListener(LoadDevices_CPU.this.registryListener);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            LoadDevices_CPU.this.upnpService.getRegistry().shutdown();
            LoadDevices_CPU.this.upnpService = null;
        }
    };
    private Runnable startSearch = new Runnable() { // from class: olive.app.LoadDevices_CPU.2
        @Override // java.lang.Runnable
        public void run() {
            LoadDevices_CPU.this.searchUPnPDevices();
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
        connect.setOnClickListener(new View.OnClickListener() { // from class: olive.app.LoadDevices_CPU.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String ip = LoadDevices_CPU.this.tf_devIP.getText().toString();
                if (ip.equals(EXTHeader.DEFAULT_VALUE)) {
                    new AlertDialog.Builder(LoadDevices_CPU.this).setTitle("Error!").setMessage("Please enter an IP address.").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.LoadDevices_CPU.3.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                    return;
                }
                LoadDevices_CPU.this.cancelSearch();
                SharedPreferences settings2 = LoadDevices_CPU.this.getSharedPreferences("Olive_Settings", 0);
                SharedPreferences.Editor prefEditor = settings2.edit();
                prefEditor.putString("devIP", ip);
                if (!LoadDevices_CPU.this.devices.contains(ip)) {
                    LoadDevices_CPU.this.devices.addFirst(ip);
                }
                int i2 = 0;
                while (LoadDevices_CPU.this.devices.size() > 0) {
                    prefEditor.putString("device" + i2, LoadDevices_CPU.this.devices.getFirst());
                    LoadDevices_CPU.this.devices.removeFirst();
                    i2++;
                }
                prefEditor.commit();
                new SendCommand().execute("http://" + ip + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reload", "yes");
                LoadDevices_CPU.this.setResult(-1, resultIntent);
                LoadDevices_CPU.this.finish();
            }
        });
        ListView deviceList = (ListView) findViewById(R.id.ListViewDevices);
        this.listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceList.setAdapter((ListAdapter) this.listAdapter);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.LoadDevices_CPU.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                LoadDevices_CPU.this.cancelSearch();
                TextView textView = (TextView) itemClicked;
                String ip = textView.getText().toString();
                String ip2 = ip.substring(ip.indexOf("(") + 1, ip.length() - 1);
                new SendCommand().execute("http://" + ip2 + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
                SharedPreferences settings2 = LoadDevices_CPU.this.getSharedPreferences("Olive_Settings", 0);
                SharedPreferences.Editor prefEditor = settings2.edit();
                prefEditor.putString("devIP", ip2);
                prefEditor.commit();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("reload", "yes");
                LoadDevices_CPU.this.setResult(-1, resultIntent);
                LoadDevices_CPU.this.finish();
            }
        });
        getApplicationContext().bindService(new Intent(this, (Class<?>) AndroidUpnpServiceImpl.class), this.serviceConnection, 1);
        this.loading = new LoadingDevices().execute(new Object[]{this});
    }

    public String getURL() {
        return this.url;
    }

    public void displayDevices() {
        if (!this.devices.equals(((BrowseRegistryListener) this.registryListener).getDevices())) {
            this.listAdapter.clear();
            this.deviceList = ((BrowseRegistryListener) this.registryListener).getDevices();
            Enumeration<String> enumDev = this.deviceList.elements();
            while (enumDev.hasMoreElements()) {
                String element = enumDev.nextElement();
                if (this.listDev.size() > 0) {
                    if (!this.listDev.contains(element)) {
                        this.listAdapter.add(element);
                    }
                } else {
                    this.listAdapter.add(element);
                }
            }
            for (int i = 0; i < this.listDev.size(); i++) {
                this.listAdapter.add(this.listDev.get(i));
            }
            this.listAdapter.notifyDataSetChanged();
        }
        this.loading = new LoadingDevices().execute(new Object[]{this});
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
        if (this.upnpService != null) {
            this.upnpService.getRegistry().removeListener(this.registryListener);
        }
    }

    public void onDestroy(Bundle savedInstanceState) {
        if (this.upnpService != null) {
            this.upnpService.getRegistry().removeListener(this.registryListener);
        }
        super.onDestroy();
    }

    public void searchUPnPDevices() {
        if (this.searchSent_count == 0 || this.searchSent_count % 2 == 0) {
            WifiManager.MulticastLock _wifiMulticastLock = ((WifiManager) getSystemService("wifi")).createMulticastLock("_wifiMulticastLock");
            _wifiMulticastLock.setReferenceCounted(true);
            _wifiMulticastLock.acquire();
            WifiManager wm = (WifiManager) getSystemService("wifi");
            WifiManager.MulticastLock multicastLock = wm.createMulticastLock("multicast");
            multicastLock.acquire();
            WifiManager wifiManager = (WifiManager) getSystemService("wifi");
            WifiInfo wifiinfo = wifiManager.getConnectionInfo();
            int intaddr = wifiinfo.getIpAddress();
            try {
                new ControlPointListener() { // from class: olive.app.LoadDevices_CPU.5
                    @Override // olive.app.upnp.ControlPointListener
                    public void onDeviceAdd(olive.app.upnp.Device device) {
                        String url = device.getURL();
                        String[] ip = url.split(ServiceReference.DELIMITER);
                        String url2 = ip[2].split(":")[0];
                        LoadDevices_CPU.this.listAdapter.add(String.valueOf(device.toString()) + " (" + url2 + ")");
                        LoadDevices_CPU.this.listAdapter.notifyDataSetChanged();
                    }

                    @Override // olive.app.upnp.ControlPointListener
                    public void onDeviceRemove(olive.app.upnp.Device device) {
                        System.out.println("device removed");
                    }
                };
                this.cp = new ControlPoint(intaddr);
                this.cp.search();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.searchSent_count++;
        }
        displayDevices();
    }
}
