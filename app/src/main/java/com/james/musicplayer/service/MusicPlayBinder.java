package com.james.musicplayer.service;

import android.os.RemoteException;

import com.james.musicplayer.runtime.PlayerRuntime;

/**
 * MusicPlayBinder [V1.0.0]
 * IMusicControlService的Stub子类继承类
 * classes : com.james.musicplayer.service.MusicPlayBinder
 * 谭建建 Create at 2014/10/31 0031 14:15
 */
public class MusicPlayBinder extends IMusicControlService.Stub  {
	public MusicPlayBinder() {
		super();
	}

	@Override
	public void registerCallback(ICallback callback) throws RemoteException {
		if (callback != null)
		{
			MusicPlayService.mCallbacks.register(callback);
		}
	}

	@Override
	public void unregisterCallback(ICallback callback) throws RemoteException {
		if (callback != null)
		{
			MusicPlayService.mCallbacks.register(callback);
		}
	}

	@Override
	public void playNext() throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().playNext();
	}

	@Override
	public void playPrevious() throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().playPrevious();
	}

	@Override
	public void pauseMusic() throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().pauseMusic();
	}

	@Override
	public void resumeMusic() throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().resumeMusic();
	}

	@Override
	public void playMusic() throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().playMusic();
	}

	@Override
	public void setPlayMod(int whitch) throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().setPlayMod(whitch);
	}

	@Override
	public int getPlayMod() throws RemoteException {
		return PlayerRuntime.sSD.getDmPlayer().getPlayMod();
	}

	@Override
	public void seekTo(int where) throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().seekTo(where);
	}

	@Override
	public int getCurrentSeekTime() throws RemoteException {
		return PlayerRuntime.sSD.getDmPlayer().getCurrentSeekTime();
	}

	@Override
	public boolean isPlaying() throws RemoteException {
		return PlayerRuntime.sSD.getDmPlayer().isPlaying();
	}

	@Override
	public int getNowPlayingTime() throws RemoteException {
		return PlayerRuntime.sSD.getDmPlayer().getCurrentSeekTime();
	}

	@Override
	public void setNowPlayingTime(int position) throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().setCurrentSeekTime(position);
	}

	@Override
	public String getNowPlayingTitle() throws RemoteException {
		return null;
	}

	@Override
	public String getNowPlayingArtist() throws RemoteException {
		return null;
	}

	@Override
	public String getNowDurationString() throws RemoteException {
		return PlayerRuntime.sSD.getDmPlayer().getNowDurationString();
	}

	@Override
	public int getNowDurationInt() throws RemoteException {
		return PlayerRuntime.sSD.getDmPlayer().getNowDurationInt();
	}


	@Override
	public void setNowPlayingList(int index) throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().setNowPlayingList(index);
	}

	@Override
	public void stopMusic() throws RemoteException {
		PlayerRuntime.sSD.getDmPlayer().stopMusic();
	}


}