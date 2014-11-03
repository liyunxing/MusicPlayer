package com.james.musicplayer.service;

import com.james.musicplayer.service.ICallback;
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
    
    boolean isPlaying();
    
    String getNowPlayingTitle();
    String getNowPlayingArtist();
    
    String getNowDurationString();
    int getNowDurationInt();
    
    int getNowPlayingTime();
    void setNowPlayingTime(int position);    
    
    void setNowPlayingList(int index);
}