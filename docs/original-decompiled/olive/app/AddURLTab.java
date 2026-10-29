package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class AddURLTab extends Activity {
    private RadioButton btn1;
    private RadioButton btn2;
    private RadioButton btn3;
    private RadioButton btn4;
    private RadioButton btn5;
    private RadioButton btn6;
    private EditText name;
    private EditText url;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addurl);
        getWindow().setFlags(4, 4);
        ((TextView) findViewById(R.id.add2favTitle)).setText("Add station to your Favorites:");
        this.name = (EditText) findViewById(R.id.name);
        this.url = (EditText) findViewById(R.id.url);
        this.btn1 = (RadioButton) findViewById(R.id.btn1);
        this.btn2 = (RadioButton) findViewById(R.id.btn2);
        this.btn3 = (RadioButton) findViewById(R.id.btn3);
        this.btn4 = (RadioButton) findViewById(R.id.btn4);
        this.btn5 = (RadioButton) findViewById(R.id.btn5);
        this.btn6 = (RadioButton) findViewById(R.id.btn6);
        this.btn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: olive.app.AddURLTab.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    AddURLTab.this.btn2.setChecked(false);
                    AddURLTab.this.btn3.setChecked(false);
                    AddURLTab.this.btn4.setChecked(false);
                    AddURLTab.this.btn5.setChecked(false);
                    AddURLTab.this.btn6.setChecked(false);
                }
            }
        });
        this.btn6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: olive.app.AddURLTab.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    AddURLTab.this.btn2.setChecked(false);
                    AddURLTab.this.btn3.setChecked(false);
                    AddURLTab.this.btn4.setChecked(false);
                    AddURLTab.this.btn5.setChecked(false);
                    AddURLTab.this.btn1.setChecked(false);
                }
            }
        });
        this.btn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: olive.app.AddURLTab.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    AddURLTab.this.btn1.setChecked(false);
                    AddURLTab.this.btn3.setChecked(false);
                    AddURLTab.this.btn4.setChecked(false);
                    AddURLTab.this.btn5.setChecked(false);
                    AddURLTab.this.btn6.setChecked(false);
                }
            }
        });
        this.btn3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: olive.app.AddURLTab.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    AddURLTab.this.btn2.setChecked(false);
                    AddURLTab.this.btn1.setChecked(false);
                    AddURLTab.this.btn4.setChecked(false);
                    AddURLTab.this.btn5.setChecked(false);
                    AddURLTab.this.btn6.setChecked(false);
                }
            }
        });
        this.btn4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: olive.app.AddURLTab.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    AddURLTab.this.btn2.setChecked(false);
                    AddURLTab.this.btn3.setChecked(false);
                    AddURLTab.this.btn1.setChecked(false);
                    AddURLTab.this.btn5.setChecked(false);
                    AddURLTab.this.btn6.setChecked(false);
                }
            }
        });
        this.btn5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: olive.app.AddURLTab.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    AddURLTab.this.btn2.setChecked(false);
                    AddURLTab.this.btn3.setChecked(false);
                    AddURLTab.this.btn4.setChecked(false);
                    AddURLTab.this.btn1.setChecked(false);
                    AddURLTab.this.btn6.setChecked(false);
                }
            }
        });
        ((Button) findViewById(R.id.btnAddURL)).setOnClickListener(new View.OnClickListener() { // from class: olive.app.AddURLTab.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (AddURLTab.this.url.getText().toString().equals(EXTHeader.DEFAULT_VALUE) || AddURLTab.this.name.getText().toString().equals(EXTHeader.DEFAULT_VALUE)) {
                    new AlertDialog.Builder(AddURLTab.this).setTitle("Error!").setMessage("Please enter a name/URL and try again").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.AddURLTab.7.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                } else {
                    AddURLTab.this.addURLtoFavs();
                }
            }
        });
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        backToRoot(null);
        super.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addURLtoFavs() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", this.name.getText().toString());
        resultIntent.putExtra("url", this.url.getText().toString());
        String genre = EXTHeader.DEFAULT_VALUE;
        if (this.btn1.isChecked()) {
            genre = "classical";
        } else if (this.btn2.isChecked()) {
            genre = "jazz";
        } else if (this.btn3.isChecked()) {
            genre = "blues";
        } else if (this.btn4.isChecked()) {
            genre = "rockpop";
        } else if (this.btn5.isChecked()) {
            genre = "spokenword";
        } else if (this.btn6.isChecked()) {
            genre = "other";
        }
        resultIntent.putExtra("genre", genre);
        backToRoot(resultIntent);
    }

    private void backToRoot(Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        setResult(-1, intent);
        finish();
    }
}
