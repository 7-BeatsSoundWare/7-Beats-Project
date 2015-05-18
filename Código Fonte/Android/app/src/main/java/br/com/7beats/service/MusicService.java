package vuziq.s7venbeats.service;


import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import vuziq.s7venbeats.mvc.model.Song;

public class MusicService extends Service  implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener{
    //http://www.klebermota.eti.br/2011/11/08/executando-um-servico-em-segundo-plano-no-android/
    //http://code.tutsplus.com/tutorials/create-a-music-player-on-android-song-playback--mobile-22778

    //media activity_player
    private MediaPlayer player;
    //song list
    private List<Song> songs;
    //current position
    private int songPosn;

    private final IBinder musicBind = new MusicBinder();

    @Override
    public void onCreate() {
//        super.onCreate();
//        songPosn = 0;
//        activity_player = new MediaPlayer();
//        initMusicPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    //serve para alguem se comunicar com o serviço
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent){
//        activity_player.stop();
//        activity_player.release();
        return false;
    }

    public void initMusicPlayer(){
//        activity_player.setWakeMode(getApplicationContext(),
//                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setSongs(List<Song> theSongs){
        songs = theSongs;
    }
    public void setSong(int songIndex){
        songPosn = songIndex;
    }

    public void playSong(){
        player.reset();
        //get song
        Song playSong = songs.get(songPosn);
        //get id
        String currentSong = playSong.getUrl();
        //set uri
        Uri songUri = Uri.parse(currentSong);
        try{
            player.setDataSource(getApplicationContext(), songUri);
            player.prepareAsync();
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public class MusicBinder extends Binder {

       public MusicService getService() {
            return MusicService.this;
        }

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service stoping", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
