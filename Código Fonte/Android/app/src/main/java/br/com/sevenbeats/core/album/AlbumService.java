package br.com.sevenbeats.core.album;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by diogojayme on 6/5/15.
 */
public interface AlbumService {
    @GET("/rest/albuns/nome/{albumName}")
    ArrayList<Album> listAlbums(@Path("albumName") String albumName);

    @GET("/rest/albuns/")
    ArrayList<Album> listAlbums();

    @GET("/rest/albuns/{idAlbum}/w_musicas")
    Album album(@Path("idAlbum") String idAlbum);
}
