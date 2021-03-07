package com.dcm.crm.workbench.dao;

import com.dcm.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {

    int addContacts(Contacts contacts);

    List<Contacts> getContactsByName(String name);
}
