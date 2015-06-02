package br.com.sevenbeats.mvc.player;

import android.content.Context;
import android.os.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.sevenbeats.mvc.interfaces.controller.GenericController;
import br.com.sevenbeats.utils.MvcAnnotations;

/**
 * Created by diogojayme on 5/31/15.
 */
@MvcAnnotations("CONTROLLER") public class PlayerController implements GenericController {

    private Context context;
    /**
     * O controller mantém uma flag de ocorrencia de erros
     * */

    private boolean mError;
    /**
     * O Controller mantém o estado de execução do processo
     * true se o controle terminou seu processamento e falso do contrário
     *
     * */

    private boolean mPrepared;


    /**
     * O listener da activity que instanciou esse controller
     *
     * */

    private Handler mMainHandler;


    PlayerModel playerModel;
    /**
     * Construtor recebe o listener só para economizar o metodo setListener
     *
     * */

    public PlayerController(Context context){
        this.context =context;
        mMainHandler = new Handler(context.getMainLooper());
        playerModel = new PlayerModel(this);
        setError(false);
        setViewPrepared(false);
        notifyMethod("onView");
    }

    /**
     * O metodo que executa a camada de lógica (MODEL)
     *
     * */

    public void getAlbum(){
        notifyMethod("getAlbum");
        playerModel.getAlbum();
    }

    public void getSongs(){
        notifyMethod("getSongs");
        playerModel.getSongs();
    }
    /**
     * @param prepared uma resposta dizendo que o model terminou o processamento
     *
     *
     * */
    @Override
    public void setViewPrepared(boolean prepared) {
        mPrepared = prepared;
    }

    /**
     * @return a view está preparada pra ser atualizada
     *
     * */

    @Override
    public boolean isViewPrepared() {
        return mPrepared;
    }


    @Override
    public void setError(boolean error) {
        mError = error;
    }

    @Override
    public boolean hasError() {
        return mError;
    }

    /**
     * Esse metodo funciona como canal de comunicação entre controle e view
     * Envia uma mensagem para a VIEW se atualizar.
     * Roda na MainThread
     *
     * */
    @Override
    public void notifyMethod(final String methodName) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Method method = null;
                    switch (methodName){
                        case "onView":
                            method = context.getClass().getMethod(methodName, Boolean.class, Boolean.class);
                            method.setAccessible(true);
                            method.invoke(context.getClass(), isViewPrepared(), hasError());
                            break;
                        case "getAlbum":
                            method = context.getClass().getMethod(methodName, String.class);
                            method.setAccessible(true);
                            method.invoke(context.getClass(), "batatas", hasError());
                            break;
                        case "getSongs":
                            method = context.getClass().getMethod(methodName, String.class);
                            method.setAccessible(true);
                            method.invoke(context.getClass(), "batatas", hasError());
                            break;
                    }

                    System.out.println(method.getName());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onControllerDestroy() {
        mMainHandler = null;
    }


}
