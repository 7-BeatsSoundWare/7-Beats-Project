package br.com.network.system.analytics.acesso;

import br.com.network.system.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Augusto Cesar
 */
public class AcessoDAOImpl implements AcessoDAO{

    private Connection con;

    public AcessoDAOImpl() {
        this.con = new ConnectionUtil().getConnection();
    }
    
    @Override
    public void insertAcesso(Acesso acesso) {
        String sql = "INSERT INTO acessos (user_agent, ip, data_acesso, requested_url) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement stmt = this.con.prepareStatement(sql);
            stmt.setString(1, acesso.getUserAgent());
            stmt.setString(2, acesso.getIp());
            stmt.setDate(3, new java.sql.Date(acesso.getDataAcesso().getTime()));
            stmt.setString(4, acesso.getRequestedURL());
            stmt.execute();
            this.con.close();
        }catch(SQLException e){
            System.err.println(e);
        }        
    }    
}
