package olive.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class SearchEntry extends Activity {
    private EditText et;
    private ListView searchList;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchentry);
        EditText input = (EditText) findViewById(R.id.searchText);
        input.performClick();
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent("android.speech.action.RECOGNIZE_SPEECH"), 0);
        ImageButton voiceControl = (ImageButton) findViewById(R.id.voiceControl);
        if (activities.size() == 0) {
            voiceControl.setEnabled(false);
        } else {
            voiceControl.setOnClickListener(new View.OnClickListener() { // from class: olive.app.SearchEntry.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                    intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                    intent.putExtra("android.speech.extra.PROMPT", "Voice search");
                    SearchEntry.this.startActivityForResult(intent, 111);
                }
            });
        }
        this.searchList = (ListView) findViewById(R.id.searchList);
        this.searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: olive.app.SearchEntry.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View itemClicked, int position, long arg3) {
                SearchEntry.this.et.setText((String) SearchEntry.this.searchList.getItemAtPosition(position));
                SearchEntry.this.executeDone();
            }
        });
        try {
            this.et = (EditText) findViewById(R.id.searchText);
            this.et.setText(EXTHeader.DEFAULT_VALUE);
        } catch (Exception e) {
        }
        this.et.requestFocus();
        InputMethodManager mgr = (InputMethodManager) getSystemService("input_method");
        mgr.showSoftInput(this.et, 1);
        ((Button) findViewById(R.id.btnSearch)).setOnClickListener(new View.OnClickListener() { // from class: olive.app.SearchEntry.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SearchEntry.this.executeDone();
            }
        });
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111 && resultCode == -1) {
            ArrayList<String> list = data.getStringArrayListExtra("android.speech.extra.RESULTS");
            String item = list.get(0);
            this.et.setText(item);
            this.searchList.setAdapter((ListAdapter) new ArrayAdapter(this, android.R.layout.simple_list_item_1, list));
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        executeDone();
        super.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeDone() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("value", this.et.getText().toString());
        setResult(-1, resultIntent);
        finish();
    }
}
