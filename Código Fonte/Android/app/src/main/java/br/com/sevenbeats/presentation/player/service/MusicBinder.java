package br.com.sevenbeats.presentation.player.service;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.List;

import br.com.sevenbeats.core.song.Song;


public class MusicBinder extends Binder implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private Song oldSong;
    private Handler handler;
    private Context context;
    private List<Song> songs;
    private MediaPlayer player;
    private int currentTrack = 0;
    private int currentSongState = 0;
    private static final int PROGRESS_DELAY = 1000;

    public MusicBinder (Context context){
        this.context = context;
        this.handler  = new Handler();
        this.player = new MediaPlayer();
        this.player.setOnErrorListener(this);
        this.player.setOnPreparedListener(this);
        this.player.setOnBufferingUpdateListener(this);
    }

    public MediaPlayer getPlayer(){
        return player;
    }

    public void setPlayList(List<Song> songs){
        this.songs = songs;
    }

    public void next(){
        setCurrentTrack(getCurrentTrack() + (getCurrentTrack() == (songs.size() -1) ? 0 : 1 ));
        setState(NEXT);
    }

    public void prev(){
        setCurrentTrack(getCurrentTrack() == 0 ? (songs.size() - 1) : (getCurrentTrack() - 1));
        setState(PREVIOUS);
    }

    public void playPause(){
        if(player.isPlaying()){
            pause();
        }else{
            if(isPlayerResumed()) {
                resume();
            }else{
                play();
            }
        }
    }

    private boolean isPlayerResumed(){
        return oldSong != null && oldSong.getId() == getCurrentSong().getId();
    }

    public void play(){
        try {
            player.reset();
            player.setDataSource(getCurrentSong().getUrl());
            player.prepareAsync();
            oldSong = getCurrentSong();
            setState(PLAY, LOADING_ENABLED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        player.pause();
        currentSongState = player.getCurrentPosition();
        handler.removeCallbacks(bufferingUpdateRunnable);
        setState(PAUSE);
    }

    public void resume(){
        player.start();
        setState(RESUME);
        handler.post(bufferingUpdateRunnable);
    }

    public void setCurrentTrack(int index){
        currentTrack = index;
        play();
    }

    public int getCurrentTrack(){
        return currentTrack;
    }

    public Song getCurrentSong(){
        return songs.get(currentTrack);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    private Runnable bufferingUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            sendBroadcastBuffer(BUFFERING, String.valueOf(player.getCurrentPosition() / 1000));
            handler.postDelayed(this, PROGRESS_DELAY);
        }
    };

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        handler.post(bufferingUpdateRunnable);
        setState(PLAY, LOADING_DISABLED);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        setState(ERROR);
        return false;
    }

    private void setState(String... state){
        for (String aState : state) {
            sendBroadcast(aState);
        }
    }

    private void sendBroadcastBuffer(String extras, String progress){
        Intent intent = new Intent(BROADCAST_REGISTER_NAME);
        intent.putExtra(EXTRAS, extras);
        intent.putExtra(extras, progress);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendBroadcast(String extras){
        Intent intent = new Intent(BROADCAST_REGISTER_NAME);
        intent.putExtra(EXTRAS, extras);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static final String PLAY = "PLAY";
    public static final String NEXT = "NEXT";
    public static final String ERROR = "ERROR";
    public static final String PAUSE = "PAUSE";
    public static final String RESUME = "RESUME";
    public static final String LOADING_ENABLED = "LOADING ENABLED";
    public static final String LOADING_DISABLED = "LOADING DISABLED";
    public static final String EXTRAS = "EXTRAS";
    public static final String BUFFERING = "BUFFER";
    public static final String PREVIOUS = "PREVIOUS";
    public static final String BROADCAST_REGISTER_NAME = "SEVENBEATS BROADCAST";
}