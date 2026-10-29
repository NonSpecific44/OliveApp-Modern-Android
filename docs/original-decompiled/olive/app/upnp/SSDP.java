package olive.app.upnp;

import java.net.DatagramPacket;
import java.util.Scanner;

/* loaded from: classes.dex */
public class SSDP {
    public static final String ADDRESS = "239.255.255.250";
    public static final String LOCATION = "LOCATION";
    public static final String NEWLINE = "\r\n";
    public static final String NT = "NT";
    public static final String NTS = "NTS";
    public static final String NTS_ALIVE = "ssdp:alive";
    public static final String NTS_BYEBYE = "ssdp:byebye";
    public static final String NTS_UPDATE = "ssdp:update";
    public static final String NT_CONTENT_DIRECTORY = "urn:schemas-upnp-org:service:ContentDirectory:1";
    public static final String NT_MEDIA_RENDERER = "urn:schemas-upnp-org:device:MediaRenderer:1";
    public static final String NT_MEDIA_SERVER = "urn:schemas-upnp-org:device:MediaServer:1";
    public static final int PORT = 1900;
    public static final String SL_MSEARCH = "M-SEARCH * HTTP/1.1";
    public static final String SL_NOTIFY = "NOTIFY * HTTP/1.1";
    public static final String SL_OK = "HTTP/1.1 200 OK";
    public static final String ST = "ST";
    public static final String ST_ContentDirectory = "ST:urn:schemas-upnp-org:service:ContentDirectory:1";
    public static final String ST_MediaRenderer = "ST:urn:schemas-upnp-org:device:MediaRenderer:1";
    public static final String ST_MediaServer = "ST:urn:schemas-upnp-org:device:MediaServer:1";

    public static String parseHeaderValue(String content, String headerName) {
        Scanner s = new Scanner(content);
        s.nextLine();
        while (s.hasNextLine()) {
            String line = s.nextLine();
            int index = line.indexOf(58);
            if (index > -1) {
                String header = line.substring(0, index);
                if (headerName.equalsIgnoreCase(header.trim())) {
                    return line.substring(index + 1).trim();
                }
            }
        }
        return null;
    }

    public static String[] parseHeaderValues(String content) {
        Scanner s = new Scanner(content);
        s.nextLine();
        String[] values = new String[3];
        while (s.hasNextLine()) {
            String line = s.nextLine();
            int index = line.indexOf(58);
            if (index > -1) {
                String header = line.substring(0, index);
                if (LOCATION.equalsIgnoreCase(header.trim())) {
                    values[0] = line.substring(index + 1).trim();
                } else if ("SERVER".equalsIgnoreCase(header.trim())) {
                    values[1] = line.substring(index + 1).trim();
                } else if (NTS.equalsIgnoreCase(header.trim())) {
                    values[2] = line.substring(index + 1).trim();
                }
            }
        }
        return values;
    }

    public static String parseHeaderValue(DatagramPacket dp, String headerName) {
        return parseHeaderValue(new String(dp.getData()), headerName);
    }

    public static String parseStartLine(String content) {
        Scanner s = new Scanner(content);
        return s.nextLine();
    }

    public static String parseStartLine(DatagramPacket dp) {
        return parseStartLine(new String(dp.getData()));
    }
}
