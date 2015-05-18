package br.com.network.streaming.musica;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MusicaUtil {

    public static Musica readMusicaFromResultSet(ResultSet rs) throws SQLException {
        Musica musica = new Musica();
        musica.setId(rs.getInt("id"));
        musica.setAnoLancamento(rs.getInt("ano_lancamento"));
        musica.setNome(rs.getString("nome"));
        musica.setUrl(rs.getString("url"));
        return musica;
    }
}
