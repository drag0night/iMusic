package vanhy.com.imusic.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import vanhy.com.imusic.OnAddedToDB;
import vanhy.com.imusic.R;
import vanhy.com.imusic.SQLite.SQLite;
import vanhy.com.imusic.fragment.PlayListFragment;
import vanhy.com.imusic.model.Album;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.model.Playlist;

public class AlbumAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Album> listPL;

    public AlbumAdapter(Context context, ArrayList<Album> listPL) {
        this.context = context;
        this.listPL = listPL;
    }

    @Override
    public int getCount() {
        return listPL.size();
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
    public View getView(final int i, View view, ViewGroup parent) {
        final ViewHolder holder;
        final ImageButton btn;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.album_item, null);

            holder = new ViewHolder();
            holder.textViewTenPL = (TextView) view.findViewById(R.id.textViewTenPL);
            holder.textViewsl = (TextView) view.findViewById(R.id.textViewSL_PL);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        btn = (ImageButton) view.findViewById(R.id.btnImageMore_BH);
        final Album pl = listPL.get(i);

        holder.textViewTenPL.setText(pl.getTitle());
        holder.textViewsl.setText(pl.getTrachCount()+" bài hát");
        holder.imgPlaylist = (ImageView) view.findViewById(R.id.albumicon);
        Picasso.with(context).load(pl.getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(holder.imgPlaylist);
        return view;
    }

    private class ViewHolder {

        private TextView textViewTenPL;
        private TextView textViewsl;
        private ImageView imgPlaylist;

    }

}
