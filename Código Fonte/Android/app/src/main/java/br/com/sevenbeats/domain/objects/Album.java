package br.com.sevenbeats.domain.objects;

import java.util.ArrayList;


public class Album {
    int id;
    String autor;
    String anoLancamento;
    ArrayList<Song> musicas;

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
