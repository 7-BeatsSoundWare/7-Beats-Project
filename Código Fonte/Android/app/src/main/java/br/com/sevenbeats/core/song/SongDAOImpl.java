package br.com.sevenbeats.core.song;

import br.com.sevenbeats.utils.internet.http.Connection;
import br.com.sevenbeats.utils.internet.gson.Parse;

/**
 * Created by diogojayme on 6/5/15.
 */
public class SongDAOImpl implements SongDAO {

    private Parse parse;
    private Connection connection;

    public SongDAOImpl(){
        parse = new Parse();
        connection = new Connection();
    }

    /**
     * retorna uma música
     * @param id
     * */
    @Override
    public Object getSongById(String id) {
        Object stream = connection.getStreamContent("http://69.28.84.155:8080/7beats/rest/musicas/id/" + id, null);
        Object object = stream;

        if(stream instanceof String){
            object = parse.toObject((String) stream, Parse.SONG);
        }

        return  object;
    }

    /**
     * Retorna uma música
     * @param name
     * */
    @Override
    public Object getSongByName(String name) {
        Object stream = connection.getStreamContent("69.28.84.155:8080/7beats/rest/musicas/nome/" + name, null);
        Object object = stream;

        if(stream instanceof String){
            object = parse.toObject((String) stream, Parse.SONG);
        }

        return  object;
    }

    /**
     * retorna uma lista de músicas
     * */
    @Override
    public Object getSongs() {
        Object stream = connection.getStreamContent("http://69.28.84.155:8080/7beats/rest/musicas/", null);
        Object object = stream;

        if(stream instanceof String){
            object = parse.toObject((String) stream, Parse.SONGLIST);
        }

        return object;
    }
}
