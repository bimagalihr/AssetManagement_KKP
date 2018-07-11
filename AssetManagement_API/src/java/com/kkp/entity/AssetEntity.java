/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.entity;

/**
 *
 * @author bimagalihr
 */
public class AssetEntity {
    private String id;
    private String id_tbl_karyawan;
    private String namaBarang;
    private String merek;
    private String tipe;
    private String noSeri;
    private String jumlah;
    private String createDate;
    private String tglKadaluarsa;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_tbl_karyawan() {
        return id_tbl_karyawan;
    }

    public void setId_tbl_karyawan(String id_tbl_karyawan) {
        this.id_tbl_karyawan = id_tbl_karyawan;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getNoSeri() {
        return noSeri;
    }

    public void setNoSeri(String noSeri) {
        this.noSeri = noSeri;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTglKadaluarsa() {
        return tglKadaluarsa;
    }

    public void setTglKadaluarsa(String tglKadaluarsa) {
        this.tglKadaluarsa = tglKadaluarsa;
    }
    
}
