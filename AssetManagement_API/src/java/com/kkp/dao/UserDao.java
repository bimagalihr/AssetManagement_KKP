/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.dao;

import com.kkp.entity.UserEntity;
import com.kkp.shared.DbConnection;
import com.kkp.shared.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author bimagalihr
 */
public class UserDao {
        
    public int getTotalUser(String fullname, String email, String createDate) {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        Util util = new Util();
        int total = 0;
        try {
            conn = dbConnection.getDatabaseConnection();
            Boolean next = false;
            String FilterData = "";

            if (!util.isNullOrEmpty(fullname)) {
                fullname = fullname.trim();
                FilterData = FilterData + " AND fullname LIKE '%" + fullname + "%'";
            }
            if (!util.isNullOrEmpty(email)) {
                email = email.trim();
                FilterData = FilterData + " AND email LIKE '%" + email + "%'";
            }
            if (!util.isNullOrEmpty(createDate)) {
                FilterData = FilterData + " AND createDate LIKE '%" + createDate + "%'";
            }

            String sql = " SELECT COUNT(0) AS Total FROM (SELECT ROW_NUMBER() OVER(ORDER by id)as rowNum, fullname, email, createDate FROM MST_USER WHERE 1 = 1" + FilterData + " and fullName <>'-' or email <>'-') as Data";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
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
            }
            conn = null;
        }
        return total;
    }
    
    public Collection getListUserLogin(String fullName, String email, String createDate, String page, String rows) {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        List listMaster = new ArrayList<Object>();
        Util util = new Util();
        try {
            conn = dbConnection.getDatabaseConnection();
            Boolean next = false;

            String FilterData = "";
            int upLimit = 0;
            int downLimit = 0;

            if (Integer.parseInt(page) == 1) {
                upLimit = 0;
                downLimit = Integer.parseInt(rows);
            } else {
                upLimit = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
                downLimit = Integer.parseInt(page) * Integer.parseInt(rows);
            }
            if (!util.isNullOrEmpty(fullName)) {
                fullName = fullName.trim();
                FilterData = FilterData + " AND fullName  LIKE '%" + fullName + "%'";
            }
            if (!util.isNullOrEmpty(email)) {
                email = email.trim();
                FilterData = FilterData + " AND email LIKE '%" + email + "%'";
            }
            if (!util.isNullOrEmpty(createDate)) {
                createDate = createDate.trim();
                FilterData = FilterData + " AND createDate LIKE '%" + createDate + "%'";
            }

            String sql = " SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER by id)as rowNum, id, fullName, email, createDate "
                    + "FROM MST_USER WHERE 1 = 1" + FilterData + ") as Data WHERE fullName <>'-' and 1 = 1 AND rowNum > " + upLimit + " AND rowNum <= " + downLimit + " ORDER by createDate DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserEntity entity = new UserEntity();
                entity.setId(rs.getInt("id"));
                entity.setFullName(rs.getString("fullName"));
                entity.setEmail(rs.getString("email"));
                entity.setCreateDate(rs.getString("createDate"));
                listMaster.add(entity);
            }
        } catch (Exception e) {
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
        return listMaster;
    }
    
    public int deleteUser(int id) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        int status = 0;
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " DELETE MST_USER WHERE id = "+id;
            PreparedStatement ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() > 0) {
                status = 1;
            } else {
                status = 0;
                System.out.println("Gagal delete user dengan id user : "+id);
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
    
    public Collection getListUserLogin(int id) {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        List listMaster = new ArrayList<Object>();
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " SELECT * FROM MST_USER WHERE id = "+id;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserEntity entity = new UserEntity();
                entity.setId(rs.getInt("id"));
                entity.setFullName(rs.getString("fullName"));
                entity.setEmail(rs.getString("email"));
                System.out.println("test nama : "+entity.getFullName());;
                listMaster.add(entity);
            }
        } catch (Exception e) {
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
        return listMaster;
    }
    
    public int editUser(String email, String password, String fullname, String createDate, int id) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        int status = 0;
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " UPDATE MST_USER SET email = ?, password = ?, fullname = ?, createDate = ? WHERE id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, fullname);
            ps.setString(4, createDate);
            ps.setInt(5, id);
            if (ps.executeUpdate() > 0) {
                status = 1;
            } else {
                status = 0;
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
    
    public int addUserLogin(String fullName, String email, String pass, String createDate) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        int status = 0;
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " INSERT INTO MST_USER (fullname, email, password, createDate) VALUES (?,?,?,?) ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, createDate);
            if (ps.executeUpdate() > 0) {
                status = 1;
                System.out.println("Sukses insert data user login");
            } else {
                System.out.println("Gagal insert data user login");
                status = 0;
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
    
    public Collection getViewUser(int id) {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        List listMaster = new ArrayList<Object>();
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " SELECT * FROM MST_USER WHERE id = "+id;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserEntity entity = new UserEntity();
                entity.setId(rs.getInt("id"));
                entity.setFullName(rs.getString("fullName"));
                entity.setEmail(rs.getString("email"));
                entity.setCreateDate(rs.getString("createDate"));
                listMaster.add(entity);
            }
        } catch (Exception e) {
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
        return listMaster;
    }
    
}
