package br.com.network.streaming.playlist;

import br.com.network.social.usuario.Usuario;
import br.com.network.streaming.musica.Musica;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Augusto César
 */
public class PlayList {
    private int id;
    private String nome;
    private Date criadoEm;
    private int idCriador;
    
    private Usuario criador;
    private List<Musica> musicas;

    // Getters and Setters
    
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

    public Date getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public int getIdCriador() {
        return idCriador;
    }

    public void setIdCriador(int idCriador) {
        this.idCriador = idCriador;
    }

    public Usuario getCriador() {
        return criador;
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }
}
