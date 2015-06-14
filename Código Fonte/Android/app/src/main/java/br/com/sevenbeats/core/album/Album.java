package br.com.sevenbeats.core.album;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import br.com.sevenbeats.core.song.Song;


public class Album  implements Parcelable {

    int id;
    String nome;
    String autor;
    String urlImgCapa;
    String anoLancamento;

    ArrayList<Song> musicas;

    public Album(){}

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public String getImageUrl() {
        return urlImgCapa;
    }

    public void setImageUrl(String imageUrl) {
        this.urlImgCapa = imageUrl;
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

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeString(autor);
        parcel.writeString(urlImgCapa);
        parcel.writeString(anoLancamento);
    }

    private Album(Parcel parcel) {
        this.id = parcel.readInt();
        this.nome = parcel.readString();
        this.autor = parcel.readString();
        this.urlImgCapa = parcel.readString();
        this.anoLancamento = parcel.readString();
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {

        @Override
        public Album createFromParcel(Parcel parcel) {
            return new Album(parcel);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
