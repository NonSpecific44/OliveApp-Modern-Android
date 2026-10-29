package olive.app;

import android.os.AsyncTask;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes.dex */
public class LoadingArtist extends AsyncTask<Object[], Void, Items> {
    String command;
    private ArtistTab myArtist = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Items doInBackground(Object[]... objects) {
        Items items = null;
        Object[] data = objects[0];
        this.myArtist = (ArtistTab) data[0];
        this.command = (String) data[1];
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            URL sourceUrl = new URL(this.myArtist.getURL());
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
        if (this.command == "search") {
            this.myArtist.showSearchResult(items);
        } else {
            this.myArtist.setItems(items);
            this.myArtist.displayItems();
        }
    }
}
