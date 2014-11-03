/*
 * MusicInfoGetter.java [V 1.0.0]
 * classes : com.james.musicplayer.tools.MusicInfoGetter
 * 谭建建 Create at 2014-10-20 上午9:30:14 
 */
package com.james.musicplayer.util;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

/**
 * 从歌曲中获取歌曲信息
 * com.james.musicplayer.tools.MusicInfoGetter
 * 
 * @author 谭建建
 *         Create at 2014-10-20 上午9:30:14
 */
public class MusicInfoGetter {

    private String title,artist,album,duration,path;


    public MusicInfoGetter(Context context,File mp3){
        
        String[] projection=new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        
        Cursor c=context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
                projection, MediaStore.Audio.Media.DATA+"=?", new String[]{mp3.getAbsolutePath()}, 
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        while(c.moveToNext()){
            title=c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
            artist=c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            int dura=c.getInt(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
            dura/=1000;
            int sec=dura%60;
            if (sec < 10) {
                duration = dura / 60 + ":0" + dura % 60;
            } else {
                duration = dura / 60 + ":" + dura % 60;
            }
            
            album=c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            path=mp3.getAbsolutePath();
        }
    }
    
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {
        return duration;
    }
    public String getPath() {
        return path;
    }

}
