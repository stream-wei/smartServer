package com.stream.netty.db;

import com.stream.netty.db.entity.MacRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by stream on 2017/7/30.
 */
public class DBUtils {
    
    private static final String query_by_id = "select * from mac_record where id = ?";
    private static final String insert = "insert into mac_record(mac_id, imei, session_id, update_date) values (?,?,?,now())";

    public static MacRecord queryById(int id){
        MacRecord macRecord = new MacRecord();
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query_by_id);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String macId = resultSet.getString(2);
                String imei = resultSet.getString(3);
                String sessionId = resultSet.getString(4);
                macRecord.setId(id);
                macRecord.setMacId(macId);
                macRecord.setImei(imei);
                macRecord.setSessionId(sessionId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return macRecord;
    }
    
    public static void insert(MacRecord macRecord){
        Connection connection = JdbcUtils.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1,macRecord.getMacId());
            statement.setString(2,macRecord.getImei());
            statement.setString(3,macRecord.getSessionId());
            int count = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        MacRecord macRecord = new MacRecord();
        macRecord.setMacId("macid");
        macRecord.setImei("imei");
        macRecord.setSessionId("sessionid");
        DBUtils.insert(macRecord);
        System.out.println(macRecord);
    }
}
