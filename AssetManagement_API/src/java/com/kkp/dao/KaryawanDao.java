/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.dao;

import com.kkp.shared.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author bimagalihr
 */
public class KaryawanDao {
    public int addKaryawan(String namaLengkap, String nomorHp, String email, String idKaryawan, String nomorKtp, String alamatKtp, String createDate) throws Exception {
        DbConnection dbConnection = new DbConnection();
        Connection conn = null;
        int status = 0;
        try {
            conn = dbConnection.getDatabaseConnection();
            String sql = " INSERT INTO MST_KARYAWAN (namaLengkap, nomorHp, email, idKaryawan, nomorKtp, alamatKtp, createDate) VALUES (?,?,?,?,?,?,?) ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, namaLengkap);
            ps.setString(2, nomorHp);
            ps.setString(3, email);
            ps.setString(4, idKaryawan);
            ps.setString(5, nomorKtp);
            ps.setString(6, alamatKtp);
            ps.setString(7, createDate);
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
}
