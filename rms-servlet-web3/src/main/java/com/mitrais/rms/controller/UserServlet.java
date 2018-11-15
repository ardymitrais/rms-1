package com.mitrais.rms.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mitrais.rms.dao.UserDao;
import com.mitrais.rms.dao.impl.UserDaoImpl;
import com.mitrais.rms.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/users/*")
public class UserServlet extends AbstractController{
	String pathLayout = getTemplatePath("/home/index");
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	// check session
    	Boolean sessionValid = checkSession(req, resp);
    	if(!sessionValid) {
    		resp.sendRedirect(req.getContextPath() + "/login/index");
    		return;
    	}
    	
    	
    	String URLpath = req.getPathInfo();
        switch(URLpath.toUpperCase()) {
        	case "/LIST":
        		list(req, resp);
        		break;
        	case "/ADD":
        		add(req, resp);
        		break;
        	case "/EDIT":
        		edit(req, resp);
        		break;
        }
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	// check session
    	Boolean sessionValid = checkSession(req, resp);
    	if(!sessionValid) {
    		resp.sendRedirect(req.getContextPath() + "/login/index");
    		return;
    	}
    	
    	String URLpath = req.getPathInfo();
        switch(URLpath.toUpperCase()) {
        	case "/DELETE":
        		delete(req, resp);
        		break;
        	case "/SAVE":
        		save(req, resp);
        		break;
        }
    	
    	
    	
    }
    
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{ 
        String pathContent = getTemplatePath(req.getServletPath()+"/form");
        req.setAttribute("title", "Add User");
        req.setAttribute("mainContent", pathContent);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(pathLayout);
        requestDispatcher.forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{ 
    	
    	String id = req.getParameter("id");
    	UserDao userDao = UserDaoImpl.getInstance();
    	
    	Optional<User> userData = userDao.find(Long.parseLong(id));
    	req.setAttribute("user",userData.get());
        String pathContent = getTemplatePath(req.getServletPath()+"/form");
        req.setAttribute("title", "Edit User");
        req.setAttribute("mainContent", pathContent);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(pathLayout);
        requestDispatcher.forward(req, resp);        
    }

    
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	    UserDao userDao = UserDaoImpl.getInstance();
        List<User> users = userDao.findAll();
        req.setAttribute("users", users);
        
        String pathContent = getTemplatePath(req.getServletPath()+req.getPathInfo());
        req.setAttribute("title", "List User");
        req.setAttribute("mainContent", pathContent);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(pathLayout);
        requestDispatcher.forward(req, resp);
    }
    
        
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	String jsonFromJsp = req.getParameter("id");
    	Gson myGson = new Gson();
    	String[] idUser = myGson.fromJson(jsonFromJsp, String[].class);

    	UserDao userDao = UserDaoImpl.getInstance();
    	
    	
    	ArrayList<Boolean> responeArr = new ArrayList<Boolean>();
    	
    	for(Integer pageNumber=0; pageNumber< idUser.length; pageNumber++) {
    		User deleteUser = new User( Long.parseLong(idUser[pageNumber]),"","");
    		Boolean respone = userDao.delete(deleteUser);
    		responeArr.add(respone);
    	}
    	String jsonRespon = new Gson().toJson(responeArr);

    	resp.getWriter().write(jsonRespon);
    }
    
    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    	String id = req.getParameter("id");
    	String userName = req.getParameter("username");
    	String password = req.getParameter("userpass");
    	
    	
    	UserDao userDao = UserDaoImpl.getInstance();
    	if(id=="") {
    		User newUser = new User(Long.parseLong("0"), userName, password);
    		userDao.save(newUser);
    	}
    	else {
    		User updateUser = new User(Long.parseLong(id), userName, password);
    		userDao.update(updateUser);
    	}
    	resp.sendRedirect(req.getContextPath() + "/users/list");
    }
}
