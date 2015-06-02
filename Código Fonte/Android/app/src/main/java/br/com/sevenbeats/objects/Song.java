package br.com.sevenbeats.objects;

/**
 * Created by diogojayme on 5/7/15.
 */
public class Song {
    private int id;
    private String url;
    private String nome;
    private String artist;
    private String anoLancamento;

    public Song(int id, String url, String nome, String artist){
        this.id = id;
        this.url = url;
        this.nome = nome;
        this.artist = artist;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
