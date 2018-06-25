package com.kkp.shared;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

public class JsonReturn {

    Gson gson = new Gson();
    HashMap hashMap = new HashMap();
    Constant constant = new Constant();
    Util util = new Util();

    public void GetObject(HttpServletRequest request, HttpServletResponse response, Object obj) throws IOException {	
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("rows", obj);
            out.println(gson.toJson(hashMap));			
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            out.close();
        }
    }

    public void HashmapWithMessage(HttpServletRequest request, HttpServletResponse response, int Status, String Message) throws IOException {
    PrintWriter out = response.getWriter();
    hashMap.clear();
    try {
        hashMap.put("status", Status);
        if ("1".equalsIgnoreCase(String.valueOf(Status))) {
            hashMap.put("message", Constant.success);
            hashMap.put("description",Message);
        } else {
            hashMap.put("message", Constant.failed);
            hashMap.put("description",Message);
        }
        out.println(gson.toJson(hashMap));
    } catch (Exception e) {
        e.printStackTrace();
        // TODO: handle exception
    } finally {
        out.close();
    }
}

    public void GridObject(HttpServletRequest request, HttpServletResponse response, Collection Data) throws IOException {	
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("total", Data.size());
            hashMap.put("rows", Data);
            out.println(gson.toJson(hashMap));
        } catch (Exception e) {
            e.printStackTrace();
                // TODO: handle exception
        } finally {
            out.close();
        }
    }

    public void GridObjectServerSide(HttpServletRequest request, HttpServletResponse response, Collection Data, int total) throws IOException {	
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("total", total);
            hashMap.put("rows", Data);
            out.println(gson.toJson(hashMap));
        } catch (Exception e) {
            e.printStackTrace();
                // TODO: handle exception
        } finally {
            out.close();
        }
    }


    public void ComboObject(HttpServletRequest request, HttpServletResponse response, Collection Data) throws IOException {	
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("status", Constant.codeSuccess);
            hashMap.put("data", Data);
            out.println(gson.toJson(hashMap));
        } catch (Exception e) {
            e.printStackTrace();
                // TODO: handle exception
        } finally {
            out.close();
        }
    }


    public void FormObject(HttpServletRequest request, HttpServletResponse response, int Status, Long Id) throws IOException {
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("status", Status);
            hashMap.put("id", Id);
            if ("1".equalsIgnoreCase(String.valueOf(Status))) {
                hashMap.put("message", Constant.success);
            } else {
                hashMap.put("message", Constant.failed);
            }
            out.println(gson.toJson(hashMap));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public void FormObjectWithDesc(HttpServletRequest request, HttpServletResponse response, int Status, String Id,String Desc) throws IOException {
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("status", Status);
            hashMap.put("id", Id);
            hashMap.put("idVal", Desc);
            if ("1".equalsIgnoreCase(String.valueOf(Status))) {
                    hashMap.put("message", Constant.success);
            } else {
                    hashMap.put("message", Constant.failed);
                    hashMap.put("description",Desc);
            }
            out.println(gson.toJson(hashMap));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }


    public void GetFormObject(HttpServletRequest request, HttpServletResponse response, HashMap Data) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            String Status = (String) Data.get("status");
            if ("1".equalsIgnoreCase(Status)) {
                Data.put("message", constant.success);
            } else {
                Data.put("message", Constant.failed);
            }
            out.println(gson.toJson(Data));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public void DeleteObject(HttpServletRequest request, HttpServletResponse response, int Status) throws IOException {
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("status", Status);
            if ("1".equalsIgnoreCase(String.valueOf(Status))) {
                hashMap.put("message", Constant.success);
            } else {
                hashMap.put("message", Constant.failed);
            }
            out.println(gson.toJson(hashMap));
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            out.close();
        }
    }

    public void OutObject(HttpServletRequest request, HttpServletResponse response,int Status,String desc, Collection Data) throws IOException {
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("status", Status);
            if ("1".equalsIgnoreCase(String.valueOf(Status))) {
                hashMap.put("message", desc);
                hashMap.put("data", Data);
            } else {
                hashMap.put("message", desc);
                hashMap.put("description",desc);
            }
           out.println(gson.toJson(hashMap));
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            out.close();
        }
    }

    public void OutStatusAndDesc(HttpServletRequest request, HttpServletResponse response, int Status,String desc ) throws IOException {
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("status", Status);
            if ("1".equalsIgnoreCase(String.valueOf(Status))) {
                hashMap.put("message", Constant.success);
                hashMap.put("description",desc);
            } else {
                hashMap.put("message", Constant.failed);
                hashMap.put("description",desc);
            }
            out.println(gson.toJson(hashMap));
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            out.close();
        }
    }

    public void OutStatusAndDescCustom(HttpServletRequest request, HttpServletResponse response, int Status,String desc, String IdSend, String PolicyNo) throws IOException {
        PrintWriter out = response.getWriter();
        hashMap.clear();
        try {
            hashMap.put("status", Status);
            if ("1".equalsIgnoreCase(String.valueOf(Status))) {
                hashMap.put("message", Constant.success);
                hashMap.put("description",desc);
                hashMap.put("id", IdSend);
                hashMap.put("policyno", PolicyNo);
            } else {
                hashMap.put("message", Constant.failed);
                hashMap.put("description",desc);
                hashMap.put("id", IdSend);
                hashMap.put("policyno", PolicyNo);
            }
            out.println(gson.toJson(hashMap));
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
                out.close();
        }
    }
}

