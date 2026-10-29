package olive.app;

import android.os.AsyncTask;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class LoadingDevices extends AsyncTask<Object[], Void, String> {
    LoadDevices_CPU myDevices = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public String doInBackground(Object[]... objects) {
        Object[] data = objects[0];
        this.myDevices = (LoadDevices_CPU) data[0];
        try {
            Thread.sleep(1000L);
            return EXTHeader.DEFAULT_VALUE;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return EXTHeader.DEFAULT_VALUE;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(String flag) {
        this.myDevices.displayDevices();
    }
}
