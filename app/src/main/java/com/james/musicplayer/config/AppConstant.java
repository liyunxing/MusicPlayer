/**
 * AppConstant.java [V1.0.0]
 * classes : com.james.musicplayer.base.AppConstant
 * 谭建建 Create at 2014-10-18 下午1:56:26
 */
package com.james.musicplayer.config;

/**
 * 共享参数
 * com.james.musicplayer.base.AppConstant
 *
 * @author 谭建建
 *         Create at 2014-10-18 下午1:56:26
 */
public class AppConstant {

    public interface ActionString {
        public static final String MUSIC_SERVICE_RECEIVER_ACTION = "com.james.musicplayer.musicServiceReceiver";
        public static final String MUSIC_PALY_SERVICE_ACTION = "com.james.musicplayer.service.musicPlayService";
        public static final String MUSIC_PROVIDER_AUTOHORITY = "com.james.musicplayer.db.musicProvider";
    }


    public interface Msg {
        /**
         * 切换歌曲
         */
        public static final int MSG_PLAYER_SONG_CHANGE = 0x0001;//切换歌曲

        /**
         * 播放状态切换
         */
        public static final int MSG_PLAYER_STATUS_CHANGE = 0x0002;//播放状态切换

        /**
         * 播放模式切换
         */
        public static final int MSG_PLAYER_MODE_CHANGE = 0x0003;//播放模式切换

        /**
         * 专辑图异步获取完成
         */
        public static final int MSG_PLAYER_ALBUM_RETRIEVED = 0x0004;// 专辑图异步获取完成

        /**
         * 当前音乐歌词异步获取完成
         */
        public static final int MSG_CURRENT_TRACK_LRC = 0x0005;// 当前音乐歌词异步获取完成

        /**
         * 当前播放按钮改变
         */
        public static final int CODE_CHANGE_PLAY_BUTTON_BG = 0x0006;// 当前播放按钮改变

    }

    public interface PlayMode {
        // 循环方式
        public static final int PLAY_MOD_LIST = 0;// 列表播放,不循环
        public static final int PLAY_MOD_CIRCLE = 1;// 列表循环播放
        public static final int PLAY_MOD_RANDOM = 2;// 随机播放
        public static final int PLAY_MOD_SINGLE = 3;// 单曲播放
    }

}
