package br.com.network.system.util;

import br.com.network.streaming.album.Album;
import br.com.network.streaming.album.AlbumDAO;
import java.util.List;

public class Testes {

    public static void main(String[] args) throws ClassNotFoundException {
        AlbumDAO dao = DAOManager.getAlbumDAO();
        List<Album> lista = dao.getAlbunsByAnoLancamento(2011);
        
        for (Album album : lista) {
            System.out.println(album.getNome());
        }
    }
}
