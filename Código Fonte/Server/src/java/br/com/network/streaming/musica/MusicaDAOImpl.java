package br.com.network.streaming.musica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.network.system.util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;

public class MusicaDAOImpl implements MusicaDAO {

    Connection con = null;

    public MusicaDAOImpl() throws ClassNotFoundException {
        this.con = new ConnectionUtil().getConnection();
    }

    @Override
    public List<Musica> getMusicas(){
        List<Musica> musicas = new ArrayList<>();
        try{
            PreparedStatement stmt = this.con.prepareStatement(MusicaSQLUtil.SQL_GET_MUSICAS);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Musica musica = MusicaUtil.readMusicaFromResultSet(rs);
                musicas.add(musica);
            }
            this.con.close();
            return musicas;
        }catch(SQLException e){
            System.err.println(e);
            return null;
        }
    }
    
    @Override
    public Musica getMusicaById(int id) {
        Musica musica = new Musica();
        try {
            PreparedStatement stmt = this.con.prepareStatement(MusicaSQLUtil.SQL_GET_BY_ID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                musica = MusicaUtil.readMusicaFromResultSet(rs);
            }
            this.con.close();
            return musica;
        } catch (SQLException e) {
            System.err.print(e);
            return null;
        }
    }

    @Override
    public List<Musica> getMusicasByLikeNome(String nome) {
        // TODO
        return null;
    }

}
