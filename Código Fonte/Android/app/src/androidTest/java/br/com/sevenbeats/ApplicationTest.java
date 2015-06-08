package br.com.sevenbeats;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by diogojayme on 6/8/15.
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        new PlayerActivityTest();
    }
}
