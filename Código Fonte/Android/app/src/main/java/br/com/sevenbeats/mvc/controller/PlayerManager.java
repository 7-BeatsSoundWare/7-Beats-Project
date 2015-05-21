package br.com.sevenbeats.mvc.controller;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.List;

import br.com.sevenbeats.mvc.model.Song;


public class PlayerManager implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {

    List<Song> songs;
    MediaPlayer player;
    int currentSongIndex = 0;
    Handler mainHandler, workerHandler;
    OnSeekBarUpdateListener listener;
    Context mContext;
    public PlayerManager(Context context){
        mContext = context;
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
        onBufferHandlerStop();
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
        onBufferHandlerStart();
        System.out.println();
    }

    void onBufferHandlerStart(){
        workerHandler.post(runnableBuffering);
    }

    void onBufferHandlerStop(){
        workerHandler.removeCallbacks(runnableBuffering);
    }


    private Runnable runnableBuffering = new Runnable() {
        @Override
        public void run() {
            sendBroadcast(String.valueOf(player.getCurrentPosition()));
            workerHandler.postDelayed(this, 1000);
        }
    };

    private void sendBroadcast(String extras){
        Intent intent = new Intent(BROADCAST_REGISTER_NAME);
        intent.putExtra(EXTRAS, BUFFERING);
        if(extras != null) {
            intent.putExtra(BUFFERING, extras);
        }
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onBufferingUpdate(final MediaPlayer mp, int percent) {
        System.out.println(mp.getCurrentPosition());
    }

    public List<Song> getPlaylist(){
        return songs;
    }

    public void onDestroy(){
        player.release();
    }

    public  interface OnSeekBarUpdateListener{
        void onSeekBarUpdate(int progress);
    }

    public static final String PLAY = "PLAY";
    public static final String NEXT = "NEXT";
    public static final String PAUSE = "PAUSE";
    public static final String EXTRAS = "EXTRAS";
    public static final String BUFFERING = "BUFFER";
    public static final String PREVIOUS = "PREVIOUS";
    public static final String BROADCAST_REGISTER_NAME = "SEVENBEATS BROADCAST";

}
