package br.com.sevenbeats.presentation.player.service;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.sevenbeats.core.song.Song;


public class MusicBinder extends Binder implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    //TESTE
    public boolean playing;
    //ENDTESTE

    public int oldIndex;
    private Song oldSong;
    private Handler handler;
    private Context context;
    private List<Song> songs;
    private boolean shuffle;
    private MediaPlayer player;
    private int currentTrack = 0;
    private int currentSongState = 0;
    private static final int PROGRESS_DELAY = 1000;
    private HashMap<Integer, Song> songsShuffleStateBehavior;

    public MusicBinder (Context context){
        this.context = context;
        this.handler  = new Handler();
        this.player = new MediaPlayer();
        this.player.setOnErrorListener(this);
        this.player.setOnPreparedListener(this);
        this.player.setOnBufferingUpdateListener(this);
        this.songsShuffleStateBehavior = new HashMap<>();
    }

    public MediaPlayer getPlayer(){
        return player;
    }

    public boolean isValidPlaylist(List<Song> songs){
        return songs != null && songs.size() != 0;
    }

    public void setPlayList(List<Song> songs){
        this.songs = songs;
    }

    public void next(){
        oldIndex = getCurrentSongIndex();
        setCurrentTrack(getCurrentSongIndex() + (getCurrentSongIndex() == (songs.size() -1) ? 0 : 1 ));
        setState(NEXT);
    }

    public void prev(){
        oldIndex = getCurrentSongIndex();
        setCurrentTrack(getCurrentSongIndex() == 0 ? (songs.size() - 1) : (getCurrentSongIndex() - 1));
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

    /**
     * CSU 2.1 - Iniciar música
     * */
    public boolean play(){
        boolean success;
        try {
            player.reset();
            player.setDataSource(getCurrentSong().getUrl());
            player.prepareAsync();
            oldSong = getCurrentSong();
            setState(PLAY, LOADING_ENABLED);
            success = true;
        } catch (Exception e){
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    /**
     * CSU 2.2 Pausar música
     * */
    public void pause(){
        player.pause();
        currentSongState = player.getCurrentPosition();
        handler.removeCallbacks(bufferingUpdateRunnable);
        setState(PAUSE);
    }

    /**
     * CSU 2.3 Resumir música
     * */
    public void resume(){
        player.start();
        setState(RESUME);
        handler.post(bufferingUpdateRunnable);
    }

    /**
     * CSU 5 Embaralhar músicas
     * */
    public void shuffle(){

        if(!shuffle){
            saveListActualState();
            Collections.shuffle(songs);
        }else{
            restoreListActualState();
        }

        setState(SHUFFLE);
    }

    private void saveListActualState(){
      //TODO reverter a lista de shuffle
    }

    private void restoreListActualState(){
      //TODO alterar o shuffle
    }

    public boolean isShuffle(){
        return shuffle;
    }

    public void setCurrentTrack(int index){
        currentTrack = index;
        play();
    }


    public void setCurrentSongIndex(int index){
        this.currentTrack = index;
    }
    public int getCurrentSongIndex(){
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
        playing = true;
        handler.post(bufferingUpdateRunnable);
        setState(PLAY, LOADING_DISABLED);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        playing = false;
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
    public static final String SHUFFLE = "SHUFFLE";
    public static final String LOADING_ENABLED = "LOADING ENABLED";
    public static final String LOADING_DISABLED = "LOADING DISABLED";
    public static final String EXTRAS = "EXTRAS";
    public static final String BUFFERING = "BUFFER";
    public static final String PREVIOUS = "PREVIOUS";
    public static final String BROADCAST_REGISTER_NAME = "SEVENBEATS BROADCAST";
}