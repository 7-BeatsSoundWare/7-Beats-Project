package br.com.network.streaming.album;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.network.streaming.musica.Musica;
import java.util.List;

/**
 * Classe de definição do modelo de Album.
 *
 * @author Augusto César
 *
 */
@JsonInclude(Include.NON_NULL)
public class Album {

    private int id;
    private String nome;
    private String autor;
    private int anoLancamento;
    private String urlImgCapa;

    private List<Musica> musicas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getUrlImgCapa() {
        return urlImgCapa;
    }

    public void setUrlImgCapa(String urlImgCapa) {
        this.urlImgCapa = urlImgCapa;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }
}
