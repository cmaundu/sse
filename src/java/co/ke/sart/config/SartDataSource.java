package co.ke.sart.config;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class SartDataSource extends BasicDataSource {

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        // TODO Auto-generated method stub
        return createDataSource().getConnection();
    }

}
