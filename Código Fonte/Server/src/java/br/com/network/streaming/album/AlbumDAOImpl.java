package br.com.network.streaming.album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.network.streaming.musica.Musica;
import br.com.network.streaming.musica.MusicaUtil;
import br.com.network.system.util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de implementacao dos metodos de AlbumDAO.
 *
 * @author Augusto Cesar
 *
 */
public class AlbumDAOImpl implements AlbumDAO {

    private Connection con = null;

    public AlbumDAOImpl() throws ClassNotFoundException {
        this.con = new ConnectionUtil().getConnection();
    }

    @Override
    public List<Album> getAlbuns() {
        List<Album> albuns = new ArrayList<>();
        try {
            PreparedStatement stmt = this.con.prepareCall(AlbumSQLUtil.SQL_GET_ALBUNS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Album album = AlbumUtil.readAlbumFromResultSet(rs);
                albuns.add(album);
            }
            this.con.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return albuns;
    }

    @Override
    public Album getAlbumById(int id) {
        Album album = new Album();
        List<Musica> listaMusicas = new ArrayList<>();

        try {
            String sqlAlbum = "SELECT * FROM albuns WHERE id = " + id;
            String sqlMusicas = "SELECT * FROM musicas WHERE id_album = " + id;

            PreparedStatement stmtAlbum = this.con.prepareStatement(sqlAlbum);
            PreparedStatement stmtMusicas = this.con.prepareStatement(sqlMusicas);

            ResultSet rsAlbum = stmtAlbum.executeQuery();
            ResultSet rsMusicas = stmtMusicas.executeQuery();

            while (rsAlbum.next()) {
                album = AlbumUtil.readAlbumFromResultSet(rsAlbum);
            }

            while (rsMusicas.next()) {
                Musica musica = MusicaUtil.readMusicaFromResultSet(rsMusicas);
                listaMusicas.add(musica);
            }
            album.setMusicas(listaMusicas);

            this.con.close();
            return album;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Album> getAlbunsByLikeNome(String nome) {
        List<Album> listaAlbuns = new ArrayList<>();
        try {
            String sqlAlbuns = "SELECT * FROM albuns WHERE nome LIKE '%" + nome + "%'";
            PreparedStatement stmtAlbuns = this.con.prepareStatement(sqlAlbuns);

            ResultSet rsAlbuns = stmtAlbuns.executeQuery();
            while (rsAlbuns.next()) {
                Album album = AlbumUtil.readAlbumFromResultSet(rsAlbuns);
                List<Musica> listaMusicas = new ArrayList<>();

                String sqlMusicas = "SELECT * FROM musicas WHERE id_album = " + album.getId();
                PreparedStatement stmtMusicas = this.con.prepareStatement(sqlMusicas);

                ResultSet rsMusicas = stmtMusicas.executeQuery();
                while (rsMusicas.next()) {
                    Musica musica = MusicaUtil.readMusicaFromResultSet(rsMusicas);
                    listaMusicas.add(musica);
                }
                album.setMusicas(listaMusicas);

                listaAlbuns.add(album);
            }

            this.con.close();
            return listaAlbuns;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public List<Album> getAlbunsByLikeAutor(String autor) {
        List<Album> listaAlbuns = new ArrayList<>();
        try {
            String sqlAlbuns = "SELECT * FROM albuns WHERE autor LIKE '%" + autor + "%'";
            PreparedStatement stmtAlbuns = this.con.prepareStatement(sqlAlbuns);

            ResultSet rsAlbuns = stmtAlbuns.executeQuery();
            while (rsAlbuns.next()) {
                Album album = AlbumUtil.readAlbumFromResultSet(rsAlbuns);
                ArrayList<Musica> listaMusicas = new ArrayList<>();

                String sqlMusicas = "SELECT * FROM musicas WHERE id_album = " + album.getId();
                PreparedStatement stmtMusicas = this.con.prepareStatement(sqlMusicas);

                ResultSet rsMusicas = stmtMusicas.executeQuery();
                while (rsMusicas.next()) {
                    Musica musica = MusicaUtil.readMusicaFromResultSet(rsMusicas);
                    listaMusicas.add(musica);
                }
                album.setMusicas(listaMusicas);

                listaAlbuns.add(album);
            }

            this.con.close();
            return listaAlbuns;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public List<Album> getAlbunsByAnoLancamento(int ano) {
        List<Album> listaAlbuns = new ArrayList<>();
        try {
            String sqlAlbuns = "SELECT * FROM albuns WHERE ano_lancamento = " + ano;
            PreparedStatement stmtAlbuns = this.con.prepareStatement(sqlAlbuns);

            ResultSet rsAlbuns = stmtAlbuns.executeQuery();
            while (rsAlbuns.next()) {
                Album album = AlbumUtil.readAlbumFromResultSet(rsAlbuns);
                ArrayList<Musica> listaMusicas = new ArrayList<>();

                String sqlMusicas = "SELECT * FROM musicas WHERE id_album = " + album.getId();
                PreparedStatement stmtMusicas = this.con.prepareStatement(sqlMusicas);

                ResultSet rsMusicas = stmtMusicas.executeQuery();
                while (rsMusicas.next()) {
                    Musica musica = MusicaUtil.readMusicaFromResultSet(rsMusicas);
                    listaMusicas.add(musica);
                }
                album.setMusicas(listaMusicas);

                listaAlbuns.add(album);
            }

            this.con.close();
            return listaAlbuns;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

}
