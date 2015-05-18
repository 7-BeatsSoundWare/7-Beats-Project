package br.com.network.system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private Connection con;

    public Connection getConnection() {
        try {
            if (this.con == null) {
                Class.forName("com.mysql.jdbc.Driver");
                this.con = DriverManager.getConnection("url", "usuario", "senha");
            } else {
                return this.con;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return this.con;
    }
}
