package vuziq.s7venbeats.mvc.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

import vuziq.s7venbeats.R;
import vuziq.s7venbeats.mvc.controller.PlayerManager;
import vuziq.s7venbeats.mvc.model.Song;
import vuziq.s7venbeats.service.MusicService;


public class PlayerActivity extends ActionBarActivity implements View.OnClickListener, PlayerManager.OnSeekBarUpdateListener {

    private  Button next;
    private  Button previous;
    private  SeekBar seekBar;
    private  Button playPause;
    private  Intent playIntent;
    private  boolean musicBound = false;
    PlayerManager playerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);
        playPause = (Button) findViewById(R.id.play_pause);
        seekBar = (SeekBar) findViewById(R.id.player_seek_bar);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        playPause.setOnClickListener(this);
        playerManager = new PlayerManager(this, this);

    }

    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            MusicService musicSrv = binder.getService();
            List<Song> songList = new ArrayList<>();
            songList.add(new Song("http://107.161.177.140:8041/Album/SS?idsong=117887&idalbum=38632&iduser=2942420&phonetype=1&quality=0&hit=false"));
            musicSrv.setSongs(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.play_pause){
            if(playerManager.getPlayer().isPlaying()){
                playerManager.pause();
            }else{
                playerManager.play();
            }
        }else if(v.getId() == R.id.next){
            playerManager.next();
        }else if(v.getId() == R.id.previous){
            playerManager.previous();
        }

    }

    @Override
    public void onSeekbarUpdate(int progress) {
        seekBar.setProgress(progress);
    }
}