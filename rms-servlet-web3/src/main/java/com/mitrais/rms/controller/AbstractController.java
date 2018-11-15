package com.mitrais.rms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class AbstractController extends HttpServlet
{
    public static final String VIEW_PREFIX = "/WEB-INF/jsp";
    public static final String VIEW_SUFFIX = ".jsp";

    AbstractController(){
    	
    }
    
    protected String getTemplatePath(String path)
    {
        if (path.equalsIgnoreCase("/"))
        {
            return VIEW_PREFIX + path + "index" + VIEW_SUFFIX;
        }
        else
        {
            return VIEW_PREFIX + path + VIEW_SUFFIX;
        }
    }
    
    protected Boolean checkSession(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession mySession = req.getSession();
    	
    	try {
    		String isLogin = mySession.getAttribute("isLogin").toString();
    		return true;
//    		resp.getWriter().write("<script> alert('"+isLogin+"'); </script>");
    	}
    	catch(NullPointerException ex) {
    		System.out.println("session invalid");
//    		resp.sendRedirect(req.getContextPath() + "/login/index");
    		return false;
    	}
    }
}
