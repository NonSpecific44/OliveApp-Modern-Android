package olive.app;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.teleal.cling.model.message.header.EXTHeader;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes.dex */
public class RequestCommand extends AsyncTask<Object[], Void, Object> {
    String command;
    ShowPlaylists myPL;
    Playscreen myPS;
    IRadioTab myRadio;
    Object response;
    SettingsTab settings;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Object doInBackground(Object[]... dialog) {
        Object[] data = dialog[0];
        this.command = (String) data[0];
        if (this.command.equals("addURL")) {
            this.myRadio = (IRadioTab) data[1];
        } else if (this.command == "add2Favs") {
            this.myPS = (Playscreen) data[1];
        } else if (this.command == "getSettings") {
            this.settings = (SettingsTab) data[1];
        } else {
            this.myPL = (ShowPlaylists) data[2];
        }
        if (this.command.equals("add2playlist")) {
            try {
                HttpClient hc = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://" + data[1] + "/includes/ajax/a_executeOperation.php?action=getActiveDevice");
                HttpResponse rp = hc.execute(post);
                String uuid = EXTHeader.DEFAULT_VALUE;
                if (rp.getStatusLine().getStatusCode() == 200) {
                    uuid = EntityUtils.toString(rp.getEntity());
                }
                if (uuid.indexOf("uuid:") > -1) {
                    uuid.replace("uuid:", EXTHeader.DEFAULT_VALUE);
                }
                if (uuid != "0") {
                    new SendCommand().execute("http://" + data[1] + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=0");
                }
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();
                URL sourceUrl = new URL("http://" + data[1] + "/server/iGetCompilationsAndTracks.php?id=ROOT_ALLPL&xmlType=noAttribute");
                XMLTrackListParser myParser = new XMLTrackListParser();
                xr.setContentHandler(myParser);
                xr.parse(new InputSource(sourceUrl.openStream()));
                this.response = XMLTrackListParser.items;
                if (uuid != "0") {
                    new SendCommand().execute("http://" + data[1] + "/includes/ajax/a_executeOperation.php?action=setActiveDevice&devId=0&uuid=" + uuid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (this.command.equals("createPlaylist")) {
            try {
                HttpClient hc2 = new DefaultHttpClient();
                String name = URLEncoder.encode(String.valueOf(data[3]));
                HttpPost post2 = new HttpPost("http://" + data[1] + "/server/newPlaylist.php?name=" + name);
                HttpResponse rp2 = hc2.execute(post2);
                if (rp2.getStatusLine().getStatusCode() == 200) {
                    this.response = EntityUtils.toString(rp2.getEntity());
                }
            } catch (IOException e2) {
            }
        } else if (this.command.equals("addTrack2Playlist")) {
            try {
                HttpClient hc3 = new DefaultHttpClient();
                HttpPost post3 = new HttpPost("http://" + data[1] + "/server/addToPlaylist.php?plid=" + data[3] + "&ids=" + data[4]);
                HttpResponse rp3 = hc3.execute(post3);
                if (rp3.getStatusLine().getStatusCode() == 200) {
                    this.response = EntityUtils.toString(rp3.getEntity());
                }
            } catch (IOException e3) {
            }
        } else if (this.command.equals("addURL") || this.command.equals("add2Favs") || this.command.equals("getSettings")) {
            try {
                HttpClient hc4 = new DefaultHttpClient();
                HttpPost post4 = new HttpPost((String) data[2]);
                HttpResponse rp4 = hc4.execute(post4);
                if (rp4.getStatusLine().getStatusCode() == 200) {
                    this.response = EntityUtils.toString(rp4.getEntity());
                }
            } catch (IOException e4) {
            }
        }
        return this.response;
    }

    @Override // android.os.AsyncTask
    protected void onPostExecute(Object response) {
        if (this.command.equals("add2playlist")) {
            this.myPL.setItems(response);
            this.myPL.showPlaylists();
            return;
        }
        if (this.command.equals("createPlaylist")) {
            this.myPL.add2playlist(response);
            return;
        }
        if (this.command.equals("addTrack2Playlist")) {
            this.myPL.add2playlist(response);
            return;
        }
        if (this.command.equals("addURL")) {
            this.myRadio.urlAdded(response);
        } else if (this.command.equals("add2Favs")) {
            this.myPS.add2Favs(response);
        } else if (this.command.equals("getSettings")) {
            this.settings.setSettings(response);
        }
    }
}
