/**
 * DBHelper.java [V1.0.0]
 * classes : com.james.musicplayer.db.DBHelper
 * 谭建建 Create at 2014-10-16 下午5:54:36
 */
package com.james.musicplayer.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 音乐数据库辅助类
 * com.james.musicplayer.db.DBHelper
 * @author 谭建建 
 * Create at 2014-10-16 下午5:54:36
 */
public class DBHelper extends SQLiteOpenHelper{

	
	public DBHelper(Context context) {
	        super(context, DBInfo.DBNAME, null, DBInfo.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlStr="create table "+DBInfo.TABLE_NAME+"(" +
//                DBInfo.ID+" integer autoincrement not null,"+
                DBInfo.TITLE+" text not null," +
                DBInfo.ARTIST+" text not null," +
                DBInfo.ALBUM+" text,"+
                DBInfo.DURATION+" text not null,"+
                DBInfo.PATH+" text not null," +
                "primary key("+DBInfo.TITLE+","+DBInfo.ARTIST+"));";
        db.execSQL(sqlStr);
	}

	/**( (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DBInfo.TABLE_NAME);  
        onCreate(db); 
	}

}
