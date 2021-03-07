package com.dcm.crm.settings.web.controller;

import com.dcm.crm.settings.service.impl.UserServiceImpl;
import com.dcm.crm.settings.domain.User;
import com.dcm.crm.settings.service.UserService;
import com.dcm.crm.utils.MD5Util;
import com.dcm.crm.utils.PrintJson;
import com.dcm.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入到用户控制界面");
        String path=req.getServletPath();
        //resp.setContentType("text/html;charset=utf-8");
        if("/settings/user/login.do".equals(path)){
            System.out.println("进入用户验证状态");
            String loginAct= req.getParameter("loginAct");
            String loginPwd=MD5Util.getMD5(req.getParameter("loginPwd"));//MD5
            String ip=req.getRemoteAddr();
            System.out.println("-------ip:"+ip);
            UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());

            try {
                User user = userService.login(loginAct, loginPwd,ip);

                req.getSession().setAttribute("user", user);

                PrintJson.printJsonFlag(resp,true);
            }catch (Exception e){

                System.out.println("出错了");
                String msg=e.getMessage();
                Map<String ,Object> map=new HashMap<>();
                map.put("success",false);
                map.put("msg",msg);
                PrintJson.printJsonObj(resp,map);
            }



        }else if("/settings/user/xxx.do".equals(path)){

        }
    }
}
