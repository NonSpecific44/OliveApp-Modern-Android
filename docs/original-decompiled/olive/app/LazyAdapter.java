package olive.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.teleal.cling.model.message.header.EXTHeader;

/* loaded from: classes.dex */
public class LazyAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private Items data;
    public ImageLoader imageLoader;
    private String ip;
    private int myLayout;

    public static class ViewHolder {
        public TextView album;
        public TextView artist;
        public ImageView image;
    }

    public LazyAdapter(Activity a, Items d, String devIP, int layout) {
        this.activity = a;
        this.data = d;
        this.ip = devIP;
        inflater = (LayoutInflater) this.activity.getSystemService("layout_inflater");
        this.imageLoader = new ImageLoader(this.activity.getApplicationContext());
        this.myLayout = layout;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.data.getArtwork().size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(this.myLayout, (ViewGroup) null);
            holder = new ViewHolder();
            holder.artist = (TextView) vi.findViewById(R.id.artist);
            holder.album = (TextView) vi.findViewById(R.id.album);
            holder.image = (ImageView) vi.findViewById(R.id.image);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        holder.artist.setText(this.data.getArtist().get(position));
        holder.album.setText(this.data.getTitle().get(position));
        holder.image.setTag(this.data.getArtwork().get(position).replace("127.0.0.1", this.ip));
        if (this.data.getArtwork().get(position) == EXTHeader.DEFAULT_VALUE) {
            holder.image.setImageResource(R.drawable.artworknotfound);
        } else {
            this.imageLoader.DisplayImage(this.data.getArtwork().get(position).replace("127.0.0.1", this.ip), this.activity, holder.image);
        }
        return vi;
    }
}
