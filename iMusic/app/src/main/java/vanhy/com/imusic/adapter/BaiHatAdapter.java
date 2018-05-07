package vanhy.com.imusic.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import vanhy.com.imusic.AddSongToNewPlaylistActivity;
import vanhy.com.imusic.OnAddedToDB;
import vanhy.com.imusic.R;
import vanhy.com.imusic.SQLite.SQLite;
import vanhy.com.imusic.fragment.PlayListFragment;
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
    public View getView(final int i, View view, ViewGroup parent) {
        ViewHolder holder;
        ImageButton btn;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(layout, null);
            holder = new BaiHatAdapter.ViewHolder();
            holder.textViewTenBh = (TextView) view.findViewById(R.id.textViewTenPL);
            holder.textViewTenCs = (TextView) view.findViewById(R.id.textViewSL_PL);
            holder.imgBaihat = (ImageView) view.findViewById(R.id.imageBaihat);
            view.setTag(holder);
        } else {
            holder = (BaiHatAdapter.ViewHolder) view.getTag();
        }
        btn = (ImageButton) view.findViewById(R.id.btnImageMore_BH);
        BaiHat bh = listBH.get(i);

        holder.textViewTenBh.setText(bh.getTitle());
        holder.textViewTenCs.setText(bh.getArtist());
        Picasso.with(context).load(bh.getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(holder.imgBaihat);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.design.widget.BottomSheetDialog dialog = new android.support.design.widget.BottomSheetDialog(context);
                View temp = inflater.inflate(R.layout.bottom_set_dialog_list_bh, null);
                ImageView imgBh = (ImageView) temp.findViewById(R.id.imageView_BoS);
                TextView textTenBh = (TextView) temp.findViewById(R.id.textViewTenBH_BoS);
                TextView textTenCs = (TextView) temp.findViewById(R.id.textViewTenCS_BoS);
                Picasso.with(context).load(listBH.get(i).getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(imgBh);
                textTenBh.setText(listBH.get(i).getTitle());
                textTenCs.setText(listBH.get(i).getArtist());
                dialog.setContentView(temp);
                dialog.show();
                LinearLayout btnAdd = (LinearLayout) temp.findViewById(R.id.addToPlaylist);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialogadd = new Dialog(context);
                        dialogadd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        View temp1 = inflater.inflate(R.layout.add_to_playlist_dialog, null);
                        ListView listView = (ListView) temp1.findViewById(R.id.listViewPlaylist);
                        final ArrayList<Playlist> listPl = SQLite.getAllPlaylist(context);
                        AddToPlaylistAdapter adapter = new AddToPlaylistAdapter(context, R.layout.add_to_playlist_item, listPl);
                        listView.setAdapter(adapter);
                        dialogadd.setContentView(temp1);
                        dialogadd.show();
                        dialog.cancel();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (SQLite.addSongToPlaylist(context, listPl.get(position).getId(), listBH.get(i))) {
                                    OnAddedToDB onAddedToDB = PlayListFragment.getInstance();
                                    onAddedToDB.onRefresh();
                                    Toast.makeText(context, "Thêm vào playlist "+listPl.get(position).getTen()+" thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Bài hát đã có trong "+listPl.get(position).getTen(), Toast.LENGTH_SHORT).show();
                                }
                                dialogadd.cancel();
                            }
                        });
                        LinearLayout btnThempl = (LinearLayout) temp1.findViewById(R.id.btnThemPlaylist);
                        btnThempl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogadd.cancel();
                                final Dialog dialog_add_pl = new Dialog(context);
                                dialog_add_pl.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                View view_add_pl = inflater.inflate(R.layout.add_play_list_dialog, null);
                                dialog_add_pl.setContentView(view_add_pl);
                                dialog_add_pl.show();
                                Button btnThem = (Button) view_add_pl.findViewById(R.id.btnThemPlaylist);
                                Button btnBoqua = (Button) view_add_pl.findViewById(R.id.btnBoqua);
                                final EditText edtTenpl = (EditText) view_add_pl.findViewById(R.id.edtTenPlaylist);

                                btnThem.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String tenpl = edtTenpl.getText().toString();
                                        if (!"".equals(tenpl)) {
                                            if (!SQLite.checkPlaylistNameExist(context, tenpl)) {
                                                ArrayList<BaiHat> arrBh = new ArrayList<BaiHat>();
                                                arrBh.add(listBH.get(i));
                                                SQLite.createPlaylist(context, tenpl, arrBh);
                                                OnAddedToDB onAddedToDB = PlayListFragment.getInstance();
                                                onAddedToDB.onRefresh();
                                                Toast.makeText(context, "Thêm vào playlist thành công", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "Playlist đã tồn tại", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        dialog_add_pl.cancel();
                                    }
                                });

                                btnBoqua.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_add_pl.cancel();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        return view;
    }


    private class ViewHolder {
        private TextView textViewTenBh;
        private TextView textViewTenCs;
        private ImageView imgBaihat;
    }
}
