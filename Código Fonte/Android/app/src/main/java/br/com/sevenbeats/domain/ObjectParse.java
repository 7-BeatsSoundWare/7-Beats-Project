package br.com.sevenbeats.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

import br.com.sevenbeats.mvc.interfaces.model.GenericModel;
import br.com.sevenbeats.objects.Album;
import br.com.sevenbeats.objects.ResponseHeader;
import br.com.sevenbeats.objects.Song;


public class ObjectParse{

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
    public ObjectParse(){
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
    public ResponseHeader convertJsonToObject(final String json, final int objectType) {
        Type collectionType = null;
        Object data;
        ResponseHeader header;

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
            header = new ResponseHeader(data, false);
        }catch (Exception e){
            data = gsonParseErrorMessage + objectType;
            header = new ResponseHeader(data, true);
        }

        return header;
    }

}
