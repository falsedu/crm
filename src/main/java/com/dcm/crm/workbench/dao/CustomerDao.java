package com.dcm.crm.workbench.dao;

import com.dcm.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    int addCustomer(Customer customer);

    Customer getCustomerByName(String company);

    List<String> getCustomerNameByName(String name);
}
