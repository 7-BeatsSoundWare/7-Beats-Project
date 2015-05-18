
package br.com.network.social.usuario;

import br.com.network.system.util.ConnectionUtil;
import br.com.network.system.util.Utils;
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
public class UsuarioDAOImpl implements UsuarioDAO{
    private final Connection con;

    public UsuarioDAOImpl() {
        this.con = new ConnectionUtil().getConnection();
    }
    
    @Override
    public Usuario getUsuarioById(int id) {
        Usuario usuario = new Usuario();
        
        try {
            PreparedStatement stmt = this.con.prepareStatement(UsuarioSQLUtil.SQL_GET_BY_ID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                usuario = UsuarioUtil.readUsuarioFromResultSet(rs, false);
            }
            
            return usuario;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public List<Usuario> getListaUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        
        try {
            PreparedStatement stmt = this.con.prepareStatement(UsuarioSQLUtil.SQL_GET_LISTA);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Usuario usuario = UsuarioUtil.readUsuarioFromResultSet(rs, false);
                listaUsuarios.add(usuario);
            }
            
            return listaUsuarios;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public List<Usuario> getUsuariosByLikeUsername(String username) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        
        try {
            PreparedStatement stmt = this.con.prepareStatement(UsuarioSQLUtil.SQL_GET_BY_LIKE_USERNAME);
            stmt.setString(1, "%" + username + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Usuario usuario = UsuarioUtil.readUsuarioFromResultSet(rs, false);
                listaUsuarios.add(usuario);
            }
            
            return listaUsuarios;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public List<Usuario> getUsuariosByLikeNome(String nome) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        
        try {
            PreparedStatement stmt = this.con.prepareStatement(UsuarioSQLUtil.SQL_GET_BY_LIKE_NOME);
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Usuario usuario = UsuarioUtil.readUsuarioFromResultSet(rs, false);
                listaUsuarios.add(usuario);
            }
            
            return listaUsuarios;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public Usuario doLogin(String username, String senha) {
        String senhaMD5 = Utils.toMD5(senha);
        try {
            PreparedStatement stmt = this.con.prepareStatement(UsuarioSQLUtil.SQL_GET_FOR_LOGIN);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Usuario usuario = UsuarioUtil.readUsuarioFromResultSet(rs, true);
                if(usuario.getSenhaMD5().equals(senhaMD5)){
                    usuario.setSenhaMD5(null);
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println(e);            
        }
        return null;
    }
    
}
