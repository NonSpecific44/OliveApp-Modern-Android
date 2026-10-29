package olive.app;

import android.app.Activity;
import android.os.AsyncTask;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes.dex */
public class LoadingAlbum extends AsyncTask<Activity, Void, Items> {
    private AlbumTab myAlbum = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Items doInBackground(Activity... activities) {
        Items items = null;
        this.myAlbum = (AlbumTab) activities[0];
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            URL sourceUrl = new URL(this.myAlbum.getURL());
            XMLParser myParser = new XMLParser();
            xr.setContentHandler(myParser);
            xr.parse(new InputSource(sourceUrl.openStream()));
            Items items2 = XMLParser.items;
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
        this.myAlbum.setItems(items);
        this.myAlbum.displayItems();
    }
}
