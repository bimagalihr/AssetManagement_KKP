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
public class User extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            String doing = request.getParameter("url");  
            System.out.println("doing : "+doing);
            if(doing != null){
                switch(doing){
                    case "view-user":
                        doViewUse(request, response);
                        break;                      
                    case "list-user":
                        doListUser(request, response);
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

    private void doViewUse(HttpServletRequest request, HttpServletResponse response) {
        try{
            request.getRequestDispatcher("./user-view.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void doListUser(HttpServletRequest request, HttpServletResponse response) {
        try{
            request.getRequestDispatcher("./user.jsp").forward(request, response);
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
