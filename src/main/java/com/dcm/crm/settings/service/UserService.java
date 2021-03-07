package com.dcm.crm.settings.service;

import com.dcm.crm.exception.LoginException;
import com.dcm.crm.settings.domain.User;

import java.util.List;

public interface UserService {

    User login(String loginAct,String loginPwd,String ip) throws LoginException;

    List<User> findAll();


}
