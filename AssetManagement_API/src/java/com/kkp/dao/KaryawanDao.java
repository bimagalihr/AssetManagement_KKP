/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.dao;

import com.kkp.encryption.CaesarCipher;
import com.kkp.entity.KaryawanEntity;
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
public class KaryawanDao {
    
    CaesarCipher cp = new CaesarCipher();
    
    public int addKaryawan(String namaLengkap, String nomorHp, String email, String idKaryawan, String nomorKtp, String alamatKtp, String createDate) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        int status = 0;
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " INSERT INTO MST_KARYAWAN (namaLengkap, nomorHp, email, idKaryawan, nomorKtp, alamatKtp, createDate) VALUES (?,?,?,?,?,?,?) ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cp.encrypt(namaLengkap));
            ps.setString(2, cp.encrypt(nomorHp));
            ps.setString(3, cp.encrypt(email));
            ps.setString(4, cp.encrypt(idKaryawan));
            ps.setString(5, cp.encrypt(nomorKtp));
            ps.setString(6, cp.encrypt(alamatKtp));
            ps.setString(7, cp.encrypt(createDate));
            if (ps.executeUpdate() > 0) {
                status = 1;
                System.out.println("Sukses insert data user karyawan");
            } else {
                System.out.println("Gagal insert data user karyawan");
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
    
    public int getTotalListTable(String namaLengkap, String nomorHp, String email, String idKaryawan, String nomorKtp, String alamatKtp, String createDate) {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        Util util = new Util();
        int total = 0;
        try {
            conn = dbConnection.getDatabaseConnection();
            Boolean next = false;
            String FilterData = "";

            if (!util.isNullOrEmpty(namaLengkap)) {
                namaLengkap = namaLengkap.trim();
                FilterData = FilterData + " AND namaLengkap LIKE '%" + cp.encrypt(namaLengkap) + "%'";
            }
            if (!util.isNullOrEmpty(nomorHp)) {
                nomorHp = nomorHp.trim();
                FilterData = FilterData + " AND nomorHp LIKE '%" + cp.encrypt(nomorHp) + "%'";
            }
            if (!util.isNullOrEmpty(email)) {
                email = email.trim();
                FilterData = FilterData + " AND email LIKE '%" + cp.encrypt(email) + "%'";
            }
            if (!util.isNullOrEmpty(idKaryawan)) {
                idKaryawan = idKaryawan.trim();
                FilterData = FilterData + " AND idKaryawan LIKE '%" + cp.encrypt(idKaryawan) + "%'";
            }
            if (!util.isNullOrEmpty(nomorKtp)) {
                nomorKtp = nomorKtp.trim();
                FilterData = FilterData + " AND nomorKtp LIKE '%" + cp.encrypt(nomorKtp) + "%'";
            }
            if (!util.isNullOrEmpty(alamatKtp)) {
                alamatKtp = alamatKtp.trim();
                FilterData = FilterData + " AND alamatKtp LIKE '%" + cp.encrypt(alamatKtp) + "%'";
            }
            if (!util.isNullOrEmpty(createDate)) {
                createDate = createDate.trim();
                FilterData = FilterData + " AND createDate LIKE '%" + cp.encrypt(createDate) + "%'";
            }

            String sql = " SELECT COUNT(0) AS Total FROM (SELECT ROW_NUMBER() OVER(ORDER by id)as rowNum, namaLengkap, nomorHp, email, idKaryawan, nomorKtp, alamatKtp, createDate FROM MST_KARYAWAN WHERE 1 = 1" + FilterData + " ) as Data";
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
    
    public Collection getListUserKaryawan(String namaLengkap, String nomorHp, String email, String idKaryawan, String nomorKtp, String alamatKtp, String createDate, String page, String rows) {
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
            if (!util.isNullOrEmpty(namaLengkap)) {
                namaLengkap = namaLengkap.trim();
                FilterData = FilterData + " AND namaLengkap  LIKE '%" + cp.encrypt(namaLengkap) + "%'";
            }
            if (!util.isNullOrEmpty(nomorHp)) {
                nomorHp = nomorHp.trim();
                FilterData = FilterData + " AND nomorHp LIKE '%" + cp.encrypt(nomorHp) + "%'";
            }
            if (!util.isNullOrEmpty(email)) {
                email = email.trim();
                FilterData = FilterData + " AND email LIKE '%" + cp.encrypt(email) + "%'";
            }
            if (!util.isNullOrEmpty(idKaryawan)) {
                idKaryawan = idKaryawan.trim();
                FilterData = FilterData + " AND idKaryawan LIKE '%" + cp.encrypt(idKaryawan) + "%'";
            }
            if (!util.isNullOrEmpty(nomorKtp)) {
                nomorKtp = nomorKtp.trim();
                FilterData = FilterData + " AND nomorKtp LIKE '%" + cp.encrypt(nomorKtp) + "%'";
            }
            if (!util.isNullOrEmpty(alamatKtp)) {
                alamatKtp = alamatKtp.trim();
                FilterData = FilterData + " AND alamatKtp LIKE '%" + cp.encrypt(alamatKtp) + "%'";
            }
            if (!util.isNullOrEmpty(createDate)) {
                createDate = createDate.trim();
                FilterData = FilterData + " AND createDate LIKE '%" + cp.encrypt(createDate) + "%'";
            }

            String sql = " SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER by id)as rowNum, id, namaLengkap, nomorHp, email, idKaryawan, nomorKtp, alamatKtp, createDate "
                    + "FROM MST_KARYAWAN WHERE 1 = 1" + FilterData + ") as Data WHERE 1 = 1 AND rowNum > " + upLimit + " AND rowNum <= " + downLimit + " ORDER by createDate DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KaryawanEntity entity = new KaryawanEntity();
                entity.setId(rs.getInt("id"));
                entity.setNamaLengkap(cp.decrypt(rs.getString("namaLengkap")));
                entity.setNomorHp(cp.decrypt(rs.getString("nomorHp")));
                entity.setEmail(cp.decrypt(rs.getString("email")));
                entity.setIdKaryawan(cp.decrypt(rs.getString("idKaryawan")));
                entity.setNomorKtp(cp.decrypt(rs.getString("nomorKtp")));
                entity.setAlamatKtp(cp.decrypt(rs.getString("alamatKtp")));
                entity.setCreateDate(cp.decrypt(rs.getString("createDate")));
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
    
    public int deleteKaryawan(int id) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        int status = 0;
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " DELETE MST_KARYAWAN WHERE id = "+id;
            PreparedStatement ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() > 0) {
                status = 1;
            } else {
                status = 0;
                System.out.println("Gagal delete data karyawan");
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
    
    public Collection getDataEdit(String id) {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        List listMaster = new ArrayList<Object>();
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " SELECT * FROM MST_KARYAWAN WHERE id = "+id;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KaryawanEntity entity = new KaryawanEntity();
                entity.setId(rs.getInt("id"));
                entity.setNamaLengkap(cp.decrypt(rs.getString("namaLengkap")));
                entity.setNomorHp(cp.decrypt(rs.getString("nomorHp")));
                entity.setEmail(cp.decrypt(rs.getString("email")));
                entity.setIdKaryawan(cp.decrypt(rs.getString("idKaryawan")));
                entity.setNomorKtp(cp.decrypt(rs.getString("nomorKtp")));
                entity.setAlamatKtp(cp.decrypt(rs.getString("alamatKtp")));
                entity.setCreateDate(cp.decrypt(rs.getString("createDate")));
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
    
    public int editKaryawan(String namaLengkap, String nomorHp, String email, String idKaryawan, String nomorKtp, String alamatKtp, String createDate, String id) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        int status = 0;
        
        try {
            
            conn = dbConnection.getDatabaseConnection();
            String sql = " UPDATE MST_KARYAWAN SET namaLengkap = ?, nomorHp = ?, email = ?, idKaryawan = ?, nomorKtp = ?, alamatKtp = ?, createDate = ? WHERE id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cp.encrypt(namaLengkap));
            ps.setString(2, cp.encrypt(nomorHp));
            ps.setString(3, cp.encrypt(email));
            ps.setString(4, cp.encrypt(idKaryawan));
            ps.setString(5, cp.encrypt(nomorKtp));
            ps.setString(6, cp.encrypt(alamatKtp));
            ps.setString(7, cp.encrypt(createDate));
            ps.setString(8, id);
            if (ps.executeUpdate() > 0) {
                status = 1;
                System.out.println("Sukses update data user karyawan");
            } else {
                System.out.println("Gagal update data user karyawan");
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
    
    public Collection getAllDataKaryawan() {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        List listMaster = new ArrayList<Object>();
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " SELECT id, namaLengkap FROM MST_KARYAWAN ORDER BY namaLengkap ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KaryawanEntity entity = new KaryawanEntity();
                entity.setId(rs.getInt("id"));
                entity.setNamaLengkap(cp.decrypt(rs.getString("namaLengkap")));
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
