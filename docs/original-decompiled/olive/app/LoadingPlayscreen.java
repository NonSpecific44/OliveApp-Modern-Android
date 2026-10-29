package olive.app;

import android.app.Activity;
import android.os.AsyncTask;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes.dex */
public class LoadingPlayscreen extends AsyncTask<Activity, Void, Metadata> {
    private Playscreen myPlayscreen = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Metadata doInBackground(Activity... activities) {
        this.myPlayscreen = (Playscreen) activities[0];
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            URL sourceUrl = new URL(this.myPlayscreen.getURL());
            XMLParserMetadata myParser = new XMLParserMetadata();
            xr.setContentHandler(myParser);
            xr.parse(new InputSource(sourceUrl.openStream()));
            Metadata metadata = XMLParserMetadata.metadata;
            return metadata;
        } catch (Exception e) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Metadata metadata) {
        this.myPlayscreen.setMetadata(metadata);
    }
}
