package olive.app;

import android.os.AsyncTask;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes.dex */
public class LoadingRequests extends AsyncTask<String, Void, Items> {
    private ArtistTab myArtist = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Items doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            System.out.print(url);
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Items items) {
        this.myArtist.setItems(items);
        this.myArtist.displayItems();
    }
}
