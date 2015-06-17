package br.com.sevenbeats.core.playlist;

import java.util.List;

import br.com.sevenbeats.core.song.Song;

/**
 * Created by diogojayme on 6/15/15.
 */
public class Playlist {

    private String id;
    private String nome;
    private String url;
    private int qtdMusicas;
    private List<Song> musicas;

    public Playlist(){

    }

    public int getQtdMusicas() {
        return qtdMusicas;
    }

    public void setQtdMusicas(int qtdMusicas) {
        this.qtdMusicas = qtdMusicas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public List<Song> getSongs() {
        return musicas;
    }

    public void setSongs(List<Song> songs) {
        this.musicas = songs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
