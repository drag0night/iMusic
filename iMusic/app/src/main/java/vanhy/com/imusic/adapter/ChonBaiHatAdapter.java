package vanhy.com.imusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import vanhy.com.imusic.R;
import vanhy.com.imusic.model.BaiHat;

public class ChonBaiHatAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<BaiHat> listBh;

    public ChonBaiHatAdapter(Context context, int layout, ArrayList<BaiHat> listBh) {
        this.context = context;
        this.layout = layout;
        this.listBh = listBh;
    }

    @Override
    public int getCount() {
        return listBh.size();
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
            holder.textViewTenBh = (TextView) view.findViewById(R.id.textViewTenPL);
            holder.textViewTenCs = (TextView) view.findViewById(R.id.textViewSL_PL);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        BaiHat bh = listBh.get(i);

        holder.textViewTenBh.setText(bh.getTitle());
        holder.textViewTenCs.setText(bh.getArtist());

        return view;
    }

    private class ViewHolder {
        TextView textViewTenBh;
        TextView textViewTenCs;
    }

}
