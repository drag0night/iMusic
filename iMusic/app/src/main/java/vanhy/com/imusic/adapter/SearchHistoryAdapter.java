package vanhy.com.imusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import vanhy.com.imusic.HistoryLoader;
import vanhy.com.imusic.R;

/**
 * Created by Admin on 4/27/2018.
 */

public class SearchHistoryAdapter extends BaseAdapter {

    private Context context;

    public SearchHistoryAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return HistoryLoader.getInstance().getHistory(context).size();
    }

    @Override
    public Object getItem(int position) {
        return HistoryLoader.getInstance().getHistory(context).get(position);
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
            view = inflater.inflate(R.layout.search_history_item, null);
            holder = new SearchHistoryAdapter.ViewHolder();
            holder.history = (TextView) view.findViewById(R.id.textViewHistory);
            //holder.textViewTenCs = (TextView) view.findViewById(R.id.textViewSL_PL);
            view.setTag(holder);
        } else {
            holder = (SearchHistoryAdapter.ViewHolder) view.getTag();
        }
        btn = view.findViewById(R.id.btnImageMore_BH);
        btn.setTag(i);

        holder.history.setText(HistoryLoader.getInstance().getHistory(context).get(i));
        //holder.textViewTenCs.setText(bh.getCasi());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton imageButton=(ImageButton)v;
                HistoryLoader.getInstance().deleteHistory(Integer.parseInt(imageButton.getTag().toString()),context);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    private class ViewHolder {
        private TextView history;
    }
}