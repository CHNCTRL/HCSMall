package com.iotek.controller;

import com.iotek.dao.impl.UserDAOImpl;
import com.iotek.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Tony on 2016/6/15.
 */
@WebServlet(name = "Login",urlPatterns = "/login.do")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            response.getWriter().write("请打开cookie后在登录!");
        } else {
            String autoLogin = request.getParameter("autoLogin");
            if (null != autoLogin) {//自动登录
                String userName = "";
                String userPass = "";
                if (null != cookies && cookies.length != 0) {
                    for (Cookie cookie : cookies) {
                        userName = "userName".equals(cookie.getName()) ? cookie.getValue() : userName;
                        userPass = "userPass".equals(cookie.getName()) ? cookie.getValue() : userPass;
                    }
                    request.setAttribute("auto", "auto");
                    login(request, response, userName, userPass);
                }
            } else {//手动登录
                request.setAttribute("auto", "noAuto");
                String isSave = request.getParameter("isSave");//判断是否保存
                String userName = request.getParameter("userName");
                String userPass = request.getParameter("userPass");
                if ("true".equals(isSave)) {
                    Cookie cookieName = new Cookie("userName", userName);
                    cookieName.setMaxAge(60 * 60 * 24 * 14);
                    response.addCookie(cookieName);
                    Cookie cookiePass = new Cookie("userPass", userPass);
                    cookiePass.setMaxAge(60 * 60 * 24 * 14);
                    response.addCookie(cookiePass);
                    login(request, response, userName, userPass);
                } else {//不保存登录
                    login(request, response, userName, userPass);
                }
            }
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response, String userName, String userPass) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UserDAOImpl userDAO = new UserDAOImpl();
        Users users = userDAO.login(userName, userPass);
        HttpSession session = request.getSession();
        if (null != users) {
            session.setAttribute("loginUser", users);
            request.getRequestDispatcher("pages/loginSuccess.jsp").forward(request, response);
            System.out.println("userDTO:*****" + users);
        } else {
            session.setAttribute("errorMsg", "用户名或密码错误!");
            request.getRequestDispatcher("pages/login.jsp").forward(request, response);
        }
    }
}
