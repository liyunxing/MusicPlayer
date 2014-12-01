package com.james.musicplayer.activities;

import com.james.musicplayer.R;
import com.james.musicplayer.R.layout;
import com.james.musicplayer.bean.MusicInfo;
import com.james.musicplayer.config.AppConstant;
import com.james.musicplayer.service.ICallback;
import com.james.musicplayer.service.IMusicControlService;
import com.james.musicplayer.util.DLOG;

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
    private MusicCallBack mMusicCallBack;

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

        mMusicCallBack=new MusicCallBack();

        ibtnLast.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ibtnNext.setOnClickListener(this);

        connection();
    }

    private void playMusic() {
        int index = getIntent().getIntExtra("position", 0);
        try {
            if (mMusicService.isPlaying()) {
                mMusicService.stopMusic();
            }
            mMusicService.setNowPlayingList(index);
            mMusicService.playMusic();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void connection() {
        Intent intent = new Intent(AppConstant.ActionString.MUSIC_PALY_SERVICE_ACTION);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE); // bindService
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicService = IMusicControlService.Stub.asInterface(service);
            try {
                mMusicService.registerCallback(mMusicCallBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicService = null;
            mMusicCallBack = null;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibtnPrevious:
                    mMusicService.playPrevious();
                    break;
                case R.id.ivPlay:
                    if (mMusicService.isPlaying()) {
                        mMusicService.pauseMusic();
                    } else {
                        mMusicService.resumeMusic();
                    }
                    break;
                case R.id.ibtnNext:
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
                    mMusicService.seekTo(mMusicService.getCurrentSeekTime());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar arg0, int position, boolean arg2) {

            }
        });


    }


    private void setSeekbar(int howmuch) {
        if (seekBar != null) {
            seekBar.setMax(howmuch);
        }
    }


    /**
     * 更新音乐信息，时长，标题，歌手
     */
    private void refreshMusicInfo() {
        MusicInfo musicInfo;
        try {
            musicInfo = mMusicService.getMusicInfo();
            //设置音乐时长
            seekBar.setMax(mMusicService.getDuration());
            txtSeekDuration.setText(musicInfo.getDuration());
            txtTitle.setText(musicInfo.getTitleString());
            txtArtist.setText(musicInfo.getArtistString());
        } catch (RemoteException e) {
            e.printStackTrace();
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

    private void refreshSeekBar(int position) {
        DLOG.i("SeekThread","run!"+position);
        try {
            if (seekBar != null && mMusicService.isPlaying()) {
                DLOG.i("SeekThread", "run!" + position);
                seekBar.setProgress(position);
                setSeekTxt(position);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AppConstant.Msg.MSG_PLAYER_SONG_CHANGE:
                    DLOG.i("SeekThread", "MSG_PLAYER_SONG_CHANGE!" );
                    refreshMusicInfo();
                    break;
                case AppConstant.Msg.MSG_PLAYER_STATUS_CHANGE:
                    refreshSeekBar(Integer.parseInt((String) msg.obj));
                    break;
                case AppConstant.Msg.MSG_PLAYER_ALBUM_RETRIEVED:
                    //refreshAlbum((String) msg.obj);
                    break;
                case AppConstant.Msg.MSG_CURRENT_TRACK_LRC:
                    //getCurLrc();
                    break;
                case AppConstant.Msg.CODE_CHANGE_PLAY_BUTTON_BG:
                    setPlayBtnBg();
                    break;
                default:
                    break;
            }
        }
    };


    class MusicCallBack extends ICallback.Stub {

        @Override
        public void callback(int msg, String content) throws RemoteException {
            switch (msg) {
                // 歌曲切换
                case AppConstant.Msg.MSG_PLAYER_SONG_CHANGE:
                    mHandler.sendEmptyMessage(AppConstant.Msg.MSG_PLAYER_SONG_CHANGE);
                    break;
                // 播放模式改变
                case AppConstant.Msg.MSG_PLAYER_MODE_CHANGE:
                    break;
                // 播放状态改变
                case AppConstant.Msg.MSG_PLAYER_STATUS_CHANGE:
                    mHandler.obtainMessage(
                            AppConstant.Msg.MSG_PLAYER_STATUS_CHANGE, content)
                            .sendToTarget();
                    break;
                case AppConstant.Msg.MSG_PLAYER_ALBUM_RETRIEVED:
                    mHandler.obtainMessage(
                            AppConstant.Msg.MSG_PLAYER_ALBUM_RETRIEVED, content)
                            .sendToTarget();
                    break;
                case AppConstant.Msg.CODE_CHANGE_PLAY_BUTTON_BG:
                    mHandler.obtainMessage(
                            AppConstant.Msg.CODE_CHANGE_PLAY_BUTTON_BG, content)
                            .sendToTarget();
                    break;
                // 歌词获取成功
                case AppConstant.Msg.MSG_CURRENT_TRACK_LRC:
                    mHandler.sendEmptyMessage(AppConstant.Msg.MSG_CURRENT_TRACK_LRC);
                    break;
            }
        }
    }
}
