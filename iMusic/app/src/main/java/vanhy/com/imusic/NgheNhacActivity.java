package vanhy.com.imusic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomSheetDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import vanhy.com.imusic.SQLite.SQLite;
import vanhy.com.imusic.Utility.Randomize;
import vanhy.com.imusic.Utility.Utility;
import vanhy.com.imusic.adapter.AddToPlaylistAdapter;
import vanhy.com.imusic.fragment.PlayListFragment;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.model.Playlist;
import vanhy.com.imusic.sharedpreference.SharedManagers;

public class NgheNhacActivity extends AppCompatActivity{

    private ImageButton btnImageMore;
    private ImageButton btnPlay;
    private SeekBar seekBar;
    private TextView textBatdau;
    private TextView textKetthuc;
    private ImageButton btnNext;
    private ImageButton btnPre;
    private TextView textTenbh;
    private TextView textTencs;
    private ImageView imageView;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private ImageButton btnFavorite;
    private ProgressBar progress;

    private ArrayList<BaiHat> songList;
    private int pos;
    private int pos_shuff;
    private int[] shuff;
    private long currentSongLength;
    private boolean isShuffle;
    private boolean isRepeat;
    private boolean isFavorite;
    private ArrayList<BaiHat> listFavorite;

    private MediaPlayer mPlayer;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nghe_nhac);
        initView();
        songList = (ArrayList<BaiHat>) getIntent().getSerializableExtra("songList");
        SharedManagers.init(this);
        isShuffle = SharedManagers.getInstance().getSuff();
        isRepeat = SharedManagers.getInstance().getRepeat();
        listFavorite = (ArrayList<BaiHat>) SharedManagers.getInstance().getListTrack();
        isFavorite = false;
        pos = getIntent().getIntExtra("position",0);
        pos_shuff=0;
        shuff = Randomize.getShuffle(songList.size());
        for (int i = 0; i < listFavorite.size(); i++) {
            if (songList.get(pos).getStreamUrl().equals(listFavorite.get(i).getStreamUrl())) {
                isFavorite = true;
                break;
            }
        }
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                toggle(mp);
            }
        });
        checkRepeat();
        checkShuffle();
        currentSongLength = songList.get(pos).getDuration();
        String stream = songList.get(pos).getStreamUrl()+"?client_id="+ Config.CLIENT_ID;
        textTencs.setText(songList.get(pos).getArtist());
        textTenbh.setText(songList.get(pos).getTitle());
        textKetthuc.setText(Utility.convertDuration((long)currentSongLength));
        try {
            progress.setVisibility(View.VISIBLE);
            mPlayer.setDataSource(stream);
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        play();
        repeat();
        shuffle();
        handleSeekbar();
        clickMore();
        favorite();
    }

    private void initView() {
        btnImageMore = (ImageButton) findViewById(R.id.btnImageMore);
        btnPlay = (ImageButton) findViewById(R.id.btnImagePlay);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textBatdau = (TextView) findViewById(R.id.textBatdau);
        textKetthuc = (TextView) findViewById(R.id.textKetthuc);
        btnNext = (ImageButton) findViewById(R.id.btnImageNext);
        btnPre = (ImageButton) findViewById(R.id.btnImagePrevious);
        textTenbh = (TextView) findViewById(R.id.textTenBaihat);
        textTencs = (TextView) findViewById(R.id.textTenCasi);
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_anim);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnRepeat = (ImageButton) findViewById(R.id.btnImageRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnImageShuffle);
        progress = (ProgressBar) findViewById(R.id.pb_main_loader);
        btnFavorite = (ImageButton) findViewById(R.id.btnImageFavourite);
    }

    private void handleSeekbar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mPlayer != null && fromUser) {
                    mPlayer.seekTo(progress * 1000);
                    textBatdau.setText(Utility.convertDuration((long)mPlayer.getCurrentPosition()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void favorite() {
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedManagers.getInstance().addTrack(songList.get(pos));
                btnFavorite.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_favorited));
            }
        });
    }

    private void checkFavorite() {
        if (isFavorite == true) {
            btnFavorite.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_favorited));
        } else {
            btnFavorite.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_favorite_bound));
        }
    }

    private void checkRepeat() {
        if (isRepeat == true) {
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    BaiHat next = songList.get(pos);
                    changeSelectedSong(pos);
                    preparedSong(next);
                }
            });
            btnRepeat.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_repeat_one));
        } else {
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(pos + 1 < songList.size()){
                        BaiHat next = songList.get(pos + 1);
                        changeSelectedSong(pos+1);
                        preparedSong(next);
                    }else{
                        BaiHat next = songList.get(0);
                        changeSelectedSong(0);
                        preparedSong(next);
                    }
                }
            });
            btnRepeat.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_repeat));
        }
    }

    private void checkShuffle() {
        if (isShuffle == true) {

            //Complete while shuffle
            if (!isRepeat) {
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (pos_shuff + 1 < songList.size()) {
                            pos = shuff[pos_shuff + 1] - 1;
                            pos_shuff++;
                            BaiHat next = songList.get(pos);
                            changeSelectedSong(pos);
                            preparedSong(next);
                        } else {
                            pos = shuff[0] - 1;
                            pos_shuff = 0;
                            BaiHat next = songList.get(pos);
                            changeSelectedSong(pos);
                            preparedSong(next);
                        }
                    }
                });
            }

            //Next while shuffle
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPlayer != null){
                        if(pos_shuff + 1 < shuff.length){
                            pos = shuff[pos_shuff+1];
                            pos_shuff++;
                            BaiHat next = songList.get(pos);
                            changeSelectedSong(pos);
                            preparedSong(next);
                        }else{
                            pos = shuff[0];
                            pos_shuff = 0;
                            BaiHat next = songList.get(pos);
                            changeSelectedSong(pos);
                            preparedSong(next);
                        }
                    }
                }
            });

            //Pre while shuffle
            btnPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPlayer != null){
                        if(pos_shuff - 1 >= 0){
                            pos=shuff[pos_shuff-1];
                            pos_shuff--;
                            BaiHat previous = songList.get(pos);
                            changeSelectedSong(pos);
                            preparedSong(previous);
                        }else{
                            pos = shuff[shuff.length-1];
                            pos_shuff = shuff.length-1;
                            BaiHat previous = songList.get(pos);
                            changeSelectedSong(pos);
                            preparedSong(previous);
                        }

                    }
                }
            });
            btnShuffle.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_shuffle_black));
        } else {
            //Complete while unshuffle
            if (!isRepeat) {
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(pos + 1 < songList.size()){
                            BaiHat next = songList.get(pos + 1);
                            changeSelectedSong(pos+1);
                            preparedSong(next);
                        }else{
                            BaiHat next = songList.get(0);
                            changeSelectedSong(0);
                            preparedSong(next);
                        }
                    }
                });
            }
            next();
            previous();
            btnShuffle.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_shuffle));
        }
    }

    private void repeat() {
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepeat == false) {
                    isRepeat = true;
                    SharedManagers.getInstance().putRepeat(isRepeat);
                    checkRepeat();
                } else {
                    isRepeat = false;
                    SharedManagers.getInstance().putRepeat(isRepeat);
                    checkRepeat();
                }
                checkShuffle();
            }
        });
    }

    private void shuffle() {
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShuffle == false) {
                    isShuffle =true;
                    SharedManagers.getInstance().putSuff(isShuffle);
                    checkShuffle();
                } else {
                    isShuffle=false;
                    SharedManagers.getInstance().putSuff(isShuffle);
                    checkShuffle();
                }
            }
        });
    }

    private void play() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayer.isPlaying() && mPlayer != null){

                    btnPlay.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_play));
                    mPlayer.pause();
                }else{
                    mPlayer.start();
                    btnPlay.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_pause));
                }
            }
        });
    }

    private void previous() {
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayer != null){
                    if(pos - 1 >= 0){
                        BaiHat previous = songList.get(pos - 1);
                        changeSelectedSong(pos - 1);
                        preparedSong(previous);
                    }else{
                        changeSelectedSong(songList.size() - 1);
                        preparedSong(songList.get(songList.size() - 1));
                    }

                }
            }
        });
    }

    private void next() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayer != null){
                    if(pos + 1 < songList.size()){
                        BaiHat next = songList.get(pos + 1);
                        changeSelectedSong(pos + 1);
                        preparedSong(next);
                    }else{
                        changeSelectedSong(0);
                        preparedSong(songList.get(0));
                    }
                }
            }
        });
    }

    private void clickMore() {
        btnImageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomdialog = new BottomSheetDialog(NgheNhacActivity.this);
                View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                ImageView imgBaihat = (ImageView) view.findViewById(R.id.imageView_BoS);
                TextView txtTenbh = (TextView) view.findViewById(R.id.textViewTenBH_BoS);
                TextView textView = (TextView) view.findViewById(R.id.textViewTenCS_BoS);
                LinearLayout addtoplaylist = (LinearLayout) view.findViewById(R.id.addToPlaylist);
                Picasso.with(NgheNhacActivity.this).load(songList.get(pos).getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(imgBaihat);
                txtTenbh.setText(songList.get(pos).getTitle());
                textView.setText(songList.get(pos).getArtist());
                bottomdialog.setContentView(view);
                bottomdialog.show();
                addtoplaylist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialogadd = new Dialog(NgheNhacActivity.this);
                        dialogadd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        View temp1 = getLayoutInflater().inflate(R.layout.add_to_playlist_dialog, null);
                        ListView listView = (ListView) temp1.findViewById(R.id.listViewPlaylist);
                        final ArrayList<Playlist> listPl = SQLite.getAllPlaylist(NgheNhacActivity.this);
                        AddToPlaylistAdapter adapter = new AddToPlaylistAdapter(NgheNhacActivity.this, R.layout.add_to_playlist_item, listPl);
                        listView.setAdapter(adapter);
                        dialogadd.setContentView(temp1);
                        dialogadd.show();
                        bottomdialog.cancel();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (SQLite.addSongToPlaylist(NgheNhacActivity.this, listPl.get(position).getId(), songList.get(pos))) {
                                    OnAddedToDB onAddedToDB = PlayListFragment.getInstance();
                                    onAddedToDB.onRefresh();
                                    Toast.makeText(NgheNhacActivity.this, "Thêm vào playlist "+listPl.get(position).getTen()+" thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(NgheNhacActivity.this, "Bài hát đã có trong "+listPl.get(position).getTen(), Toast.LENGTH_SHORT).show();
                                }
                                dialogadd.cancel();
                            }
                        });
                        LinearLayout btnThempl = (LinearLayout) temp1.findViewById(R.id.btnThemPlaylist);
                        btnThempl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogadd.cancel();
                                final Dialog dialog_add_pl = new Dialog(NgheNhacActivity.this);
                                dialog_add_pl.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                View view_add_pl = getLayoutInflater().inflate(R.layout.add_play_list_dialog, null);
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
                                            if (!SQLite.checkPlaylistNameExist(NgheNhacActivity.this, tenpl)) {
                                                ArrayList<BaiHat> arrBh = new ArrayList<BaiHat>();
                                                arrBh.add(songList.get(pos));
                                                SQLite.createPlaylist(NgheNhacActivity.this, tenpl, arrBh);
                                                OnAddedToDB onAddedToDB = PlayListFragment.getInstance();
                                                onAddedToDB.onRefresh();
                                                Toast.makeText(NgheNhacActivity.this, "Thêm vào playlist thành công", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(NgheNhacActivity.this, "Playlist đã tồn tại", Toast.LENGTH_SHORT).show();
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
    }

    private void toggle(final MediaPlayer mp) {
        if(mp.isPlaying()){
            mp.stop();
            mp.reset();
        } else{
            mp.start();
            progress.setVisibility(View.GONE);
            imageView.startAnimation(animation);
            btnPlay.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_pause));
            final Handler mHandler = new Handler();
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int mCurrentPosition = 0;
                    seekBar.setMax((int) currentSongLength / 1000);
                    try {
                        mCurrentPosition = mPlayer.getCurrentPosition();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    seekBar.setProgress(mCurrentPosition/1000);
                    textKetthuc.setText(Utility.convertDuration((long)currentSongLength));
                    textBatdau.setText(Utility.convertDuration((long)mCurrentPosition));
                    mHandler.postDelayed(this, 1000);

                }
            });
        }
    }

    private void changeSelectedSong(int index) {
        pos = index;
        textTencs.setText(songList.get(index).getArtist());
        textTenbh.setText(songList.get(index).getTitle());
    }

    private void preparedSong(BaiHat song) {
        progress.setVisibility(View.VISIBLE);
        imageView.clearAnimation();
        currentSongLength = song.getDuration();
        textKetthuc.setText(Utility.convertDuration(song.getDuration()));
        textBatdau.setText("00:00");
        String stream = song.getStreamUrl()+"?client_id="+ Config.CLIENT_ID;
        mPlayer.reset();
        try {
            mPlayer.setDataSource(stream);
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if(mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
        }
        super.onDestroy();
    }
}
