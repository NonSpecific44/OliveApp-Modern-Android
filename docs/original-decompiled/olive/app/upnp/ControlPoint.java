package olive.app.upnp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ControlPoint {
    private SSDPSocket mSSDPSocket;
    boolean stopRun = true;
    private Map<String, String> mCache = new HashMap();
    private ControlPointListener mListener = null;
    private Runnable mRespNotifyHandler = new Runnable() { // from class: olive.app.upnp.ControlPoint.1
        @Override // java.lang.Runnable
        public void run() {
            while (ControlPoint.this.stopRun) {
                try {
                    DatagramPacket dp = ControlPoint.this.mSSDPSocket.receive();
                    System.out.println("received: " + new String(dp.getData(), dp.getOffset(), dp.getLength()));
                    if (SSDPRespMsg.isSSDPRespMsg(dp) || SSDPNotifyMsg.isSSDPNotifyMsg(dp)) {
                        ControlPoint.this.handleRespMsg(dp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public ControlPoint(int inetaddr) throws Exception {
        this.mSSDPSocket = new SSDPSocket(inetaddr);
        try {
            new Thread(this.mRespNotifyHandler).start();
        } catch (Exception b) {
            throw b;
        }
    }

    public void search(String type) throws IOException {
        SSDPSearchMsg search = new SSDPSearchMsg(type);
        this.mSSDPSocket.send(search.toString());
    }

    public void search() throws IOException {
        search(SSDP.ST_MediaServer);
    }

    public void registerListener(ControlPointListener listener) {
        this.mListener = listener;
    }

    public void unregisterListener() {
        this.mListener = null;
    }

    public void close() {
        this.stopRun = false;
        if (this.mSSDPSocket != null) {
            this.mSSDPSocket.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRespMsg(DatagramPacket dp) {
        String[] values = SSDP.parseHeaderValues(new String(dp.getData()));
        if (values[2] != null && !values[2].equals(SSDP.NTS_BYEBYE)) {
            if (values[0].indexOf("MediaServer1") > -1) {
                notifyDeviceAdd(values[0]);
            } else if (values[0].indexOf("upnpdesc_1") > -1 && values[1].toLowerCase().indexOf("server") > -1) {
                notifyDeviceAdd(values[0].replace("upnpdesc_", "MediaServer"));
            }
        }
    }

    private void notifyDeviceAdd(String url) {
        Device device = Device.createInstanceFromXML(url);
        if (device.mDeviceType == "Melody") {
            device = Device.createInstanceFromXML(url.replace("MediaServer", "upnpdesc_"));
        }
        if (device != null) {
            System.out.println("Add " + device + "[" + url + "]");
            String deviceURL = extractURL(url);
            if (deviceURL != null && !this.mCache.containsKey(deviceURL)) {
                if (device.mFriendlyName == null) {
                    System.out.println("wrong name?");
                } else {
                    this.mCache.put(deviceURL, device.mFriendlyName);
                }
            } else {
                System.out.println("duplicate");
            }
            if (this.mListener != null) {
                this.mListener.onDeviceAdd(device);
            }
        }
    }

    private String extractURL(String url) {
        String[] urlArr = url.split(":");
        if (urlArr.length <= 1) {
            return null;
        }
        String url2 = urlArr[1].substring(2, urlArr[1].length());
        return url2;
    }

    public Map<String, String> getDevices() {
        return this.mCache;
    }
}
