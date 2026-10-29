package olive.app;

import android.app.Activity;
import android.os.AsyncTask;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes.dex */
public class LoadingXMLParserData extends AsyncTask<Activity, Void, Items> {
    Object myClass = null;
    String className = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Items doInBackground(Activity... activities) {
        Items items = null;
        this.myClass = activities[0];
        this.className = this.myClass.getClass().getName();
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            URL sourceUrl = null;
            if (this.className.equals("olive.app.TrackListTab")) {
                sourceUrl = new URL(((TrackListTab) this.myClass).getURL());
            } else if (this.className.equals("olive.app.PlaylistTab")) {
                sourceUrl = new URL(((PlaylistTab) this.myClass).getURL());
            } else if (this.className.equals("olive.app.LoadDevices")) {
                sourceUrl = new URL(((LoadDevices_CPU) this.myClass).getURL());
            } else if (this.className.equals("olive.app.SourcesTab")) {
                sourceUrl = new URL(((SourcesTab) this.myClass).getURL());
            } else if (this.className.equals("olive.app.IRadioTab")) {
                sourceUrl = new URL(((IRadioTab) this.myClass).getURL());
            }
            XMLTrackListParser myParser = new XMLTrackListParser();
            xr.setContentHandler(myParser);
            xr.parse(new InputSource(sourceUrl.openStream()));
            items = XMLTrackListParser.items;
            return items;
        } catch (Exception e) {
            return items;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Items items) {
        if (this.className.equals("olive.app.TrackListTab")) {
            ((TrackListTab) this.myClass).setItems(items);
            ((TrackListTab) this.myClass).displayItems();
            return;
        }
        if (this.className.equals("olive.app.PlaylistTab")) {
            ((PlaylistTab) this.myClass).setItems(items);
            ((PlaylistTab) this.myClass).displayItems();
        } else if (this.className.equals("olive.app.SourcesTab")) {
            ((SourcesTab) this.myClass).setItems(items);
            ((SourcesTab) this.myClass).displayItems();
        } else if (this.className.equals("olive.app.IRadioTab")) {
            ((IRadioTab) this.myClass).setItems(items);
            ((IRadioTab) this.myClass).displayItems();
        }
    }
}
