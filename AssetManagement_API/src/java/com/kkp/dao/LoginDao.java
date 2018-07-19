/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.dao;

import com.kkp.encryption.CaesarCipher;
import com.kkp.shared.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bimagalihr
 */
public class LoginDao {
    
    CaesarCipher cp = new CaesarCipher();
    
    public String getLogin(String emailLogin, String passwordEncrypt) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        String email = "";
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " SELECT fullname FROM MST_USER WHERE email = ? AND password = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cp.encrypt(emailLogin));
            ps.setString(2, passwordEncrypt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                email = cp.decrypt(rs.getString("fullname"));
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
        return email;
    }
}

