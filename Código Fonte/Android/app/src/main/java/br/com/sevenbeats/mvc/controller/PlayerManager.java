package br.com.sevenbeats.mvc.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;

import java.io.IOException;
import java.util.List;

import br.com.sevenbeats.mvc.model.Song;


public class PlayerManager implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {

    List<Song> songs;
    MediaPlayer player;
    int currentSongIndex = 0;
    Handler mainHandler, workerHandler;
    OnSeekBarUpdateListener listener;

    public PlayerManager(Context context){
        mainHandler = new Handler(context.getMainLooper());
        HandlerThread handlerThread = new HandlerThread("Worker thread");
        handlerThread.start();
        workerHandler = new Handler(handlerThread.getLooper());
        player = new MediaPlayer();
        player.setOnErrorListener(this);
        player.setOnPreparedListener(this);
        player.setOnBufferingUpdateListener(this);
//        PlayerRemoteReceiver.setPlayer(this);
    }

    public void play(){
        try {
            player.reset();
            player.setDataSource(getCurrentTrack().getUrl());
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying(){
        return player != null && player.isPlaying();
    }

    public void pause(){
        player.pause();
    }

    public void next(){
        setCurrentTrackIndex(getCurrentTrackIndex() + (getCurrentTrackIndex() == (songs.size() - 1) ? 0 : 1));
        play();
    }

    public void previous(){
        setCurrentTrackIndex(getCurrentTrackIndex() == 0 ? (songs.size() - 1) : (getCurrentTrackIndex() - 1));
        play();
    }

    public Song getCurrentTrack(){
        return songs.get(currentSongIndex);
    }

    public int getCurrentTrackIndex(){
        return  this.currentSongIndex;
    }

    public void setCurrentTrackIndex(int currentSongIndex){
        this.currentSongIndex = currentSongIndex;
    }

    public void setPlayList(List<Song> songs){
        this.songs = songs;
    }

    public MediaPlayer getPlayer(){
        return player;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        System.out.println();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onBufferingUpdate(final MediaPlayer mp, int percent) {
    }

    public List<Song> getPlaylist(){
        return songs;
    }
    public static interface OnSeekBarUpdateListener{
        void onSeekbarUpdate(int progress);
    }

    public void onDestroy(){
        player.release();
    }
}
