package olive.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Artists tab, one row per artist. The Olive's "browse by artist" returns one row per album (album title,
 * artist, artwork, album id), which the original app listed as-is, so each artist appeared once per album.
 * After the original code shows a page of results, this groups the rows by artist; tapping an artist opens
 * OliveArtistActivity with their albums. Because grouped rows no longer line up with the original
 * scroll-based paging (64 albums at a time), the remaining pages are fetched straight away.
 */
public final class OliveArtists {
    static final String EXTRA_ARTIST = "olive.artist";
    static final String EXTRA_IDS = "olive.albumIds";
    static final String EXTRA_TITLES = "olive.albumTitles";
    static final String EXTRA_ART = "olive.albumArt";
    private static final int PAGE = 64;

    private static final LruCache<String, Bitmap> ART = new LruCache<String, Bitmap>(64);
    private static final ExecutorService IO = Executors.newFixedThreadPool(3);
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    private OliveArtists() {
    }

    static final class Artist {
        final String name;
        final ArrayList<String> ids = new ArrayList<String>();
        final ArrayList<String> titles = new ArrayList<String>();
        final ArrayList<String> art = new ArrayList<String>();

        Artist(String name) {
            this.name = name;
        }

        String cover() {
            for (String a : art) {
                if (a.length() > 0) {
                    return a;
                }
            }
            return "";
        }
    }

    /** Groups album rows by artist (case-insensitive, in the order the Olive returns them). */
    static List<Artist> group(List<String> artists, List<String> titles, List<String> ids, List<String> art, String devIP) {
        Map<String, Artist> byKey = new LinkedHashMap<String, Artist>();
        int n = Math.min(Math.min(artists.size(), titles.size()), ids.size());
        for (int i = 0; i < n; i++) {
            String name = artists.get(i) == null ? "" : artists.get(i).trim();
            String key = name.toLowerCase(Locale.ROOT);
            Artist a = byKey.get(key);
            if (a == null) {
                a = new Artist(name.length() > 0 ? name : "Unknown artist");
                byKey.put(key, a);
            }
            a.ids.add(ids.get(i));
            a.titles.add(titles.get(i));
            String url = i < art.size() && art.get(i) != null ? art.get(i).trim() : "";
            // The Olive reports its own artwork URLs as 127.0.0.1, as the original artwork loader also handles.
            a.art.add(url.length() > 0 && devIP != null ? url.replace("127.0.0.1", devIP) : url);
        }
        return new ArrayList<Artist>(byKey.values());
    }

