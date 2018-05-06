package vanhy.com.imusic;

import android.app.Activity;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import vanhy.com.imusic.Utility.Utility;
import vanhy.com.imusic.model.BaiHat;

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

    private ArrayList<BaiHat> songList;
    private int pos;
    private long currentSongLength;

    private MediaPlayer mPlayer;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nghe_nhac);
        initView();
        songList = (ArrayList<BaiHat>) getIntent().getSerializableExtra("songList");
        pos = getIntent().getIntExtra("position",0);
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                toggle(mp);
            }
        });

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

        currentSongLength = songList.get(pos).getDuration();
        String stream = songList.get(pos).getStreamUrl()+"?client_id="+ Config.CLIENT_ID;
        textTencs.setText(songList.get(pos).getArtist());
        textTenbh.setText(songList.get(pos).getTitle());
        textKetthuc.setText(Utility.convertDuration((long)currentSongLength));
        try {
            mPlayer.setDataSource(stream);
            mPlayer.prepareAsync();
            imageView.startAnimation(animation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        play();
        previous();
        next();
        handleSeekbar();
        clickMore();
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
                BottomSheetDialog bottomdialog = new BottomSheetDialog(NgheNhacActivity.this);
                View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                View view_dialog = getLayoutInflater().inflate(R.layout.add_to_playlist_dialog, null);
                ImageView imgBaihat = (ImageView) view.findViewById(R.id.imageView_BoS);
                TextView txtTenbh = (TextView) view.findViewById(R.id.textViewTenBH_BoS);
                TextView textView = (TextView) view.findViewById(R.id.textViewTenCS_BoS);
                Picasso.with(NgheNhacActivity.this).load(songList.get(pos).getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(imgBaihat);
                txtTenbh.setText(songList.get(pos).getTitle());
                textTencs.setText(songList.get(pos).getArtist());
                bottomdialog.setContentView(view);
                bottomdialog.show();
            }
        });
    }

    private void toggle(final MediaPlayer mp) {
        if(mp.isPlaying()){
            mp.stop();
            mp.reset();
        } else{
            mp.start();
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
