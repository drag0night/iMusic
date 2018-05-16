package vanhy.com.imusic.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vanhy.com.imusic.AddSongToNewPlaylistActivity;
import vanhy.com.imusic.OnAddedToDB;
import vanhy.com.imusic.PlaylistDetailActivity;
import vanhy.com.imusic.R;
import vanhy.com.imusic.SQLite.SQLite;
import vanhy.com.imusic.adapter.PlaylistAdapter;
import vanhy.com.imusic.model.Playlist;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListFragment extends Fragment implements OnAddedToDB{

    private Context context;
    private ArrayList<Playlist> listPl;
    private PlaylistAdapter adapter;
    private ListView listview;
    private static final PlayListFragment instance = new PlayListFragment();

    public static PlayListFragment getInstance() {
        return instance;
    }

    public PlayListFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public Context getContext() {
         return context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        context = getActivity();
        listPl = SQLite.getAllPlaylist(context);
        listview = (ListView) view.findViewById(R.id.listViewPlaylist);
        adapter = new PlaylistAdapter(this.getContext(), R.layout.playlist_item, listPl);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, PlaylistDetailActivity.class);
                intent.putExtra("playlist", listPl.get(position));
                startActivity(intent);
            }
        });

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
        final EditText edtTenpl = (EditText) dialog.findViewById(R.id.edtTenPlaylist);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenpl = edtTenpl.getText().toString();
                if (!"".equals(tenpl)) {
                    if (!SQLite.checkPlaylistNameExist(context, tenpl)) {
                        Intent intent = new Intent(getActivity(), AddSongToNewPlaylistActivity.class);
                        intent.putExtra("tenpl", tenpl);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Playlist đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.cancel();
            }
        });

        btnBoqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onRefresh() {
        listPl = SQLite.getAllPlaylist(context);
        adapter = new PlaylistAdapter(context, R.layout.playlist_item, listPl);
        listview.setAdapter(adapter);
    }
}
