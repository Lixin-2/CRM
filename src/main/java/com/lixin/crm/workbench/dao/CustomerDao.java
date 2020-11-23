package com.lixin.crm.workbench.dao;

import com.lixin.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    int insertCustacts(Customer customer);

    int selectCountTotal(Customer customer);

    List<Customer> selectCustomerPageList(Customer customer);

    Customer selectCustomerById(String id);

    int updateCustomer(Customer customer);

    int deleteCustomerByIds(String[] id);

    Customer selectCustomerByIdForOwner(String id);
}
