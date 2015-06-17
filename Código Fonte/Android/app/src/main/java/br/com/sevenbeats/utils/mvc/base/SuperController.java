package br.com.sevenbeats.utils.mvc.base;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import br.com.sevenbeats.utils.service.ServiceManager;
import br.com.sevenbeats.utils.mvc.communication.Invoker;
import br.com.sevenbeats.utils.mvc.interfaces.controller.Controller;

/**
 * Created by diogojayme on 6/12/15.
 */
public class SuperController implements Controller {

    Handler mainHandler;
    Fragment fragment;
    Activity activity;

    public SuperController(Context context, Fragment fragment){
        this.fragment = fragment;
        initHandler(context.getMainLooper());
    }

    public SuperController(Context context){
        this.activity = (Activity) context;
        initHandler(context.getMainLooper());
    }

    public void initHandler(Looper looper){
        this.mainHandler = new android.os.Handler(looper);
    }

    @Override
    @SuppressWarnings("unused")
    public void onRequest(String method, String... params) {}

//    @Override
    public void onRequest(String method, Object... params) {

    }

    @Override
    public void onResult(final String methodName, final Object result) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyView(methodName, result);
                mainHandler.removeCallbacks(this);
            }
        });
    }

    @Override
    public void onResult(final String methodName) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyView(methodName);
                mainHandler.removeCallbacks(this);
            }
        });
    }

    @Override
    public void onError(final String method) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyView(method);
                mainHandler.removeCallbacks(this);
            }
        });
    }

    @Override
    public void notifyView(String methodName, Object... params) {
        if(fragment == null){
            Invoker.invoke(activity, methodName, params);
        }else{
            Invoker.invoke(fragment, methodName, params);
        }
    }

    @Override
    public void finalizePendingWork() {
        ServiceManager.destroyInstance();
    }
}
