
package br.com.network.streaming.playlist;

import br.com.network.streaming.musica.Musica;
import br.com.network.streaming.musica.MusicaSQLUtil;
import br.com.network.streaming.musica.MusicaUtil;
import br.com.network.system.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Augusto César
 */
public class PlayListDAOImpl implements PlayListDAO{
    private final Connection con;

    public PlayListDAOImpl() {
        this.con = new ConnectionUtil().getConnection();
    }
    
    @Override
    public List<PlayList> getPlayListsByIdCriador(int idCriador) {
        List<PlayList> listaPlayLists = new ArrayList<>();
        try{
            PreparedStatement stmt = this.con.prepareStatement(PlayListSQLUtil.SQL_GET_BY_ID_CRIADOR);
            stmt.setInt(1, idCriador);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                PlayList playList = PlayListUtil.readPlayListFromResultSet(rs); 
                listaPlayLists.add(playList);                
            }
            return listaPlayLists;
        }catch(SQLException e){
            System.err.println(e);
            return null;
        }        
    }

    @Override
    public PlayList getPlayListById(int id) {
        PlayList playlist = new PlayList();
        try{
            PreparedStatement stmt = this.con.prepareStatement(PlayListSQLUtil.SQL_GET_BY_ID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                playlist = PlayListUtil.readPlayListFromResultSet(rs);                
            }
            
            playlist.setMusicas(new ArrayList<Musica>());
            stmt = this.con.prepareStatement(MusicaSQLUtil.SQL_GET_BY_ID_PLAYLIST);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                Musica musica = MusicaUtil.readMusicaFromResultSet(rs);
                playlist.getMusicas().add(musica);
            }
            
            return playlist;
        }catch(SQLException e){
            System.err.println(e);
        }
        return null;
    }
    
}
