package vanhy.com.imusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vanhy.com.imusic.R;
import vanhy.com.imusic.model.CaSi;

public class CaSiAdapter extends BaseAdapter{

    private Context context;
    private int layout;
    private ArrayList<CaSi> listCs;

    public CaSiAdapter(Context context, int layout, ArrayList<CaSi> listCs) {
        this.context = context;
        this.layout = layout;
        this.listCs = listCs;
    }

    @Override
    public int getCount() {
        return listCs.size();
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
            holder.textViewTenCs = view.findViewById(R.id.textViewTenCasi);
            holder.textViewSl = view.findViewById(R.id.textViewSL);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CaSi cs = listCs.get(i);

        holder.textViewTenCs.setText(cs.getTen());
        holder.textViewSl.setText(cs.getSl()+" bài hát");

        return view;
    }

    private class ViewHolder {
        TextView textViewTenCs;
        TextView textViewSl;
    }
}
