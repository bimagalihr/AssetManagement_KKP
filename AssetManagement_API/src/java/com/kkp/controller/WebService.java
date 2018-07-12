/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.controller;

import com.google.gson.Gson;
import com.kkp.dao.AssetDao;
import com.kkp.dao.KaryawanDao;
import com.kkp.dao.TokenDao;
import com.kkp.dao.LoginDao;
import com.kkp.dao.UserDao;
import com.kkp.entity.GetJsonList;
import com.kkp.shared.JsonReturn;
import com.kkp.shared.Util;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bimagalihr
 */
public class WebService extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            String doing = request.getParameter("doing");  
            System.out.println("doing : "+doing);
            if(doing != null){
                switch(doing){
                    case "list-user-login":
                        doListUserLogin(request, response);
                        break; 
                    case "data-edit-user":
                        doEditUser(request, response);
                        break; 
                    case "data-view-user":
                        doViewUser(request, response);
                        break; 
                    case "list-data-karyawan":
                        doDataListKaryawan(request, response);
                        break; 
                    case "data-edit-karyawan":
                        doDataEditKaryawan(request, response);
                        break; 
                    case "list-data-asset":
                        doListDataAsset(request, response);
                        break; 
                    case "list-all-data-karyawan":
                        doListAllDataKaryawan(request, response);
                        break; 
                    case "data-edit-asset":
                        doDataEditAsset(request, response);
                        break; 
                    case "view-data-asset":
                        doViewAsset(request, response);
                        break; 
                    default:
                        System.out.println("Parameter Url Salah");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            String doing = request.getParameter("doing");  
            System.out.println("doing : "+doing);
            if(doing != null){
                switch(doing){
                    case "login":
                        doLogin(request, response);
                        break; 
                    case "infoLogin":
                        doInfoLogin(request, response);
                        break; 
                    case "start":
                        doStart(request, response);
                        break; 
                    case "logout":
                        doLogout(request, response);
                        break; 
                    case "user-delete":
                        doUserDelete(request, response);
                        break;
                    case "user-edit":
                        doUserEdit(request, response);
                        break;
                    case "user-add":
                        doUserAdd(request, response);
                        break;
                    case "karyawan-add":
                        doKaryawanAdd(request, response);
                        break;
                    case "karyawan-delete":
                        doDeleteKaryawan(request, response);
                        break;
                    case "karyawan-edit":
                        doKaryawanEdit(request, response);
                        break;
                    case "barang-add":
                        doAddBarang(request, response);
                        break;
                    case "asset-delete":
                        doDeleteAsset(request, response);
                        break;
                    case "asset-edit":
                        doEditAsset(request, response);
                        break;
                    default:
                        System.out.println("Parameter Url Salah");
                }
            }else{
                System.out.println("Error Url");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Masuk doLogin");
        
        Gson gson                   = new Gson();
        HashMap hashMap             = new HashMap();
        JsonReturn jsonreturn       = new JsonReturn();
        Collection listMaster       = new ArrayList<>();                
        SimpleDateFormat sdfFormat  = new SimpleDateFormat("yyyyMMddHHmmss");
        sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));                
        int insertToken             = 0;
        int status                  = 0;
        String desc                 = "";
        
        try{           
            String dateTime         = sdfFormat.format(new Date());
            String token            = UUID.randomUUID().toString();
            String emailLogin       = request.getParameter("emailLogin");
            String passwordLogin    = request.getParameter("passwordLogin");
            String passwordEncrypt  = Util.encrypt(passwordLogin);            
            LoginDao dao            = new LoginDao();
            String userSelect       = dao.getLogin(emailLogin, passwordEncrypt);
            
            if(!Util.isNullOrEmpty(userSelect)){
                if(!Util.isNullOrEmpty(emailLogin)){
                    TokenDao tokenDao = new TokenDao();
                    insertToken = tokenDao.writeToken(token, emailLogin, dateTime);
                    if(insertToken == 1){
                        request.getSession(true).setAttribute("tokenSession", token);
                        request.getSession(true).setAttribute("fullNameLogin", userSelect);                        
                        String tokenSession = (String) request.getSession().getAttribute("tokenSession");
                        
                        if(insertToken == 1){    
                            status = 1;
                            desc = "Sukses";
                            
                            hashMap.put("fullName", userSelect);
                            hashMap.put("token", token);
                            hashMap.put("user", emailLogin);
                            hashMap.put("dateTime", dateTime);                
                            listMaster.add(hashMap);   
                        }else{
                            status = 0;
                            desc = "Gagal";
                            
                            hashMap.put("status", insertToken);
                            hashMap.put("message", "Fail");
                            hashMap.put("data",null);
                            listMaster.add(hashMap);                              
                        } 
                    }
                }else{
                System.out.println("Parameter user tidak boleh kosong !");
            }
            }else{
                System.out.println("User Tidak ada di database");
            }
            
            jsonreturn.OutObject(request, response, status, desc, listMaster);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doStart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson       = new Gson();
        PrintWriter out = response.getWriter();
        try{
            out.println(gson.toJson("Ini adalah Web Service"));
        }catch(Exception e){
            e.printStackTrace();                    
        }finally{
            out.close();
        }        
    }

    private void doInfoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Masuk doInfoLogin");
        
        HashMap hashMap         = new HashMap();        
        JsonReturn jsonreturn   = new JsonReturn();
        Collection listMaster   = new ArrayList<>();        
        int status              = 0;
        String desc             = "";
        
        try{
            
            String fullName = (String) request.getSession().getAttribute("fullNameLogin");
            String token    = (String) request.getSession().getAttribute("tokenSession");
            
            if(fullName != null){
                status  = 1;
                desc    = "Sukses ambil data login";
                hashMap.put("fullNameLogin", fullName);
                hashMap.put("token", token);
                listMaster.add(hashMap);                
            }else{
                status  = 0;
                desc    = "Gagal ambil data login";
                listMaster.add("");
            }
            
            jsonreturn.OutObject(request, response, status, desc, listMaster);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Masuk doLogout");
        
        Gson gson           = new Gson();
        HashMap hashMap     = new HashMap();
        HashMap hashMapSub  = new HashMap(); 
        PrintWriter out     = response.getWriter();
        
        try{
            
            request.getSession().invalidate();
            hashMap.put("status", 1);
            hashMap.put("description", "Sukses logout");
            out.println(gson.toJson(hashMap));
            hashMap.clear();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doListUserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Masuk doListUserLogin");
        
        JsonReturn jsonreturn   = new JsonReturn();
        UserDao dao             = new UserDao();
        Util util               = new Util();
        JsonReturn jsonReturn   = new JsonReturn();
        TokenDao tokenDao       = new TokenDao();
        Gson gson               = new Gson();
        
        try{           
            String token            = (String) request.getSession().getAttribute("tokenSession");
            String filter           = request.getParameter("filter");
            String filterRules      = request.getParameter("filterRules");
            String page             = request.getParameter("page");
            String rows             = request.getParameter("rows");
            String fullName         = "";
            String email            = "";
            String createDate       = "";            
            Collection getListUser  = new ArrayList();
            int total               = 0;
            
            //System.out.println("token Session  ; "+token);
            int tokenAvailable = tokenDao.getTokenIdle(token);
            System.out.println("tokenAvaliable : "+tokenAvailable);
            
            if(tokenAvailable == 1){
                if (!util.isNullOrEmpty(filterRules) && filterRules.contains("{")) {
                    System.out.println("Masuk untuk filter");
                    GetJsonList[] arr = gson.fromJson(filterRules, GetJsonList[].class);
                    for (GetJsonList values : arr) {
                        if (values.getField().equals("fullName")) {
                            fullName = values.getValue();
                        } else if (values.getField().equals("email")) {
                            email = values.getValue();
                        } else if (values.getField().equals("createDate")) {
                            createDate = values.getValue();
                        } 
                    }
                }

                total       = dao.getTotalUser(fullName, email, createDate);            
                getListUser = dao.getListUserLogin(fullName, email, createDate, page, rows);
                
                jsonReturn.GridObjectServerSide(request, response, getListUser, total);
                
            }else{
                jsonReturn.HashmapWithMessage(request, response, 0, "Invalid Token !");
            }      
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doUserDelete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doUserDelete");
        
        HashMap hashMap         = new HashMap();
        JsonReturn jsonreturn   = new JsonReturn();
        UserDao dao             = new UserDao();
        Util util               = new Util();    
        TokenDao tokenDao       = new TokenDao();
        int status              = 0;
        String desc             = "";
        
        try{
            String token        = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable  = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){
                int id          = Integer.valueOf(request.getParameter("id"));
                int deleteUser  = dao.deleteUser(id);

                if(deleteUser == 1){
                    status = 1;
                    desc = "Sukses delete user dengan id user : "+id;
                }else{
                    status = 0;
                    desc = "Gagal delete user dengan id user : "+id;
                }            
                jsonreturn.OutStatusAndDesc(request, response, status, desc); 
            }else{
                jsonreturn.OutStatusAndDesc(request, response, 0, "Invalid Token !");
            }                       
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doEditUser(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("masuk doEditUser");
        
        UserDao dao             = new UserDao();
        JsonReturn jsonReturn   = new JsonReturn();
        TokenDao tokenDao       = new TokenDao();
        String desc             = "";
        int status              = 0;
        Collection getData;
        
        try{
            String token        = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable  = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){        
                int id = Integer.valueOf(request.getParameter("id"));
                getData = dao.getListUserLogin(id);

                if(getData != null){
                    status = 1;
                    desc = "Sukses ambil data";
                }else{
                    status = 0;
                    desc = "Gagal ambil data";
                }         

                jsonReturn.OutObject(request, response, status, desc, getData); 
                
            }else{
                jsonReturn.OutStatusAndDesc(request, response, 0, "Invalid Token !");
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doUserEdit(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doUserEdit");
        
        Util util               = new Util();
        UserDao dao             = new UserDao();
        JsonReturn jsonReturn   = new JsonReturn();        
        int status              = 0;
        String desc             = "";
        
        try{
            
            SimpleDateFormat sdfFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
            String createDate           = sdfFormat.format(new Date());            
            int id                      = Integer.valueOf(request.getParameter("id"));
            String fullName             = request.getParameter("fullName");
            String email                = request.getParameter("email");
            String pass                 = util.encrypt(request.getParameter("pass"));            
            int editUser                = dao.editUser(email, pass, fullName, createDate, id);
            
            if(editUser == 1){
                status = 1;
                desc = "Sukses mengubah data user login";
            }else{
                status = 0;
                desc = "Gagal mengubah data user login";
            }
            
            jsonReturn.OutStatusAndDesc(request, response, status, desc);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doUserAdd(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doUserAdd");
        
        UserDao dao             = new UserDao();
        JsonReturn jsonReturn   = new JsonReturn();
        Util util               = new Util();        
        int status              = 0;
        String desc             = "";
        
        try{
            
            SimpleDateFormat sdfFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
            String createDate           = sdfFormat.format(new Date());            
            String fullName             = request.getParameter("fullName");
            String email                = request.getParameter("email");
            String password             = util.encrypt(request.getParameter("pass"));            
            int addUser                 = dao.addUserLogin(fullName, email, password, createDate);
            
            if(addUser == 1){
                status  = 1;
                desc    = "Sukses menambahkan user login";
            }else{
                status  = 0;
                desc    = "Gagal menambahkan user login";
            }
            
            jsonReturn.OutStatusAndDesc(request, response, status, desc);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doKaryawanAdd(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doKaryawanAdd");
        
        KaryawanDao dao             = new KaryawanDao();
        JsonReturn jsonReturn       = new JsonReturn();
        SimpleDateFormat sdfFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
        int status                  = 0; 
        String desc                 = "";
        TokenDao tokenDao           = new TokenDao();
        
        try{
            
            String token        = (String) request.getSession().getAttribute("tokenSession");
            String namaLengkap  = request.getParameter("namaLengkap");
            String nomorHp      = request.getParameter("nomorHp");
            String email        = request.getParameter("email");
            String idKaryawan   = request.getParameter("idKaryawan");
            String nomorKtp     = request.getParameter("nomorKtp");
            String alamatKtp    = request.getParameter("alamatKtp");
            String dateTime     = sdfFormat.format(new Date());            
            int tokenAvailable  = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){
                int addDataKaryawan = dao.addKaryawan(namaLengkap, nomorHp, email, idKaryawan, nomorKtp, alamatKtp, dateTime);
                if(addDataKaryawan == 1){
                    status  = 1;
                    desc    = "Sukses menambahkan data karyawan";
                }else{
                    status  = 0;
                    desc    = "Gagal menambahkan data karyawan";
                }
            }else{
                jsonReturn.HashmapWithMessage(request, response, 0, "Invalid Token !");
            }            
            jsonReturn.OutStatusAndDesc(request, response, status, desc);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doDataListKaryawan(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doListKaryawan");
        
        JsonReturn jsonreturn   = new JsonReturn();
        KaryawanDao dao         = new KaryawanDao();
        Util util               = new Util();
        JsonReturn jsonReturn   = new JsonReturn();
        TokenDao tokenDao       = new TokenDao();
        Gson gson               = new Gson();
        
        try{           
            
            String token            = (String) request.getSession().getAttribute("tokenSession");
            String filter           = request.getParameter("filter");
            String filterRules      = request.getParameter("filterRules");
            String page             = request.getParameter("page");
            String rows             = request.getParameter("rows");
            String namaLengkap      = "";
            String nomorHp          = "";
            String email            = "";
            String idKaryawan       = "";
            String nomorKtp         = "";
            String alamatKtp        = "";
            String createDate       = "";            
            Collection getListUser  = new ArrayList();
            int total               = 0;            
            int tokenAvailable      = tokenDao.getTokenIdle(token); 
            
            if(tokenAvailable == 1){
                if (!util.isNullOrEmpty(filterRules) && filterRules.contains("{")) {
                    GetJsonList[] arr = gson.fromJson(filterRules, GetJsonList[].class);
                    for (GetJsonList values : arr) {
                        if (values.getField().equals("namaLengkap")) {
                            namaLengkap = values.getValue();
                        } else if (values.getField().equals("nomorHp")) {
                            nomorHp = values.getValue();
                        } else if (values.getField().equals("email")) {
                            email = values.getValue();
                        } else if (values.getField().equals("idKaryawan")) {
                            idKaryawan = values.getValue();
                        } else if (values.getField().equals("nomorKtp")) {
                            nomorKtp = values.getValue();
                        } else if (values.getField().equals("alamatKtp")) {
                            alamatKtp = values.getValue();
                        } else if (values.getField().equals("createDate")) {
                            createDate = values.getValue();
                        } 
                    }
                }

                total       = dao.getTotalListTable(namaLengkap, nomorHp, email, idKaryawan, nomorKtp, alamatKtp, createDate);
                getListUser = dao.getListUserKaryawan(namaLengkap, nomorHp, email, idKaryawan, nomorKtp, alamatKtp, createDate, page, rows);
                
                jsonReturn.GridObjectServerSide(request, response, getListUser, total);
                
            }else{
                jsonReturn.HashmapWithMessage(request, response, 0, "Invalid Token !");
            }      
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doViewUser(HttpServletRequest request, HttpServletResponse response) {        
        System.out.println("masuk diViewUser");
        
        UserDao dao             = new UserDao();
        JsonReturn jsonReturn   = new JsonReturn();
        TokenDao tokenDao       = new TokenDao();
        String desc             = "";
        int status              = 0;
        Collection getData;
        
        try{
            
        
            String token        = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable  = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){
                int id  = Integer.valueOf(request.getParameter("id"));
                getData = dao.getViewUser(id);            
                if(getData != null){
                    status  = 1;
                    desc    = "Sukses ambil data";
                }else{
                    status  = 0;
                    desc    = "Gagal ambil data";
                }          

                jsonReturn.OutObject(request, response, status, desc, getData);  
                
            }else{
                
                jsonReturn.OutStatusAndDesc(request, response, 0, "Invalid Token !");
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doDeleteKaryawan(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doDeleteKaryawan");
        
        HashMap hashMap         = new HashMap();
        JsonReturn jsonreturn   = new JsonReturn();
        KaryawanDao dao         = new KaryawanDao();
        AssetDao assetDao       = new AssetDao();
        Util util               = new Util();        
        int status              = 0;
        String desc             = "";
        
        try{
            
            int id              = Integer.valueOf(request.getParameter("id"));
            int deleteKaryawan  = dao.deleteKaryawan(id);
            
            if(deleteKaryawan == 1){
                status = 1;
                desc = "Sukses delete data karyawan dengan id karyawan : "+id;
            }else{
                status = 0;
                desc = "Gagal delete data karyawan dengan id karyawan : "+id;
            }            
            jsonreturn.OutStatusAndDesc(request, response, status, desc);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doDataEditKaryawan(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doDataEditKaryawan");
        
        Collection getData;
        KaryawanDao dao     = new KaryawanDao();
        Util util           = new Util();
        JsonReturn jr       = new JsonReturn();
        TokenDao tokenDao   = new TokenDao();
        int status          = 0;
        String desc         = "";
                
        try{
            
            String id           = request.getParameter("id");            
            String token        = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable  = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){
                if(!util.isNullOrEmpty(id)){
                    getData = dao.getDataEdit(id);
                    status  = 1;
                    desc    = "Sukses";
                }else{
                    getData = null;
                    status  = 0;
                    desc    = "Data tidak ditemukan atau terjadi kesalahan";
                }
                
                jr.OutObject(request, response, status, desc, getData);   
                
            }else{
                
                jr.HashmapWithMessage(request, response, 0, "Invalid Token !");
                
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doKaryawanEdit(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doKaryawanEdit");
        
        Util util = new Util();
        KaryawanDao dao             = new KaryawanDao();
        JsonReturn jsonReturn       = new JsonReturn();
        SimpleDateFormat sdfFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
        int status                  = 0; 
        String desc                 = "";
        TokenDao tokenDao           = new TokenDao();
        
        try{
            
            String id           = request.getParameter("id");
            String namaLengkap  = request.getParameter("namaLengkap");
            String nomorHp      = request.getParameter("nomorHp");
            String email        = request.getParameter("email");
            String idKaryawan   = request.getParameter("idKaryawan");
            String nomorKtp     = request.getParameter("nomorKtp");
            String alamatKtp    = request.getParameter("alamatKtp");
            String dateTime     = sdfFormat.format(new Date());    
            String token        = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable  = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){
                if(!util.isNullOrEmpty(id)){
                    int updateData = dao.editKaryawan(namaLengkap, nomorHp, email, idKaryawan, nomorKtp, alamatKtp, dateTime, id);
                    if(updateData == 1){
                        status  = 1;
                        desc    = "Sukses menambahkan data karyawan";
                    }else{
                        status  = 0;
                        desc    = "Gagal menambahkan data karyawan";
                    }
                    
                    jsonReturn.OutStatusAndDesc(request, response, status, desc);
                    
                }else{
                    jsonReturn.HashmapWithMessage(request, response, 0, "Kesalahan Data");  
                }                
            }else{                
                jsonReturn.HashmapWithMessage(request, response, 0, "Invalid Token !");                
            }                 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doListDataAsset(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doListDataAsset");
        
        JsonReturn jsonreturn   = new JsonReturn();
        AssetDao dao            = new AssetDao();
        Util util               = new Util();
        JsonReturn jsonReturn   = new JsonReturn();
        TokenDao tokenDao       = new TokenDao();
        Gson gson               = new Gson();
        
        try{           
                        
            String filter           = request.getParameter("filter");
            String filterRules      = request.getParameter("filterRules");
            String page             = request.getParameter("page");
            String rows             = request.getParameter("rows");
            String namaLengkap      = "";
            String idKaryawan       = "";
            String nomorHp          = "";
            String email            = "";
            String namaBarang       = "";
            String merek            = "";
            String tipe             = "";
            String noSeri           = "";            
            String jumlah           = "";            
            String createDate       = "";            
            String tglKadaluarsa    = "";            
            Collection getListData  = new ArrayList();
            int total               = 0;         
            String token            = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable      = tokenDao.getTokenIdle(token); 
            
            if(tokenAvailable == 1){
                if (!util.isNullOrEmpty(filterRules) && filterRules.contains("{")) {
                    GetJsonList[] arr = gson.fromJson(filterRules, GetJsonList[].class);
                    for (GetJsonList values : arr) {
                        if (values.getField().equals("namaLengkap")) {
                            namaLengkap = values.getValue();
                        } else if (values.getField().equals("idKaryawan")) {
                            idKaryawan = values.getValue();
                        } else if (values.getField().equals("nomorHp")) {
                            nomorHp = values.getValue();
                        } else if (values.getField().equals("email")) {
                            email = values.getValue();
                        } else if (values.getField().equals("namaBarang")) {
                            namaBarang = values.getValue();
                        } else if (values.getField().equals("merek")) {
                            merek = values.getValue();
                        } else if (values.getField().equals("tipe")) {
                            tipe = values.getValue();
                        } else if (values.getField().equals("noSeri")) {
                            noSeri = values.getValue();
                        } else if (values.getField().equals("jumlah")) {
                            jumlah = values.getValue();
                        } else if (values.getField().equals("createDate")) {
                            createDate = values.getValue();
                        } else if (values.getField().equals("tglKadaluarsa")) {
                            tglKadaluarsa = values.getValue();
                        } 
                    }
                }

                total       = dao.getTotalListTable(namaLengkap, idKaryawan, nomorHp, email, namaBarang, merek, tipe, noSeri, jumlah, createDate, tglKadaluarsa);
                getListData = dao.getListAsset(namaLengkap, idKaryawan, nomorHp, email, namaBarang, merek, tipe, noSeri, jumlah, createDate, tglKadaluarsa, page, rows);
                
                jsonReturn.GridObjectServerSide(request, response, getListData, total);
                
            }else{
                jsonReturn.HashmapWithMessage(request, response, 0, "Invalid Token !");
            }      
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doListAllDataKaryawan(HttpServletRequest request, HttpServletResponse response) {
        
        JsonReturn jsonreturn   = new JsonReturn();
        KaryawanDao dao         = new KaryawanDao();
        Util util               = new Util();
        JsonReturn jsonReturn   = new JsonReturn();
        TokenDao tokenDao       = new TokenDao();
        Gson gson               = new Gson();
        
        try{           
                     
            Collection getListDataKaryawan  = new ArrayList();
            String token                    = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable              = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){                

                getListDataKaryawan = dao.getAllDataKaryawan();
                jsonReturn.OutObject(request, response, 1, "Sukses", getListDataKaryawan);
                
            }else{
                jsonReturn.HashmapWithMessage(request, response, 0, "Invalid Token !");
            }      
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doAddBarang(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doAddBarang");
        
        AssetDao dao                = new AssetDao();
        JsonReturn jr               = new JsonReturn();
        SimpleDateFormat sdfFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
        int status                  = 0; 
        String desc                 = "";
        TokenDao tokenDao           = new TokenDao();
        SimpleDateFormat sdfFormat2 = new SimpleDateFormat("yyyyMMdd");
        sdfFormat2.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
        int currentDate             = Integer.valueOf(sdfFormat2.format(new Date()));
        
        try{
                        
            String pilihKaryawan    = request.getParameter("pilihKaryawan");
            String namaBarang       = request.getParameter("namaBarang");
            String merek            = request.getParameter("merek");
            String tipe             = request.getParameter("tipe");
            String noSeri           = request.getParameter("noSeri");
            String jumlah           = request.getParameter("jumlah");
            int tglKadaluarsa       = Integer.valueOf(request.getParameter("tglKadaluarsa"));
            String dateTime         = sdfFormat.format(new Date());
            String token            = (String) request.getSession().getAttribute("tokenSession");            
            int tokenAvailable      = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){
                if(tglKadaluarsa > currentDate){
                    int addBarang = dao.addBarang(pilihKaryawan, namaBarang, merek, tipe, noSeri, jumlah, String.valueOf(tglKadaluarsa), dateTime);
                    if(addBarang == 1){
                        status  = 1;
                        desc    = "Sukses menambahkan data barang";
                    }else{
                        status  = 0;
                        desc    = "Gagal menambahkan data barang";
                    }
                }else{
                    jr.HashmapWithMessage(request, response, -1, "Tanggal kadaluarsa harus melebih tanggal sekarang");
                }
                
            }else{
                jr.HashmapWithMessage(request, response, 0, "Invalid Token !");
            }            
            jr.OutStatusAndDesc(request, response, status, desc);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doDeleteAsset(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doDeleteAsset");
        
        JsonReturn jr           = new JsonReturn();
        AssetDao dao             = new AssetDao();
        Util util               = new Util();    
        TokenDao tokenDao       = new TokenDao();
        int status              = 0;
        String desc             = "";
        
        try{
            String token        = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable  = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){
                int id          = Integer.valueOf(request.getParameter("id"));
                int deleteAsset = dao.deleteDataAsset(id);

                if(deleteAsset == 1){
                    status = 1;
                    desc = "Sukses delete data asset dengan id : "+id;
                }else{
                    status = 0;
                    desc = "Gagal delete data asset dengan id : "+id;
                }            
                jr.OutStatusAndDesc(request, response, status, desc); 
            }else{
                jr.OutStatusAndDesc(request, response, 0, "Invalid Token !");
            }                       
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doDataEditAsset(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doDataEditAsset");
        
        Collection getData;
        AssetDao dao        = new AssetDao();
        Util util           = new Util();
        JsonReturn jr       = new JsonReturn();
        TokenDao tokenDao   = new TokenDao();
        int status          = 0;
        String desc         = "";
                
        try{
            
            String id           = request.getParameter("id");            
            String token        = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable  = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){
                if(!util.isNullOrEmpty(id)){
                    getData = dao.getDataEdit(id);
                    status  = 1;
                    desc    = "Sukses";
                }else{
                    getData = null;
                    status  = 0;
                    desc    = "Data tidak ditemukan atau terjadi kesalahan";
                }
                
                jr.OutObject(request, response, status, desc, getData);   
                
            }else{
                
                jr.HashmapWithMessage(request, response, 0, "Invalid Token !");
                
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doEditAsset(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doEditAsset");
        
        AssetDao dao                = new AssetDao();
        JsonReturn jr               = new JsonReturn();
        SimpleDateFormat sdfFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
        int status                  = 0; 
        String desc                 = "";
        TokenDao tokenDao           = new TokenDao();
        SimpleDateFormat sdfFormat2 = new SimpleDateFormat("yyyyMMdd");
        sdfFormat2.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
        int currentDate             = Integer.valueOf(sdfFormat2.format(new Date()));
        Util util                   = new Util();
        
        try{
            
            String id               = request.getParameter("id");
            String pilihKaryawan    = request.getParameter("pilihKaryawan");
            String namaBarang       = request.getParameter("namaBarang");
            String merek            = request.getParameter("merek");
            String tipe             = request.getParameter("tipe");
            String noSeri           = request.getParameter("noSeri");
            String jumlah           = request.getParameter("jumlah");
            int tglKadaluarsa       = Integer.valueOf(request.getParameter("tglKadaluarsa"));
            String dateTime         = sdfFormat.format(new Date());    
            String token            = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable      = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){
                if(!util.isNullOrEmpty(id)){
                    if(tglKadaluarsa > currentDate){                        
                        int updateData = dao.updateBarang(pilihKaryawan, namaBarang, merek, tipe, noSeri, jumlah, String.valueOf(tglKadaluarsa), dateTime, id);
                        if(updateData == 1){
                            status  = 1;
                            desc    = "Sukses menambahkan data karyawan";
                        }else{
                            status  = 0;
                            desc    = "Gagal menambahkan data karyawan";
                        }
                        
                        jr.OutStatusAndDesc(request, response, status, desc);
                        
                    }else{
                        jr.HashmapWithMessage(request, response, -1, "Tanggal kadaluarsa harus melebih tanggal sekarang");
                    }
                    
                }else{
                    jr.HashmapWithMessage(request, response, 0, "Kesalahan Data");  
                }                
            }else{                
                jr.HashmapWithMessage(request, response, 0, "Invalid Token !");                
            }                 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doViewAsset(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doViewAsset");
        
        AssetDao dao            = new AssetDao();
        JsonReturn jr           = new JsonReturn();
        TokenDao tokenDao       = new TokenDao();
        String desc             = "";
        int status              = 0;
        Collection getData;
        
        try{
            
            String token        = (String) request.getSession().getAttribute("tokenSession");
            int tokenAvailable  = tokenDao.getTokenIdle(token);
            
            if(tokenAvailable == 1){        
                String id = request.getParameter("id");
                getData = dao.getDataViewAsset(id);

                if(getData != null){
                    status = 1;
                    desc = "Sukses ambil data";
                }else{
                    status = 0;
                    desc = "Gagal ambil data";
                }         

                jr.OutObject(request, response, status, desc, getData); 
                
            }else{
                jr.OutStatusAndDesc(request, response, 0, "Invalid Token !");
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */

}
