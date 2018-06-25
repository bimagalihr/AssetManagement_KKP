/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.dao;

import com.kkp.shared.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bimagalihr
 */
public class TokenDao {
    public int writeToken(String token, String user, String dateTime) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        int status = 0;
        try {           
            conn = dbConnection.getDatabaseConnection();     
            String sql = " INSERT INTO MST_TOKEN (Token, UserLogin, DateTime) VALUES (?,?,?) ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, token);
            ps.setString(2, user);
            ps.setString(3, dateTime);
            if(ps.executeUpdate()> 0){
                status = 1;
                System.out.println("Sukses INSERT token di tabel MST_TOKEN");
            }else{
                status = 0;
                System.out.println("Gagal INSERT token di tabel MST_TOKEN");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.getSQLState();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.getSQLState();
            }
            conn = null;
        }
        return status;
    }
    
    public String getUser(String user) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        String result = "";
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql1 = " SELECT username FROM MST_USER WHERE username = '"+user+"' ";
            System.out.println("sql : "+sql1);
            PreparedStatement preparedStatement = conn.prepareStatement(sql1);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = rs.getString("username");
            }
        } catch (Exception e) {
            System.out.println("Error ::: " + e.getMessage());
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.getSQLState();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.getSQLState();
            }
            conn = null;
        }
        return result;
    }
}

