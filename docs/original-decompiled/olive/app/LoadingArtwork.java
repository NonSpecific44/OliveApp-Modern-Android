package olive.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: classes.dex */
public class LoadingArtwork extends AsyncTask<Object[], Void, Object[]> {
    ImageView imageView;
    LazyAdapter myAdaper;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Object[] doInBackground(Object[]... dialog) {
        Object[] data = dialog[0];
        this.myAdaper = (LazyAdapter) data[0];
        this.imageView = (ImageView) data[1];
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URL url = new URL((String) data[2]);
            URLConnection conn = url.openConnection();
            try {
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                if (httpConn.getResponseCode() == 200) {
                    inputStream = httpConn.getInputStream();
                }
            } catch (Exception e) {
            }
            bitmap = BitmapFactory.decodeStream(inputStream, null, bmOptions);
            inputStream.close();
        } catch (IOException e2) {
        }
        Object[] response = {this.imageView, bitmap, (String) data[2]};
        return response;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Object[] data) {
    }
}
