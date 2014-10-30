/*
 * MusicListObserver.java [V 1.0.0]
 * classes : com.james.musicplayer.tools.MusicListObserver
 * ̷���� Create at 2014-10-20 ����2:30:14 
 */
package com.james.musicplayer.tools;

import com.james.musicplayer.db.DBInfo;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;

/**
 * com.james.musicplayer.tools.MusicListObserver
 * @author ̷���� 
 * Create at 2014-10-20 ����2:30:14
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