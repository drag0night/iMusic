package vanhy.com.imusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
            holder.imgPlaylist = (ImageView) view.findViewById(R.id.imgPlaylist);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Playlist pl = listpl.get(i);

        holder.textViewTenpl.setText(pl.getTen());
        holder.textViewsl.setText(pl.getListBh().size()+" bài hát");
        if (pl.getListBh().size() == 0) {
            holder.imgPlaylist.setImageResource(R.drawable.music_placeholder);
        } else {
            Picasso.with(context).load(pl.getListBh().get(0).getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(holder.imgPlaylist);
        }

        return view;
    }

    private class ViewHolder {
        TextView textViewTenpl;
        TextView textViewsl;
        ImageView imgPlaylist;
    }

}
