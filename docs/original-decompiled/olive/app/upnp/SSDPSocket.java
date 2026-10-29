package olive.app.upnp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;

/* loaded from: classes.dex */
public class SSDPSocket {
    MulticastSocket mLocalSocket;
    NetworkInterface mNetIf;
    SocketAddress mSSDPMulticastGroup;

    public SSDPSocket(int intaddr) throws Exception {
        try {
            byte[] byteaddr = {(byte) (intaddr & 255), (byte) ((intaddr >> 8) & 255), (byte) ((intaddr >> 16) & 255), (byte) ((intaddr >> 24) & 255)};
            InetAddress addr = InetAddress.getByAddress("androidDevice", byteaddr);
            this.mSSDPMulticastGroup = new InetSocketAddress("239.255.255.250", 1900);
            this.mLocalSocket = new MulticastSocket(1900);
            this.mLocalSocket.setTimeToLive(2);
            this.mNetIf = NetworkInterface.getByInetAddress(addr);
            this.mLocalSocket.joinGroup(this.mSSDPMulticastGroup, this.mNetIf);
            this.mLocalSocket.setSoTimeout(15000);
        } catch (Exception a) {
            throw a;
        }
    }

    public void send(String data) throws IOException {
        DatagramPacket dp = new DatagramPacket(data.getBytes(), 0, data.getBytes().length, InetAddress.getByName("239.255.255.250"), 1900);
        this.mLocalSocket.send(dp);
    }

    public DatagramPacket receive() throws IOException {
        byte[] buf = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        this.mLocalSocket.receive(dp);
        return dp;
    }

    public void close() {
        if (this.mLocalSocket != null) {
            try {
                this.mLocalSocket.leaveGroup(this.mSSDPMulticastGroup, this.mNetIf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mLocalSocket.close();
        }
    }

    public void openSSDP(int intaddr) {
        try {
            byte[] byteaddr = {(byte) (intaddr & 255), (byte) ((intaddr >> 8) & 255), (byte) ((intaddr >> 16) & 255), (byte) ((intaddr >> 24) & 255)};
            InetAddress addr = InetAddress.getByAddress("androidtestdevice", byteaddr);
            this.mLocalSocket = new MulticastSocket(1900);
            this.mLocalSocket.setTimeToLive(2);
            this.mNetIf = NetworkInterface.getByInetAddress(addr);
            this.mLocalSocket.joinGroup(InetAddress.getByName("239.255.255.250"));
            this.mLocalSocket.setSoTimeout(10000);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        SSDPSearchMsg search = new SSDPSearchMsg(SSDP.ST_MediaServer);
        try {
            send(search.toString());
            while (true) {
                receive();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                receive();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e2) {
            System.out.println(e2.getMessage());
        }
    }
}
