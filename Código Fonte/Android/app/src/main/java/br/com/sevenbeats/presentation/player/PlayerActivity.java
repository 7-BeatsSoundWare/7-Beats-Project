package br.com.sevenbeats.presentation.player;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.presentation.player.service.DownloadService;
import br.com.sevenbeats.presentation.player.service.MusicBinder;
import br.com.sevenbeats.presentation.player.service.MusicService;
import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterOptionItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
@MvcPattern("Presenter") public class PlayerActivity extends AppCompatActivity implements OnAdapterOptionItemClickListener, SeekBar.OnSeekBarChangeListener {

    public boolean created;
    public boolean started;
    public boolean serviceConnected;

    public MusicBinder musicBinder;
    private Song mCurrentSelectedSong;
    private PlayerController mPlayerController;

    @InjectView(R.id.next) ImageButton mNext;
    @InjectView(R.id.song_name) TextView mSong;
    @InjectView(R.id.player_timer) TextView mTimer;
    @InjectView(R.id.artist_name) TextView mArtist;
    @InjectView(R.id.player_cover) ImageView mCover;
    @InjectView(R.id.previous) ImageButton mPrevious;
    @InjectView(R.id.player_toolbar) Toolbar mToolbar;
    @InjectView(R.id.player_seek_bar) SeekBar mSeekBar;
    @InjectView(R.id.player_queue) RecyclerView mQueue;
    @InjectView(R.id.play_pause) ImageButton mPlayPause;
    @InjectView(R.id.player_shuffle) ImageButton mShuffle;
    @InjectView(R.id.player_queue_btn) ImageButton mQueueBtn;
    @InjectView(R.id.player_loading) ProgressBar mLoadingPlayer;
    @InjectView(R.id.player_loading_view_container) View mLoadingContainer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
        }

        mQueue.setLayoutManager(new LinearLayoutManager(this));
        mPlayerController = new PlayerController(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        registerForContextMenu(mQueue);
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, musicConnection, Context.BIND_AUTO_CREATE);

        created = true;
    }


    private boolean hasExtras(String extraName){
        if(getIntent() == null)
            throw new NullPointerException("PlayerActivity must have a unique song or a playlist");

        if(extraName.equals(PlayerConstants.EXTRA_SONG_ID)){
            return getIntent().hasExtra(extraName);
        }else if(extraName.equals(PlayerConstants.EXTRA_PLAYLIST)){
            return getIntent().hasExtra(PlayerConstants.EXTRA_PLAYLIST);
        }

        return false;
    }

    private String getExtrasSongId(){
        return getIntent().getExtras().getString(PlayerConstants.EXTRA_SONG_ID);
    }

    private ArrayList<Song> getExtraPlayList(){
        ArrayList<Song> songs = null;

        if(getIntent().hasExtra(PlayerConstants.EXTRA_PLAYLIST)){
            songs =getIntent().getExtras().getParcelableArrayList(PlayerConstants.EXTRA_PLAYLIST);
        }

        return songs;
    }

    @Override protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(PlayerActivity.this).registerReceiver(musicViewUpdaterBroadcastReceiver, new IntentFilter(MusicBinder.BROADCAST_REGISTER_NAME));
    }

    @Override protected void onResume() {
        super.onResume();
        started = true;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(PlayerActivity.this).unregisterReceiver(musicViewUpdaterBroadcastReceiver);
        ButterKnife.reset(this);
    }

    /**
     * CSU3.1 avançar música
     * */
    @SuppressWarnings("unused")
    @OnClick(R.id.next) public void nextClick(View v){
        musicBinder.next();
    }

    /**
     * CSU3.1 retroceder música
     * */
    @SuppressWarnings("unused")
    @OnClick(R.id.previous) public void previousClick(View v){
        musicBinder.prev();
    }

    /**
     * CSU2 Controlar música
     *      CSU2.1 Iniciar música
     *      CSU2.2 Pausar música
     *      CSU2.3 Resumir música
     * */
    @SuppressWarnings("unused")
    @OnClick(R.id.play_pause) public void playPauseClick(View v){
        musicBinder.playPause();
    }

    /**
     * CSU5 - Embaralhar músicas
     * */
    @SuppressWarnings("unused")
    @OnClick(R.id.player_shuffle) public void shuffleClick(View v){
        musicBinder.shuffle();
    }
    @SuppressWarnings("unused")
    @OnClick(R.id.player_queue_btn) public void onQueueClick(View v){
        mQueue.setVisibility(mQueue.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    private void changePLayPause(boolean playing){

        mPlayPause.setImageResource(playing ? R.drawable.ic_pause : R.drawable.ic_play);
    }

    private void setNextEnabled(boolean enabled){
        mNext.setClickable(enabled);
        mNext.setFocusable(enabled);
    }

    private void setPreviousEnabled(boolean enabled){
        mPrevious.setClickable(enabled);
        mPrevious.setFocusable(enabled);
    }

    private void setPlayPauseEnabled(boolean enabled){
        mPlayPause.setClickable(enabled);
        mPlayPause.setFocusable(enabled);
    }

    private void setSeekBarEnabled(boolean enabled){
        mSeekBar.setProgress(0);
        mSeekBar.setClickable(enabled);
        mSeekBar.setFocusable(enabled);
    }

    private void setLoadingEnabled(boolean loading){
        mPlayPause.setVisibility(loading ? View.GONE : View.VISIBLE);
        mLoadingPlayer.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @SuppressWarnings("unused")
    private void setErrorViewEnabled(boolean error){
        mCover.setImageResource(error ? R.drawable.error_placeholder : R.drawable.album2);
    }

    private void setViewEnabled(boolean enabled){
        mLoadingContainer.setVisibility(enabled ? View.GONE : View.VISIBLE);

        if(enabled){
            AlphaAnimation animation = new AlphaAnimation(1.0f , 0.0f);
            mLoadingContainer.setAnimation(animation);
            animation.setFillAfter(true);
            animation.setDuration(300);
            animation.start();
        }

    }

    private void setSongChanged(){
        mSong.setText(musicBinder.getCurrentSong().getName());
        mArtist.setText(musicBinder.getCurrentSong().getArtist());
        Picasso.with(this).load(musicBinder.getCurrentSong().getAlbum().getImageUrl()).placeholder(R.drawable.placeholder).into(mCover);
    }

    private void setBufferUpdate(int bufferUpdate){
        System.out.println(musicBinder.getPlayer().getDuration() / 1000);
        mSeekBar.setMax(musicBinder.getPlayer().getDuration() / 1000);
        mSeekBar.setProgress(bufferUpdate);
        mTimer.setText(convertSecondsToText(bufferUpdate));
    }

    private String convertSecondsToText(int time) {
        int minutes = (int) Math.floor(time / 60f);
        String strMinutes = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        int seconds = time % 60;
        String strSeconds = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
        return strMinutes + ":" + strSeconds;
    }

    private void setShuffleEnabled(boolean enabled){
        mShuffle.setImageResource(enabled ? R.drawable.ic_av_shuffle : R.drawable.ic_av_shuffle_activated);
    }

    private void notifySongChanged(){
        setSongChanged();
        changePLayPause(musicBinder.getPlayer().isPlaying());
    }

    @SuppressWarnings("unused")
    public  void onError(){
        Log.d("MVC", "Updating view from method caller >>> onALbum");
        Toast.makeText(this, "onError", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    public  void onSongs(ArrayList<Song> songs) {
        //TODO validar para a view não receber nenhuma atualização enquanto o metodo setPlayList não for chamado
        musicBinder.setPlayList(songs);
        QueueAdapter adapter = new QueueAdapter();
        adapter.setData(songs, this);
        mQueue.setAdapter(adapter);
        notifySongChanged();
    }

    @SuppressWarnings("unused")
    public void onView(Boolean prepared) {
        Log.d("MVC", "Updating view from method caller >>> onView");
        setViewEnabled(prepared);
        setNextEnabled(prepared);
        setSeekBarEnabled(prepared);
        setPreviousEnabled(prepared);
        setPlayPauseEnabled(prepared);
    }

    @Override
    public void onBackPressed() {
        if(mQueue.getVisibility() == View.VISIBLE){
            mQueue.setVisibility(View.INVISIBLE);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("DOWNLOAD");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle() == "DOWNLOAD"){
            Intent intent = new Intent(this, DownloadService.class);
            intent.putExtra(PlayerConstants.extraUrl, mCurrentSelectedSong.getUrl());
            intent.putExtra(PlayerConstants.extraFileName, mCurrentSelectedSong.getName());
            startService(intent);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(Object data, int position) {
        if (data instanceof Song){
            musicBinder.setCurrentTrack(position);
        }
    }

    @Override
    public void onOptionItemClick(Object data, int position) {
        if (data instanceof Song){
            mQueue.showContextMenu();
            mCurrentSelectedSong = (Song) data;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }

    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MusicBinder) service;

            if(hasExtras(PlayerConstants.EXTRA_SONG_ID)){
                mPlayerController.onRequest(PlayerConstants.METHOD_ON_SONGS, getExtrasSongId());
            }else{
                if(getExtraPlayList() == null){
//                    throw new NullPointerException("You must set a song or playlist");
                    //nao adiciona nenhuma playlist
                }else{
                    onSongs(getExtraPlayList());
                }

            }

            serviceConnected = true;
        }

        @Override public void onServiceDisconnected(ComponentName name) {
        }
    };

    private BroadcastReceiver musicViewUpdaterBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String extra = intent.getStringExtra(MusicBinder.EXTRAS);
            switch (extra){
                case MusicBinder.PLAY:
                    changePLayPause(true);
                    break;
                case MusicBinder.PAUSE:
                    changePLayPause(false);
                    break;
                case MusicBinder.BUFFERING:
                    String buffer = intent.getStringExtra(MusicBinder.BUFFERING);
                    setBufferUpdate(Integer.valueOf(buffer));
                    break;
                case MusicBinder.NEXT:
                    setSongChanged();
                    break;
                case MusicBinder.PREVIOUS:
                    setSongChanged();
                    break;
                case MusicBinder.RESUME:
                    changePLayPause(true);
                    break;
                case MusicBinder.SHUFFLE:
                    setShuffleEnabled(true);
                    break;
                case MusicBinder.LOADING_ENABLED:
                    setLoadingEnabled(true);
                    break;
                case MusicBinder.LOADING_DISABLED:
                    setLoadingEnabled(false);
                    break;
            }
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(b) {
            seekBar.setProgress(i);
            mTimer.setText(convertSecondsToText(i));
            musicBinder.getPlayer().seekTo(i * 1000);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}