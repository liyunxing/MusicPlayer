package com.james.musicplayer;

import android.app.Application;

import com.james.musicplayer.util.Initer;

/**
 * MusicPlayerApplication [V1.0.0]
 * classes : com.james.musicplayer.MusicPlayerApplication
 * 谭建建 Create at 2014/11/3 0003 9:17
 */
public class MusicPlayerApplication extends Application {


	@Override
	public void onCreate() {
		super.onCreate();
		new Initer(this).start();
	}
}
