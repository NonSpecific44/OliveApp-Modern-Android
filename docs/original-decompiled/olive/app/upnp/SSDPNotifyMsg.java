package olive.app.upnp;

import java.net.DatagramPacket;

/* loaded from: classes.dex */
public class SSDPNotifyMsg {
    public static boolean isSSDPNotifyMsg(DatagramPacket dp) {
        String startLine = SSDP.parseStartLine(dp);
        return SSDP.SL_NOTIFY.equals(startLine);
    }

    public static boolean isAlive(DatagramPacket dp) {
        String NTSValue = SSDP.parseHeaderValue(dp, SSDP.NTS);
        return SSDP.NTS_ALIVE.equals(NTSValue);
    }

    public static boolean isByeByte(DatagramPacket dp) {
        String NTSValue = SSDP.parseHeaderValue(dp, SSDP.NTS);
        return SSDP.NTS_BYEBYE.equals(NTSValue);
    }

    public static boolean isUpdate(DatagramPacket dp) {
        String NTSValue = SSDP.parseHeaderValue(dp, SSDP.NTS);
        return SSDP.NTS_UPDATE.equals(NTSValue);
    }

    public static boolean isContentDirectory(DatagramPacket dp) {
        String NTValue = SSDP.parseHeaderValue(dp, SSDP.NT);
        return "urn:schemas-upnp-org:service:ContentDirectory:1".equals(NTValue);
    }

    public static boolean isMediaServer(DatagramPacket dp) {
        String NTValue = SSDP.parseHeaderValue(dp, SSDP.NT);
        return "urn:schemas-upnp-org:device:MediaServer:1".equals(NTValue);
    }

    public static boolean isMediaRenderer(DatagramPacket dp) {
        String NTValue = SSDP.parseHeaderValue(dp, SSDP.NT);
        return "urn:schemas-upnp-org:device:MediaRenderer:1".equals(NTValue);
    }
}
