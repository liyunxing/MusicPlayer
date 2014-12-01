package com.james.musicplayer.runtime;

import android.content.Context;

/**
 * PreferenceHelper [V1.0.0]
 * 手机内存存储工具
 * classes : com.james.musicplayer.runtime.PreferenceHelper
 * 谭建建 Create at 2014/11/3 0003 10:28
 */
public class PreferenceHelper {

	/** The m ctx. */
	private Context mCtx;

	/** The m PreferenceHelper. */
	private static PreferenceHelper mPreferenceHelper;

	public PreferenceHelper(Context ctx)
	{
		mCtx = ctx;
	}

	public synchronized static PreferenceHelper ins(Context ctx)
	{
		if (mPreferenceHelper == null)
			return new PreferenceHelper(ctx);
		return mPreferenceHelper;
	}
}
