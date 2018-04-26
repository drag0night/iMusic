package vanhy.com.imusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vanhy.com.imusic.BottomSheetDialog;
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
                android.support.design.widget.BottomSheetDialog dialog = new android.support.design.widget.BottomSheetDialog(context);
                View temp = inflater.inflate(R.layout.bottom_set_dialog_list_bh, null);
                dialog.setContentView(temp);
                dialog.show();
            }
        });

        return view;
    }

    private class ViewHolder {
        private TextView textViewTenBh;
        private TextView textViewTenCs;
    }
}
