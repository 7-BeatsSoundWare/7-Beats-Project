package br.com.network.streaming.playlist;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Augusto César
 */
public class PlayListUtil {
    public static PlayList readPlayListFromResultSet(ResultSet rs) throws SQLException{
        PlayList playList = new PlayList();
        
        playList.setId(rs.getInt("id"));
        playList.setNome(rs.getString("nome"));
        playList.setCriadoEm(rs.getDate("criado_em"));
        playList.setIdCriador(rs.getInt("id_criador"));
        
        return playList;
    }
}
