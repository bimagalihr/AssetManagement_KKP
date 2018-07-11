/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.mapping;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bimagalihr
 */
public class Karyawan extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            String doing = request.getParameter("url");  
            System.out.println("doing : "+doing);
            if(doing != null){
                switch(doing){
                    case "add-karyawan":
                        doAddKaryawan(request, response);
                        break; 
                    case "edit-karyawan":
                        doEditKaryawan(request, response);
                        break; 
                    case "list-karyawan":
                        doListKaryawan(request, response);
                        break; 
                    case "view-karyawan":
                        doViewKaryawan(request, response);
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

    private void doAddKaryawan(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("./karyawan-add.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doListKaryawan(HttpServletRequest request, HttpServletResponse response) {
        try{
            request.getRequestDispatcher("./karyawan-list.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doEditKaryawan(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("./karyawan-edit.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doViewKaryawan(HttpServletRequest request, HttpServletResponse response) {
        try{
            request.getRequestDispatcher("./karyawan-view.jsp").forward(request, response);
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
