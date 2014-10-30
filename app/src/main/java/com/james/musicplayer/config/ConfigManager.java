/**
 * ConfigManager.java [V1.0.0]
 * classes : com.james.musicplayer.config.ConfigManager
 * 谭建建 Create at 2014-10-17 上午11:08:23
 */
package com.james.musicplayer.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 设置信息管理类
 * com.james.musicplayer.config.ConfigManager
 * @author 谭建建 
 * Create at 2014-10-17 上午11:08:23
 */
public class ConfigManager {
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	
	/**
	 * @param mContext
	 */
	public ConfigManager(Context mContext) {
		super();
		this.mContext = mContext;
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	}
	
	public void setPlayModConfig(int whitch) {
		if (mSharedPreferences == null) {
			mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		}
		mSharedPreferences.edit().putInt(DefaultConfigInfo.PLAY_MODE_NAME, whitch);
	}

	public int getPlayMod() {
		if (mSharedPreferences == null) {
			mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		}
		return mSharedPreferences.getInt(DefaultConfigInfo.PLAY_MODE_NAME, DefaultConfigInfo.PLAY_MODE);
	}

	public void storeCurrent(int whitchQueue, int currentIndex) {
		if (mSharedPreferences == null) {
			mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		}
		mSharedPreferences.edit().putInt(DefaultConfigInfo.LAST_QUEUE_NAME, whitchQueue);
		mSharedPreferences.edit().putInt(DefaultConfigInfo.LAST_INDEX_NAME, currentIndex);
	}

	public int getLastCursor() {
		if (mSharedPreferences == null) {
			mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		}
		return mSharedPreferences.getInt(DefaultConfigInfo.LAST_INDEX_NAME, DefaultConfigInfo.LAST_INDEX);
	}

	public int getLastQueue() {
		if (mSharedPreferences == null) {
			mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		}
		return mSharedPreferences.getInt(DefaultConfigInfo.LAST_QUEUE_NAME, DefaultConfigInfo.LAST_QUEUE);
	}
	
}
