package vanhy.com.imusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vanhy.com.imusic.R;
import vanhy.com.imusic.model.Playlist;

public class AddToPlaylistAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Playlist> listpl;

    public AddToPlaylistAdapter(Context context, int layout, ArrayList<Playlist> listpl) {
        this.context = context;
        this.layout = layout;
        this.listpl = listpl;
    }

    @Override
    public int getCount() {
        return listpl.size();
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
            holder.textViewTenpl = (TextView) view.findViewById(R.id.textViewTenPL);
            holder.textViewsl = (TextView) view.findViewById(R.id.textViewSL);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Playlist pl = listpl.get(i);

        holder.textViewTenpl.setText(pl.getTen());
        holder.textViewsl.setText(pl.getSl()+" bài hát");

        return view;
    }

    private class ViewHolder {
        TextView textViewTenpl;
        TextView textViewsl;
    }

}
