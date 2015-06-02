package br.com.sevenbeats.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

import br.com.sevenbeats.mvc.interfaces.model.GenericModel;
import br.com.sevenbeats.objects.Album;
import br.com.sevenbeats.objects.Song;


public class ObjectParse implements Response{

    GenericModel model;

    /**
     *
     * Esse objeto foi criado com intuito de separar a camada
     * de MVC da camada de DOMINIO ou NEGOCIO, assim sendo,
     * uma alteração na camada de negocio não muda o MVC
     * e vice versa. O model precisa de um listener para
     * comunicação entre camada de Domain e MVC
     *
     * */
    public ObjectParse(GenericModel model){
        if(model == null){
            throw new NullPointerException("Domain need model != null");
        }

        this.model = model;
    }

    /*
    * OBJETO SONG
    * */
    public final static int SONG = 1;

    /*
    * Objeto ALBUM
    * */
    public final static int ALBUM = 2;

    private String gsonParseErrorMessage = "Cannot parse gson type =";
    /**
     * Faz o parse de um json específico e converte no objeto escolhido
     * @param json o conteúdo json em uma string
     * @param objectType o tipo do objeto a ser convertido
     * @return  retorna o json convertido no objeto ou uma
     * mensagem de erro caso um erro de parse ocorra
     *
     * */
    public Object convertJsonToObject(final String json, final int objectType) {
        Type collectionType = null;
        Object data;

        switch (objectType) {
            case SONG:
                collectionType = new TypeToken<Collection<Song>>(){}.getType();
                break;
            case ALBUM:
                collectionType = new TypeToken<Album>(){}.getType();
                break;
        }

        try {
            data = new Gson().fromJson(json, collectionType);
            onResponse(data, true);
        }catch (Exception e){
            data = gsonParseErrorMessage + objectType;
            onResponse(data, false);
        }

        return data;
    }

    /**
     * Domain envia uma mensagem de sucesso ou falha
     * @param response em caso do model precisar do objeto na resposta
     * @param success mensagem de sucesso ou falha em caso do parse falhar
     *
     * */
    @Override
    public void onResponse(Object response, boolean success) {
        if(success){
            model.onSuccess(response);
        }else{
            model.onFailed(response);
        }
    }

}
