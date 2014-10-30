/**
 * ImageProvider.java [V1.0.0]
 * classes : com.james.musicplayer.tools.ImageProvider
 * 谭建建 Create at 2014-10-18 上午10:08:27
 */
package com.james.musicplayer.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

/**
 * 获取音乐内置图片
 * com.james.musicplayer.tools.ImageProvider
 * @author 谭建建 
 * Create at 2014-10-18 上午10:08:27
 */
public class ImageProvider {
	static Uri sArtworkUri = Uri
			.parse("content://media/external/audio/albumart");
	static BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
	static Bitmap mCacheBit;
	
}
