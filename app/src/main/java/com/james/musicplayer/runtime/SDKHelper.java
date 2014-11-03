package com.james.musicplayer.runtime;

/**
 * SDKHelper [V1.0.0]
 * SDK 工具类 The Class SDKHelper.
 * classes : com.james.musicplayer.runtime.SDKHelper
 * 谭建建 Create at 2014/11/3 0003 10:14
 */
public class SDKHelper {

	/** The sdkHelper. */
	private static SDKHelper sSDKHelper = null;

	public synchronized static SDKHelper instance()
	{
		if (sSDKHelper == null)
		{
			return new SDKHelper();
		}
		return sSDKHelper;
	}

}
