package com.dcm.crm.settings.service.impl;

import com.dcm.crm.exception.LoginException;
import com.dcm.crm.settings.dao.UserDao;
import com.dcm.crm.settings.domain.User;
import com.dcm.crm.settings.service.UserService;
import com.dcm.crm.utils.DateTimeUtil;
import com.dcm.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {


    private UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd,String ip) throws LoginException {
        Map<String,String> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.selectOne(map);
        if(user==null){
            throw new LoginException("用户名或密码错误");

        }
        String expireTime=user.getExpireTime();
        String currTime= DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currTime)<0){
            throw new LoginException("账号过期");
        }
        String lockState = user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("账号已经锁定");
        }

        String allowIps = user.getAllowIps();
        System.out.println("allowIps:"+allowIps);
        if(!allowIps.contains(ip)){
            throw new LoginException("ip非法");
        }


        return user;
    }

    @Override
    public List<User> findAll() {
        return userDao.selectAll();
    }


}
