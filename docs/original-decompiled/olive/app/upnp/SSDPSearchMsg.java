package olive.app.upnp;

/* loaded from: classes.dex */
public class SSDPSearchMsg {
    static final String HOST = "Host:239.255.255.250:1900";
    static final String MAN = "Man:\"ssdp:discover\"";
    int mMX = 4;
    String mST;

    public SSDPSearchMsg(String ST) {
        this.mST = ST;
    }

    public int getmMX() {
        return this.mMX;
    }

    public void setmMX(int mMX) {
        this.mMX = mMX;
    }

    public String getmST() {
        return this.mST;
    }

    public void setmST(String mST) {
        this.mST = mST;
    }

    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(SSDP.SL_MSEARCH).append(SSDP.NEWLINE);
        content.append(HOST).append(SSDP.NEWLINE);
        content.append(MAN).append(SSDP.NEWLINE);
        content.append(this.mST).append(SSDP.NEWLINE);
        content.append("MX:" + this.mMX).append(SSDP.NEWLINE);
        return content.toString();
    }
}
