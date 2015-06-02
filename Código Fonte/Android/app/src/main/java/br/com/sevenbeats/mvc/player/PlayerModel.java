package br.com.sevenbeats.mvc.player;

import br.com.sevenbeats.mvc.interfaces.model.GenericModel;
import br.com.sevenbeats.utils.MvcAnnotations;
import br.com.sevenbeats.domain.Connection;
import br.com.sevenbeats.domain.ObjectParse;

/**
 * Created by diogojayme on 5/31/15.
 */
@MvcAnnotations("MODEL") public class PlayerModel implements GenericModel {
    PlayerController mPlayerController;

    /**
     * Recebe o listener de quem chamou esse metodo
     * Executa em uma thread secundária
     *
     * */
    public PlayerModel(PlayerController playerController){
        //TODO somente um controller pode instanciar essa classe
        if(playerController == null){throw new NullPointerException("Controller == null");}
        mPlayerController = playerController;
        onPreparing();
    }

    /**
     * Busca dados do servidor atualiza o controller e envia
     * os dados para a view quando terminados. Não necessita
     * retorno, o broadcast receiver do metodo enviará os dados
     * para o destinatário
     *
     * */
    public void getAlbum(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                @SuppressWarnings("unchecked")
                ObjectParse parse = new ObjectParse(PlayerModel.this);
                parse.convertJsonToObject(Connection.getStreamContent("http://69.28.84.155:8080/7beats/rest/albuns/id/1", null), ObjectParse.ALBUM);
            }
        }).start();
    }


    public void getSongs(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                @SuppressWarnings("unchecked")
                ObjectParse parse = new ObjectParse(PlayerModel.this);
                parse.convertJsonToObject(Connection.getStreamContent("http://69.28.84.155:8080/7beats/rest/musicas", null), ObjectParse.SONG);
            }
        }).start();
    }

    @Override public void onPreparing(){
        mPlayerController.setError(false);
        mPlayerController.setViewPrepared(false);
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
        mPlayerController.setError(false);
        mPlayerController.setViewPrepared(true);
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
        mPlayerController.setError(true);
        mPlayerController.setViewPrepared(false);
    }

    @Override
    public void onModelDestroy() {
        mPlayerController = null;
    }
}
