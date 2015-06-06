package br.com.sevenbeats.core.album;

import br.com.sevenbeats.utils.internet.http.Connection;
import br.com.sevenbeats.utils.internet.gson.Parse;

/**
 * Created by diogojayme on 6/5/15.
 */
public class AlbumDAOImpl implements AlbumDAO{
    private Parse parse;
    private Connection connection;

    public AlbumDAOImpl(){
         parse = new Parse();
         connection = new Connection();
    }

    @Override
    public Object getAlbumById(String id) {
        Object stream = connection.getStreamContent("http://69.28.84.155:8080/7beats/rest/albuns/id/" + id, null);
        Object object = stream;

        if(stream instanceof String){
            object = parse.toObject((String) stream, Parse.ALBUM);
        }

        return object;
    }

    @Override
    public Object getAlbumByName(String album) {
        Object stream = connection.getStreamContent("69.28.84.155:8080/7beats/rest/albuns/nome/" + album, null);
        Object object = stream;

        if(stream instanceof String){
            object = parse.toObject((String) stream, Parse.ALBUM);
        }

        return object;
    }

    @Override
    public Object getAlbumByArtist(String artist) {
        Object stream = connection.getStreamContent("69.28.84.155:8080/7beats/rest/albuns/autor/" + artist, null);
        Object object = stream;

        if(stream instanceof String){
            object = parse.toObject((String) stream, Parse.ALBUM);
        }

        return object;

    }

}
