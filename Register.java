package com.iotek.controller;

import com.iotek.dao.impl.UserDAOImpl;
import com.iotek.model.Users;
import com.iotek.utils.IDType;
import com.iotek.utils.RoundID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Tony on 2016/6/15.
 */
@WebServlet(name = "Register",urlPatterns = "/register.do")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userName = request.getParameter("userName");
        String userPass = request.getParameter("userPass");
        String userEmail = request.getParameter("userEmail");
        Users users = new Users();
        users.setUserID(RoundID.getID(IDType.USER));
        users.setUserName(userName);
        users.setUserPass(userPass);
        users.setUserEmail(userEmail);
        users.setUserImg("userAvatars/default1.jpg");
        UserDAOImpl userDAO = new UserDAOImpl();
        boolean flag = userDAO.addUsers(users);
        if (flag){
            request.getRequestDispatcher("pages/registerSuccess.jsp").forward(request,response);
        }else {
            request.getSession().setAttribute("registerMsg","服务器异常，请重试！");
            request.getRequestDispatcher("pages/register.jsp").forward(request,response);
        }
    }
}
