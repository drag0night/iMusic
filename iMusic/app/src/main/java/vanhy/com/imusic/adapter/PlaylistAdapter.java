package vanhy.com.imusic.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vanhy.com.imusic.AddSongToNewPlaylistActivity;
import vanhy.com.imusic.OnAddedToDB;
import vanhy.com.imusic.R;
import vanhy.com.imusic.SQLite.SQLite;
import vanhy.com.imusic.fragment.PlayListFragment;
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
    public View getView(final int i, View view, ViewGroup parent) {
        final ViewHolder holder;
        final ImageButton btn;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.textViewTenPL = (TextView) view.findViewById(R.id.textViewTenPL);
            holder.textViewsl = (TextView) view.findViewById(R.id.textViewSL_PL);
            holder.imgPlaylist = (ImageView) view.findViewById(R.id.imageBaihat);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        btn = (ImageButton) view.findViewById(R.id.btnImageMore_BH);
        final Playlist pl = listPL.get(i);

        holder.textViewTenPL.setText(pl.getTen());
        holder.textViewsl.setText(pl.getListBh().size()+" bài hát");
        if (pl.getListBh().size() == 0) {
            holder.imgPlaylist.setImageResource(R.drawable.music_placeholder);
        } else {
            Picasso.with(context).load(pl.getListBh().get(0).getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(holder.imgPlaylist);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                View temp = inflater.inflate(R.layout.bottom_sheet_list_playlist, null);
                ImageView imgPl = (ImageView) temp.findViewById(R.id.imageView_BoS);
                TextView txtTenpk = (TextView) temp.findViewById(R.id.textViewTenPlaylist_BoS);
                TextView txtSl = (TextView) temp.findViewById(R.id.textViewSL_BoS);
                LinearLayout btnEditList = (LinearLayout) temp.findViewById(R.id.btnImageEditList);
                LinearLayout btnEdit = (LinearLayout) temp.findViewById(R.id.btnImageEdit);
                LinearLayout btnXoa = (LinearLayout) temp.findViewById(R.id.btnImageXoa);
                if (listPL.get(i).getListBh().size() == 0) {
                    imgPl.setImageResource(R.drawable.music_placeholder);
                } else {
                    Picasso.with(context).load(listPL.get(i).getListBh().get(0).getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(imgPl);
                }
                txtTenpk.setText(listPL.get(i).getTen());
                txtSl.setText(listPL.get(i).getListBh().size()+" bài hát");
                dialog.setContentView(temp);
                dialog.show();
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SQLite.deletePlaylist(context, pl)) {
                            OnAddedToDB onAddedToDB = PlayListFragment.getInstance();
                            onAddedToDB.onRefresh();
                            Toast.makeText(context, "Xóa playlist thành công", Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                });
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        final Dialog dialog_edit = new Dialog(context);
                        dialog_edit.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        View temp = inflater.inflate(R.layout.add_play_list_dialog, null);
                        TextView tieude = (TextView) temp.findViewById(R.id.textViewTieuDe);
                        final EditText edtTenpl = (EditText) temp.findViewById(R.id.edtTenPlaylist);
                        Button btnXacnhan = (Button) temp.findViewById(R.id.btnThemPlaylist);
                        Button btnBoqua = (Button) temp.findViewById(R.id.btnBoqua);
                        btnXacnhan.setText("Xác nhận");
                        tieude.setText("Sửa tên playlist");
                        dialog_edit.setContentView(temp);
                        dialog_edit.show();
                        btnXacnhan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tenpl = edtTenpl.getText().toString();
                                if (!"".equals(tenpl)) {
                                    if (SQLite.checkPlaylistNameExist(context, tenpl)) {
                                        Toast.makeText(context, "Playlist đã tồn tại", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (SQLite.editNamePlaylist(context, pl.getId(), tenpl)) {
                                            OnAddedToDB onAddedToDB = PlayListFragment.getInstance();
                                            onAddedToDB.onRefresh();
                                            Toast.makeText(context, "Đã sửa tên playlist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                dialog_edit.cancel();
                            }
                        });
                        btnBoqua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog_edit.cancel();
                            }
                        });
                    }
                });
            }
        });

        return view;
    }

    private class ViewHolder {

        private TextView textViewTenPL;
        private TextView textViewsl;
        private ImageView imgPlaylist;

    }

}
