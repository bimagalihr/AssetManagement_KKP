/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkp.mapping;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bimagalihr
 */
public class Asset extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            try{
                response.setHeader("Access-Control-Allow-Origin", "*");
                String doing = request.getParameter("url");  
                System.out.println("doing : "+doing);
                if(doing != null){
                    switch(doing){
                        case "list-asset":
                            doListAsset(request, response);
                            break;
                        case "add-asset":
                            doAddAsset(request, response);
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

    private void doListAsset(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("./asset-list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAddAsset(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("./asset-add.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
