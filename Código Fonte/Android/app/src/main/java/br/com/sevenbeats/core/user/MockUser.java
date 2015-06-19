package br.com.sevenbeats.core.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by diogojayme on 6/15/15.
 */
public class MockUser {

    public static MockUser user;

    private int id;
    private String nome;
    private boolean skip;
    private String username;
    private boolean premium;
    private String senha;
    private String senhaMD5;
    private boolean autenticado;

    public MockUser(){
        this.id = 1;
        this.nome = "agusto cesar";
        this.senha = "adminadmin";
        this.username = "augustoccesar";
        this.senhaMD5 = md5(senha);
        this.autenticado = false;
        user = this;
    }


    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public MockUser getInstance(){
        return user == null ? new MockUser() : user;
    }

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

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
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

    public String getPassword() {
        return senha;
    }

    public void setPassword(String password) {
        this.senha = password;
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
