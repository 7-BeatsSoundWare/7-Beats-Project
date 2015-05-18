package br.com.sevenbeats.mvc.model;

/**
 * Created by diogojayme on 5/7/15.
 */
public class Song {
    private String url;
    private String name;
    private String artist;

    public Song(String url, String name, String artist){
        this.url = url;
        this.name = name;
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
