package olive.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/* loaded from: classes.dex */
public class TrackAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private Items myItems;

    public static class ViewHolder {
        public TextView title;
    }

    public TrackAdapter(Items items, Activity activity) {
        this.myItems = items;
        this.inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View vi = convertView;
        if (convertView == null) {
            vi = this.inflater.inflate(R.layout.track_item, (ViewGroup) null);
            holder = new ViewHolder();
            holder.title = (TextView) vi.findViewById(R.id.trackTitle);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        holder.title.setText(this.myItems.getTitle().get(position));
        return vi;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.myItems.getTitle().size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }
}
