package br.com.sevenbeats.core.album;

import java.util.ArrayList;

import br.com.sevenbeats.core.song.Song;


public class Album {
    int id;
    String autor;
    String imageUrl;
    String anoLancamento;
    ArrayList<Song> musicas;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public ArrayList<Song> getMusicas() {
        return musicas;
    }

    public void setMusicas(ArrayList<Song> musicas) {
        this.musicas = musicas;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }


}
