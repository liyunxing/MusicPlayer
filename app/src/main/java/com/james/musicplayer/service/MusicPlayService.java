/**
 * MusicPlayService.java [V1.0.0]
 * classes : com.james.musicplayer.service.MusicPlayService
 * 谭建建 Create at 2014-10-16 下午8:32:02
 */
package com.james.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;

import javax.security.auth.callback.Callback;

/**
 * 音乐播放服务类，播放音乐，同时会向主线程发送广播改变UI com.james.musicplayer.service.MusicPlayService
 * 
 * @author 谭建建 Create at 2014-10-16 下午8:32:02
 */
public class MusicPlayService extends Service implements Callback{

	// 活动回调服务方法
	private MusicPlayBinder mMusicPlayBinder = new MusicPlayBinder();

	public static RemoteCallbackList<ICallback> mCallbacks=new RemoteCallbackList<ICallback>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

	@Override
	public IBinder onBind(Intent intent) {
		if (mMusicPlayBinder != null) {
			return mMusicPlayBinder;
		}
		return null;
	}

}
