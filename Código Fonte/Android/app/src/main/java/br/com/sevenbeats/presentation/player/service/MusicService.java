package br.com.sevenbeats.presentation.player.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class MusicService extends Service {


    private final IBinder musicBind = new MusicBinder(this);

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent){
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
