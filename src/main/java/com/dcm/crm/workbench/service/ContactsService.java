package com.dcm.crm.workbench.service;

import com.dcm.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsService {
    List<Contacts> getContactsByName(String name);
}
