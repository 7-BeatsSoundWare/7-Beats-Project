package br.com.sevenbeats;

import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.TextView;

import org.junit.runner.RunWith;

import br.com.sevenbeats.presentation.player.PlayerActivity;
/**
 * Created by diogojayme on 6/8/15.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PlayerActivityTest extends ActivityInstrumentationTestCase2<PlayerActivity> {

    PlayerActivity activity;
    TextView songName;

    public PlayerActivityTest() {
        super(PlayerActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
    }


    @SmallTest public void testPreconditions(){
        assertNotNull(activity);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
