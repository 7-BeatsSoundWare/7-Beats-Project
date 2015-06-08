package br.com.sevenbeats;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.TextView;

import br.com.sevenbeats.presentation.player.PlayerActivity;

/**
 * Created by diogojayme on 6/8/15.
 */
public class PlayerActivityTest extends ActivityInstrumentationTestCase2<PlayerActivity> {

    PlayerActivity activity;

    public PlayerActivityTest() {
        super(PlayerActivity.class);
        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
        testViews();
    }

    @SmallTest
    public void testViews(){
        TextView textView = (TextView) activity.findViewById(R.id.song_name);
        assertNull(textView);
    }
}
