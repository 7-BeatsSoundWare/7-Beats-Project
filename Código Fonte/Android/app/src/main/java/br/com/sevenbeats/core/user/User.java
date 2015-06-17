package br.com.sevenbeats.core.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by diogojayme on 6/15/15.
 */
public class User {
    private int id;
    private String nome;
    private String username;
    private boolean premium;
    private String senhaMD5;
    private Date ultimoLogin;
    private String urlImgPerfil;
    private boolean autenticado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
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

    public String md5(String s) {
        String md5;

        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            md5 = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            md5 = null;
        }

        return md5;
    }
}
