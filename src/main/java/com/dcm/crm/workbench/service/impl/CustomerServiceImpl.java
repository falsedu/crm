package com.dcm.crm.workbench.service.impl;

import com.dcm.crm.workbench.dao.CustomerDao;
import com.dcm.crm.workbench.service.CustomerService;
import com.dcm.crm.utils.SqlSessionUtil;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {


    private CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);


    @Override
    public List<String> getCustomerNameByName(String name) {


        List<String> sList= customerDao.getCustomerNameByName(name);

        return sList;
    }
}
