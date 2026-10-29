package olive.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.teleal.common.mock.http.MockHttpServletRequest;

/* loaded from: classes.dex */
public class RadioAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private Items data;
    public ImageLoader imageLoader;
    private int myLayout;

    public static class ViewHolder {
        public ImageView image;
        public TextView title;
    }

    public RadioAdapter(Activity a, Items d, int layout) {
        this.activity = a;
        this.data = d;
        inflater = (LayoutInflater) this.activity.getSystemService("layout_inflater");
        this.imageLoader = new ImageLoader(this.activity.getApplicationContext());
        this.myLayout = layout;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.data.getTitle().size();
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
            holder.title = (TextView) vi.findViewById(R.id.stationTitle);
            holder.image = (ImageView) vi.findViewById(R.id.genreImage);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        holder.title = (TextView) vi.findViewById(R.id.stationTitle);
        holder.title.setText(this.data.getTitle().get(position));
        holder.image.setTag(this.data.getTitle().get(position));
        try {
            if (this.data.getObjId().get(position).indexOf(MockHttpServletRequest.DEFAULT_PROTOCOL) < 0) {
                if (this.data.getGenre().get(position).equals("classical")) {
                    holder.image.setImageResource(R.drawable.classical);
                } else if (this.data.getGenre().get(position).equals("jazz")) {
                    holder.image.setImageResource(R.drawable.jazz);
                } else if (this.data.getGenre().get(position).equals("blues")) {
                    holder.image.setImageResource(R.drawable.blues);
                } else if (this.data.getGenre().get(position).equals("rockpop")) {
                    holder.image.setImageResource(R.drawable.rockpop);
                } else if (this.data.getGenre().get(position).equals("spokenword")) {
                    holder.image.setImageResource(R.drawable.spokenword);
                } else if (this.data.getGenre().get(position).equals("other")) {
                    holder.image.setImageResource(R.drawable.other);
                } else {
                    holder.image.setVisibility(0);
                }
            } else {
                holder.image.setVisibility(0);
            }
        } catch (Exception e) {
            holder.image.setVisibility(0);
        }
        return vi;
    }
}
