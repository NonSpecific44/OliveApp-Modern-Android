package olive.app;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/* loaded from: classes.dex */
public class VolumeDialog extends Dialog implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private ImageView cancel;
    private String devIP;
    private SeekBar volumeSlider;

    public VolumeDialog(Context context, String devIP) {
        super(context);
        this.devIP = devIP;
        setContentView(R.layout.volume_setup);
        this.cancel = (ImageView) findViewById(R.id.cancelVolume);
        this.cancel.setOnClickListener(this);
        this.volumeSlider = (SeekBar) findViewById(R.id.volumeSlider);
        this.volumeSlider.setOnSeekBarChangeListener(this);
        Object[] request = {this, devIP};
        new GetVolume().execute(request);
    }

    public void updateVolume(String volVal) {
        try {
            TextView volumeVal = (TextView) findViewById(R.id.volumeVal);
            float val = Float.valueOf(volVal).floatValue() * 100.0f;
            volumeVal.setText(String.valueOf((int) val));
            findViewById(R.id.volumeProgress).setVisibility(4);
            this.volumeSlider.setProgress((int) val);
        } catch (Exception e) {
            dismiss();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v == this.cancel) {
            dismiss();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView volumeVal = (TextView) findViewById(R.id.volumeVal);
        volumeVal.setText(String.valueOf(progress));
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        new SendCommand().execute("http://" + this.devIP + "/includes/ajax/a_executeOperation.php?action=volumeSet&volume=" + seekBar.getProgress() + "&mutestat=1");
    }
}
