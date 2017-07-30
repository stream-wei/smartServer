package com.stream.netty.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by stream on 2017/7/29.
 */
public class JdbcUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);
    
    private static DataSource dataSource = null;
    
    static{
        dataSource= new ComboPooledDataSource("c3p0");
    }
    
    public static void releaseConnection(Connection connection){
        try {
            if(connection != null ) {
                connection.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        JdbcUtils.getConnection();
    }
}
