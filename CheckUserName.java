package com.iotek.controller;

import com.iotek.dao.impl.UserDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Tony on 2016/6/2.
 */
@WebServlet(name = "CheckUserName",urlPatterns = "/checkUserName.do")
public class CheckUserName extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        Set<String> userNameSet = userDAO.listUserNames();
        String userName = request.getParameter("userName");
        boolean flag = false;
        if (userNameSet.contains(userName)){
            flag=true;
        }
        if (flag){
            response.getWriter().write("false");
        }else {
            response.getWriter().write("true");
        }
    }
}
