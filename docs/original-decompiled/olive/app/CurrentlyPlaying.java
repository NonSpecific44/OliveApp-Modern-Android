package olive.app;

import android.os.AsyncTask;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.teleal.cling.model.message.header.EXTHeader;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes.dex */
public class CurrentlyPlaying extends AsyncTask<Object, Void, Metadata> {
    LoadTabs myTab = null;
    TrackListTab myTrackListTab = null;
    String flag = EXTHeader.DEFAULT_VALUE;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Metadata doInBackground(Object... data) {
        this.flag = (String) data[0];
        if (this.flag.equals("TrackListTab")) {
            this.myTrackListTab = (TrackListTab) data[1];
            System.out.println("TrackListTab pull");
        } else {
            this.myTab = (LoadTabs) data[1];
            System.out.println("LoadTabs pull");
        }
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            URL sourceUrl = new URL(this.myTab.getUrl());
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
        if (this.flag.equals("LoadTabs")) {
            this.myTab.updateMetadata(metadata);
        } else {
            this.myTrackListTab.updateMetadata(metadata);
        }
    }
}
