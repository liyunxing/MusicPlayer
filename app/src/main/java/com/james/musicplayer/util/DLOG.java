package com.james.musicplayer.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.james.musicplayer.runtime.PlayerRuntime;


/**
 * 日志服务 功能模块日志:需要输出带模块tag的log日志,记录功能实现状态与结果
 * LOG中需要标识结果,并定义关键字 UI控制日志:需要记录操作动作,激活事件,定义关键字.
 * 
 * @author yangyu
 */
public class DLOG {

    public static void d(String tag, String log) {
        if (PlayerRuntime.DEBUG)
            Log.d(tag, log);
    }
    public static void v(String tag, String log) {
        if (PlayerRuntime.DEBUG)
            Log.v(tag, log);
    }

    public static void e(String tag, String log) {
        if (PlayerRuntime.DEBUG)
            Log.e(tag, log);
    }

    public static void i(String tag, String log) {
        if (PlayerRuntime.DEBUG)
            Log.i(tag, log);
    }

}