    /** Called with the Artists tab right after the original code has shown a page of results. */
    @SuppressWarnings("unchecked")
    public static void onArtistsShown(final Activity tab) {
        try {
            Object items = field(tab, "items");
            if (items == null || !"".equals(call(items, "getError"))) {
                return;
            }
            String devIP = (String) field(tab, "devIP");
            List<Artist> artists = group((List<String>) call(items, "getArtist"), (List<String>) call(items, "getTitle"),
                    (List<String>) call(items, "getObjId"), (List<String>) call(items, "getArtwork"), devIP);
            ListView list = (ListView) field(tab, "artists");
            ListAdapter current = list.getAdapter();
            final Adapter adapter;
            if (current instanceof Adapter) {
                adapter = (Adapter) current;
                adapter.artists = artists;
                adapter.notifyDataSetChanged();
            } else {
                adapter = new Adapter(tab, artists);
                list.setAdapter(adapter);
            }
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    open(tab, adapter.artists.get(position));
                }
            });
            loadNextPage(tab, items);
        } catch (Exception e) {
            e.printStackTrace(); // leave the original list in place
        }
    }

    static void open(Activity from, Artist artist) {
        Intent intent = new Intent(from, OliveArtistActivity.class)
                .putExtra(EXTRA_ARTIST, artist.name)
                .putStringArrayListExtra(EXTRA_IDS, artist.ids)
                .putStringArrayListExtra(EXTRA_TITLES, artist.titles)
                .putStringArrayListExtra(EXTRA_ART, artist.art);
        from.startActivity(intent);
    }

    /** Fetches the next page with the original loader, until the Olive's reported total is loaded. */
    private static void loadNextPage(Activity tab, Object items) throws Exception {
        String url = (String) field(tab, "url");
        if (url == null || !url.contains("searchCriteria=artist") || Boolean.TRUE.equals(field(tab, "processing"))) {
            return; // search results come in one go
        }
        int total;
        try {
            total = Integer.parseInt(String.valueOf(call(items, "getTotalItems")).trim());
        } catch (NumberFormatException e) {
            return;
        }
        int loaded = ((List<?>) call(items, "getObjId")).size();
        int start = (Integer) field(tab, "startindex");
        if (loaded >= total || start + PAGE >= total) {
            return;
        }
        set(tab, "processing", Boolean.TRUE);
        set(tab, "startindex", start + PAGE);
        set(tab, "url", "http://" + field(tab, "devIP") + "/backend/artworkZapp.php?cmd=browseart&searchCriteria=artist&xml=true&startIndex=" + (start + PAGE));
        AsyncTask<?, ?, ?> loader = (AsyncTask<?, ?, ?>) Class.forName("olive.app.LoadingArtist").getConstructor().newInstance();
        // The loader is an AsyncTask<Object[], ...>: its parameters must be an Object[][], as in the original.
        set(tab, "myAsynTask", Compat.exec(loader, new Object[][] {new Object[] {tab, ""}}));
    }

    static final class Adapter extends BaseAdapter {
        private final Activity activity;
        List<Artist> artists;

        Adapter(Activity activity, List<Artist> artists) {
            this.activity = activity;
            this.artists = artists;
        }

        public int getCount() {
            return artists.size();
        }

        public Object getItem(int position) {
            return artists.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View row, ViewGroup parent) {
            if (row == null) {
                row = LayoutInflater.from(activity).inflate(res(activity, "layout", "artist_item"), parent, false);
            }
            Artist a = artists.get(position);
            ((TextView) row.findViewById(res(activity, "id", "artist"))).setText(a.name);
            TextView sub = (TextView) row.findViewById(res(activity, "id", "album"));
            sub.setVisibility(View.VISIBLE);
            sub.setText(a.ids.size() == 1 ? a.titles.get(0) : a.ids.size() + " albums");
            bindArt((ImageView) row.findViewById(res(activity, "id", "image")), a.cover());
            return row;
        }
    }

    /** Loads artwork into an image view (cached; recycled rows show the right image). */
    static void bindArt(final ImageView view, final String url) {
        view.setTag(url);
        Bitmap cached = url.length() == 0 ? null : ART.get(url);
        if (cached != null || url.length() == 0) {
            if (cached != null) {
                view.setImageBitmap(cached);
            } else {
                view.setImageResource(res(view.getContext(), "drawable", "artworknotfound"));
            }
            return;
        }
        view.setImageResource(res(view.getContext(), "drawable", "artworknotfound"));
        final int px = Math.round(56 * view.getResources().getDisplayMetrics().density);
        IO.execute(new Runnable() {
            public void run() {
                final Bitmap bitmap = OliveArt.load(url, px);
                MAIN.post(new Runnable() {
                    public void run() {
                        if (bitmap != null) {
                            ART.put(url, bitmap);
                            if (url.equals(view.getTag())) {
                                view.setImageBitmap(bitmap);
                            }
                        }
                    }
                });
            }
        });
    }

    static int res(Context c, String type, String name) {
        return c.getResources().getIdentifier(name, type, c.getPackageName());
    }

    private static Object field(Object target, String name) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }

    private static void set(Object target, String name, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    private static Object call(Object target, String method) throws Exception {
        Method m = target.getClass().getMethod(method);
        return m.invoke(target);
    }
}
