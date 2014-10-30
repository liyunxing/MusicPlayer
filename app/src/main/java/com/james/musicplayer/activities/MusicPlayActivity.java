package com.james.musicplayer.activities;

import com.james.musicplayer.R;
import com.james.musicplayer.R.layout;
import com.james.musicplayer.config.AppConstant;
import com.james.musicplayer.service.IMusicControlService;
import com.james.musicplayer.service.MusicBrodcastCode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MusicPlayActivity extends ActionBarActivity implements OnClickListener {

    private ImageButton ibtnLast, ibtnNext;
    private ImageView ivPlay;    
    private TextView txtTitle, txtArtist, txtSeekDuration, txtSeekPlayTime;
    
    private SeekBar seekBar;
    private Handler mSeekHandler = new Handler();

    private IMusicControlService mMusicService;

    private MusicPlayServiceReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);

        ibtnLast = (ImageButton) findViewById(R.id.ibtnPrevious);
        ivPlay = (ImageView) findViewById(R.id.ivPlay);
        ibtnNext = (ImageButton) findViewById(R.id.ibtnNext);

        txtTitle = (TextView) findViewById(R.id.txtMusicName);
        txtArtist = (TextView) findViewById(R.id.txtMusicArtist);
        txtSeekDuration = (TextView) findViewById(R.id.txtSeekDuration);
        txtSeekPlayTime = (TextView) findViewById(R.id.txtSeekPlayTime);

        initSeekBar();

        ibtnLast.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ibtnNext.setOnClickListener(this);

        connection();
    }

    private void playMusic() {
        int index = getIntent().getIntExtra("position", 0);
        try {
            if(mMusicService.isPlaying()){
                mMusicService.stopMusic();
            }
            mMusicService.setNowPlayingList(index);
            mMusicService.playMusic();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
        mSeekHandler.post(new Runnable() {

            @Override
            public void run() {
                try {
                    if (seekBar != null && mMusicService.isPlaying()) {
                        int time=mMusicService.getNowPlayingTime();
                        seekBar.setProgress(time);
                        setSeekTxt(time);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mSeekHandler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onStart() {
        mReceiver = new MusicPlayServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConstant.MUSIC_SERVICE_RECEIVER_ACTION);
        registerReceiver(mReceiver, filter);
        super.onStart();
    }

    private void connection() {
        Intent intent = new Intent(AppConstant.MUSIC_PALY_SERVICE_ACTION);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE); // bindService
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicService = IMusicControlService.Stub.asInterface(service);
            playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicService = null;
        }
    };

    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibtnPrevious :
                    mMusicService.playPrevious();
                    break;
                case R.id.ivPlay :
                    if (mMusicService.isPlaying()) {
                        mMusicService.pauseMusic();
                    } else {
                        mMusicService.resumeMusic();
                    }
                    break;
                case R.id.ibtnNext :
                    mMusicService.playNext();
                    break;
            }
        } catch (RemoteException e) {
            Log.i("ServiceRemoteException", e.getMessage());
        }
    }

    private void setPlayBtnBg() {
        try {
            if (mMusicService.isPlaying()) {
                ivPlay.setImageResource(R.drawable.play_pause_selector);
            } else {
                ivPlay.setImageResource(R.drawable.play_play_selector);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initSeekBar() {

        seekBar = (SeekBar) findViewById(R.id.seekPlay);
        seekBar.setMax(1000);
        seekBar.setSoundEffectsEnabled(true);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                try {
                    mMusicService.seekTo(mMusicService.getNowPlayingTime());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar arg0, int position, boolean arg2) {
                try {
                    mMusicService.setNowPlayingTime(position);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        
        
    }
    
    
    private void setSeekbar(int howmuch) {
        if (seekBar != null) {
            seekBar.setMax(howmuch);
        }
    }

    public void setMusicInfo(String title, String artist) {
        txtTitle.setText(title);
        txtArtist.setText(artist);
    }

    private class MusicPlayServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int code = intent.getIntExtra(MusicBrodcastCode.KEY, -1);
            switch (code) {
                case MusicBrodcastCode.CODE_CHANGE_PLAY_BUTTON_BG :
                    setPlayBtnBg();
                    break;
                case MusicBrodcastCode.CODE_SET_SEEKBAR_MAX:
                    int durationInt;
                    try {
                        durationInt = mMusicService.getNowDurationInt();
                        int time = mMusicService.getNowPlayingTime();
                        setSeekTxt(time);
                        setSeekbar(durationInt);
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case MusicBrodcastCode.CODE_SET_SEEKBAR_TXT_DURATION :
                    try {
                        String duration = mMusicService.getNowDurationString();
                        txtSeekDuration.setText(duration);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case MusicBrodcastCode.CODE_CHANGE_MUSIC_INFO :
                    String title;
                    try {
                        title = mMusicService.getNowPlayingTitle();
                        String artist = mMusicService.getNowPlayingArtist();
                        setMusicInfo(title, artist);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }

    //设置进度条左右时间显示
    private void setSeekTxt(int time) {
        String timeStr;
        time /= 1000;
        int sec = time % 60;
        if (sec < 10) {
            timeStr = time / 60 + ":0" + time % 60;
        } else {
            timeStr = time / 60 + ":" + time % 60;
        }
        txtSeekPlayTime.setText(timeStr);
    }

}
