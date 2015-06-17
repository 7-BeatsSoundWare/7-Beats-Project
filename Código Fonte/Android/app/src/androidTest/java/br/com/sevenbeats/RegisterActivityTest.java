package br.com.sevenbeats;

import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.runner.RunWith;

import br.com.sevenbeats.presentation.auth.register.RegisterActivity;

/**
 * Created by diogojayme on 6/8/15.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

    RegisterActivity activity;

    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
    }

    @SmallTest public void testPreconditions() throws Exception{
        assertNotNull(activity);
    }

    /**
     * Verifica se o nome possui tamanho valido
     * */
    @LargeTest public void tamanhoValidoNome()  throws Exception{
        assertTrue(activity.isValidLenght(""));
        assertTrue(activity.isValidLenght("bdf"));
        assertTrue((activity.isValidLenght("fd99")));
        assertTrue((activity.isValidLenght("1232asas")));
        assertTrue((activity.isValidLenght("@@@@@@@@")));
        assertTrue(activity.isValidLenght("diogojayme"));
        assertTrue(activity.isValidLenght("diogo1"));
    }

    /**
     * Testa o tamanho minimo e maximo do username
     * */
    @LargeTest public void tamanhoValidoUsername()  throws Exception{
        assertTrue(activity.isValidUsernameLenght("bdf"));
        assertTrue((activity.isValidUsernameLenght("fd99")));
        assertTrue(activity.isValidUsernameLenght(""));
        assertTrue((activity.isValidUsernameLenght("1232asas")));
        assertTrue((activity.isValidUsernameLenght("@@@@@@@@")));
        assertTrue(activity.isValidUsernameLenght("diogojayme"));
        assertTrue(activity.isValidUsernameLenght("diogo1"));
    }

    /**
     * testa se um texto contém caracteres especiais
     * */
    @LargeTest public void formatoValido()  throws Exception{
        assertTrue((activity.isValidNameFormat("aaaaaaa")));
        assertTrue((activity.isValidNameFormat("bbbb000")));
        assertTrue((activity.isValidNameFormat("a1")));
        assertTrue((activity.isValidNameFormat("_1123dsd")));
        assertTrue((activity.isValidNameFormat("@32sdsd")));
        assertTrue((activity.isValidNameFormat("1a1")));
        assertTrue((activity.isValidNameFormat("00001a1")));
        assertTrue((activity.isValidNameFormat("00001a1")));
        assertTrue((activity.isValidNameFormat("diogo@323")));
    }

    /**
     * testa se a senha contem tamanho valido
     * */
    @LargeTest public void senhatamanhoValida()  throws Exception{
        assertTrue(activity.isValidPassword(""));
        assertTrue(activity.isValidPassword("2323"));
        assertTrue(activity.isValidPassword("@as23sdsd"));
        assertTrue(activity.isValidPassword("@as###23sdsd"));
        assertTrue(activity.isValidPassword("1029390239203920as###23sdsd"));
        assertTrue(activity.isValidPassword("&&*@kjsdksjdks1029390239203920as###23sdsd"));
        assertTrue(activity.isValidPassword("238901"));
        assertTrue(activity.isValidPassword("kkkkkkk"));
        assertTrue(activity.isValidPassword("adminadmin"));
    }

    /**
     * Testa se as senhas correspondem
     * */
    @LargeTest public void senhasCorrespondem()  throws Exception{
        assertTrue(activity.matchPassword("aaa", "bbb"));
        assertTrue(activity.matchPassword("111","111"));
    }
}
