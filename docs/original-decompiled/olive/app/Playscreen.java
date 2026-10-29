package olive.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.teleal.cling.model.ServiceReference;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class Playscreen extends Activity {
    Bitmap bitmap;
    private String devIP = null;
    private String url = null;
    private ProgressDialog myDialog = null;
    private Metadata metadata = null;
    private ImageView shuffle = null;
    private ImageView repeat = null;
    private ImageView star1 = null;
    private ImageView star2 = null;
    private ImageView star3 = null;
    private ImageView star4 = null;
    private ImageView star5 = null;
    private Boolean ratingJustSet = false;
    private Boolean playModeJustSet = false;
    private Boolean blockProgress = false;
    private int height = 0;
    private int smallScreens = 800;
    int largeScreens = 1024;
    private SeekBar volumeBar = null;
    private ImageButton playpause = null;
    boolean fastforward = false;
    private boolean fastrewind = false;
    String curr_playState = null;
    Handler mHandler = new Handler();
    String currentBitmap = EXTHeader.DEFAULT_VALUE;
    String currentGenre = EXTHeader.DEFAULT_VALUE;
    boolean blockVolumUpdate = false;
    boolean blockVolumUpdateBtn = false;
    ShapeDrawable pgDrawable = null;
    ClipDrawable progress = null;
    int connectionWeak = 0;
    boolean playPause_longClick = false;
    Handler pHandler = new Handler();
    private Runnable loadArtwork = new Runnable() { // from class: olive.app.Playscreen.1
        @Override // java.lang.Runnable
        public void run() {
            if (!Playscreen.this.metadata.getAlbumart().equals(EXTHeader.DEFAULT_VALUE) && !Playscreen.this.metadata.getAlbumart().equals("images/artworknotfound.gif")) {
                try {
                    if (!Playscreen.this.currentBitmap.equals(Playscreen.this.metadata.getAlbumart())) {
                        Playscreen.this.bitmap = BitmapFactory.decodeStream((InputStream) new URL(Playscreen.this.metadata.getAlbumart()).getContent());
                        Playscreen.this.currentBitmap = Playscreen.this.metadata.getAlbumart();
                        return;
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    Playscreen.this.bitmap = null;
                    return;
                }
            }
            if (Playscreen.this.currentBitmap != EXTHeader.DEFAULT_VALUE) {
                if (Playscreen.this.metadata.getAlbumart().equals(EXTHeader.DEFAULT_VALUE) || Playscreen.this.metadata.getAlbumart().equals("images/artworknotfound.gif")) {
                    Playscreen.this.bitmap = null;
                }
            }
        }
    };
    private Runnable pullMetadata = new Runnable() { // from class: olive.app.Playscreen.2
        @Override // java.lang.Runnable
        public void run() {
            System.out.println("Playscreen pull");
            if (!Playscreen.this.isFinishing()) {
                new LoadingPlayscreen().execute(Playscreen.this);
            }
        }
    };
    private Runnable updateProgressbar = new Runnable() { // from class: olive.app.Playscreen.3
        @Override // java.lang.Runnable
        public void run() {
            String newString;
            String newString2;
            TextView time1 = (TextView) Playscreen.this.findViewById(R.id.time1);
            if (Playscreen.this.blockProgress.booleanValue()) {
                Playscreen.this.blockProgress = false;
            } else {
                int newValSec = 0;
                int newValMin = 0;
                int newValHours = 0;
                try {
                    if (!time1.getText().equals(Playscreen.this.metadata.getCurrentTrackDuration())) {
                        String[] newTime = time1.getText().toString().split(":");
                        int length = newTime.length;
                        if (length > 0 && newTime[length - 1] != EXTHeader.DEFAULT_VALUE) {
                            if (Playscreen.this.fastrewind) {
                                newValSec = Integer.valueOf(newTime[length - 1]).intValue() - 1;
                            } else {
                                newValSec = Integer.valueOf(newTime[length - 1]).intValue() + 1;
                            }
                            if (newValSec >= 60) {
                                newValSec = 0;
                                newValMin = Integer.valueOf(newTime[length - 2]).intValue() + 1;
                                if (newValMin >= 60) {
                                    newValMin = 0;
                                    newValHours = 0 + 1;
                                }
                            } else if (newValSec < 0) {
                                newValSec = 60;
                                newValMin = Integer.valueOf(newTime[length - 2]).intValue() - 1;
                                if (newValMin < 0) {
                                    newValMin = 0;
                                    if (newTime.length > 2) {
                                        newValHours = Integer.valueOf(newTime[length - 3]).intValue() - 1;
                                        if (newValHours < 0) {
                                            newValHours = 0;
                                            newValMin = 0;
                                            newValSec = 0;
                                        } else {
                                            newValMin = 60;
                                        }
                                    } else {
                                        newValSec = 0;
                                    }
                                }
                            } else {
                                newValMin = Integer.valueOf(newTime[length - 2]).intValue();
                            }
                            if (newValSec <= 9) {
                                newString = "0" + String.valueOf(newValSec);
                            } else {
                                newString = String.valueOf(newValSec);
                            }
                            if (newValMin == 0) {
                                newString2 = "00:" + newString;
                            } else if (newValMin <= 9) {
                                newString2 = "0" + String.valueOf(newValMin) + ":" + newString;
                            } else {
                                newString2 = String.valueOf(String.valueOf(newValMin)) + newString;
                            }
                            if (newValHours > 0 && newValHours <= 9) {
                                newString2 = "0" + String.valueOf(newValHours) + newString2;
                            } else if (newValHours > 9) {
                                newString2 = String.valueOf(String.valueOf(newValHours)) + newString2;
                            }
                            time1.setText(newString2);
                        }
                        double progress = (newValMin * 60) + newValSec + (newValHours * 60);
                        String[] secMin = String.valueOf(((TextView) Playscreen.this.findViewById(R.id.time2)).getText()).split(":");
                        ProgressBar pg = (ProgressBar) Playscreen.this.findViewById(R.id.progressBar);
                        if (secMin.length != 2) {
                            if (!Playscreen.this.metadata.getProgress().equals(EXTHeader.DEFAULT_VALUE)) {
                                float pro = Float.valueOf(Playscreen.this.metadata.getProgress()).floatValue() * 100.0f;
                                pg.setProgress((int) pro);
                            }
                        } else {
                            double whole = (Double.parseDouble(secMin[0]) * 60.0d) + Double.parseDouble(secMin[1]);
                            double percent = 100.0d / whole;
                            pg.setProgress((int) Math.round(percent * progress));
                        }
                    }
                } catch (Exception e) {
                }
            }
            Playscreen.this.updateProgress();
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.height = getResources().getDisplayMetrics().heightPixels;
        if (this.height <= this.smallScreens) {
            setContentView(R.layout.playscreen);
        } else {
            setContentView(R.layout.playscreenlarge);
        }
        SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
        this.devIP = settings.getString("devIP", "none");
        if (this.devIP != "none") {
            this.myDialog = ProgressDialog.show(this, "Working...", "Loading playscreen...", true, false);
            this.url = "http://" + this.devIP + "/server/getMetadata.php";
            new LoadingPlayscreen().execute(this);
        }
        initPlayscreen();
    }

    private void initPlayscreen() {
        if (this.height < this.smallScreens) {
            findViewById(R.id.progressBar).setVisibility(8);
            findViewById(R.id.TimeLayout).setVisibility(8);
        }
        if (this.height > this.smallScreens) {
            this.volumeBar = (SeekBar) findViewById(R.id.volumeBar);
            this.volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: olive.app.Playscreen.4
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int arg1, boolean arg2) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Playscreen.this.blockVolumUpdate = true;
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=volumeSet&volume=" + seekBar.getProgress() + "&mutestat=1");
                }
            });
        }
        this.playpause = (ImageButton) findViewById(R.id.playpause);
        ImageView left_skip = (ImageView) findViewById(R.id.left_skip);
        left_skip.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.5
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                if (!Playscreen.this.fastrewind) {
                    if (!Playscreen.this.playpause.getTag().toString().equals("STOPPED")) {
                        new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=left_skip");
                        Intent intent = new Intent(Playscreen.this, (Class<?>) Playscreen.class);
                        intent.addFlags(65536);
                        Playscreen.this.startActivity(intent);
                        Playscreen.this.finish();
                        Playscreen.this.overridePendingTransition(0, 0);
                        Playscreen.this.currentBitmap = EXTHeader.DEFAULT_VALUE;
                        return;
                    }
                    return;
                }
                Playscreen.this.fastrewind = false;
            }
        });
        left_skip.setOnLongClickListener(new View.OnLongClickListener() { // from class: olive.app.Playscreen.6
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View v) {
                new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlaySpeed&speed=-5");
                Playscreen.this.fastrewind = true;
                return false;
            }
        });
        left_skip.setOnTouchListener(new View.OnTouchListener() { // from class: olive.app.Playscreen.7
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == 1) {
                    new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlaySpeed&speed=1");
                }
                return false;
            }
        });
        ImageView right_skip = (ImageView) findViewById(R.id.right_skip);
        right_skip.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.8
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                if (!Playscreen.this.fastforward) {
                    if (Playscreen.this.metadata.getCurrentTrack().equals(EXTHeader.DEFAULT_VALUE) || Playscreen.this.metadata.getNumberOfTracks().equals(EXTHeader.DEFAULT_VALUE) || !Playscreen.this.metadata.getCurrentTrack().equals(Playscreen.this.metadata.getNumberOfTracks())) {
                        if (Playscreen.this.playpause.getTag().toString().equals("STOPPED")) {
                            Playscreen.this.metadata.getCurrentPlayMode().equals("REPEAT_ONE");
                            return;
                        }
                        new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=right_skip");
                        Intent intent = new Intent(Playscreen.this, (Class<?>) Playscreen.class);
                        intent.addFlags(65536);
                        Playscreen.this.startActivity(intent);
                        Playscreen.this.finish();
                        Playscreen.this.overridePendingTransition(0, 0);
                        Playscreen.this.currentBitmap = EXTHeader.DEFAULT_VALUE;
                        return;
                    }
                    Context context = Playscreen.this.getApplicationContext();
                    Toast toast = Toast.makeText(context, "No tracks left in this selection.", 1);
                    toast.show();
                    return;
                }
                Playscreen.this.fastforward = false;
            }
        });
        right_skip.setOnLongClickListener(new View.OnLongClickListener() { // from class: olive.app.Playscreen.9
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View v) {
                new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlaySpeed&speed=5");
                Playscreen.this.fastforward = true;
                return false;
            }
        });
        right_skip.setOnTouchListener(new View.OnTouchListener() { // from class: olive.app.Playscreen.10
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == 1) {
                    new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlaySpeed&speed=1");
                }
                return false;
            }
        });
        ImageView playPause = (ImageView) findViewById(R.id.playpause);
        playPause.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.11
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                if (!Playscreen.this.playPause_longClick) {
                    if (Playscreen.this.playpause.getTag().toString().equals("STOPPED")) {
                        try {
                            URL url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=playItem&upnpid=" + Playscreen.this.metadata.getObjId());
                            url.getContent();
                            Playscreen.this.playpause.setImageResource(R.drawable.playpause);
                            Playscreen.this.playpause.setTag("PLAYING");
                            return;
                        } catch (Exception e) {
                            return;
                        }
                    }
                    Playscreen.this.pHandler.removeCallbacks(Playscreen.this.updateProgressbar);
                    new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&root=null&upnpid=null&sortCrit=+upnp:originalTrackNumber&index=0");
                    if (Playscreen.this.playpause.getTag().toString().equals("PLAYING")) {
                        Playscreen.this.playpause.setImageResource(R.drawable.pause);
                        Playscreen.this.playpause.setTag("PAUSED_PLAYBACK");
                        return;
                    } else {
                        if (Playscreen.this.playpause.getTag().toString().equals("PAUSED_PLAYBACK")) {
                            Playscreen.this.playpause.setImageResource(R.drawable.playpause);
                            Playscreen.this.playpause.setTag("PLAYING");
                            return;
                        }
                        return;
                    }
                }
                Playscreen.this.playPause_longClick = false;
            }
        });
        playPause.setOnLongClickListener(new View.OnLongClickListener() { // from class: olive.app.Playscreen.12
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View arg0) {
                Playscreen.this.pHandler.removeCallbacks(Playscreen.this.updateProgressbar);
                new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=controlPlayer&id=stop");
                Playscreen.this.playPause_longClick = true;
                Playscreen.this.playpause.setImageResource(R.drawable.stop);
                Playscreen.this.playpause.setTag("STOPPED");
                return false;
            }
        });
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.13
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                Playscreen.this.finish();
            }
        });
        ImageView volume = (ImageView) findViewById(R.id.volume);
        volume.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.14
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                VolumeDialog volumeSetup = new VolumeDialog(Playscreen.this, Playscreen.this.devIP);
                volumeSetup.setTitle("Adjust Volume:");
                volumeSetup.show();
            }
        });
        ImageView add2pl = (ImageView) findViewById(R.id.playlist);
        add2pl.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.15
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    if (!Playscreen.this.metadata.getPlayType().equals("INTERNET_RADIO")) {
                        if (Playscreen.this.metadata.getObjId().indexOf("ROOT_ALL") > -1) {
                            Bundle bundle = new Bundle();
                            bundle.putString("objId", Playscreen.this.metadata.getObjId());
                            Intent intent = new Intent(Playscreen.this, (Class<?>) ShowPlaylists.class);
                            intent.putExtras(bundle);
                            Playscreen.this.startActivity(intent);
                        } else {
                            Context context = Playscreen.this.getApplicationContext();
                            Toast toast = Toast.makeText(context, "Not possible for a none-Olive device.", 1);
                            toast.show();
                        }
                    } else {
                        String url = "http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=addRadioToFavs&name=" + URLEncoder.encode(Playscreen.this.metadata.getStationTitle()) + "&url=" + URLEncoder.encode(Playscreen.this.metadata.getStationUrl()) + "&genre=" + URLEncoder.encode(Playscreen.this.metadata.getMajor_genre()) + "&subgenre=" + URLEncoder.encode(Playscreen.this.metadata.getGenre()) + "&bitrate=" + URLEncoder.encode(Playscreen.this.metadata.getBitrate()) + "&country=" + URLEncoder.encode(Playscreen.this.metadata.getCountry());
                        Object[] data = {"add2Favs", Playscreen.this, url};
                        new RequestCommand().execute(data);
                    }
                } catch (Exception e) {
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.15.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });
        this.star1 = (ImageView) findViewById(R.id.star1);
        this.star1.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.16
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    Playscreen.this.ratingJustSet = true;
                    Playscreen.this.star1.setImageResource(R.drawable.star_set);
                    Playscreen.this.star2.setImageResource(R.drawable.star_unset);
                    Playscreen.this.star3.setImageResource(R.drawable.star_unset);
                    Playscreen.this.star4.setImageResource(R.drawable.star_unset);
                    Playscreen.this.star5.setImageResource(R.drawable.star_unset);
                    new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setRating&id=" + Playscreen.this.metadata.getObjId() + "&starsOld=vu:myRating&starsNew=1");
                } catch (Exception e) {
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.16.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });
        this.star2 = (ImageView) findViewById(R.id.star2);
        this.star2.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.17
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    Playscreen.this.ratingJustSet = true;
                    Playscreen.this.star1.setImageResource(R.drawable.star_set);
                    Playscreen.this.star2.setImageResource(R.drawable.star_set);
                    Playscreen.this.star3.setImageResource(R.drawable.star_unset);
                    Playscreen.this.star4.setImageResource(R.drawable.star_unset);
                    Playscreen.this.star5.setImageResource(R.drawable.star_unset);
                    new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setRating&id=" + Playscreen.this.metadata.getObjId() + "&starsOld=vu:myRating&starsNew=2");
                } catch (Exception e) {
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.17.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });
        this.star3 = (ImageView) findViewById(R.id.star3);
        this.star3.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.18
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    Playscreen.this.ratingJustSet = true;
                    Playscreen.this.star1.setImageResource(R.drawable.star_set);
                    Playscreen.this.star2.setImageResource(R.drawable.star_set);
                    Playscreen.this.star3.setImageResource(R.drawable.star_set);
                    Playscreen.this.star4.setImageResource(R.drawable.star_unset);
                    Playscreen.this.star5.setImageResource(R.drawable.star_unset);
                    new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setRating&id=" + Playscreen.this.metadata.getObjId() + "&starsOld=vu:myRating&starsNew=3");
                } catch (Exception e) {
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.18.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });
        this.star4 = (ImageView) findViewById(R.id.star4);
        this.star4.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.19
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    Playscreen.this.ratingJustSet = true;
                    Playscreen.this.star1.setImageResource(R.drawable.star_set);
                    Playscreen.this.star2.setImageResource(R.drawable.star_set);
                    Playscreen.this.star3.setImageResource(R.drawable.star_set);
                    Playscreen.this.star4.setImageResource(R.drawable.star_set);
                    Playscreen.this.star5.setImageResource(R.drawable.star_unset);
                    new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setRating&id=" + Playscreen.this.metadata.getObjId() + "&starsOld=vu:myRating&starsNew=4");
                } catch (Exception e) {
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.19.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });
        this.star5 = (ImageView) findViewById(R.id.star5);
        this.star5.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.20
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    Playscreen.this.ratingJustSet = true;
                    Playscreen.this.star1.setImageResource(R.drawable.star_set);
                    Playscreen.this.star2.setImageResource(R.drawable.star_set);
                    Playscreen.this.star3.setImageResource(R.drawable.star_set);
                    Playscreen.this.star4.setImageResource(R.drawable.star_set);
                    Playscreen.this.star5.setImageResource(R.drawable.star_set);
                    new SendCommand().execute("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setRating&id=" + Playscreen.this.metadata.getObjId() + "&starsOld=vu:myRating&starsNew=5");
                } catch (Exception e) {
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.20.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });
        this.repeat = (ImageView) findViewById(R.id.repeat);
        this.repeat.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.21
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    if (Playscreen.this.metadata.getPlayType().equals("MUSIC_ITEM")) {
                        try {
                            Playscreen.this.playModeJustSet = true;
                            String shuffleMode = Playscreen.this.shuffle.getTag().toString();
                            String repeatMode = Playscreen.this.repeat.getTag().toString();
                            URL url = null;
                            if (shuffleMode.equals("shuffle_off") && repeatMode.equals("repeat_off")) {
                                url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=REPEAT_ONE&id=" + Playscreen.this.metadata.getObjId());
                                Playscreen.this.repeat.setImageResource(R.drawable.repeat_one);
                                Playscreen.this.repeat.setTag("repeat_one");
                            } else if (shuffleMode.equals("shuffle_off") && repeatMode.equals("repeat_on")) {
                                url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=NORMAL&id=" + Playscreen.this.metadata.getObjId());
                                Playscreen.this.repeat.setImageResource(R.drawable.repeat_off);
                                Playscreen.this.repeat.setTag("repeat_off");
                            } else if (repeatMode.equals("repeat_one")) {
                                url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=REPEAT_NORMAL&id=" + Playscreen.this.metadata.getObjId());
                                Playscreen.this.repeat.setImageResource(R.drawable.repeat_on);
                                Playscreen.this.repeat.setTag("repeat_on");
                            } else if (shuffleMode.equals("shuffle_on") && repeatMode.equals("repeat_off")) {
                                url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=REPEAT_SHUFFLE&id=" + Playscreen.this.metadata.getObjId());
                                Playscreen.this.repeat.setImageResource(R.drawable.repeat_on);
                                Playscreen.this.repeat.setTag("repeat_on");
                            } else if (shuffleMode.equals("shuffle_on") && repeatMode.equals("repeat_on")) {
                                url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=SHUFFLE&id=" + Playscreen.this.metadata.getObjId());
                                Playscreen.this.repeat.setImageResource(R.drawable.repeat_off);
                                Playscreen.this.repeat.setTag("repeat_off");
                            }
                            url.getContent();
                            return;
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            return;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage("Only possible for local music playback.").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.21.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                } catch (Exception e3) {
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.21.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });
        this.shuffle = (ImageView) findViewById(R.id.shuffle);
        this.shuffle.setOnClickListener(new View.OnClickListener() { // from class: olive.app.Playscreen.22
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                try {
                    if (Playscreen.this.metadata.getPlayType().equals("MUSIC_ITEM")) {
                        try {
                            Playscreen.this.playModeJustSet = true;
                            String shuffleMode = Playscreen.this.shuffle.getTag().toString();
                            String repeatMode = Playscreen.this.repeat.getTag().toString();
                            URL url = null;
                            if (shuffleMode.equals("shuffle_off") && repeatMode.equals("repeat_off")) {
                                url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=SHUFFLE&id=" + Playscreen.this.metadata.getObjId());
                                Playscreen.this.shuffle.setImageResource(R.drawable.shuffle_on);
                                Playscreen.this.shuffle.setTag("shuffle_on");
                            } else if (shuffleMode.equals("shuffle_off") && (repeatMode.equals("repeat_on") || repeatMode.equals("repeat_one"))) {
                                if (repeatMode.equals("repeat_one")) {
                                    Playscreen.this.repeat.setImageResource(R.drawable.repeat_off);
                                    url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=SHUFFLE&id=" + Playscreen.this.metadata.getObjId());
                                    Playscreen.this.repeat.setTag("repeat_off");
                                } else {
                                    url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=REPEAT_SHUFFLE&id=" + Playscreen.this.metadata.getObjId());
                                }
                                Playscreen.this.shuffle.setImageResource(R.drawable.shuffle_on);
                                Playscreen.this.shuffle.setTag("shuffle_on");
                            } else if (shuffleMode.equals("shuffle_on") && repeatMode.equals("repeat_off")) {
                                url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=NORMAL&id=" + Playscreen.this.metadata.getObjId());
                                Playscreen.this.shuffle.setImageResource(R.drawable.shuffle_off);
                                Playscreen.this.shuffle.setTag("shuffle_off");
                            } else if (shuffleMode.equals("shuffle_on") && repeatMode.equals("repeat_on")) {
                                url = new URL("http://" + Playscreen.this.devIP + "/includes/ajax/a_executeOperation.php?action=setPlayMode&mode=REPEAT_NORMAL&id=" + Playscreen.this.metadata.getObjId());
                                Playscreen.this.shuffle.setImageResource(R.drawable.shuffle_off);
                                Playscreen.this.shuffle.setTag("shuffle_off");
                            }
                            url.getContent();
                            return;
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            return;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage("Only possible for local music playback.").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.22.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                } catch (Exception e3) {
                    new AlertDialog.Builder(Playscreen.this).setTitle("Error!").setMessage(R.string.connectionError).setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.22.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        });
    }

    public String getURL() {
        return this.url;
    }

    public void setMetadata(Metadata metadata) {
        String MyColor;
        String MyColor2;
        if (metadata != null) {
            SharedPreferences settings = getSharedPreferences("Olive_Settings", 0);
            SharedPreferences.Editor prefEditor = settings.edit();
            if (metadata.getAlbum().equals(EXTHeader.DEFAULT_VALUE) && metadata.getTransportState().equals("STOPPED")) {
                prefEditor.putString("albumTitle", EXTHeader.DEFAULT_VALUE);
                prefEditor.putString("trackTitle", "No track currently playing...");
            } else if (metadata.getPlayType().equals("INTERNET_RADIO") && !metadata.getStationTitle().equals(EXTHeader.DEFAULT_VALUE)) {
                prefEditor.putString("albumTitle", metadata.getStationTitle());
                prefEditor.putString("trackTitle", metadata.getDctitle());
            } else {
                prefEditor.putString("albumTitle", metadata.getAlbum());
                prefEditor.putString("trackTitle", metadata.getDctitle());
            }
            prefEditor.commit();
            Boolean update = false;
            if (!this.currentGenre.equals(EXTHeader.DEFAULT_VALUE) && !this.currentGenre.equals(metadata.getMajor_genre())) {
                if (metadata.getMajor_genre().equals("classical")) {
                    MyColor2 = "#8DC63F";
                } else if (metadata.getMajor_genre().equals("jazz")) {
                    MyColor2 = "#AA5EA5";
                } else if (metadata.getMajor_genre().equals("blues")) {
                    MyColor2 = "#0098D1";
                } else if (metadata.getMajor_genre().equals("rockpop")) {
                    MyColor2 = "#EC1A24";
                } else if (metadata.getMajor_genre().equals("spokenword")) {
                    MyColor2 = "#ECD517";
                } else {
                    MyColor2 = "#FAA21A";
                }
                this.pgDrawable.getPaint().setColor(Color.parseColor(MyColor2));
                this.currentGenre = metadata.getMajor_genre();
            }
            if (this.height >= this.smallScreens && this.metadata == null) {
                ProgressBar pg2 = (ProgressBar) findViewById(R.id.progressBar);
                float[] roundedCorners = {5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
                this.pgDrawable = new ShapeDrawable(new RoundRectShape(roundedCorners, null, null));
                if (metadata.getMajor_genre().equals("classical")) {
                    MyColor = "#8DC63F";
                } else if (metadata.getMajor_genre().equals("jazz")) {
                    MyColor = "#AA5EA5";
                } else if (metadata.getMajor_genre().equals("blues")) {
                    MyColor = "#0098D1";
                } else if (metadata.getMajor_genre().equals("rockpop")) {
                    MyColor = "#EC1A24";
                } else if (metadata.getMajor_genre().equals("spokenword")) {
                    MyColor = "#ECD517";
                } else {
                    MyColor = "#FAA21A";
                }
                this.pgDrawable.getPaint().setColor(Color.parseColor(MyColor));
                this.progress = new ClipDrawable(this.pgDrawable, 3, 1);
                pg2.setProgressDrawable(this.progress);
                pg2.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal));
                if (this.metadata == null) {
                    update = true;
                    if (this.height >= this.smallScreens && !metadata.getProgress().equals(EXTHeader.DEFAULT_VALUE)) {
                        float pro = Float.valueOf(metadata.getProgress()).floatValue() * 100.0f;
                        pg2.setProgress((int) pro);
                    }
                }
                this.currentGenre = metadata.getMajor_genre();
            }
            this.metadata = metadata;
            if (update.booleanValue() && this.height >= this.smallScreens) {
                updateProgress();
            } else if (this.height >= this.smallScreens && ((this.curr_playState.equals("STOPPED") || this.curr_playState.equals("PAUSED_PLAYBACK")) && (!metadata.getTransportState().equals("STOPPED") || metadata.getTransportState().equals("PAUSED_PLAYBACK")))) {
                updateProgress();
            }
            this.curr_playState = metadata.getTransportState();
            if (metadata.getTransportState().equals("STOPPED")) {
                this.playpause.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                this.playpause.setTag("STOPPED");
            } else if (metadata.getTransportState().equals("PLAYING")) {
                this.playpause.setImageDrawable(getResources().getDrawable(R.drawable.playpause));
                this.playpause.setTag("PLAYING");
            } else if (metadata.getTransportState().equals("PAUSED_PLAYBACK")) {
                this.playpause.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                this.playpause.setTag("PAUSED_PLAYBACK");
            }
            if (metadata.getPlayType().equals("INTERNET_RADIO")) {
                ((TextView) findViewById(R.id.trackNoTitle)).setText("BITRATE");
                ((TextView) findViewById(R.id.track)).setText(String.valueOf(metadata.getBitrate()) + " kbit/s");
                ((TextView) findViewById(R.id.artistTitle)).setText("COUNTRY");
                ((TextView) findViewById(R.id.artist)).setText(metadata.getCountry());
                ((TextView) findViewById(R.id.albumTitle)).setText("GENRE");
                ((TextView) findViewById(R.id.album)).setText(metadata.getGenre());
                ((TextView) findViewById(R.id.stationName)).setText("STATION NAME");
                ((TextView) findViewById(R.id.stationName)).setVisibility(0);
                ((TextView) findViewById(R.id.stationNameTitle)).setText(metadata.getStationTitle());
                ((TextView) findViewById(R.id.stationNameTitle)).setVisibility(0);
                ((ImageButton) findViewById(R.id.right_skip)).setVisibility(8);
                ((ImageButton) findViewById(R.id.left_skip)).setVisibility(8);
                if (this.height > this.smallScreens) {
                    ((TextView) findViewById(R.id.dctitle)).setPadding(5, 25, 0, 310);
                } else if (this.height == this.smallScreens) {
                    ((TextView) findViewById(R.id.dctitle)).setPadding(5, 25, 0, 270);
                } else {
                    ((TextView) findViewById(R.id.dctitle)).setPadding(5, 25, 0, this.height / 4);
                }
            } else {
                ((ImageButton) findViewById(R.id.right_skip)).setVisibility(0);
                ((ImageButton) findViewById(R.id.left_skip)).setVisibility(0);
                ((TextView) findViewById(R.id.trackNoTitle)).setText("TRACK");
                ((TextView) findViewById(R.id.track)).setText(String.valueOf(metadata.getCurrentTrack()) + ServiceReference.DELIMITER + metadata.getNumberOfTracks());
                ((TextView) findViewById(R.id.albumTitle)).setText("ALBUM");
                ((TextView) findViewById(R.id.album)).setText(metadata.getAlbum());
                ((TextView) findViewById(R.id.artistTitle)).setText("ARTIST");
                ((TextView) findViewById(R.id.artist)).setText(metadata.getArtist());
                ((TextView) findViewById(R.id.stationName)).setVisibility(8);
                ((TextView) findViewById(R.id.stationNameTitle)).setVisibility(8);
            }
            TextView trackTitle = (TextView) findViewById(R.id.dctitle);
            trackTitle.setText(metadata.getDctitle());
            new Thread(this.loadArtwork).start();
            if (this.bitmap != null) {
                ((ImageView) findViewById(R.id.artwork)).setImageBitmap(this.bitmap);
            } else {
                ((ImageView) findViewById(R.id.artwork)).setImageDrawable(getResources().getDrawable(R.drawable.artworknotfound));
            }
            if (metadata.getPlayType().equals("INTERNET_RADIO")) {
                ((LinearLayout) findViewById(R.id.TimeLayout)).setVisibility(8);
            } else if (this.height >= this.smallScreens) {
                ((LinearLayout) findViewById(R.id.TimeLayout)).setVisibility(0);
                if (metadata.getRelativeTimePosition().equals("0:00.000")) {
                    ((TextView) findViewById(R.id.time1)).setText("00:00");
                } else {
                    ((TextView) findViewById(R.id.time1)).setText(metadata.getRelativeTimePosition());
                }
                ((TextView) findViewById(R.id.time2)).setText(metadata.getCurrentTrackDuration());
            }
            if (metadata.getMajor_genre().equals("classical")) {
                findViewById(R.id.major_genre).setBackgroundColor(Color.rgb(141, 198, 63));
                ((TextView) findViewById(R.id.dctitle)).setTextColor(Color.rgb(141, 198, 63));
            } else if (metadata.getMajor_genre().equals("jazz")) {
                findViewById(R.id.major_genre).setBackgroundColor(Color.rgb(170, 94, 165));
                ((TextView) findViewById(R.id.dctitle)).setTextColor(Color.rgb(170, 94, 165));
            } else if (metadata.getMajor_genre().equals("blues")) {
                findViewById(R.id.major_genre).setBackgroundColor(Color.rgb(0, 152, 209));
                ((TextView) findViewById(R.id.dctitle)).setTextColor(Color.rgb(0, 152, 209));
            } else if (metadata.getMajor_genre().equals("rockpop")) {
                findViewById(R.id.major_genre).setBackgroundColor(Color.rgb(236, 26, 36));
                ((TextView) findViewById(R.id.dctitle)).setTextColor(Color.rgb(236, 26, 36));
            } else if (metadata.getMajor_genre().equals("spokenword")) {
                findViewById(R.id.major_genre).setBackgroundColor(Color.rgb(236, 213, 23));
                ((TextView) findViewById(R.id.dctitle)).setTextColor(Color.rgb(236, 213, 23));
            } else {
                findViewById(R.id.major_genre).setBackgroundColor(Color.rgb(250, 162, 26));
                ((TextView) findViewById(R.id.dctitle)).setTextColor(Color.rgb(250, 162, 26));
            }
            if (metadata.getPlayType().equals("INTERNET_RADIO")) {
                ((LinearLayout) findViewById(R.id.TimeLayout)).setVisibility(8);
                ((LinearLayout) findViewById(R.id.ratingLayout)).setVisibility(8);
                ((LinearLayout) findViewById(R.id.metadataLayout)).setVisibility(8);
                ((ImageView) findViewById(R.id.left_skip)).setVisibility(8);
                ((ImageView) findViewById(R.id.right_skip)).setVisibility(8);
            } else {
                if (this.height >= this.smallScreens) {
                    ((LinearLayout) findViewById(R.id.TimeLayout)).setVisibility(0);
                }
                ((LinearLayout) findViewById(R.id.ratingLayout)).setVisibility(0);
                ((LinearLayout) findViewById(R.id.metadataLayout)).setVisibility(0);
                ((ImageView) findViewById(R.id.left_skip)).setVisibility(0);
                ((ImageView) findViewById(R.id.right_skip)).setVisibility(0);
                if (metadata.getMyRating() != EXTHeader.DEFAULT_VALUE && Integer.valueOf(metadata.getMyRating()).intValue() >= 0) {
                    if (!this.ratingJustSet.booleanValue()) {
                        ImageView star1 = (ImageView) findViewById(R.id.star1);
                        ImageView star2 = (ImageView) findViewById(R.id.star2);
                        ImageView star3 = (ImageView) findViewById(R.id.star3);
                        ImageView star4 = (ImageView) findViewById(R.id.star4);
                        ImageView star5 = (ImageView) findViewById(R.id.star5);
                        switch (Integer.valueOf(metadata.getMyRating()).intValue()) {
                            case 0:
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                break;
                            case 1:
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                break;
                            case 2:
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                break;
                            case 3:
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                break;
                            case 4:
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.star_unset));
                                break;
                            case 5:
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.star_set));
                                break;
                        }
                    } else {
                        this.ratingJustSet = false;
                    }
                }
                TextView left1title = (TextView) findViewById(R.id.left1title);
                TextView left2title = (TextView) findViewById(R.id.left2title);
                TextView left3title = (TextView) findViewById(R.id.left3title);
                TextView left1content = (TextView) findViewById(R.id.left1content);
                TextView left2content = (TextView) findViewById(R.id.left2content);
                TextView left3content = (TextView) findViewById(R.id.left3content);
                TextView right1title = (TextView) findViewById(R.id.right1title);
                TextView right2title = (TextView) findViewById(R.id.right2title);
                TextView right3title = (TextView) findViewById(R.id.right3title);
                TextView right1content = (TextView) findViewById(R.id.right1content);
                TextView right2content = (TextView) findViewById(R.id.right2content);
                TextView right3content = (TextView) findViewById(R.id.right3content);
                if (metadata.getMajor_genre().equals("classical")) {
                    left1title.setText("ORCHESTRA");
                    left1content.setText(metadata.getOrchestra());
                    left2title.setText("INSTRUMENT/VOICE");
                    left2content.setText(metadata.getFeaturedInstruments());
                    left3title.setText("PERIOD");
                    left3content.setText(metadata.getPeriod());
                    right1title.setText("SOLOIST");
                    right1content.setText(metadata.getArtist());
                    right2title.setText("CONDUCTOR");
                    right2content.setText(String.valueOf(metadata.getConductorFirstname()) + " " + metadata.getConductorLastname());
                    right3title.setText("RELEASE DATE");
                    right3content.setText(metadata.getReleasedDate());
                    TextView composer = (TextView) findViewById(R.id.artistTitle);
                    composer.setText("COMPOSER");
                    ((TextView) findViewById(R.id.album)).setText(metadata.getWork());
                    TextView work = (TextView) findViewById(R.id.albumTitle);
                    work.setText("WORK");
                    ((TextView) findViewById(R.id.artist)).setText(String.valueOf(metadata.getComposerLastname()) + " " + metadata.getComposerFirstname());
                } else if (metadata.getMajor_genre().equals("jazz")) {
                    left1title.setText("SIDEMEN");
                    left1content.setText(metadata.getAdditionalArtists());
                    left2title.setText("RELEASE DATE");
                    left2content.setText(metadata.getReleasedDate());
                    left3title.setText("RECORD LABEL");
                    left3content.setText(metadata.getPublishedBy());
                    right1title.setText("INSTRUMENT/VOICE");
                    right1content.setText(metadata.getFeaturedInstruments());
                    right2title.setText("ORIG. RECORDED");
                    right2content.setText(metadata.getOriginalRecording());
                    right3title.setText("PRODUCER");
                    right3content.setText(metadata.getProducer());
                } else {
                    left1title.setText("RELEASE DATE");
                    left1content.setText(metadata.getReleasedDate());
                    left2title.setText("ORIG. RECORDED");
                    left2content.setText(metadata.getOriginalRecording());
                    left3title.setText("PRODUCER");
                    left3content.setText(metadata.getProducer());
                    right1title.setText("ORIG. ARTIST");
                    right1content.setText(metadata.getOriginalPerformer());
                    right2title.setText("ADDITIONAL ARTISTS");
                    right2content.setText(metadata.getAdditionalArtists());
                    right3title.setText("RECORD LABEL");
                    right3content.setText(metadata.getPublishedBy());
                }
            }
            if (!this.playModeJustSet.booleanValue()) {
                ImageView repeat = (ImageView) findViewById(R.id.repeat);
                ImageView shuffle = (ImageView) findViewById(R.id.shuffle);
                if (metadata.getCurrentPlayMode().equals("NORMAL")) {
                    repeat.setImageResource(R.drawable.repeat_off);
                    repeat.setTag("repeat_off");
                    shuffle.setImageResource(R.drawable.shuffle_off);
                    shuffle.setTag("shuffle_off");
                } else if (metadata.getCurrentPlayMode().equals("REPEAT_ONE")) {
                    repeat.setImageResource(R.drawable.repeat_one);
                    repeat.setTag("repeat_one");
                    shuffle.setImageResource(R.drawable.shuffle_off);
                    shuffle.setTag("shuffle_off");
                } else if (metadata.getCurrentPlayMode().equals("REPEAT_NORMAL")) {
                    repeat.setImageResource(R.drawable.repeat_on);
                    repeat.setTag("repeat_on");
                    shuffle.setImageResource(R.drawable.shuffle_off);
                    shuffle.setTag("shuffle_off");
                } else if (metadata.getCurrentPlayMode().equals("REPEAT_SHUFFLE")) {
                    repeat.setImageResource(R.drawable.repeat_on);
                    repeat.setTag("repeat_on");
                    shuffle.setImageResource(R.drawable.shuffle_on);
                    shuffle.setTag("shuffle_on");
                } else if (metadata.getCurrentPlayMode().equals("SHUFFLE")) {
                    repeat.setImageResource(R.drawable.repeat_off);
                    repeat.setTag("repeat_off");
                    shuffle.setImageResource(R.drawable.shuffle_on);
                    shuffle.setTag("shuffle_on");
                }
            } else {
                this.playModeJustSet = false;
            }
            ImageView volume = (ImageView) findViewById(R.id.volume);
            if (metadata.getVolume().equals("volume_0.png")) {
                volume.setImageResource(R.drawable.volume_0);
            } else if (metadata.getVolume().equals("volume_1.png")) {
                volume.setImageResource(R.drawable.volume_1);
            } else if (metadata.getVolume().equals("volume_2.png")) {
                volume.setImageResource(R.drawable.volume_2);
            } else if (metadata.getVolume().equals("volume_3.png")) {
                volume.setImageResource(R.drawable.volume_3);
            } else if (metadata.getVolume().equals("volume_4.png")) {
                volume.setImageResource(R.drawable.volume_4);
            } else if (metadata.getVolume().equals("volume_mute.png")) {
                volume.setImageResource(R.drawable.volume_mute);
            }
            if (this.height > this.smallScreens && !this.blockVolumUpdate) {
                if (!this.blockVolumUpdateBtn) {
                    int newVal = 0;
                    try {
                        newVal = (this.volumeBar.getMax() * Integer.parseInt(metadata.getVolVal())) / 100;
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    this.volumeBar.setProgress(newVal);
                } else {
                    this.blockVolumUpdateBtn = false;
                }
            }
            this.mHandler.removeCallbacks(this.pullMetadata);
            this.mHandler.postDelayed(this.pullMetadata, 3000L);
            this.myDialog.dismiss();
            return;
        }
        if (this.myDialog != null) {
            this.myDialog.dismiss();
        }
        if (this.connectionWeak > 1) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Connection to the device weak.", 1);
            toast.show();
            this.connectionWeak = 0;
        } else {
            this.connectionWeak++;
        }
        this.mHandler.removeCallbacks(this.pullMetadata);
        this.mHandler.postDelayed(this.pullMetadata, 3000L);
        this.blockVolumUpdate = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress() {
        if (this.metadata != null && !this.metadata.getTransportState().equals("STOPPED") && !this.metadata.getTransportState().equals("PAUSED_PLAYBACK")) {
            this.pHandler.removeCallbacks(this.updateProgressbar);
            if (this.fastforward) {
                this.pHandler.postDelayed(this.updateProgressbar, 200L);
            } else if (this.fastrewind) {
                this.pHandler.postDelayed(this.updateProgressbar, 500L);
            } else {
                this.pHandler.postDelayed(this.updateProgressbar, 1000L);
            }
        }
    }

    public void displayError() {
        this.myDialog.dismiss();
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(R.string.connectionError).setPositiveButton("Try Again", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.23
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                Playscreen.this.myDialog = ProgressDialog.show(Playscreen.this, "Working...", "Loading playscreen...", true, false);
                Playscreen.this.url = "http://" + Playscreen.this.devIP + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true";
                new LoadingPlayscreen().execute(Playscreen.this);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.24
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                Playscreen.this.myDialog.dismiss();
            }
        }).show();
    }

    public void add2Favs(Object response) {
        if (response.equals("1")) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Successfully added radio station", 1);
            toast.show();
        } else if (response.equals("duplicate")) {
            new AlertDialog.Builder(this).setTitle("Error!").setMessage("The station is already saved as Favorite.").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.25
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        } else {
            new AlertDialog.Builder(this).setTitle("Error!").setMessage("Favorite could not be saved. Please try again.").setNeutralButton("Ok", new DialogInterface.OnClickListener() { // from class: olive.app.Playscreen.26
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        int newVal;
        int newVal2;
        try {
            int action = event.getAction();
            int keyCode = event.getKeyCode();
            switch (keyCode) {
                case 24:
                    if (action == 1) {
                        if (this.height > this.smallScreens) {
                            newVal2 = this.volumeBar.getProgress() + 5;
                        } else {
                            newVal2 = Integer.parseInt(this.metadata.getVolVal()) + 5;
                        }
                        if (newVal2 > 100) {
                            newVal2 = 100;
                        }
                        new SendCommand().execute("http://" + this.devIP + "/includes/ajax/a_executeOperation.php?action=volumeSet&volume=" + newVal2 + "&mutestat=1");
                        if (this.height > this.smallScreens) {
                            this.volumeBar.setProgress(newVal2);
                            this.blockVolumUpdateBtn = true;
                        }
                    }
                    return true;
                case 25:
                    if (action == 1) {
                        if (this.height > this.smallScreens) {
                            newVal = this.volumeBar.getProgress() - 5;
                        } else {
                            newVal = Integer.parseInt(this.metadata.getVolVal()) - 5;
                        }
                        if (newVal < 0) {
                            newVal = 0;
                        }
                        new SendCommand().execute("http://" + this.devIP + "/includes/ajax/a_executeOperation.php?action=volumeSet&volume=" + newVal + "&mutestat=1");
                        if (this.height > this.smallScreens) {
                            this.volumeBar.setProgress(newVal);
                            this.blockVolumUpdateBtn = true;
                        }
                    }
                    return true;
                default:
                    return super.dispatchKeyEvent(event);
            }
        } catch (Exception e) {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        this.mHandler.removeCallbacks(this.pullMetadata);
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mHandler.removeCallbacks(this.pullMetadata);
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
