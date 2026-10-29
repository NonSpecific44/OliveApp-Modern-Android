package olive.app.upnp;

import java.net.DatagramPacket;

/* loaded from: classes.dex */
public class SSDPRespMsg {
    public static boolean isSSDPRespMsg(DatagramPacket dp) {
        String startLine = SSDP.parseStartLine(dp);
        return SSDP.SL_OK.equals(startLine);
    }
}
