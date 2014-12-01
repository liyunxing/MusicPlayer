/**
 * DBInfo.java [V1.0.0]
 * classes : com.james.musicplayer.bean.DBInfo
 * 谭建建 Create at 2014-10-16 下午6:03:10
 */
package com.james.musicplayer.db;

import com.james.musicplayer.config.AppConstant;

import android.net.Uri;

/**
 * 数据库相关信息
 * com.james.musicplayer.bean.DBInfo
 * @author 谭建建 
 * Create at 2014-10-16 下午6:03:10
 */
public class DBInfo {
	
	public static final int VERSION=1;//数据库版本

	public static final String DBNAME="musics.db";//数据库名
	

	//音乐表属性值
	public static String TABLE_NAME="musicinfo";
//    public static String ID = "_id";
    public static String ID = "rowid";
    public static final String TITLE = "title";
    public static final String ARTIST = "artist";
    public static final String ALBUM = "album";
    public static final String DURATION = "duration";
    public static final String  PATH= "path";
    
    
    //provider的不同地址对应的返回值
    public static final int ITEMS = 1;
    public static final int ITEM_ID = 2;
     
    
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.james.db";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.james.db";
     
    //音乐表provider uri
    public static final Uri MUSIC_URI = Uri.parse("content://" + AppConstant.ActionString.MUSIC_PROVIDER_AUTOHORITY + "/"+TABLE_NAME);
    
}
