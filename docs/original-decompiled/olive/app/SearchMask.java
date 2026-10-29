package olive.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class SearchMask extends Activity {
    private String query = EXTHeader.DEFAULT_VALUE;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if ("android.intent.action.SEARCH".equals(intent.getAction())) {
            this.query = intent.getStringExtra("query");
            LoadTabs myHost = new LoadTabs();
            myHost.getHost();
            System.out.println(this.query);
        }
    }

    public String getURL() {
        return "sdf";
    }
}
