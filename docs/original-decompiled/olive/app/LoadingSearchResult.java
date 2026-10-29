package olive.app;

import android.app.Activity;
import android.os.AsyncTask;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes.dex */
public class LoadingSearchResult extends AsyncTask<Activity, Void, Items> {
    private SearchListTab mySearchList = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Items doInBackground(Activity... activities) {
        this.mySearchList = (SearchListTab) activities[0];
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            URL sourceUrl = new URL(this.mySearchList.getURL());
            XMLTrackListParser myParser = new XMLTrackListParser();
            xr.setContentHandler(myParser);
            xr.parse(new InputSource(sourceUrl.openStream()));
            Items items = XMLTrackListParser.items;
            return items;
        } catch (Exception e) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Items items) {
        this.mySearchList.setItems(items);
        this.mySearchList.displayItems();
    }
}
