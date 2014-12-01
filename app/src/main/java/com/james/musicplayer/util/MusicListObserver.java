/*
 * MusicListObserver.java [V 1.0.0]
 * classes : com.james.musicplayer.tools.MusicListObserver
 * 谭建建 Create at 2014-10-20 下午2:30:14 
 */
package com.james.musicplayer.util;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

/**
 * com.james.musicplayer.tools.MusicListObserver
 * @author 谭建建 
 * Create at 2014-10-20 下午2:30:14
 */
public class MusicListObserver extends ContentObserver{



    private Context mContext;  
    private Handler mHandler;  
    
    /**
     * @param context
     * @param handler
     */
    public MusicListObserver(Context context,Handler handler) {
        super(handler);
        mContext=context;
        mHandler=handler;
    }

    @Override
    public void onChange(boolean selfChange) {          
        mHandler.obtainMessage().sendToTarget();
    }
}
