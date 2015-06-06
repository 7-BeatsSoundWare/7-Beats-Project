package br.com.sevenbeats.utils.mvc.communication;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.sevenbeats.presentation.player.PlayerControl;
import br.com.sevenbeats.utils.internet.exception.Constants;

/**
 * Created by diogojayme on 6/5/15.
 */
public class Invoker {

    public static void invoke(Activity activity, String methodName, Object... params){
        Method method;
        try {
            if(params != null){
                Class[] classes = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    classes[i] = (Class) params[i].getClass();
                }
                method = activity.getClass().getMethod(methodName, classes);
                method.setAccessible(true);
                method.invoke(activity, params);
            }else{
                method = activity.getClass().getMethod(methodName);
                method.setAccessible(true);
                method.invoke(activity);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            if(e instanceof NoSuchMethodException){
                Log.d(PlayerControl.DEBUG_TAG, Constants.EXCEPTION_1 + methodName);
            }else if(e instanceof InvocationTargetException){
                Log.d(PlayerControl.DEBUG_TAG, Constants.EXCEPTION_2 + activity.getClass().getName() + " metodo >>>" + methodName);
            }else {
                Log.d(PlayerControl.DEBUG_TAG, Constants.EXCEPTION_3 + methodName);
            }

            e.printStackTrace();
        }
    }
}
