/**
 * AppConstant.java [V1.0.0]
 * classes : com.james.musicplayer.base.AppConstant
 * 谭建建 Create at 2014-10-18 下午1:56:26
 */
package com.james.musicplayer.config;

/**
 * 共享参数
 * com.james.musicplayer.base.AppConstant
 * @author 谭建建 
 * Create at 2014-10-18 下午1:56:26
 */
public class AppConstant {

	public static final String MUSIC_SERVICE_RECEIVER_ACTION= "com.james.musicplayer.musicServiceReceiver";
	public static final String MUSIC_PALY_SERVICE_ACTION = "com.james.musicplayer.service.musicPlayService";
	public static final String MUSIC_PROVIDER_AUTOHORITY = "com.james.musicplayer.db.musicProvider";
	
	

	// 循环方式
	public static final int PLAY_MOD_LIST = 0;// 列表播放,不循环
	public static  final int PLAY_MOD_CIRCLE = 1;// 列表循环播放
	public static  final int PLAY_MOD_RANDOM = 2;// 随机播放
	public static  final int PLAY_MOD_SINGLE = 3;// 单曲播放
	
}
