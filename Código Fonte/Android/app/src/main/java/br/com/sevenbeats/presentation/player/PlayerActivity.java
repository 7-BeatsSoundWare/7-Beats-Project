package br.com.sevenbeats.presentation.player;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.album.Album;
import br.com.sevenbeats.utils.internet.http.ErrorResponse;
import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.presentation.player.service.MusicBinder;
import br.com.sevenbeats.presentation.player.service.MusicService;
import br.com.sevenbeats.utils.annotation.MvcPattern;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

@MvcPattern("Presenter") public class PlayerActivity extends Activity {
    private MusicBinder mBinder;
    private PlayerControl mPlayerControl;

    @InjectView(R.id.next) ImageButton mNext;
    @InjectView(R.id.song_name) TextView mSong;
    @InjectView(R.id.player_cover) ImageView mCover;
    @InjectView(R.id.player_timer) TextView mTimer;
    @InjectView(R.id.artist_name) TextView mArtist;
    @InjectView(R.id.previous) ImageButton mPrevious;
    @InjectView(R.id.play_pause) ImageButton mPlayPause;
    @InjectView(R.id.player_seek_bar) ProgressBar mSeekBar;
    @InjectView(R.id.player_loading) ProgressBar mLoadingPlayer;
    @InjectView(R.id.player_loading_view_container) View mLoadingContainer;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.inject(this);
        initActivityData();
    }

    private void initActivityData(){
        mPlayerControl = new PlayerControl(this);
    }

    @Override protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(PlayerActivity.this).registerReceiver(musicViewUpdaterBroadcastReceiver, new IntentFilter(MusicBinder.BROADCAST_REGISTER_NAME));
    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(PlayerActivity.this).unregisterReceiver(musicViewUpdaterBroadcastReceiver);
        ButterKnife.reset(this);
    }

    @OnClick(R.id.next) public void next(View v){
        mBinder.next();
    }

    @OnClick(R.id.previous) public void previous(View v){
        mBinder.prev();
    }

    @OnClick(R.id.play_pause) public void playPause(View v){
        mBinder.playPause();
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

    private void setErrorViewEnabled(boolean error){
        mCover.setImageResource(error ? R.drawable.error_placeholder : R.drawable.album2);
    }

    private void setViewEnabled(boolean enabled){
        mLoadingContainer.setVisibility(enabled ? View.INVISIBLE : View.VISIBLE);

        if(enabled){
            AlphaAnimation animation = new AlphaAnimation(1.0f , 0.0f);
            mLoadingContainer.setAnimation(animation);
            animation.setFillAfter(true);
            animation.setDuration(300);
            animation.start();
        }

    }

    private void setSongChanged(){
        mSong.setText(mBinder.getCurrentSong().getName());
        mArtist.setText(mBinder.getCurrentSong().getArtist());
    }

    private void setBufferUpdate(int bufferUpdate){
        mSeekBar.setProgress(bufferUpdate);

        if(bufferUpdate <= 60){

            if(bufferUpdate <= 9){
                mTimer.setText("00:0" + bufferUpdate);
            }else{
                mTimer.setText("00:" + bufferUpdate);
            }

        }else{
            mTimer.setText(bufferUpdate + "");
        }
    }

    private void updateView(){
        setSongChanged();
        changePLayPause(mBinder.getPlayer().isPlaying());
    }

    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicBinder) service;
            List<Song> songList = new ArrayList<>();
            songList.add(new Song(1,"http://69.28.84.155/public/musicas/years_and_years_desire.mp3","Desire", "Y & Y EP"));
            songList.add(new Song(2,"http://69.28.84.155/public/musicas/years_and_years_take_shelter.mp3","Take Shelter", "Y & Y EP"));
            songList.add(new Song(3,"http://69.28.84.155/public/musicas/years_and_years_king.mp3","King", "Y & Y EP"));
            songList.add(new Song(4,"http://69.28.84.155/public/musicas/years_and_years_memo.mp3", "Memo", "Y & Y EP"));
            mBinder.setPlayList(songList);
            updateView();
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
                case MusicBinder.LOADING_ENABLED:
                    setLoadingEnabled(true);
                    break;
                case MusicBinder.LOADING_DISABLED:
                    setLoadingEnabled(false);
                    break;
            }
        }
    };

    @SuppressWarnings("unused")
    public  void onError(ErrorResponse errorResponse){
        Log.d("MVC", "Updating view from method caller >>> onALbum");
        Toast.makeText(this, "onError" + errorResponse.getErrorId(), Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    public  void onAlbum(Album album){
        Log.d("MVC", "Updating view from method caller >>> onALbum");
        Toast.makeText(this,"OnALbum",Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    public  void onSongsList(ArrayList<Song> list){
        Log.d("MVC", "Updating view from method caller >>> onSongs");
        System.out.println();
        Toast.makeText(this,"onSongs",Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    public void onView(Boolean prepared) {
        Log.d("MVC", "Updating view from method caller >>> onView");
        setViewEnabled(prepared);
        setNextEnabled(prepared);
        setSeekBarEnabled(prepared);
        setPreviousEnabled(prepared);
        setPlayPauseEnabled(prepared);

        if (prepared && musicConnection != null) {
            //controller avisa quando o service deverá começar a ser executado
            Intent i = new Intent(this, MusicService.class);
            startService(i);
            bindService(new Intent(this, MusicService.class), musicConnection, Context.BIND_AUTO_CREATE);
        }

    }

    public static final String onView = "onView";
    public static final String onAlbum = "onAlbum";
    public static final String onSongs = "onSongs";
    public static final String onError = "onError";
    public static final String onSongsList = "onSongsList";


}