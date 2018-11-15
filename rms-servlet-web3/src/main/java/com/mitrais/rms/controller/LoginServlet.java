package com.mitrais.rms.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mitrais.rms.dao.UserDao;
import com.mitrais.rms.dao.impl.UserDaoImpl;

import java.io.IOException;
import java.util.Date;

@WebServlet("/login/*")
public class LoginServlet extends AbstractController
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	try{
    		String URLpath = req.getPathInfo().toUpperCase();
    		switch(URLpath) {
	        	case "/INDEX":
		    		index(req, resp);
		    		break;
	        	case "/LOGOUT":
	        		logout(req, resp);
	        		break;
	        }
    		
    	}
    	catch (NullPointerException e) {
    		resp.sendRedirect(req.getContextPath() + "/login/index");
		}
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NullPointerException
    {
        String URLpath = req.getPathInfo();
        switch(URLpath.toUpperCase()) {
        	case "/AUTH":
        		auth(req, resp);
        		break;
        }
    }

    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	String path = getTemplatePath(req.getServletPath());
    	RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
    	requestDispatcher.forward(req, resp);
    }

    public void auth(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	String username = req.getParameter("userku");
    	String password = req.getParameter("passku");
    	UserDao userDao = UserDaoImpl.getInstance();
    	
    	
    	Boolean verified = userDao.authentication(username, password);
    	if(verified) {
    		HttpSession mySession = req.getSession();
    		mySession.setAttribute("userName", username);
    		mySession.setAttribute("isLogin", true);
    		Date timeNow = new Date();
    		mySession.setAttribute("loginAt", timeNow.getTime());
    		mySession.setMaxInactiveInterval(30*60);
    		resp.sendRedirect(req.getContextPath() + "/home/index");

    	}
    	else {
    		req.setAttribute("message", "Username or password doesn't match");
    		String path = getTemplatePath("/login");
    		req.getRequestDispatcher(path).forward(req, resp);
    	}
    }
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	HttpSession mySession = req.getSession();
    	mySession.invalidate();
    	resp.sendRedirect(req.getContextPath() + "/login/index");
    }
}
