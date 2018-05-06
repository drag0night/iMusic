package vanhy.com.imusic;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import vanhy.com.imusic.Utility.Utility;
import vanhy.com.imusic.dialog.BottomSheetDialog;
import vanhy.com.imusic.model.BaiHat;

public class NgheNhacActivity extends AppCompatActivity{

    private ImageButton btnImageMore;
    private ImageButton btnPlay;
    private SeekBar seekBar;
    private TextView textBatdau;
    private TextView textKetthuc;

    private BaiHat song;
    private ArrayList<BaiHat> songList;
    private int pos;
    private long currentSongLength;

    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nghe_nhac);
        initView();
        song = (BaiHat) getIntent().getSerializableExtra("song");
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

        String stream = song.getStreamUrl()+"?client_id="+ Config.CLIENT_ID;
        mPlayer.reset();
        try {
            mPlayer.setDataSource(stream);
            mPlayer.prepareAsync();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        play();
        handleSeekbar();
        clickMore();
    }

    private void initView() {
        btnImageMore = (ImageButton) findViewById(R.id.btnImageMore);
        btnPlay = (ImageButton) findViewById(R.id.btnImagePlay);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textBatdau = (TextView) findViewById(R.id.textBatdau);
        textKetthuc = (TextView) findViewById(R.id.textKetthuc);
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
        String stream = song.getStreamUrl()+"?client_id="+ Config.CLIENT_ID;
        mPlayer.reset();
        try {
            mPlayer.setDataSource(stream);
            mPlayer.prepareAsync();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clickMore() {
        btnImageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialog = new BottomSheetDialog();
                dialog.show(getSupportFragmentManager(), null);
            }
        });
    }

    private void toggle(final MediaPlayer mp) {
        if(mp.isPlaying()){
            mp.stop();
            mp.reset();
        }else{
            mp.start();
            btnPlay.setImageDrawable(ContextCompat.getDrawable(NgheNhacActivity.this, R.drawable.ic_pause));
            final Handler mHandler = new Handler();
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    seekBar.setMax((int) currentSongLength / 1000);
                    int mCurrentPosition = mPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    textKetthuc.setText(Utility.convertDuration((long)currentSongLength));
                    textBatdau.setText(Utility.convertDuration((long)mPlayer.getCurrentPosition()));
                    mHandler.postDelayed(this, 1000);

                }
            });
        }
    }

    private void changeSelectedSong(int index) {
        pos = index;
    }

    private void preparedSong(BaiHat song) {
        currentSongLength = song.getDuration();
    }

    @Override
    protected void onDestroy() {
        if(mPlayer != null){
            mPlayer.release();
        }
        super.onDestroy();
    }
}
