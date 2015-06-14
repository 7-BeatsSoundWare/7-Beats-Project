package br.com.sevenbeats.core.song;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by diogojayme on 6/5/15.
 */
public interface SongService {
     @GET("/rest/musicas")
     ArrayList<Song> listSongs();

     @GET("/rest/musicas/{idSong}")
     Song song(@Path("idSong") String idSong);

     @GET("/rest/musicas/{idSong}/w_album")
     Song songWAlbum(@Path("idSong") String idSong);
}
