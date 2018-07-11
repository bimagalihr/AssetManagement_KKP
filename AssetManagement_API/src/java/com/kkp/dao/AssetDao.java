/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.dao;

import com.kkp.entity.AssetKaryawanEntity;
import com.kkp.shared.DbConnection;
import com.kkp.shared.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author bimagalihr
 */
public class AssetDao {
    public int getTotalListTable(String namaLengkap, String idKaryawan, String nomorHp, String email, String namaBarang, String merek, String tipe, String noSeri, String jumlah, String createDate, String tglKadaluarsa) {
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
                FilterData = FilterData + " AND namaLengkap LIKE '%" + namaLengkap + "%'";
            }
            if (!util.isNullOrEmpty(idKaryawan)) {
                idKaryawan = idKaryawan.trim();
                FilterData = FilterData + " AND idKaryawan LIKE '%" + idKaryawan + "%'";
            }
            if (!util.isNullOrEmpty(nomorHp)) {
                nomorHp = nomorHp.trim();
                FilterData = FilterData + " AND nomorHp LIKE '%" + nomorHp + "%'";
            }
            if (!util.isNullOrEmpty(email)) {
                email = email.trim();
                FilterData = FilterData + " AND email LIKE '%" + email + "%'";
            }
            if (!util.isNullOrEmpty(namaBarang)) {
                namaBarang = namaBarang.trim();
                FilterData = FilterData + " AND namaBarang LIKE '%" + namaBarang + "%'";
            }
            if (!util.isNullOrEmpty(merek)) {
                merek = merek.trim();
                FilterData = FilterData + " AND merek LIKE '%" + merek + "%'";
            }
            if (!util.isNullOrEmpty(tipe)) {
                tipe = tipe.trim();
                FilterData = FilterData + " AND tipe LIKE '%" + tipe + "%'";
            }
            if (!util.isNullOrEmpty(noSeri)) {
                noSeri = noSeri.trim();
                FilterData = FilterData + " AND noSeri LIKE '%" + noSeri + "%'";
            }
            if (!util.isNullOrEmpty(jumlah)) {
                jumlah = jumlah.trim();
                FilterData = FilterData + " AND jumlah LIKE '%" + jumlah + "%'";
            }
            if (!util.isNullOrEmpty(createDate)) {
                createDate = createDate.trim();
                FilterData = FilterData + " AND createDate LIKE '%" + createDate + "%'";
            }
            if (!util.isNullOrEmpty(tglKadaluarsa)) {
                tglKadaluarsa = tglKadaluarsa.trim();
                FilterData = FilterData + " AND tglKadaluarsa LIKE '%" + tglKadaluarsa + "%'";
            }

            String sql = " SELECT COUNT(0) AS Total FROM (SELECT ROW_NUMBER() OVER(ORDER by ma.id)as rowNum, mk.namaLengkap, mk.idKaryawan, mk.nomorHp, mk.email, ma.namaBarang, ma.merek, ma.tipe, ma.noSeri, ma.jumlah, ma.createDate, ma.tglKadaluarsa FROM MST_ASSET ma LEFT JOIN MST_KARYAWAN mk ON ma.id_tbl_karyawan = mk.id WHERE 1 = 1" + FilterData + " ) as Data";
            System.out.println(sql);
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
    
    
    public Collection getListAsset(String namaLengkap, String idKaryawan, String nomorHp, String email, String namaBarang, String merek, String tipe, String noSeri, String jumlah, String createDate, String tglKadaluarsa, String page, String rows) {
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
                FilterData = FilterData + " AND namaLengkap  LIKE '%" + namaLengkap + "%'";
            }
            if (!util.isNullOrEmpty(idKaryawan)) {
                idKaryawan = idKaryawan.trim();
                FilterData = FilterData + " AND idKaryawan  LIKE '%" + idKaryawan + "%'";
            }
            if (!util.isNullOrEmpty(nomorHp)) {
                nomorHp = nomorHp.trim();
                FilterData = FilterData + " AND nomorHp LIKE '%" + nomorHp + "%'";
            }
            if (!util.isNullOrEmpty(email)) {
                email = email.trim();
                FilterData = FilterData + " AND email LIKE '%" + email + "%'";
            }
            if (!util.isNullOrEmpty(namaBarang)) {
                namaBarang = namaBarang.trim();
                FilterData = FilterData + " AND namaBarang LIKE '%" + namaBarang + "%'";
            }
            if (!util.isNullOrEmpty(merek)) {
                merek = merek.trim();
                FilterData = FilterData + " AND merek LIKE '%" + merek + "%'";
            }
            if (!util.isNullOrEmpty(tipe)) {
                tipe = tipe.trim();
                FilterData = FilterData + " AND tipe LIKE '%" + tipe + "%'";
            }
            if (!util.isNullOrEmpty(noSeri)) {
                noSeri = noSeri.trim();
                FilterData = FilterData + " AND noSeri LIKE '%" + noSeri + "%'";
            }
            if (!util.isNullOrEmpty(jumlah)) {
                jumlah = jumlah.trim();
                FilterData = FilterData + " AND jumlah LIKE '%" + jumlah + "%'";
            }
            if (!util.isNullOrEmpty(createDate)) {
                createDate = createDate.trim();
                FilterData = FilterData + " AND createDate LIKE '%" + createDate + "%'";
            }
            if (!util.isNullOrEmpty(tglKadaluarsa)) {
                tglKadaluarsa = tglKadaluarsa.trim();
                FilterData = FilterData + " AND tglKadaluarsa LIKE '%" + tglKadaluarsa + "%'";
            }

