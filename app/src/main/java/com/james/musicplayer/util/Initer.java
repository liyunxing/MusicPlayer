package com.james.musicplayer.util;

import android.app.Application;
import android.util.Log;
import com.james.musicplayer.runtime.PlayerRuntime;
import com.james.musicplayer.runtime.SDCardUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * SDKIniter [V1.0.0]
 * SDK初始化工具
 * classes : com.james.musicplayer.util.SDKIniter
 * 谭建建 Create at 2014/11/3 0003 9:27
 */
public class Initer extends Thread {
	private Application application;

	private static boolean isInited = false;

	public Initer(Application application) {
		this.application = application;
	}

	@Override
	public void run() {
		SDCardUtil.checkSDCard(application);
		PlayerRuntime.application = application;
		PlayerRuntime.ins(application).init();
		setInited(true);
	}

	public synchronized static boolean isInited() {
		return isInited;
	}

	public synchronized static void setInited(boolean isInited) {
        Initer.isInited = isInited;
	}

}
