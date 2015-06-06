package br.com.sevenbeats.core.song;

/**
 * Created by diogojayme on 6/5/15.
 */
public interface SongDAO{
     Object getSongs();
     Object getSongById(String id);
     Object getSongByName(String songName);
}
