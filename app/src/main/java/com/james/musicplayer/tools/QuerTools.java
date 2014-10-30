/**
 * QuerTools.java [V1.0.0]
 * classes : com.james.musicplayer.tools.QuerTools
 * 谭建建 Create at 2014-10-17 上午11:48:58
 */
package com.james.musicplayer.tools;

import java.util.ArrayList;

import com.james.musicplayer.bean.MusicInfo;
import com.james.musicplayer.db.DBInfo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * 数据库查询工具类 com.james.musicplayer.tools.QuerTools
 * 
 * @author 谭建建 Create at 2014-10-17 上午11:48:58
 */
public class QuerTools {

	private ContentResolver mContentResolver;
	private ArrayList<MusicInfo> resultList = null;

	private Cursor cursor;
	private String TAG = "database";

	private int limitNum = 12;

	public QuerTools(Context context) {
		mContentResolver = context.getContentResolver();
	}

	/**
	 * @param uri
	 *            Provider路径
	 * @param order
	 *            查询排序要求
	 * @param limitCount
	 *            是否限制最小列表数
	 * @return 音乐信息实体列表
	 */
	public Cursor getMusicCursorFromDataBase() {
		try {
			cursor = mContentResolver.query(DBInfo.MUSIC_URI, new String[] { DBInfo.ID+" as _id", DBInfo.TITLE,
	                DBInfo.ARTIST, DBInfo.ALBUM,DBInfo.DURATION,DBInfo.PATH }, null, null, null);

			/*cursor.moveToFirst();
			 resultList = new ArrayList<MusicInfo>();
			
			while (!cursor.isAfterLast() && cursor.getCount() > 0) {
				MusicInfo MusicInfo = new MusicInfo();
                
				MusicInfo.setId(cursor.getLong(cursor.getColumnIndex(DBInfo.ID)));
				MusicInfo.setTitleString(cursor.getString(cursor.getColumnIndex(DBInfo.TITLE)));
				MusicInfo.setArtistString(cursor.getString(cursor.getColumnIndex(DBInfo.ARTIST)));
				MusicInfo.setAlbumString(cursor.getString(cursor.getColumnIndex(DBInfo.ALBUM)));
				MusicInfo.setDuration(cursor.getString(cursor.getColumnIndex(DBInfo.DURATION)));
				MusicInfo.setPathString(cursor.getString(cursor.getColumnIndex(DBInfo.PATH)));

				Log.i(TAG, MusicInfo.getTitleString());

				resultList.add(MusicInfo);
				if (limitCount) {
					if (resultList.size() > limitNum) {
						return resultList;
					}
				}
				cursor.moveToNext();
			}*/
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return cursor;
	}


	/**
	 * @param song_id 歌曲ID
	 * @param uri Provider路径
	 * @return 删除歌曲数量
	 */
	public int removeMusicFromDatabase(long _id, Uri uri) {
		try {
			return mContentResolver.delete(uri, DBInfo.ID+"=?",
					new String[] { _id + "" });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void insertMusicToDatabase(MusicInfo musicinfo){
	    ContentValues values=new ContentValues();
	    values.put(DBInfo.TITLE, musicinfo.getTitleString());
	    values.put(DBInfo.ARTIST, musicinfo.getArtistString());
	    values.put(DBInfo.ALBUM, musicinfo.getAlbumString());
	    values.put(DBInfo.DURATION, musicinfo.getDuration());
	    values.put(DBInfo.PATH, musicinfo.getPathString());
		mContentResolver.insert(DBInfo.MUSIC_URI, values);
	}
	
	int getLimitNum() {
		return limitNum;
	}
}
