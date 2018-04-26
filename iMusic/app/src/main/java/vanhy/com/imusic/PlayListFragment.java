package vanhy.com.imusic;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import vanhy.com.imusic.adapter.PlaylistAdapter;
import vanhy.com.imusic.model.Playlist;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListFragment extends Fragment {


    public PlayListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        final Activity context = getActivity();
        ArrayList<Playlist> pl = new ArrayList<Playlist>();
        pl.add(new Playlist("My playlist 1", 10));
        pl.add(new Playlist("My playlist 2", 50));
        ListView listview = (ListView) view.findViewById(R.id.listViewPlaylist);
        PlaylistAdapter adapter = new PlaylistAdapter(this.getContext(), R.layout.playlist_item, pl);
        listview.setAdapter(adapter);

        ImageButton btn = (ImageButton) view.findViewById(R.id.btnImageAddPlaylist);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View temp = inflater.inflate(R.layout.add_play_list_dialog, null);
                dialog.setContentView(temp);
                dialog.show();
                actionClick(dialog);
            }
        });

        return view;
    }

    private void actionClick(final Dialog dialog) {
        Button btnThem = (Button) dialog.findViewById(R.id.btnThemPlaylist);
        Button btnBoqua = (Button) dialog.findViewById(R.id.btnBoqua);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSongToNewPlaylist.class);
                startActivity(intent);
            }
        });

        btnBoqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

}
