package olive.app;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes.dex */
public class SendCommand extends AsyncTask<String, Void, Void> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(String... data) {
        String urlStr = data[0];
        try {
            URL url = new URL(urlStr);
            url.getContent();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute() {
    }
}
