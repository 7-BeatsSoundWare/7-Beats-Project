package br.com.sevenbeats.presentation.auth.register;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import br.com.sevenbeats.core.user.MockUser;
import br.com.sevenbeats.presentation.auth.login.LoginConstants;
import br.com.sevenbeats.utils.mvc.base.SuperController;
import br.com.sevenbeats.utils.service.ServiceManager;

/**
 * Created by diogojayme on 6/15/15.
 */
public class RegisterController extends SuperController {

    private Handler backgroundHandler;
    private Context context;

    public RegisterController(Context context) {
        super(context);
        this.context = context;
        HandlerThread secondaryThread = new HandlerThread("Background Handler");
        secondaryThread.start();
        this.backgroundHandler = new Handler(secondaryThread.getLooper());
    }

    @Override
    public void onRequest(final String method, final Object... params) {
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    Object obj = null;

                    if(method.equals(LoginConstants.METHOD_ON_LOGIN)) {
                        MockUser user = (MockUser) params[0];
                        obj = ServiceManager.getInstance().build().getUserService().auth(user.getUsername(), user.getPassword());
                    }

                    onResult(method, obj != null);
                }catch (Exception e){
                    e.printStackTrace();
                    onError(method);
                }
            }
        });
    }
}
