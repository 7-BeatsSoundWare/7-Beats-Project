package br.com.sevenbeats.mvc.controller.player;

import android.content.Context;
import android.os.Handler;

import br.com.sevenbeats.mvc.controller.GenericController;
import br.com.sevenbeats.utils.SevenBeatsAnnotations;
import br.com.sevenbeats.mvc.model.player.Model;

/**
 * Created by diogojayme on 5/31/15.
 */
@SevenBeatsAnnotations("CONTROLLER") public class Controller implements GenericController {

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

    private PreparedControllerListener mListener;

    private Handler mMainHandler;

    /**
     * Construtor recebe o listener só para economizar o metodo setListener
     *
     * */

    public Controller(Context context, PreparedControllerListener listener){
        mMainHandler = new Handler(context.getMainLooper());
        mListener = listener;
    }

    /**
     * O metodo que executa a camada de lógica (MODEL)
     *
     * */

    public void getData(){
        Model model = new Model(this);
        model.getData();
    }

    /**
     * @param prepared uma resposta dizendo que o model terminou o processamento
     *
     *
     * */

    @Override
    public void setViewPrepared(boolean prepared) {
        mPrepared = prepared;
        notifyPrepared();
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
    public void notifyPrepared() {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onViewPrepared(isViewPrepared(), hasError());
            }
        });
    }

    @Override
    public void onControllerDestroy() {
        mMainHandler = null;
        mListener = null;
    }

    /**
     * A view deve implementar essa interface para poder receber as atualizações,
     * funciona como o mesmo conceito de Observer
     *
     * */

    public interface PreparedControllerListener{
        void onViewPrepared(boolean prepared, boolean hasError);
    }
}
