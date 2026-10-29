package olive.app;

import android.os.AsyncTask;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/* loaded from: classes.dex */
public class GetVolume extends AsyncTask<Object[], Void, String> {
    VolumeDialog myVolClass;
    private String response;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public String doInBackground(Object[]... dialog) {
        Object[] data = dialog[0];
        this.myVolClass = (VolumeDialog) data[0];
        String devIP = (String) data[1];
        try {
            HttpClient hc = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://" + devIP + "/server/getVolume.php");
            HttpResponse rp = hc.execute(post);
            if (rp.getStatusLine().getStatusCode() == 200) {
                this.response = EntityUtils.toString(rp.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.response;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(String response) {
        this.myVolClass.updateVolume(response);
    }
}
