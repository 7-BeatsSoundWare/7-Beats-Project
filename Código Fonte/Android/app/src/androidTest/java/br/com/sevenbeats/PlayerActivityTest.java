package br.com.sevenbeats;

import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.presentation.player.PlayerActivity;
/**
 * Created by diogojayme on 6/8/15.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PlayerActivityTest extends ActivityInstrumentationTestCase2<PlayerActivity> {

    PlayerActivity activity;

    public PlayerActivityTest() {
        super(PlayerActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
    }

    @SmallTest public void testPreconditions() throws Exception{
        assertNotNull(activity);
        assertTrue(activity.created);
        assertTrue(activity.started);
        assertNotNull(activity.musicBinder);
    }

    @SmallTest public void testValidList1(){
        List<Song> songList = new ArrayList<>();
        songList.add(new Song(3, "", "", "", null));
        assertTrue(activity.musicBinder.isValidPlaylist(songList));
    }

    @SmallTest public void testValidList2(){
        List<Song> songList = null;
        assertFalse(activity.musicBinder.isValidPlaylist(songList));
    }

    @SmallTest public void testPlay1(){
        List<Song> songList = new ArrayList<>();
        songList.add(null);
        activity.musicBinder.setPlayList(songList);
        assertTrue(activity.musicBinder.play());
    }

    @SmallTest public void testPlay2(){
        List<Song> songList = new ArrayList<>();
        songList.add(new Song(3, "http://69.28.84.155/public/musicas/kendrick_lamar_sherane_aka_master_splinters_daughter.mp3", "", "", null));
        activity.musicBinder.setPlayList(songList);
        assertTrue(activity.musicBinder.play());
    }

    @SmallTest public void testPlay3(){
        List<Song> songList = new ArrayList<>();
        songList.add(new Song(3, "fsdfsdfsdf", "", "", null));
        activity.musicBinder.setPlayList(songList);
        assertTrue(activity.musicBinder.play());
    }


//    /**
//     * Esse teste não depende de entradas do usuário,
//     * e sim de uma resposta correta do servidor
//     * */
//    @SmallTest public void testPlayMusicaInvalida() throws Exception{
//        activity.musicBinder.playing = false;
//        List<Song> songList = new ArrayList<>();
//        songList.add(new Song(1, "dfjkj232jkdjsk", "", "", new Album()));
//        activity.musicBinder.setPlayList(songList);
//        activity.musicBinder.play();
//        try {
//            Thread.sleep(5000);
//            assertFalse(activity.musicBinder.playing);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SmallTest public void testPlayMusicaValida() throws Exception{
//        activity.musicBinder.playing = false;
//        List<Song> songList = new ArrayList<>();
//        songList.add(new Song(1, "http://69.28.84.155/public/musicas/kendrick_lamar_the_art_of_peer_pressure.mp3", "", "", new Album()));
//        activity.musicBinder.setPlayList(songList);
//        activity.musicBinder.play();
//
//        try {
//            Thread.sleep(5000);
//            assertTrue(activity.musicBinder.playing);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SmallTest public void testNextMusicaInvalida() throws Exception{
//        activity.musicBinder.playing = false;
//        List<Song> songList = new ArrayList<>();
//        songList.add(null);
//        activity.musicBinder.setPlayList(songList);
//        activity.musicBinder.next();
//
//        try {
//            Thread.sleep(5000);
//            assertFalse(activity.musicBinder.playing);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SmallTest public void testNextMusicaValida() throws Exception{
//        activity.musicBinder.playing = false;
//        List<Song> songList = new ArrayList<>();
//        songList.add(new Song(1, "http://69.28.84.155/public/musicas/kendrick_lamar_the_art_of_peer_pressure.mp3", "", "", new Album()));
//        activity.musicBinder.setPlayList(songList);
//        activity.musicBinder.next();
//
//        try {
//            Thread.sleep(5000);
//            assertTrue(activity.musicBinder.playing);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @SmallTest public void testPrevMusicaValida() throws Exception{
//        activity.musicBinder.playing = false;
//        List<Song> songList = new ArrayList<>();
//        songList.add(new Song(1, "http://69.28.84.155/public/musicas/kendrick_lamar_the_art_of_peer_pressure.mp3", "", "", new Album()));
//        activity.musicBinder.setPlayList(songList);
//        activity.musicBinder.prev();
//
//        try {
//            Thread.sleep(5000);
//            assertTrue(activity.musicBinder.playing);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * teste falha
//     * */
//    @SmallTest public void testPrevMusicaInvalida() throws Exception{
//        activity.musicBinder.playing = false;
//        List<Song> songList = new ArrayList<>();
//        songList.add(null);
//        activity.musicBinder.setPlayList(songList);
//        activity.musicBinder.prev();
//        try {
//            Thread.sleep(5000);
//            assertFalse(activity.musicBinder.playing);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @SmallTest public void testPauseMusica() throws Exception{
//        activity.musicBinder.playing = false;
//        List<Song> songList = new ArrayList<>();
//        songList.add(new Song(1, "http://69.28.84.155/public/musicas/kendrick_lamar_the_art_of_peer_pressure.mp3", "", "", new Album()));
//        activity.musicBinder.setPlayList(songList);
//        activity.musicBinder.play();
//        try {
//            Thread.sleep(5000);
//            assertTrue(activity.musicBinder.playing);
//            activity.musicBinder.pause();
//            assertFalse(activity.musicBinder.playing);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
