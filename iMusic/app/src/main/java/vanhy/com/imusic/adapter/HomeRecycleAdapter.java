package vanhy.com.imusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vanhy.com.imusic.NgheNhacActivity;
import vanhy.com.imusic.R;
import vanhy.com.imusic.model.BaiHat;

public class HomeRecycleAdapter extends RecyclerView.Adapter {
    private ArrayList<BaiHat> personNames;
    private Context context;
    public HomeRecycleAdapter(Context context, ArrayList<BaiHat> personNames) {
        this.context = context;
        this.personNames = personNames;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_home_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hold, final int position) {
        MyViewHolder holder=(MyViewHolder)hold;
        holder.name.setText(personNames.get(position).getTitle());
        Picasso.with(context).load(personNames.get(position).getArtworkUrl()).placeholder(R.mipmap.ducphuc).into(holder.image);
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NgheNhacActivity.class);
                intent.putExtra("songList", personNames);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return personNames.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}