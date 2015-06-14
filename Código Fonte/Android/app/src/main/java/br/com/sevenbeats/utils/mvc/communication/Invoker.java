package br.com.sevenbeats.utils.mvc.communication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by diogojayme on 6/5/15.
 */
public class Invoker {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void invoke(Activity activity, String methodName, Object... params){
        Method method;
        try {
            if(params != null && params.length != 0){
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
            }else if(e instanceof InvocationTargetException){
            }else {
            }

            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void invoke(Fragment fragment, String methodName, Object... params){
        Method method;
        try {
            if(params != null && params.length != 0){
                Class[] classes = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    classes[i] = (Class) params[i].getClass();
                }
                method = fragment.getClass().getMethod(methodName, classes);
                method.setAccessible(true);
                method.invoke(fragment, params);
            }else{
                method = fragment.getClass().getMethod(methodName);
                method.setAccessible(true);
                method.invoke(fragment);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            if(e instanceof NoSuchMethodException){
            }else if(e instanceof InvocationTargetException){
            }else {
            }

            e.printStackTrace();
        }
    }

}
