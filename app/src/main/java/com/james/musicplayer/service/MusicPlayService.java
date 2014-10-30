/**
 * MusicPlayService.java [V1.0.0]
 * classes : com.james.musicplayer.service.MusicPlayService
 * 谭建建 Create at 2014-10-16 下午8:32:02
 */
package com.james.musicplayer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.james.musicplayer.bean.MusicInfo;
import com.james.musicplayer.config.AppConstant;
import com.james.musicplayer.config.ConfigManager;
import com.james.musicplayer.db.DBInfo;
import com.james.musicplayer.tools.QuerTools;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * 音乐播放服务类，播放音乐，同时会向主线程发送广播改变UI com.james.musicplayer.service.MusicPlayService
 * 
 * @author 谭建建 Create at 2014-10-16 下午8:32:02
 */
public class MusicPlayService extends Service implements OnCompletionListener {

    private static boolean ISPLAYING = false;// 播放器是否正在播放音乐

	// 当前播放音乐信息
	private static ArrayList<MusicInfo> NOW_PLAYING_LIST;// 当前播放列表
	private static int NOW_PLAYING_INDEX = 0;// 当前播放位置
	private static String NOW_PLAYING_TITLE = "";// 当前播放音乐标题
	private static String NOW_PLAYING_ARTIST = "";// 当前播放音乐所属歌手
	private static String NOW_PLAYING_ALBUM = "";// 当前播放音乐所属专辑
	private static String NOW_PLAYING_PATH = "";// 当前播放音乐所属路径
	private static long NOW_PLAYING_ID = 0;// 当前播放音乐ID
	private static String NOW_DURATION = "";// 当前播放音乐时长

	private static int NOW_PLAYING_TIME = -1;// 当前播放音乐时间
	private static int CURRENT_PLAY_MOD = -1;// 当前循环模式

	private ConfigManager mConfigManager;

	// 查询工具类
	QuerTools querTools;

	// 播放器
	private MusicPlayer mMusicPlayer = new MusicPlayer();

	// 活动回调服务方法
	private IMusicControlService.Stub binder = new ServiceStub(this);

	

    @Override
    public void onCreate() {
        mConfigManager = new ConfigManager(this);
        querTools = new QuerTools(this);
        super.onCreate();
    }

	@Override
	public IBinder onBind(Intent intent) {
		if (binder != null) {
			return binder;
		}
		return null;
	}

	/**
	 * 设定播放循环模式
	 * 
	 * @param whitch
	 *            循环模式参数
	 * @throws RemoteException
	 */
	public void setPlayMod(int whitch) throws RemoteException {
		mConfigManager.setPlayModConfig(whitch);
	}

	public void resumeMusic() throws RemoteException {
		if (NOW_PLAYING_INDEX >= 0) {
			mMusicPlayer.start();
		}
		ISPLAYING = true;
		setPlayBtnBg();
	}

	public void playPrevious() throws RemoteException {
		CURRENT_PLAY_MOD = getPlayMod();
		switch (CURRENT_PLAY_MOD) {
		case AppConstant.PLAY_MOD_LIST:
			if (NOW_PLAYING_INDEX == 0) {
				NOW_PLAYING_INDEX = 0;
			} else {
				NOW_PLAYING_INDEX = NOW_PLAYING_INDEX - 1;
			}
			break;
		case AppConstant.PLAY_MOD_CIRCLE:
			if (NOW_PLAYING_INDEX == 0) {
				NOW_PLAYING_INDEX = NOW_PLAYING_LIST.size() - 1;
			} else {
				NOW_PLAYING_INDEX = NOW_PLAYING_INDEX - 1;
			}
			break;
		case AppConstant.PLAY_MOD_RANDOM:
			NOW_PLAYING_INDEX = new Random()
					.nextInt(NOW_PLAYING_LIST.size() - 1);
			break;
		case AppConstant.PLAY_MOD_SINGLE:
			break;
		default:
			NOW_PLAYING_INDEX = 0;
			break;
		}
		playMusic();
        changeUIBrodcast(MusicBrodcastCode.CODE_SET_SEEKBAR_MAX);
		ISPLAYING = true;
		setPlayBtnBg();
	}

