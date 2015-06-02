package br.com.sevenbeats.mvc.player;

import android.util.Log;

import br.com.sevenbeats.domain.Connection;
import br.com.sevenbeats.domain.ObjectParse;
import br.com.sevenbeats.mvc.interfaces.model.GenericModel;
import br.com.sevenbeats.objects.Response;
import br.com.sevenbeats.objects.ResponseHeader;
import br.com.sevenbeats.utils.annotation.MvcPattern;

/**
 * Created by diogojayme on 5/31/15.
 */
@MvcPattern("MODEL") public class PlayerModel implements GenericModel {
    private PlayerController playerController;

    public PlayerModel(PlayerController playerController){
        if(playerController == null){
            throw new NullPointerException("Controller == null");
        }
        this.playerController = playerController;
    }

    @Override public void executeRequestedMethod(final String methodName){
        //executar em background
        Log.d("MVC", "Model executing work of " + methodName + " in background");
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (methodName){
                    case PlayerActivity.sOnAlbum:
                        requestAlbum(methodName);
                        break;
                    case PlayerActivity.sOnSong:
                        requestSong(methodName);
                        break;
                }
            }
        }).start();
    }

    public void requestSong(String methodName){
        ObjectParse parse = new ObjectParse();
        ResponseHeader header = parse.convertJsonToObject(Connection.getStreamContent("http://69.28.84.155:8080/7beats/rest/musicas", null), ObjectParse.SONG);
        Response response = new Response(methodName, header);
        setRequestedMethodComplete(response);
        Log.d("MVC", "Model Request for " + methodName + " finalized execution");
    }

    public void requestAlbum(String methodName){
        ObjectParse parse = new ObjectParse();
        ResponseHeader header = parse.convertJsonToObject(Connection.getStreamContent("http://69.28.84.155:8080/7beats/rest/albuns/id/1", null), ObjectParse.ALBUM);
        Response response = new Response(methodName, header);
        setRequestedMethodComplete(response);
        Log.d("MVC", "Model Request for " + methodName + " finalized execution");
    }

    @Override public void setRequestedMethodComplete(Object response){
        this.playerController.onRequestCompleted(response);
        Log.d("MVC", "Model sending response to controller");
    }
}
