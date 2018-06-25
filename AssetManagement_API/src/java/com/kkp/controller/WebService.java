/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.controller;

import com.google.gson.Gson;
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
                    case "data-list-karyawan":
                        doDataListKaryawan(request, response);
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
        Gson gson = new Gson();
        HashMap hashMap = new HashMap();
        JsonReturn jsonreturn = new JsonReturn();
        Collection listMaster = new ArrayList<>();
                
        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
                
        int insertToken = 0;
        int status = 0;
        String desc = "";
        
        try{           
            String dateTime = sdfFormat.format(new Date());
            String token = UUID.randomUUID().toString();
            String emailLogin = request.getParameter("emailLogin");
            String passwordLogin = request.getParameter("passwordLogin");
            String passwordEncrypt = Util.encrypt(passwordLogin);
            
            System.out.println("emailLogin : "+emailLogin);
            System.out.println("passwordEncrypt : "+passwordEncrypt);
            
            LoginDao dao = new LoginDao();
            String userSelect = dao.getLogin(emailLogin, passwordEncrypt);
            
            if(!Util.isNullOrEmpty(userSelect)){
                if(!Util.isNullOrEmpty(emailLogin)){
                    TokenDao tokenDao = new TokenDao();
                    insertToken = tokenDao.writeToken(token, emailLogin, dateTime);
                    if(insertToken == 1){
                        request.getSession(true).setAttribute("tokenSession", token);
                        request.getSession(true).setAttribute("fullNameLogin", userSelect);
                        System.out.println("fullNameLogin : "+userSelect);
                        
                        String tokenSession = (String) request.getSession().getAttribute("tokenSession");
                        System.out.println("CurrentSession : "+tokenSession);
                        
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
        Gson gson = new Gson();
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
        
        int status = 0;
        String desc = "";
        try{
            String fullName = (String) request.getSession().getAttribute("fullNameLogin");
            String token    = (String) request.getSession().getAttribute("tokenSession");
            System.out.println("fullname : "+fullName);
            System.out.println("token : "+token);
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
        Gson gson = new Gson();
        HashMap hashMap = new HashMap();
        HashMap hashMapSub = new HashMap(); 
        System.out.println("doLogout");
        PrintWriter out = response.getWriter();
        try{
            request.getSession().invalidate();
            hashMap.put("status", 1);
            out.println(gson.toJson(hashMap));
            hashMap.clear();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doListUserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Masuk doListUserLogin");
        JsonReturn jsonreturn = new JsonReturn();
        UserDao dao = new UserDao();
        Util util = new Util();
        JsonReturn jsonReturn = new JsonReturn();
        Gson gson = new Gson();
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
            
            if(token != null){
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
        HashMap hashMap = new HashMap();
        JsonReturn jsonreturn = new JsonReturn();
        UserDao dao = new UserDao();
        Util util = new Util();
        
        int status = 0;
        String desc = "";
        
        try{
            int id = Integer.valueOf(request.getParameter("id"));
            System.out.println("id : "+id);
            int deleteUser = dao.deleteUser(id);
            if(deleteUser == 1){
                status = 1;
                desc = "Sukses delete user dengan id user : "+id;
            }else{
                status = 0;
                desc = "Gagal delete user dengan id user : "+id;
            }
            
            jsonreturn.OutStatusAndDesc(request, response, status, desc);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doEditUser(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("masuk doEditUser");
        UserDao dao = new UserDao();
        JsonReturn jsonReturn = new JsonReturn();
        Collection getData;
        String desc = "";
        int status = 0;
        try{
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
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doUserEdit(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Masuk doUserEdit");
        Util util = new Util();
        UserDao dao = new UserDao();
        JsonReturn jsonReturn = new JsonReturn();
        
        int status = 0;
        String desc = "";
        try{
            SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
            String createDate = sdfFormat.format(new Date());
            
            int id = Integer.valueOf(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String pass = util.encrypt(request.getParameter("pass"));
            
            int editUser = dao.editUser(email, pass, fullName, createDate, id);
            
            System.out.println("status editUser : "+editUser);
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
        UserDao dao = new UserDao();
        JsonReturn jsonReturn = new JsonReturn();
        Util util = new Util();
        
        int status  = 0;
        String desc = "";
        try{
            SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
            String createDate = sdfFormat.format(new Date());
            
            String fullName = request.getParameter("fullName");
            String email    = request.getParameter("email");
            String password = util.encrypt(request.getParameter("pass"));
            
            int addUser = dao.addUserLogin(fullName, email, password, createDate);
            
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
        KaryawanDao dao = new KaryawanDao();
        JsonReturn jsonReturn = new JsonReturn();
        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        sdfFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Bangkok"));
        int status  = 0; 
        String desc = "";
        try{
            String namaLengkap = request.getParameter("namaLengkap");
            String nomorHp = request.getParameter("nomorHp");
            String email = request.getParameter("email");
            String idKaryawan = request.getParameter("idKaryawan");
            String nomorKtp = request.getParameter("nomorKtp");
            String alamatKtp = request.getParameter("alamatKtp");
            String dateTime = sdfFormat.format(new Date());
            
            System.out.println("namaLengkap : "+namaLengkap);
            System.out.println("nomorHp : "+nomorHp);
            System.out.println("email : "+email);
            System.out.println("idKaryawan : "+idKaryawan);
            System.out.println("nomorKtp : "+nomorKtp);
            System.out.println("alamatKtp : "+alamatKtp);
            
            int addDataKaryawan = dao.addKaryawan(namaLengkap, nomorHp, email, idKaryawan, nomorKtp, alamatKtp, dateTime);
            if(addDataKaryawan == 1){
                status  = 1;
                desc    = "Sukses menambahkan data karyawan";
            }else{
                status  = 0;
                desc    = "Gagal menambahkan data karyawan";
            }
            
            jsonReturn.OutStatusAndDesc(request, response, status, desc);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doDataListKaryawan(HttpServletRequest request, HttpServletResponse response) {
        try{
            
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
