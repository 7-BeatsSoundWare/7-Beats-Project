package br.com.sevenbeats.core.playlist;

import java.util.List;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by diogojayme on 6/5/15.
 */
public interface PlaylistService {

    @GET("/rest/usuarios/{idUser}/playlists")
    List<Playlist> getListPlaylistByIdUser(@Path("idUser") String idUser);

    @GET("/rest/playlist/playlist/{name}/user/id/{idUser}")
    boolean createPlaylist(@Path("name") String name, @Path("idUser") String idUser);

    @GET("/rest/playlists/id/{idPlaylist}/song/{idSong}/user/{idUser}")
    boolean addSongPlaylist (@Path("idPlaylist") String idPlaylist, @Path("idSong") String idSong, @Path("idUser") String idUser);

    @GET("/rest/playlists/{idPlaylist}")
    Playlist getPlaylistById(@Path("idPlaylist") String idPlaylist);

    @GET("/rest/playlists/{idPlaylist}/w_musicas")
    Playlist getPlaylistByIdWMusicas(@Path("idPlaylist") String idPlaylist);

    @PUT("/rest/playlists/{idPlaylist}/edit/{name}")
    Playlist renamePlaylist(@Path("idPlaylist") String idPlaylist, @Path("name")  String name);

    @DELETE("/rest/playlists/{idPlaylist}/user/{idUser}")
    Playlist removePlaylist(@Path("idPlaylist") String idPlaylist, @Path("idUser")  String idUser);

    @DELETE("/rest/playlists/id/{idPlaylist}/song/{idSong}/user/{idUser}")
    boolean removeSongFromPlaylist(@Path("idPlaylist") String idPlaylist, @Path("idSong") String idSong, @Path("idUser") String idUser);

}
