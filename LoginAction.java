package com.iotek.controller;

import com.iotek.dao.impl.UserDAOImpl;
import com.iotek.model.Users;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/15.
 */
public class LoginAction implements Action {
    private String userName;
    private String userPass;

    private Map<String,Object> sessionMap = null;
    private Map<String,Object> applicationMap = null;
    private Map<String,Object> requestMap = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @Override
    public String toString() {
        return "LoginAction{" +
                "userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", sessionMap=" + sessionMap +
                ", applicationMap=" + applicationMap +
                ", requestMap=" + requestMap +
                '}';
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        sessionMap = actionContext.getSession();
        applicationMap = actionContext.getApplication();
       /* if (userName.equals("struts2")){
            return SUCCESS;
        }else {
            return INPUT;
        }*/
        Object num = applicationMap.get("onlineNum");//获得当前人数
        UserDAOImpl userDAO = new UserDAOImpl();
        Users users = userDAO.login(getUserName(),getUserPass());
        if (null != users){
            if (null != num) {
                sessionMap.put("loginUser",users);
                int lineNum = Integer.parseInt(num.toString());
                applicationMap.put("onlineNum", lineNum + 1);
            }else{
                applicationMap.put("onlineNum", 1);
            }
            return SUCCESS;
        }else {
            return INPUT;
        }
    }

    public String logout() throws Exception{
        applicationMap = ActionContext.getContext().getApplication();
        sessionMap = ActionContext.getContext().getSession();
        int onlineNum = Integer.parseInt(applicationMap.get("onlineNum").toString());
        System.out.println(onlineNum);
        applicationMap.put("onlineNum",onlineNum-1);
        if (sessionMap instanceof SessionMap){
            ((SessionMap<String, Object>) sessionMap).invalidate();//使session失效
        }
        return SUCCESS;
    }
}