	public void playNext() throws RemoteException {

	    
		CURRENT_PLAY_MOD = getPlayMod();
		switch (CURRENT_PLAY_MOD) {
		case AppConstant.PLAY_MOD_LIST:
			if (NOW_PLAYING_INDEX == NOW_PLAYING_LIST.size() - 1) {
				mMusicPlayer.stop();
			} else {
				NOW_PLAYING_INDEX = NOW_PLAYING_INDEX + 1;
			}
			break;
		case AppConstant.PLAY_MOD_CIRCLE:
			if (NOW_PLAYING_INDEX == NOW_PLAYING_LIST.size() - 1) {
				NOW_PLAYING_INDEX = 0;
			} else {
				NOW_PLAYING_INDEX = NOW_PLAYING_INDEX + 1;
			}
			break;
		case AppConstant.PLAY_MOD_RANDOM:
			NOW_PLAYING_INDEX = new Random()
					.nextInt(NOW_PLAYING_LIST.size() - 1);
			break;
		case AppConstant.PLAY_MOD_SINGLE:
			break;
		default:
			NOW_PLAYING_INDEX = 0;
		}
		playMusic();
        changeUIBrodcast(MusicBrodcastCode.CODE_SET_SEEKBAR_MAX);
		ISPLAYING = true;
		setPlayBtnBg();
	}

	public void pauseMusic() throws RemoteException {
		mMusicPlayer.pause();
		ISPLAYING = false;
		setPlayBtnBg();
	}

    public void stopMusic() {
        mMusicPlayer.stop();
        ISPLAYING = false;
        setPlayBtnBg();
    }

