package com.dcm.crm.settings.dao;

import com.dcm.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {


    User selectOne(Map<String, String> map);

    List<User> selectAll();


}
