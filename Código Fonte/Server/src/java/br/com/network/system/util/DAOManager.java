package br.com.network.system.util;

import br.com.network.social.usuario.UsuarioDAO;
import br.com.network.social.usuario.UsuarioDAOImpl;
import br.com.network.streaming.musica.MusicaDAO;
import br.com.network.streaming.musica.MusicaDAOImpl;
import br.com.network.streaming.album.AlbumDAO;
import br.com.network.streaming.album.AlbumDAOImpl;
import br.com.network.streaming.playlist.PlayListDAO;
import br.com.network.streaming.playlist.PlayListDAOImpl;
import br.com.network.system.analytics.acesso.AcessoDAO;
import br.com.network.system.analytics.acesso.AcessoDAOImpl;

public class DAOManager {

    public static UsuarioDAO getUsuarioDAO() throws ClassNotFoundException {
        return new UsuarioDAOImpl();
    }
    
    public static MusicaDAO getMusicaDAO() throws ClassNotFoundException {
        return new MusicaDAOImpl();
    }
    
    public static PlayListDAO getPlayListDAO() throws ClassNotFoundException {
        return new PlayListDAOImpl();
    }

    public static AlbumDAO getAlbumDAO() throws ClassNotFoundException {
        return new AlbumDAOImpl();
    }
    
    public static AcessoDAO getAcessoDAO() throws ClassNotFoundException {
        return new AcessoDAOImpl();
    }
}