	/**
	 * 播放音乐
	 */
	private void playMusic() {
        if (NOW_PLAYING_LIST != null) {
            NOW_PLAYING_PATH = NOW_PLAYING_LIST.get(NOW_PLAYING_INDEX).getPathString();

            if (NOW_PLAYING_INDEX >= 0) {
                try {
                    mMusicPlayer.reset();
                    mMusicPlayer.setDataSource(NOW_PLAYING_PATH);
                    mMusicPlayer.prepare();
                    mMusicPlayer.start();

                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ISPLAYING = true;
                mMusicPlayer.setOnCompletionListener(this);
                setPlayBtnBg();
            }
        }
    }

	// 每首音乐播放结束后调用
	@Override
	public void onCompletion(MediaPlayer mp) {
		try {
			playNext();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// 改变音乐播放按钮
	private void setPlayBtnBg() {
		changeUIBrodcast(MusicBrodcastCode.CODE_CHANGE_PLAY_BUTTON_BG);
	}

	private void seekTo(int where) {
		mMusicPlayer.seekTo(where);
	}

	private int getCurrentSeekTime() {
		return mMusicPlayer.getCurrentPosition();
	}

	public int getPlayMod() throws RemoteException {
		return mConfigManager.getPlayMod();
	}
	
	public int getNowDurationInt(){
	    return mMusicPlayer.getDuration();
	}

	/**
	 * IMusicControlService的Stub子类继承类 com.james.musicplayer.service.ServiceStub
	 * 
	 * @author 谭建建 Create at 2014-10-18 上午10:47:39
	 */
	public class ServiceStub extends IMusicControlService.Stub  {
		private MusicPlayService mService;

		public ServiceStub(MusicPlayService mService) {
			super();
			this.mService = mService;
		}

		@Override
		public void playNext() throws RemoteException {
			mService.playNext();
		}

		@Override
		public void playPrevious() throws RemoteException {
			mService.playPrevious();
		}

		@Override
		public void pauseMusic() throws RemoteException {
			mService.pauseMusic();
		}

		@Override
		public void resumeMusic() throws RemoteException {
			mService.resumeMusic();
		}

		@Override
		public void playMusic() throws RemoteException {
			mService.playMusic();
		}

		@Override
		public void setPlayMod(int whitch) throws RemoteException {
			mService.setPlayMod(whitch);
		}

		@Override
		public int getPlayMod() throws RemoteException {
		    return mService.getPlayMod();
		}

		@Override
		public void seekTo(int where) throws RemoteException {
			mService.seekTo(where);
		}

		@Override
		public int getCurrentSeekTime() throws RemoteException {
		    return mService.getCurrentSeekTime();
		}

		@Override
		public boolean isPlaying() throws RemoteException {
			return ISPLAYING;
		}

		@Override
		public int getNowPlayingTime() throws RemoteException {
			return NOW_PLAYING_TIME;
		}

		@Override
		public void setNowPlayingTime(int position) throws RemoteException {
            NOW_PLAYING_TIME = position;
		}

		@Override
		public String getNowPlayingTitle() throws RemoteException {
			return NOW_PLAYING_TITLE;
		}

		@Override
		public String getNowPlayingArtist() throws RemoteException {
			return NOW_PLAYING_ARTIST;
		}

        @Override
        public String getNowDurationString() throws RemoteException {
            return NOW_DURATION;
        }

        @Override
        public int getNowDurationInt() throws RemoteException {
            return mService.getNowDurationInt();
        }


        @Override
        public void setNowPlayingList(int index) throws RemoteException {
            mService.setNowPlayingList(index);            
        }

        @Override
        public void stopMusic() throws RemoteException {
            mService.stopMusic();
        }


	}

	/**
	 * MusicPlayer继承MediaPlayer类，并重写MediaPlayer类中的pause()及start()方法，
	 * 在方法中更新当前音乐信息及更新音乐播放界面中的音乐信息 com.james.musicplayer.service.MusicPlayer
	 * 
	 * @author 谭建建 Create at 2014-10-18 上午10:48:39
	 */
	class MusicPlayer extends MediaPlayer {

		@Override
		public void pause() throws IllegalStateException {
			updateInfo();
			super.pause();
		}

		@Override
		public void start() throws IllegalStateException {
			updateInfo();
            // 设置音乐Seek,play按钮，音乐信息
            changeUIBrodcast(MusicBrodcastCode.CODE_SET_SEEKBAR_TXT_DURATION);
            changeUIBrodcast(MusicBrodcastCode.CODE_CHANGE_PLAY_BUTTON_BG);
			changeUIBrodcast(MusicBrodcastCode.CODE_CHANGE_MUSIC_INFO);
			super.start();
		}

		private void updateInfo() {
			NOW_PLAYING_TITLE = NOW_PLAYING_LIST.get(NOW_PLAYING_INDEX)
					.getTitleString();
			NOW_PLAYING_ARTIST = NOW_PLAYING_LIST.get(NOW_PLAYING_INDEX)
					.getArtistString();
			NOW_PLAYING_ALBUM = NOW_PLAYING_LIST.get(NOW_PLAYING_INDEX)
					.getAlbumString();
			NOW_PLAYING_ID = NOW_PLAYING_LIST.get(NOW_PLAYING_INDEX).getId();
			NOW_DURATION = NOW_PLAYING_LIST.get(NOW_PLAYING_INDEX)
					.getDuration();
		}
	}

	/**
	 * 发送改变播放界面广播，使用MusicBrodcastCode中静态参数传入brodcastCode
	 * 
	 * @param brodcastCode
	 *            键值对中的值
	 * @param bundle
	 *            额外要发送的bundle数据，可为null
	 */
	private void changeUIBrodcast(int brodcastCode) {
		Intent intent = new Intent();// 创建Intent对象
		intent.setAction(AppConstant.MUSIC_SERVICE_RECEIVER_ACTION);		
		intent.putExtra(MusicBrodcastCode.KEY, brodcastCode);
		sendBroadcast(intent);// 发送广播
	}


    public void setNowPlayingList(int index) {
        Cursor cursor =querTools.getMusicCursorFromDataBase();
        NOW_PLAYING_LIST=getNowPlayingList(cursor);            
        NOW_PLAYING_INDEX=index;
    }

    public ArrayList<MusicInfo> getNowPlayingList(Cursor cursor) {
        cursor.moveToFirst();
        ArrayList<MusicInfo> resultList = new ArrayList<MusicInfo>();
       
       while (!cursor.isAfterLast() && cursor.getCount() > 0) {
           MusicInfo MusicInfo = new MusicInfo();           
           MusicInfo.setId(cursor.getLong(cursor.getColumnIndex("_id")));
           MusicInfo.setTitleString(cursor.getString(cursor.getColumnIndex(DBInfo.TITLE)));
           MusicInfo.setArtistString(cursor.getString(cursor.getColumnIndex(DBInfo.ARTIST)));
           MusicInfo.setAlbumString(cursor.getString(cursor.getColumnIndex(DBInfo.ALBUM)));
           MusicInfo.setDuration(cursor.getString(cursor.getColumnIndex(DBInfo.DURATION)));
           MusicInfo.setPathString(cursor.getString(cursor.getColumnIndex(DBInfo.PATH)));
           resultList.add(MusicInfo);
           cursor.moveToNext();
       }
       return resultList;
    }

}
