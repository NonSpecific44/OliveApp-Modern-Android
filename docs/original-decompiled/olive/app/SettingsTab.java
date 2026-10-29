package olive.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

/* loaded from: classes.dex */
public class SettingsTab extends Activity {
    private String devIP;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        Object[] data = {"getSettings", this, "http://" + this.devIP + "/includes/ajax/a_executeOperation.php?action=getSystemMode"};
        new RequestCommand().execute(data);
        ((ImageButton) findViewById(R.id.clearArtwork)).setOnClickListener(new View.OnClickListener() { // from class: olive.app.SettingsTab.1
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                ProgressDialog d = ProgressDialog.show(SettingsTab.this, "Working...", "Clearing cache...", true, false);
                new ImageLoader(SettingsTab.this.getApplicationContext()).clearCache();
                SharedPreferences settings2 = SettingsTab.this.getSharedPreferences("Olive_Settings", 0);
                SharedPreferences.Editor prefEditor = settings2.edit();
                prefEditor.clear();
                prefEditor.commit();
                d.dismiss();
            }
        });
        ((ToggleButton) findViewById(R.id.toggleStandby)).setOnClickListener(new View.OnClickListener() { // from class: olive.app.SettingsTab.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((ToggleButton) SettingsTab.this.findViewById(R.id.toggleStandby)).isChecked()) {
                    new SendCommand().execute("http://" + SettingsTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=setSystemMode&mode=Active");
                } else {
                    new SendCommand().execute("http://" + SettingsTab.this.devIP + "/includes/ajax/a_executeOperation.php?action=setSystemMode&mode=Powersaving");
                }
            }
        });
    }

    public void setSettings(Object data) {
        ((ToggleButton) findViewById(R.id.toggleStandby)).setVisibility(0);
        ((ProgressBar) findViewById(R.id.waiting)).setVisibility(8);
        try {
            if (data.toString().indexOf("Active") > -1 && data.toString().indexOf("Partial") < 0) {
                ((ToggleButton) findViewById(R.id.toggleStandby)).setChecked(true);
            } else {
                ((ToggleButton) findViewById(R.id.toggleStandby)).setChecked(false);
            }
        } catch (Exception e) {
            ((ToggleButton) findViewById(R.id.toggleStandby)).setChecked(true);
        }
    }

    public void onDestroy(Bundle savedInstanceState) {
        finish();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
