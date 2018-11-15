package com.mitrais.rms.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home/index")
public class HomeServlet extends AbstractController
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	String path = getTemplatePath(req.getServletPath());
    	RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
    	req.setAttribute("title", "Welcome, " + req.getSession().getAttribute("userName"));
    	requestDispatcher.forward(req, resp);
    }
}
