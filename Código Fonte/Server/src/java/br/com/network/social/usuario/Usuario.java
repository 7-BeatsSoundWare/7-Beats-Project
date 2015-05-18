
package br.com.network.social.usuario;

import br.com.network.streaming.playlist.PlayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author Augusto César
 */
public class Usuario {
    private int id;
    private String username;
    private String nome;
    private String senhaMD5;
    private String urlImgPerfil;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoLogin;
    
    private List<PlayList> playLists;

    // Getters and Setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenhaMD5() {
        return senhaMD5;
    }

    public void setSenhaMD5(String senhaMD5) {
        this.senhaMD5 = senhaMD5;
    }

    public String getUrlImgPerfil() {
        return urlImgPerfil;
    }

    public void setUrlImgPerfil(String urlImgPerfil) {
        this.urlImgPerfil = urlImgPerfil;
    }

    public Date getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Date ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public List<PlayList> getPlayLists() {
        return playLists;
    }

    public void setPlayLists(List<PlayList> playLists) {
        this.playLists = playLists;
    }
}
