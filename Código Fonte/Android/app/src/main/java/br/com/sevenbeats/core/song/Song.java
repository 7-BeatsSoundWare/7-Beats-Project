package br.com.sevenbeats.core.song;


import android.os.Parcel;
import android.os.Parcelable;

import br.com.sevenbeats.core.album.Album;

/**
 * Created by diogojayme on 5/7/15.
 */
public class Song implements Parcelable{
    private int id;
    private String url;
    private String nome;
    private String artist;
    private String anoLancamento;

    private Album album;

    public Song(int id, String url, String nome, String artist, Album album){
        this.id = id;
        this.url = url;
        this.nome = nome;
        this.album = album;
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
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

    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {

        @Override
        public Song createFromParcel(Parcel parcel) {
            return new Song(parcel);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeString(url);
        parcel.writeString(artist);
        parcel.writeString(anoLancamento);
        parcel.writeParcelable(album, flags);
    }

    private Song(Parcel parcel) {
        this.id = parcel.readInt();
        this.nome = parcel.readString();
        this.url = parcel.readString();
        this.artist = parcel.readString();
        this.anoLancamento = parcel.readString();
        this.album = parcel.readParcelable(Album.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
