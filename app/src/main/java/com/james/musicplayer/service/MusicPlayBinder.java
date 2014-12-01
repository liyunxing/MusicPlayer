package com.james.musicplayer.service;

import android.os.RemoteException;

import com.james.musicplayer.bean.MusicInfo;
import com.james.musicplayer.runtime.PlayerRuntime;

/**
 * MusicPlayBinder [V1.0.0]
 * IMusicControlService的Stub子类继承类
 * classes : com.james.musicplayer.service.MusicPlayBinder
 * 谭建建 Create at 2014/10/31 0031 14:15
 */
public class MusicPlayBinder extends IMusicControlService.Stub {
    public MusicPlayBinder() {
        super();
    }

    @Override
    public void registerCallback(ICallback callback) throws RemoteException {
        if (callback != null) {
            MusicPlayService.mCallbacks.register(callback);
        }
    }

    @Override
    public void unregisterCallback(ICallback callback) throws RemoteException {
        if (callback != null) {
            MusicPlayService.mCallbacks.register(callback);
        }
    }

    @Override
    public void playNext() throws RemoteException {
        PlayerRuntime.sSD.getPlayer().playNext();
    }

    @Override
    public void playPrevious() throws RemoteException {
        PlayerRuntime.sSD.getPlayer().playPrevious();
    }

    @Override
    public void pauseMusic() throws RemoteException {
        PlayerRuntime.sSD.getPlayer().pauseMusic();
    }

    @Override
    public void resumeMusic() throws RemoteException {
        PlayerRuntime.sSD.getPlayer().resumeMusic();
    }

    @Override
    public void playMusic() throws RemoteException {
        PlayerRuntime.sSD.getPlayer().playMusic();
    }

    @Override
    public void setPlayMod(int whitch) throws RemoteException {
        PlayerRuntime.sSD.getPlayer().setPlayMod(whitch);
    }

    @Override
    public int getPlayMod() throws RemoteException {
        return PlayerRuntime.sSD.getPlayer().getPlayMod();
    }

    @Override
    public void seekTo(int where) throws RemoteException {
        PlayerRuntime.sSD.getPlayer().seekTo(where);
    }

    @Override
    public int getCurrentSeekTime() throws RemoteException {
        return PlayerRuntime.sSD.getPlayer().getCurrentSeekTime();
    }

    @Override
    public int getDuration() throws RemoteException {
        return PlayerRuntime.sSD.getPlayer().getDuration();
    }

    @Override
    public boolean isPlaying() throws RemoteException {
        return PlayerRuntime.sSD.getPlayer().isPlaying();
    }

    @Override
    public MusicInfo getMusicInfo() throws RemoteException {
        return PlayerRuntime.sSD.getPlayer().getMusicInfo();
    }

    @Override
    public void setNowPlayingList(int index) throws RemoteException {
        PlayerRuntime.sSD.getPlayer().setNowPlayingList(index);
    }

    @Override
    public void stopMusic() throws RemoteException {
        PlayerRuntime.sSD.getPlayer().stopMusic();
    }


}