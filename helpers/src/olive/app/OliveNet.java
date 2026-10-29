package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Finds Olives by scanning networks with ordinary (unicast) connections. The original discovery uses
 * SSDP multicast, which routers don't forward, so it only finds players on the phone's own subnet.
 * This also scans subnets the user has used before or names explicitly, which works across VLANs and VPNs.
 */
public final class OliveNet {
    private static final String PREFS = "Olive_Settings";
    private static final String SAVED_SUBNETS = "scanSubnets";
    private static final int MAX_HOSTS = 1024;

    private OliveNet() {
    }

    private static int id(Activity a, String name) {
        return a.getResources().getIdentifier(name, "id", a.getPackageName());
    }

    /** Called when the device picker opens: shows the scan button and scans known subnets in the background. */
    public static void setupPicker(final Activity picker) {
        View button = picker.findViewById(id(picker, "scanNetwork"));
        if (button != null) {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    askForSubnet(picker);
                }
            });
        }
        final List<String> subnets = knownSubnets(picker);
        if (!subnets.isEmpty()) {
            // After the picker's own first search step, which sets the status line.
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public void run() {
                    scan(picker, subnets, false);
                }
            }, 100);
        }
    }

    /** The phone's own Wi-Fi subnet, subnets of previously used players, and subnets scanned before. */
    static List<String> knownSubnets(Activity a) {
        Set<String> out = new LinkedHashSet<String>();
        try {
            WifiManager wifi = (WifiManager) a.getApplicationContext().getSystemService(Activity.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            int ip = info == null ? 0 : info.getIpAddress();
            if (ip != 0) {
                out.add((ip & 255) + "." + (ip >> 8 & 255) + "." + (ip >> 16 & 255) + ".0/24");
            }
        } catch (RuntimeException ignored) {
        }
        SharedPreferences prefs = a.getSharedPreferences(PREFS, 0);
        List<String> used = new ArrayList<String>();
        used.add(prefs.getString("devIP", ""));
        for (int i = 0; i < 5; i++) {
            used.add(prefs.getString("device" + i, ""));
        }
        for (String host : used) {
            String subnet = subnetOf(host);
            if (subnet != null) {
                out.add(subnet);
            }
        }
        for (String s : prefs.getString(SAVED_SUBNETS, "").split(",")) {
            if (s.trim().length() > 0) {
                out.add(s.trim());
            }
        }
        return new ArrayList<String>(out);
    }

    /** "10.50.10.7" or "10.50.10.7:80" to "10.50.10.0/24"; null for hostnames. */
    static String subnetOf(String host) {
        if (host == null) {
            return null;
        }
        Matcher m = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.\\d{1,3}(:\\d+)?$").matcher(host.trim());
        return m.find() ? m.group(1) + "." + m.group(2) + "." + m.group(3) + ".0/24" : null;
    }

    /**
     * Hosts in a subnet written as "10.50.10.0/24", "10.50.10.*", "10.50.10", "10.50.10." or a single
     * address (meaning its /24). Prefixes from /22 (1022 hosts) to /32. Returns null if not understood.
     */
    static List<String> hostsIn(String subnet) {
        String s = subnet.trim().replace("*", "0");
        int prefix = 24;
        int slash = s.indexOf('/');
        if (slash >= 0) {
            try {
                prefix = Integer.parseInt(s.substring(slash + 1).trim());
            } catch (NumberFormatException e) {
                return null;
            }
            s = s.substring(0, slash).trim();
        }
        while (s.endsWith(".")) {
            s = s.substring(0, s.length() - 1);
        }
        String[] parts = s.split("\\.");
        if (parts.length == 3) {
            s = s + ".0";
            parts = s.split("\\.");
        }
        if (parts.length != 4 || prefix < 22 || prefix > 32) {
            return null;
        }
        long addr = 0;
        for (String p : parts) {
            int v;
            try {
                v = Integer.parseInt(p);
            } catch (NumberFormatException e) {
                return null;
            }
            if (v < 0 || v > 255) {
                return null;
            }
            addr = addr << 8 | v;
        }
        long mask = prefix == 0 ? 0 : (0xffffffffL << (32 - prefix)) & 0xffffffffL;
        long network = addr & mask;
        long size = 1L << (32 - prefix);
        List<String> hosts = new ArrayList<String>();
        for (long i = 0; i < size && hosts.size() < MAX_HOSTS; i++) {
            long a = network + i;
            if (size > 2 && (i == 0 || i == size - 1)) {
                continue; // network and broadcast addresses
            }
            hosts.add((a >> 24 & 255) + "." + (a >> 16 & 255) + "." + (a >> 8 & 255) + "." + (a & 255));
        }
        return hosts;
    }

    private static void askForSubnet(final Activity picker) {
        final EditText input = new EditText(picker);
        input.setSingleLine(true);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        List<String> known = knownSubnets(picker);
        input.setText(known.isEmpty() ? "192.168.1.0/24" : known.get(known.size() - 1));
        input.setSelectAllOnFocus(true);
        FrameLayout box = new FrameLayout(picker);
        int pad = Math.round(24 * picker.getResources().getDisplayMetrics().density);
        box.setPadding(pad, pad / 3, pad, 0);
        box.addView(input);
        new AlertDialog.Builder(picker)
                .setTitle("Scan a network")
                .setMessage("Enter the network your Olive is on, e.g. 10.50.10.0/24. Use this when the Olive is on a different subnet or VLAN from your phone.")
                .setView(box)
                .setPositiveButton("Scan", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int which) {
                        String subnet = input.getText().toString().trim();
                        if (hostsIn(subnet) == null) {
                            new AlertDialog.Builder(picker).setTitle("Not a network")
                                    .setMessage("Use a form like 10.50.10.0/24 (from /22 to /32).")
                                    .setPositiveButton("OK", null).show();
                            return;
                        }
                        List<String> one = new ArrayList<String>();
                        one.add(subnet);
                        scan(picker, one, true);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /** Scans the subnets in the background and adds any Olives found to the picker's list. */
    static void scan(final Activity picker, final List<String> subnets, final boolean remember) {
        final Handler main = new Handler(Looper.getMainLooper());
        final List<String> hosts = new ArrayList<String>();
        for (String subnet : subnets) {
            List<String> h = hostsIn(subnet);
            if (h != null) {
                hosts.addAll(h);
            }
        }
        final String label = subnets.size() == 1 ? subnets.get(0) : subnets.size() + " networks";
        status(picker, "Scanning " + label + "…", true);
        final AtomicInteger found = new AtomicInteger();
        final ExecutorService pool = Executors.newFixedThreadPool(48);
        for (final String host : hosts) {
            pool.execute(new Runnable() {
                public void run() {
                    final String name = probe(host, 80, 1900, 400);
                    if (name != null) {
                        found.incrementAndGet();
                        main.post(new Runnable() {
                            public void run() {
                                addResult(picker, name, host);
                            }
                        });
                    }
                }
            });
        }
        pool.shutdown();
        new Thread(new Runnable() {
            public void run() {
                try {
                    pool.awaitTermination(2, TimeUnit.MINUTES);
                } catch (InterruptedException ignored) {
                }
                main.post(new Runnable() {
                    public void run() {
                        int n = found.get();
                        if (n > 0 && remember) {
                            rememberSubnets(picker, subnets);
                        }
                        status(picker, n > 0 ? "Found " + n + " Olive" + (n == 1 ? "" : "s") + " on " + label + ". Tap one to connect."
                                : "No Olive found on " + label + ". Check the network, or enter the address above.", false);
                    }
                });
            }
        }).start();
    }

    /**
     * Returns the player's name if host runs an Olive web interface (its now-playing endpoint answers
     * with playmetadata), else null. The name comes from the player's UPnP description when it answers.
     */
    public static String probe(String host, int httpPort, int ssdpPort, int connectTimeoutMs) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, httpPort), connectTimeoutMs);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                socket.close();
            } catch (Exception ignored) {
            }
        }
        String base = "http://" + host + (httpPort == 80 ? "" : ":" + httpPort);
        String xml = OliveMediaService.read(base + "/server/getMetadata.php", 3000);
        if (xml == null || !xml.toLowerCase().contains("playmetadata")) {
            return null;
        }
        String name = upnpName(host, ssdpPort);
        return name != null ? name : "Olive";
    }

    /** Friendly name from a unicast SSDP search; brackets removed because the picker parses "Name (address)". */
    static String upnpName(String host, int ssdpPort) {
        DatagramSocket udp = null;
        try {
            udp = new DatagramSocket();
            udp.setSoTimeout(800);
            byte[] msg = ("M-SEARCH * HTTP/1.1\r\nHOST: 239.255.255.250:1900\r\nMAN: \"ssdp:discover\"\r\nMX: 1\r\n"
                    + "ST: upnp:rootdevice\r\n\r\n").getBytes("US-ASCII");
            udp.send(new DatagramPacket(msg, msg.length, InetAddress.getByName(host), ssdpPort));
            byte[] buf = new byte[2048];
            DatagramPacket reply = new DatagramPacket(buf, buf.length);
            udp.receive(reply);
            Matcher loc = Pattern.compile("(?im)^LOCATION:\\s*(\\S+)").matcher(new String(buf, 0, reply.getLength(), "US-ASCII"));
            if (!loc.find()) {
                return null;
            }
            String desc = OliveMediaService.read(loc.group(1).replace("127.0.0.1", host), 3000);
            if (desc == null) {
                return null;
            }
            Matcher m = Pattern.compile("<friendlyName>([^<]+)</friendlyName>").matcher(desc);
            if (!m.find()) {
                return null;
            }
            String name = m.group(1).replace("Renderer", "").replace("(", "").replace(")", "").trim();
            return name.length() > 0 ? name : null;
        } catch (Exception e) {
            return null;
        } finally {
            if (udp != null) {
                udp.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    static void addResult(Activity picker, String name, String host) {
        // While SSDP search runs, the picker rebuilds its list from the ControlPoint's device map every 2s,
        // so add the result there too (keyed by address, like SSDP results) or it would be wiped.
        try {
            Field cpField = picker.getClass().getDeclaredField("cp");
            cpField.setAccessible(true);
            Object cp = cpField.get(picker);
            if (cp != null) {
                Method getDevices = cp.getClass().getMethod("getDevices");
                ((Map<String, String>) getDevices.invoke(cp)).put(host, name);
            }
        } catch (Exception ignored) {
        }
        ListView list = (ListView) picker.findViewById(id(picker, "ListViewDevices"));
        ListAdapter adapter = list == null ? null : list.getAdapter();
        if (adapter instanceof ArrayAdapter) {
            ArrayAdapter<String> items = (ArrayAdapter<String>) adapter;
            String row = name + " (" + host + ")";
            for (int i = 0; i < items.getCount(); i++) {
                if (items.getItem(i).endsWith("(" + host + ")")) {
                    return;
                }
            }
            items.add(row);
            items.notifyDataSetChanged();
        }
    }

    private static void rememberSubnets(Activity a, List<String> subnets) {
        SharedPreferences prefs = a.getSharedPreferences(PREFS, 0);
        Set<String> saved = new LinkedHashSet<String>();
        for (String s : subnets) {
            saved.add(s.trim());
        }
        for (String s : prefs.getString(SAVED_SUBNETS, "").split(",")) {
            if (s.trim().length() > 0 && saved.size() < 5) {
                saved.add(s.trim());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : saved) {
            sb.append(sb.length() == 0 ? "" : ",").append(s);
        }
        prefs.edit().putString(SAVED_SUBNETS, sb.toString()).apply();
    }

    private static void status(Activity picker, String text, boolean busy) {
        TextView t = (TextView) picker.findViewById(id(picker, "searchDevicesText"));
        View spinner = picker.findViewById(id(picker, "searchLoadingIcon"));
        if (t != null) {
            t.setVisibility(View.VISIBLE);
            t.setText(text);
        }
        if (spinner != null) {
            spinner.setVisibility(busy ? View.VISIBLE : View.GONE);
        }
    }
}