            String sql = " SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER by ma.id)as rowNum, ma.id, mk.namaLengkap, mk.idKaryawan, mk.nomorHp, mk.email, ma.namaBarang, ma.merek, ma.tipe, ma.noSeri, ma.jumlah, ma.createDate, ma.tglKadaluarsa "
                    + "FROM MST_ASSET ma LEFT JOIN MST_KARYAWAN mk ON ma.id_tbl_karyawan = mk.id WHERE 1 = 1" + FilterData + ") as Data WHERE 1 = 1 AND rowNum > " + upLimit + " AND rowNum <= " + downLimit + " ORDER by createDate DESC";
            System.out.println(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AssetKaryawanEntity entity = new AssetKaryawanEntity();
                entity.setId(rs.getInt("id"));
                entity.setNamaLengkap(rs.getString("namaLengkap"));
                entity.setIdKaryawan(rs.getString("idKaryawan"));
                entity.setNomorHp(rs.getString("nomorHp"));
                entity.setEmail(rs.getString("email"));
                entity.setNamaBarang(rs.getString("namaBarang"));
                entity.setMerek(rs.getString("merek"));
                entity.setTipe(rs.getString("tipe"));
                entity.setNoSeri(rs.getString("noSeri"));
                entity.setJumlah(rs.getString("jumlah"));
                entity.setCreateDate(rs.getString("createDate"));
                entity.setTglKadaluarsa(rs.getString("tglKadaluarsa"));
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
    
    public int addBarang(String pilihKaryawan, String namaBarang, String merek, String tipe, String noSeri, String jumlah, String tglKadaluarsa, String createDate) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        int status = 0;
        try {
            SimpleDateFormat beforeDate = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat updateDate = new SimpleDateFormat("yyyy-MM-dd");
            String reformatTglKadaluarsa = updateDate.format(beforeDate.parse(tglKadaluarsa));
            
            conn = dbConnection.getDatabaseConnection();
            String sql = " INSERT INTO MST_ASSET (id_tbl_karyawan, namaBarang, merek, tipe, noSeri, jumlah, tglKadaluarsa, createDate) VALUES (?,?,?,?,?,?,?,?) ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pilihKaryawan);
            ps.setString(2, namaBarang);
            ps.setString(3, merek);
            ps.setString(4, tipe);
            ps.setString(5, noSeri);
            ps.setString(6, jumlah);
            ps.setString(7, reformatTglKadaluarsa);
            ps.setString(8, createDate);
            if (ps.executeUpdate() > 0) {
                status = 1;
                System.out.println("Sukses insert data barang");
            } else {
                System.out.println("Gagal insert data barang");
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
    
    
    public int deleteDataAsset(int id) throws Exception {
        DbConnection dbConnection   = new DbConnection();
        Connection conn             = null;
        int status                  = 0;
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " DELETE MST_ASSET WHERE id = "+id;
            PreparedStatement ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() > 0) {
                status = 1;
            } else {
                status = 0;
                System.out.println("Gagal delete data asset");
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
    
//    public int updateKaryawanAsset(int id) throws Exception {
//        DbConnection dbConnection = new DbConnection();
//        Connection conn = null;
//        int status = 0;
//        
//        try {
//            
//            conn = dbConnection.getDatabaseConnection();
//            String sql = " UPDATE MST_ASSET SET id_tbl_karyawan = 0 WHERE id_tbl_karyawan = ? ";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, id);
//            if (ps.executeUpdate() > 0) {
//                status = 1;
//                System.out.println("Sukses update data");
//            } else {
//                System.out.println("Gagal update data");
//                status = 0;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                conn.close();
//            } catch (SQLException ex) {
//                ex.getSQLState();
//            }            
//        } finally {
//            try {
//                conn.close();
//            } catch (SQLException ex) {
//                ex.getSQLState();
//            }
//            conn = null;
//        }
//        return status;
//    }

}
