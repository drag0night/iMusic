package vanhy.com.imusic.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import vanhy.com.imusic.R;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.model.Playlist;

public class BaiHatAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<BaiHat> listBH;

    public BaiHatAdapter(Context context, int layout, ArrayList<BaiHat> listBH) {
        this.context = context;
        this.layout = layout;
        this.listBH = listBH;
    }

    @Override
    public int getCount() {
        return listBH.size();
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
            holder = new BaiHatAdapter.ViewHolder();
            holder.textViewTenBh = (TextView) view.findViewById(R.id.textViewTenPL);
            holder.textViewTenCs = (TextView) view.findViewById(R.id.textViewSL_PL);
            view.setTag(holder);
        } else {
            holder = (BaiHatAdapter.ViewHolder) view.getTag();
        }
        btn = (ImageButton) view.findViewById(R.id.btnImageMore_BH);
        BaiHat bh = listBH.get(i);

        holder.textViewTenBh.setText(bh.getTen());
        holder.textViewTenCs.setText(bh.getCasi());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.design.widget.BottomSheetDialog dialog = new android.support.design.widget.BottomSheetDialog(context);
                View temp = inflater.inflate(R.layout.bottom_set_dialog_list_bh, null);
                dialog.setContentView(temp);
                dialog.show();
                LinearLayout btnAdd = (LinearLayout) temp.findViewById(R.id.addToPlaylist);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogadd = new Dialog(context);
                        dialogadd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        View temp1 = inflater.inflate(R.layout.add_to_playlist_dialog, null);
                        ListView listView = (ListView) temp1.findViewById(R.id.listViewPlaylist);
                        ArrayList<Playlist> listPl = new ArrayList<Playlist>();
                        listPl.add(new Playlist("My playlist 1", 10));
                        listPl.add(new Playlist("My playlist 2", 50));
                        AddToPlaylistAdapter adapter = new AddToPlaylistAdapter(context, R.layout.add_to_playlist_item, listPl);
                        listView.setAdapter(adapter);
                        dialogadd.setContentView(temp1);
                        dialogadd.show();
                        dialog.cancel();
                    }
                });
            }
        });

        return view;
    }

    private void setOnClick() {

    }

    private class ViewHolder {
        private TextView textViewTenBh;
        private TextView textViewTenCs;
    }
}
