package br.com.sevenbeats.mvc.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.sevenbeats.R;
import br.com.sevenbeats.mvc.controller.PlayerManager;
import br.com.sevenbeats.mvc.model.Song;
import br.com.sevenbeats.service.MusicService;


public class PlayerActivity extends Activity implements View.OnClickListener, PlayerManager.OnSeekBarUpdateListener {

    private  ImageButton next;
    private  ImageButton previous;
    private  SeekBar seekBar;
    private  ImageButton playPause;
    private TextView song;
    private TextView artist;
    private  Intent playIntent;
    private PlayerManager playerManager;
    private  boolean musicBound = false;

    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            playerManager = binder.getPlayer();
            List<Song> songList = new ArrayList<>();
            songList.add(new Song("http://69.28.84.155/public/musicas/years_and_years_desire.mp3","Desire", "Y & Y EP"));
            songList.add(new Song("http://69.28.84.155/public/musicas/years_and_years_take_shelter.mp3","Take Shelter", "Y & Y EP"));
            songList.add(new Song("http://69.28.84.155/public/musicas/years_and_years_king.mp3","King", "Y & Y EP"));
            songList.add(new Song("http://69.28.84.155/public/musicas/years_and_years_memo.mp3","Memo", "Y & Y EP"));
            playerManager.setPlayList(songList);
            onSongChanged();
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        next = (ImageButton) findViewById(R.id.next);
        song = (TextView) findViewById(R.id.song_name);
        artist = (TextView) findViewById(R.id.artist_name);
        previous = (ImageButton) findViewById(R.id.previous);
        seekBar = (SeekBar) findViewById(R.id.player_seek_bar);
        playPause = (ImageButton) findViewById(R.id.play_pause);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        playPause.setOnClickListener(this);
        Intent svc = new Intent(this, MusicService.class);
        startService(svc);
        bindService(new Intent(this, MusicService.class), musicConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (musicBound) {
//            unbindService(musicConnection);
//            musicBound = false;
//        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.play_pause){
           onPlayClick();
        }else if(v.getId() == R.id.next){
            onNextClick();
        }else if(v.getId() == R.id.previous){
          onPreviousClick();
        }

    }

    private void onPlayClick() {
        if (playerManager != null) {
            if (playerManager.isPlaying()) {
                playerManager.pause();
                setPlayPause(false);
            } else {
                playerManager.play();
                setPlayPause(true);
            }
        }
    }

    private void setPlayPause(boolean playing){
        playPause.setImageResource(playing ? R.drawable.ic_pause : R.drawable.ic_play);
    }

    private void onNextClick() {
        if (playerManager != null) {
            playerManager.next();
            onSongChanged();
        }
    }

    private void onPreviousClick() {
        if (playerManager != null) {
            playerManager.previous();
            onSongChanged();
        }
    }

    private void onSongChanged(){
        song.setText(playerManager.getCurrentTrack().getName());
        artist.setText(playerManager.getCurrentTrack().getArtist());
    }

    @Override
    public void onSeekbarUpdate(int progress) {
        seekBar.setProgress(progress);
    }

}