
package br.com.network.social.usuario;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Augusto César
 */
public class UsuarioUtil {
    public static Usuario readUsuarioFromResultSet(ResultSet rs, boolean withSenha) throws SQLException{
        Usuario usuario = new Usuario();
        
        usuario.setId(rs.getInt("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setNome(rs.getString("nome"));
        if(withSenha) usuario.setSenhaMD5(rs.getString("senha_MD5"));
        usuario.setUrlImgPerfil(rs.getString("url_img_perfil"));
        usuario.setUltimoLogin(rs.getDate("ultimo_login"));
        
        return usuario;
    }
}
