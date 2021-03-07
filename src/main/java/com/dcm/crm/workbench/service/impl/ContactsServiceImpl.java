package com.dcm.crm.workbench.service.impl;

import com.dcm.crm.workbench.domain.Contacts;
import com.dcm.crm.utils.SqlSessionUtil;
import com.dcm.crm.workbench.dao.ContactsDao;
import com.dcm.crm.workbench.service.ContactsService;

import java.util.List;

public class ContactsServiceImpl implements ContactsService {
    private ContactsDao contactsDao= SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    @Override
    public List<Contacts> getContactsByName(String name) {
       return  contactsDao.getContactsByName(name);

    }
}
