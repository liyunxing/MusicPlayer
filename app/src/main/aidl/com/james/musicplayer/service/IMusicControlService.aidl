package com.james.musicplayer.service;

import com.james.musicplayer.service.ICallback;
import com.james.musicplayer.bean.MusicInfo;
interface IMusicControlService{

    void registerCallback(ICallback callback);
    void unregisterCallback(ICallback callback);

    void playNext();
    void playPrevious();
    void pauseMusic();
    void resumeMusic();
    void playMusic();
    void stopMusic();
    
    void setPlayMod(int whitch);
    int getPlayMod();
    
    void seekTo(int where);
    int getCurrentSeekTime();
    int getDuration();
    
    boolean isPlaying();

    MusicInfo getMusicInfo();

    void setNowPlayingList(int index);
}