package com.james.musicplayer.service.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.james.musicplayer.runtime.PlayerRuntime;
import com.james.musicplayer.service.MusicPlayService;

/**
 * SD [V1.0.0]
 * 业务逻辑操作类
 * classes : com.james.musicplayer.service.logic.SD
 * 谭建建 Create at 2014/11/3 0003 10:52
 */
public class SD {

	/**
	 * The m sd.
	 */
	private static SD mSD;

	/**
	 * The m context.
	 */
	private Context mContext;

	/**
	 * The is init logic.
	 */
	private boolean isInitLogic = false;

	/** 播放器. */
	private MusicPlayer mMusicPlayer;

	/**
	 * Instantiates a new sd.
	 */
	private SD() {
	}

	/**
	 * Gets the single instance of SD.
	 *
	 * @return single instance of SD
	 */
	public static SD getInstance() {
		synchronized (SD.class) {

			if (mSD == null) {
				mSD = new SD();
			}
			return mSD;
		}
	}

	/**
	 * 初始化.
	 *
	 * @param context the context
	 * @throws Exception the exception
	 */
	public void init(Context context) throws Exception {
		if (context == null) {
			throw new Exception("dammit context is null !!! ");
		}
		mContext = context.getApplicationContext();
		synchronized (this) {
			if (!isInitLogic) {
				isInitLogic = true;
				RegReceivers();
				initPlayer();
				startMusicPlayService();
				this.notifyAll();

			}
		}
	}


	/**
	 * 初始化播放器.
	 */
	private void initPlayer() {
		mMusicPlayer = MusicPlayer.ins(PlayerRuntime.application);
	}

	private void startMusicPlayService() {
		Intent intent = new Intent();
//		intent.setAction(AppConstant.MUSIC_PALY_SERVICE_ACTION);
		intent.setClass(mContext, MusicPlayService.class);
		mContext.startService(intent);
	}

	public void stopMusicPlayService() {
		Intent intent = new Intent();
//		intent.setAction(AppConstant.MUSIC_PALY_SERVICE_ACTION);
		intent.setClass(mContext, MusicPlayService.class);
		mContext.stopService(intent);
	}

	private DMReceiver mReceiver;

	private IntentFilter mFilter;

	public static boolean isPhoneClose;

	private static boolean networkRegister = true;

	private void RegReceivers() {
		networkRegister = true;
		// 注册
		if (mReceiver == null) {
			mFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
			mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			mFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
			mFilter.addAction(Intent.ACTION_SHUTDOWN);
			mFilter.addAction("android.intent.action.PHONE_STATE");
			mFilter.addAction(Intent.ACTION_HEADSET_PLUG);
			mFilter.setPriority(Integer.MAX_VALUE);
			mReceiver = new DMReceiver();
			mContext.registerReceiver(mReceiver, mFilter);
		}

	}

	public static class DMReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent == null) {
				return;
			}
			String action = intent.getAction();
			Log.d("onReceive", "PHONE_STATE:>>>>>>>>>>>>>>>>>>>>>>>>" + action);
			if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
				// 在用户按下拨号键开始拨打电话时，歌曲停止播放

				// 停止音乐播放
				if (PlayerRuntime.sSD.getDmPlayer().isPlaying()) {
					PlayerRuntime.sSD.getDmPlayer().pause();
					isPhoneClose = true;
				}
			} else if (action.equals("android.intent.action.PHONE_STATE")) {
				/*
				 * 解决bug：静音或振动状态下，sim2来电，音乐不停止。原因是此时通过TelephonyManager的getCallState
				 * ()得到的状态为idle
				 */
				String state = intent
						.getStringExtra(TelephonyManager.EXTRA_STATE);
				if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {
					if (isPhoneClose) {
						// 不是用户暂停的
						PlayerRuntime.sSD.getDmPlayer().playMusic();
						// RT.sDM.getDmPlayer().startVolume(true);
						isPhoneClose = false;
					}
				} else if (state
						.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

				} else if (state
						.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
					// 停止音乐播放
					if (PlayerRuntime.sSD.getDmPlayer().isPlaying()) {
						Log.d("onReceive",
								"PHONE_STATE:cause pause>>>>>>>>>>>>>>>>>>>>>>>>"
										+ action);
						PlayerRuntime.sSD.getDmPlayer().pause();
						isPhoneClose = true;
					}
				}

			} else if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
				if (intent.getIntExtra("state", 0) == 1) {
					/* 耳机插入 */
//					if (PlayerRuntime.Setting.ActiveHeadsetControl) {
//						DmMediaButtonRecv.regMediaBtnEventReceiver();
//					}
				}
			} else {
				if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent
						.getAction())) {
					// 拔耳机
					PlayerRuntime.sSD.getDmPlayer().pause();
				} else {
					if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
						// 关机时进行一个歌曲暂停操作
						PlayerRuntime.sSD.getDmPlayer().pause();
					}
				}
			}

		}

	}

	public MusicPlayer getDmPlayer() {
		synchronized (this) {
			check(mMusicPlayer);
		}
		return mMusicPlayer;
	}


	/**
	 * Check Object .
	 *
	 * @param lg
	 *            the lg
	 */
	private void check(Object lg) {

		if (lg == null || !isInitLogic) {
			try {
				wait();
				if (!isInitLogic)
					throw new RuntimeException(
							"Error SD class is not initialized!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
