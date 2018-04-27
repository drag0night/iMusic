package vanhy.com.imusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import vanhy.com.imusic.R;
import vanhy.com.imusic.model.Album;

public class AlbumAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Album> listalbum;

    public AlbumAdapter(Context context, int layout, ArrayList<Album> listalbum) {
        this.context = context;
        this.layout = layout;
        this.listalbum = listalbum;
    }

    @Override
    public int getCount() {
        return listalbum.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.textViewTenalbum = (TextView) view.findViewById(R.id.textViewTenAlbum);
            holder.textViewsl = (TextView) view.findViewById(R.id.textViewSL);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Album ab = listalbum.get(i);

        holder.textViewTenalbum.setText(ab.getTen());
        holder.textViewsl.setText(ab.getSl()+" bài hát");

        return view;
    }

    private class ViewHolder {
        TextView textViewTenalbum;
        TextView textViewsl;
    }

}
