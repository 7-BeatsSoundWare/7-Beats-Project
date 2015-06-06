package br.com.sevenbeats.core.album;

/**
 * Created by diogojayme on 6/5/15.
 */
public interface AlbumDAO{
    Object getAlbumById(String id);
    Object getAlbumByName(String name);
    Object getAlbumByArtist(String artist);
}
