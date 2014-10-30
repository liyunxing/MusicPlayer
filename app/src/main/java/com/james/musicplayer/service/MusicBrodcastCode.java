/**
 * MusicBrodcastCode.java [V1.0.0]
 * classes : com.james.musicplayer.service.MusicBrodcastCode
 * 谭建建 Create at 2014-10-17 下午5:04:06
 */
package com.james.musicplayer.service;

/**
 * MusicPlayService向UI发送广播时的参数设定，接收广播时根据参数名获取参数值
 * com.james.musicplayer.service.MusicBrodcastCode
 * @author 谭建建 
 * Create at 2014-10-17 下午5:04:06
 */
public class MusicBrodcastCode {

	public static final String KEY="CHANGE_UI";//发送广播时putExtra中默认的KEY名
	
	
	public static final int CODE_CHANGE_PLAY_BUTTON_BG=0;//改变play按键的背景
	public static final int CODE_SET_SEEKBAR_TXT_DURATION=1;//设置seekbar时间显示
	public static final int CODE_SET_SEEKBAR_MAX=2;//设置seekbar最大值
	public static final int CODE_CHANGE_MUSIC_INFO=3;//改变当前播放音乐的信息
}
