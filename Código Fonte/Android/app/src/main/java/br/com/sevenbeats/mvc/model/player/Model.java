package br.com.sevenbeats.mvc.model.player;

import br.com.sevenbeats.mvc.model.GenericModel;
import br.com.sevenbeats.utils.SevenBeatsAnnotations;
import br.com.sevenbeats.domain.Connection;
import br.com.sevenbeats.domain.ObjectParse;
import br.com.sevenbeats.mvc.controller.player.Controller;

/**
 * Created by diogojayme on 5/31/15.
 */
@SevenBeatsAnnotations("MODEL") public class Model implements GenericModel {
    Controller mController;

    /**
     * Recebe o listener de quem chamou esse metodo
     * Executa em uma thread secundária
     *
     * */
    public Model(Controller controller){
        //TODO somente um controller pode instanciar essa classe
        if(controller == null){throw new NullPointerException("Controller == null");}
        mController = controller;
        onPreparing();
    }

    /**
     * Busca dados do servidor atualiza o controller e envia
     * os dados para a view quando terminados. Não necessita
     * retorno, o broadcast receiver do metodo enviará os dados
     * para o destinatário
     *
     * */
    public void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                @SuppressWarnings("unchecked")
                ObjectParse parse = new ObjectParse(Model.this);
                parse.convertJsonToObject(Connection.getStreamContent("http://69.28.84.155:8080/rest/musicas", null), ObjectParse.SONG);
                parse.convertJsonToObject(Connection.getStreamContent("http://69.28.84.155:8080/rest/albuns/id/1", null), ObjectParse.ALBUM);
            }
        }).start();
    }

    @Override public void onPreparing(){
        mController.setError(false);
        mController.setViewPrepared(false);
    }

    /**
     * setViewPrepare(true) sempre será verdadeiro pois o negocios só
     * retorna 'sucess' em caso de tudo ter ocorrido corretamente.
     *
     * @param response com o objeto contendo a resposta esperada
     * Nessa parte podemos enviar 'true' para atualizar a view
     *
     * */
    @Override public void onSuccess(Object response){
        mController.setError(false);
        mController.setViewPrepared(true);
    }

    /**
     * setViewPrepare(false) sempre será falso pois a camada de negocios sempre irá
     * retornar 'failed' em caso de algum erro e o
     *
     * @param  response com a resposta contendo o erro
     * Nesse contexto podemos enviar 'false' para atualizar a view
     *
     * */
    @Override public void onFailed(Object response){
        mController.setError(true);
        mController.setViewPrepared(false);
    }

    @Override
    public void onModelDestroy() {
        mController = null;
    }
}
