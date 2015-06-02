package br.com.sevenbeats.mvc.player;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import br.com.sevenbeats.mvc.interfaces.Invoker;
import br.com.sevenbeats.mvc.interfaces.controller.GenericController;
import br.com.sevenbeats.objects.Response;
import br.com.sevenbeats.utils.annotation.MvcPattern;

@MvcPattern("CONTROLLER") public class PlayerController implements GenericController {
    private Context context;
    private Handler mainHandler;
    private PlayerModel playerModel;
    private PlayerActivity activity;
    public PlayerController(Context context){
        this.mainHandler = new Handler(context.getMainLooper());
        this.context = context;
        this.activity = (PlayerActivity) context;
        this.playerModel = new PlayerModel(this);
        Invoker.invoke(activity,PlayerActivity.sOnView, true, false);
    }

    @Override
    public void request(String methodName){
        Log.d("MVC", "Controller calling >>>" + methodName);
        this.playerModel.executeRequestedMethod(methodName);
    }

    @Override
    public void onRequestCompleted(Object result) {
        //utilizar o header do reponse para os parametros
        if(result instanceof Response){
            final Response response = (Response) result;
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Invoker.invoke(activity, response.getMethodCaller(), response.getHeader().getData(), response.getHeader().hasError());
                }
            });

            Log.d("MVC", "Controller sending message to >>>" + response.getMethodCaller());
        }
    }
}
