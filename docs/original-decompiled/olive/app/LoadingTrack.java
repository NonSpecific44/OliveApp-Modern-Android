package olive.app;

import android.app.Activity;
import android.os.AsyncTask;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes.dex */
public class LoadingTrack extends AsyncTask<Activity, Void, Items> {
    private TrackTab myTrack = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Items doInBackground(Activity... activities) {
        Items items = null;
        this.myTrack = (TrackTab) activities[0];
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            URL sourceUrl = new URL(this.myTrack.getURL());
            XMLTrackListParser myParser = new XMLTrackListParser();
            xr.setContentHandler(myParser);
            xr.parse(new InputSource(sourceUrl.openStream()));
            Items items2 = XMLTrackListParser.items;
            return items2;
        } catch (Exception e) {
            if (0 == 0) {
                items = new Items();
            }
            items.setError(e.getMessage());
            System.out.println(e);
            return items;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Items items) {
        this.myTrack.setItems(items);
        this.myTrack.displayItems();
    }
}
