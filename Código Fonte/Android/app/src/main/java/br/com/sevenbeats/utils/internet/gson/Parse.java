package br.com.sevenbeats.utils.internet.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

import br.com.sevenbeats.core.album.Album;
import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.utils.internet.http.ErrorResponse;


public class Parse {


    /**
     *
     * Esse objeto foi criado com intuito de separar a camada
     * de MVC da camada de DOMINIO ou NEGOCIO, assim sendo,
     * uma alteração na camada de negocio não muda o MVC
     * e vice versa. O model precisa de um listener para
     * comunicação entre camada de Domain e MVC
     *
     * */
    public Parse(){
    }

    public final static int SONG = 1;

    /*
    * Objeto ALBUM
    * */
    public final static int ALBUM = 2;

    /*
    * OBJETO SONG
    * */
    public final static int SONGLIST = 3;

    private String parseErrorMessage = "Cannot parse object ";

    /**
     * Faz o parse de um json específico e converte no objeto escolhido
     * @param json o conteúdo json em uma string
     * @param objectType o tipo do objeto a ser convertido
     * @return  retorna o json convertido no objeto ou uma
     * mensagem de erro caso um erro de parse ocorra
     *
     * */
    public Object toObject(final String json, final int objectType) {
        Type type = null;
        Object object;

        switch (objectType) {
            case SONG:
                type = new TypeToken<Song>() {}.getType();
                break;
            case ALBUM:
                type = new TypeToken<Album>() {}.getType();
                break;
            case SONGLIST:
                type = new TypeToken<Collection<Song>>() {}.getType();
                break;
        }

        try {
            object = new Gson().fromJson(json, type);
        } catch (Exception e) {
            object = new ErrorResponse(PARSE_OBJECT_ERROR, parseErrorMessage + objectType);
            e.printStackTrace();
        }

        return object;
    }

    private static final int PARSE_OBJECT_ERROR = 1000;
}
