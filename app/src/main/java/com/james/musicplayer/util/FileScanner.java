/**
 * ScanMP3File.java [V1.0.0]
 * classes : com.james.musicplayer.tools.ScanMP3File
 * 谭建建 Create at 2014-10-19 上午9:56:15
 */
package com.james.musicplayer.util;

import java.io.File;
import java.io.FileFilter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.james.musicplayer.bean.MusicInfo;

/**
 * com.james.musicplayer.tools.FileScanner
 * 
 * @author 谭建建
 *         Create at 2014-10-19 上午9:56:15
 */
public class FileScanner {

    private Context mContext;
    private final String LOG = "File Path";

    public FileScanner(Context context) {
        this.mContext= context;
    }

    public void scanMp3File() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MusicFiles";
        Log.v(LOG, path);
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = scanFile(dir, ".mp3");
            if (files != null) {
                putMusicToDatabase(mContext,files);
            }
        } else {
            dir.mkdirs();
        }
    }

    private void putMusicToDatabase(Context context,File[] files) {
        MusicInfoGetter mig;
        MusicInfo musicInfo = new MusicInfo();
        for (File file : files) {
            try {
                mig = new MusicInfoGetter(context,file);
                musicInfo.setTitleString(mig.getTitle());
                musicInfo.setArtistString(mig.getArtist());
                musicInfo.setAlbumString(mig.getAlbum());
                musicInfo.setDuration(mig.getDuration());
                musicInfo.setPathString(mig.getPath());
                new QuerTools(mContext).insertMusicToDatabase(musicInfo);            
            } catch (Exception e) {
                Log.v(LOG, "创建音乐信息失败！" + e.getMessage());
            }
        }
    }

    /**
     * @param rootPath
     *            根目录
     * @param fileName
     *            扫描文件后缀
     */
    public File[] scanFile(File rootPath, final String filterName) {

        //    	File[] musicFils=
        return rootPath.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (((pathname + "").toLowerCase()).endsWith(filterName)) {
                    Log.v(LOG, pathname.getAbsolutePath());
                    return true;
                }
                if (pathname.isDirectory()) {//如果是目录  
                    scanFile(pathname, filterName);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * 多格式扫描
     * 
     * @param rootPath
     *            根目录
     * @param filterNames
     *            格式数组
     */
    public void scanFile(File rootPath, final String[] filterNames) {
        for (int i = 0; i < filterNames.length; i++) {
            scanFile(rootPath, filterNames[i]);
        }
    }

}
