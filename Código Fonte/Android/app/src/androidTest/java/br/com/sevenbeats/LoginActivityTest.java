package br.com.sevenbeats;

import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.runner.RunWith;

import br.com.sevenbeats.presentation.auth.login.LoginActivity;

/**
 * Created by diogojayme on 6/8/15.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    LoginActivity activity;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
    }

    @SmallTest public void testPreconditions(){
        assertNotNull(activity);
    }

//    /**
//     * Verificar se um usuário sem permissão pode acessar o aplicativo
//     * */
//    volatile boolean wait;
//    @LargeTest public void testValidLogin() throws Exception{
//        final MockUser user = new MockUser();
//        user.setName("augustoccesar");
//        user.setPassword("adminadmin");
//
//        wait = true;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Object obj = ServiceManager.getInstance().build().getUserService().auth(user.getUsername(), user.getPassword());
//                wait = false;
//                assertNotNull(obj);
//            }
//        }).start();
//
//        try {
//            while (wait){
//                Thread.sleep(10000);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Verificar se um usuário sem permissão pode acessar o aplicativo
//     * */
//    @LargeTest public void testInvalidLogin() throws Exception{
//        final MockUser user = new MockUser();
//        user.setName("invalidusername");
//        user.setPassword("invalidPassword");
//        wait = true;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                wait = false;
//                Object obj = ServiceManager.getInstance().build().getUserService().auth(user.getUsername(), user.getPassword());
//                assertNull(obj);
//            }
//        }).start();
//
//        try {
//            while (wait) {
//                Thread.sleep(10000);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @LargeTest public void testPass1()  throws Exception{
        assertTrue(activity.isValidPassword("adminadmin"));
    }

    @LargeTest public void testPass2()  throws Exception{
        assertTrue((activity.isValidPassword("12345")));
    }

    @LargeTest public void testPass3()  throws Exception{
        assertTrue((activity.isValidPassword("abscdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz")));
    }

    @LargeTest public void testPass4()  throws Exception{
        assertTrue((activity.isValidPassword("")));
    }

    @LargeTest public void testLogin1()  throws Exception {
        assertTrue((activity.isValidUsernameLenght("augustoccesar")));
    }

    @LargeTest public void testLogin2()  throws Exception {
        assertTrue((activity.isValidUsernameLenght("login")));
    }

    @LargeTest public void testLogin3()  throws Exception {
        assertTrue((activity.isValidUsernameLenght("abcdefghijklmnopqrstuvwxyz")));
    }

    @LargeTest public void testLogin4()  throws Exception {
        assertTrue((activity.isValidUsernameLenght("")));
    }

    @LargeTest public void testLoginFormat5()  throws Exception {
        assertTrue((activity.isValidNameFormat("@123456")));
    }

    @LargeTest public void testLoginFormat6()  throws Exception {
        assertTrue((activity.isValidNameFormat("augustoccesar")));
    }

}

