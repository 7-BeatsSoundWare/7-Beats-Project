package br.com.sevenbeats.mvc.interfaces;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by diogojayme on 6/2/15.
 */
public class Invoker {

    public static void invoke(Activity activity, String methodName, Object... args){
        Method method;
        try {
            Class[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classes[i] = (Class) args[i].getClass();
            }
            method = activity.getClass().getMethod(methodName, classes);
            method.setAccessible(true);
            method.invoke(activity, args);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
