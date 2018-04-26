package vanhy.com.imusic.adapter;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import vanhy.com.imusic.R;
import vanhy.com.imusic.model.Playlist;

public class PlaylistAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Playlist> listPL;

    public PlaylistAdapter(Context context, int layout, ArrayList<Playlist> listPL) {
        this.context = context;
        this.layout = layout;
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
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        ImageButton btn;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.textViewTenPL = (TextView) view.findViewById(R.id.textViewTenPL);
            holder.textViewsl = (TextView) view.findViewById(R.id.textViewSL_PL);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        btn = (ImageButton) view.findViewById(R.id.btnImageMore_BH);
        Playlist pl = listPL.get(i);

        holder.textViewTenPL.setText(pl.getTen());
        holder.textViewsl.setText(pl.getSl()+" bài hát");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialog = new BottomSheetDialog(context);
                View temp = inflater.inflate(R.layout.bottom_sheet_list_playlist, null);
                dialog.setContentView(temp);
                dialog.show();
            }
        });

        return view;
    }

    private class ViewHolder {

        TextView textViewTenPL;
        TextView textViewsl;

    }

}
