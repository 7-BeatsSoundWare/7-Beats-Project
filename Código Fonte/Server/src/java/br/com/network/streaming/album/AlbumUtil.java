package br.com.network.streaming.album;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Metodos para facilitar a manipulacao da classe Album.
 *
 * @author Augusto Cesar
 *
 */
public class AlbumUtil {

    /**
     * Metodo que le os dados de um ResultSet que contenha dados de Album.
     *
     * @param rs Instancia de ResultSet que contem os dados de Album.
     * @return Instancia de Album preenchida com os dados do ResultSet.
     * @throws SQLException
     */
    public static Album readAlbumFromResultSet(ResultSet rs) throws SQLException {
        Album album = new Album();
        album.setId(rs.getInt("id"));
        album.setAnoLancamento(rs.getInt("ano_lancamento"));
        album.setAutor(rs.getString("autor"));
        album.setNome(rs.getString("nome"));
        album.setUrlImgCapa(rs.getString("url_img_capa"));
        return album;
    }
}
