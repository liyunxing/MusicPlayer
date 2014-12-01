package com.james.musicplayer.runtime;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * SDCardUtil [V1.0.0]
 * classes : com.james.musicplayer.runtime.SDCardUtil
 * 谭建建 Create at 2014/11/3 0003 9:56
 */
public class SDCardUtil {

	public static boolean isEnable = false;

	/**
	 * 检查SDCARD是否存在
	 *
	 * @param context
	 * @return
	 */
	public static boolean checkSDCard(Context context)
	{

		File file = Environment.getExternalStorageDirectory();

		if (file == null || !file.exists())
		{
			isEnable = false;
		} else
		{
			isEnable = true;
		}

		return isEnable;
	}
}
