package br.com.sevenbeats;

import android.app.FragmentTransaction;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.runner.RunWith;

import java.util.ArrayList;

import br.com.sevenbeats.core.album.Album;
import br.com.sevenbeats.presentation.album.detail.AlbumDetailFragment;
import br.com.sevenbeats.presentation.main.MainActivity;
import br.com.sevenbeats.presentation.playlists.detail.PlaylistDetailFragment;
import br.com.sevenbeats.presentation.playlists.list.PlaylistFragment;
import br.com.sevenbeats.presentation.search.SearchFragment;
import br.com.sevenbeats.utils.service.ServiceManager;

/**
 * Created by diogojayme on 6/8/15.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    //TESTE
    public boolean created;
    public boolean started;
    public boolean viewCreated;
    //ENDTESTE

    MainActivity activity;
    FragmentTransaction transaction;
    public MainActivityTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        transaction = activity.getFragmentManager().beginTransaction();
    }


    @SmallTest public void testPreconditions()  throws Exception{
        assertNotNull(activity);
        assertNotNull(transaction);
    }

    SearchFragment fragment;
    @LargeTest
    public void testSearchFragmentLifeCycle() throws Exception{
        try {
            fragment = new SearchFragment();
            assertNotNull(fragment);
            transaction.add(R.id.main_fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            getInstrumentation().callActivityOnStart(activity);
            getInstrumentation().callActivityOnResume(activity);

            assertTrue(fragment.created);
            assertTrue(fragment.started);
            assertTrue(fragment.viewCreated);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @UiThreadTest
    public void testSearch1(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Object obj = ServiceManager.getInstance().build().getAlbumService().listAlbums("Good Kid, M.A.A.D City");
                ArrayList<Album> albumList = (ArrayList<Album>) obj;
                assertTrue(albumList.size() > 0);
            }
        }).start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @UiThreadTest
    public void testSearch2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Object obj = ServiceManager.getInstance().build().getAlbumService().listAlbums("dsfsdsfs");
                ArrayList<Album> albumList = (ArrayList<Album>) obj;
                assertTrue(albumList.size() > 0);
            }
        }).start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @LargeTest
    public void testPlaylistFragmentLifeCycle()  throws Exception{
        try {
            PlaylistFragment underTest = new PlaylistFragment();
            assertNotNull(underTest);
            transaction.add(R.id.main_fragment_container, underTest);
            transaction.addToBackStack(null);
            transaction.commit();

            getInstrumentation().callActivityOnStart(activity);
            getInstrumentation().callActivityOnResume(activity);

            assertTrue(underTest.created);
            assertTrue(underTest.started);
            assertTrue(underTest.viewCreated);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @LargeTest
    public void testAlbumDetailFragmentLifeCycle()  throws Exception{
        try {
            AlbumDetailFragment underTest = new AlbumDetailFragment();
            assertNotNull(underTest);
            transaction.add(R.id.main_fragment_container, underTest);
            transaction.addToBackStack(null);
            transaction.commit();

            getInstrumentation().callActivityOnStart(activity);
            getInstrumentation().callActivityOnResume(activity);

            assertTrue(underTest.created);
            assertTrue(underTest.started);
            assertTrue(underTest.viewCreated);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @LargeTest
    public void testPlaylistDetailFragmentLifeCycle() throws Exception {
        try {
            PlaylistDetailFragment underTest = new PlaylistDetailFragment();
            assertNotNull(underTest);
            transaction.add(R.id.main_fragment_container, underTest);
            transaction.addToBackStack(null);
            transaction.commit();

            getInstrumentation().callActivityOnStart(activity);
            getInstrumentation().callActivityOnResume(activity);

            assertTrue(underTest.created);
            assertTrue(underTest.started);
            assertTrue(underTest.viewCreated);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
