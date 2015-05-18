package br.com.sevenbeats.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import br.com.sevenbeats.mvc.controller.PlayerManager;
import br.com.sevenbeats.notification.MusicNotification;


public class MusicService extends Service {
    //http://www.klebermota.eti.br/2011/11/08/executando-um-servico-em-segundo-plano-no-android/
    //http://code.tutsplus.com/tutorials/create-a-music-player-on-android-song-playback--mobile-22778

    private PlayerManager playerManager;
    private final IBinder musicBind = new MusicBinder();

    @Override
    public void onCreate() {
        playerManager = new PlayerManager(this);
//        MusicNotification notification = new MusicNotification(this).create(1000, "");
//        startForeground(notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(playerManager.getPlayer() != null && playerManager.getPlaylist() != null) {
            if (playerManager.getPlayer().isPlaying()) {
                playerManager.pause();
            } else {
                playerManager.play();
            }
        }else{
            System.out.println("onstartCommand playermanager == null");
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    public class MusicBinder extends Binder {

        public PlayerManager getPlayer() {
            return playerManager;
        }

    }

    @Override
    public boolean onUnbind(Intent intent){
        return false;
    }

    public void startForeground(MusicNotification notification){
        startForeground(1000, notification.getNotification());
    }

    public void stopForeground(){
        stopForeground();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playerManager != null)
            playerManager.getPlayer().release();
    }
}
