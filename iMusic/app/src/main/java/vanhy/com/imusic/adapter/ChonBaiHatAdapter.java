package vanhy.com.imusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

import vanhy.com.imusic.R;
import vanhy.com.imusic.model.BaiHat;

public class ChonBaiHatAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<BaiHat> listBh;
    private ArrayList<BaiHat> selectedList;

    public ChonBaiHatAdapter(Context context, int layout, ArrayList<BaiHat> listBh) {
        this.context = context;
        this.layout = layout;
        this.listBh = listBh;
        this.selectedList = new ArrayList<BaiHat>();
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
    public View getView(final int i, View view, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.textViewTenBh = (TextView) view.findViewById(R.id.textViewTenPL);
            holder.textViewTenCs = (TextView) view.findViewById(R.id.textViewSL_PL);
            holder.imgBaihat = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkboxBH);

        BaiHat bh = listBh.get(i);

        holder.textViewTenBh.setText(bh.getTitle());
        holder.textViewTenCs.setText(bh.getArtist());
        Picasso.with(context).load(bh.getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(holder.imgBaihat);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedList.add(listBh.get(i));
                } else {
                    Iterator<BaiHat> it = selectedList.iterator();
                    while (it.hasNext()) {
                        BaiHat objBh = it.next();
                        if (objBh.getId() == listBh.get(i).getId()) {
                            it.remove();
                        }
                    }
                    Toast.makeText(context, selectedList.size()+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public ArrayList<BaiHat> getListSelected() {
        return selectedList;
    }

    private class ViewHolder {
        TextView textViewTenBh;
        TextView textViewTenCs;
        private ImageView imgBaihat;
    }

}
