package olive.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * An artist's albums, opened from the Artists tab. Uses the app's list-screen layout (toolbar, list,
 * now-playing bar); tapping an album opens its tracks in the original track list screen.
 */
public class OliveArtistActivity extends Activity {
    private ArrayList<String> ids;
    private ArrayList<String> titles;
    private ArrayList<String> art;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(id("layout", "listview"));
        Intent in = getIntent();
        String artist = in.getStringExtra(OliveArtists.EXTRA_ARTIST);
        ids = list(in.getStringArrayListExtra(OliveArtists.EXTRA_IDS));
        titles = list(in.getStringArrayListExtra(OliveArtists.EXTRA_TITLES));
        art = list(in.getStringArrayListExtra(OliveArtists.EXTRA_ART));

        ((TextView) findViewById(id("id", "ListViewTitle"))).setText(artist);
        findViewById(id("id", "shuffleAll")).setVisibility(View.GONE);
        View.OnClickListener back = new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        };
        findViewById(id("id", "back")).setOnClickListener(back);
        findViewById(id("id", "backImg")).setOnClickListener(back);
        View.OnClickListener home = new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent().setClassName(OliveArtistActivity.this, "olive.app.LoadTabs")
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        };
        findViewById(id("id", "home")).setOnClickListener(home);
        findViewById(id("id", "homeImg")).setOnClickListener(home);
        findViewById(id("id", "playerbar")).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent().setClassName(OliveArtistActivity.this, "olive.app.Playscreen"));
            }
        });

        ListView albums = (ListView) findViewById(id("id", "ListView"));
        albums.setAdapter(new BaseAdapter() {
            public int getCount() {
                return ids.size();
            }

            public Object getItem(int position) {
                return titles.get(position);
            }

            public long getItemId(int position) {
                return position;
            }

            public View getView(int position, View row, ViewGroup parent) {
                if (row == null) {
                    row = LayoutInflater.from(OliveArtistActivity.this).inflate(id("layout", "album_item"), parent, false);
                }
                ((TextView) row.findViewById(id("id", "album"))).setText(position < titles.size() ? titles.get(position) : "");
                row.findViewById(id("id", "artist")).setVisibility(View.GONE);
                OliveArtists.bindArt((ImageView) row.findViewById(id("id", "image")), position < art.size() ? art.get(position) : "");
                return row;
            }
        });
        albums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                // The same extras the original Artists tab passed to the album's track list.
                Bundle bundle = new Bundle();
                bundle.putString("objId", ids.get(position));
                bundle.putString("albumTitle", position < titles.size() ? titles.get(position) : "");
                startActivity(new Intent().setClassName(OliveArtistActivity.this, "olive.app.TrackListTab").putExtras(bundle));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The now-playing bar shows what the other screens last saw (as the original list screens do).
        SharedPreferences prefs = getSharedPreferences("Olive_Settings", 0);
        ((TextView) findViewById(id("id", "playerTitle"))).setText(prefs.getString("trackTitle", ""));
        ((TextView) findViewById(id("id", "playerAlbum"))).setText(prefs.getString("albumTitle", ""));
    }

    private static ArrayList<String> list(ArrayList<String> value) {
        return value != null ? value : new ArrayList<String>();
    }

    private int id(String type, String name) {
        return getResources().getIdentifier(name, type, getPackageName());
    }
}
