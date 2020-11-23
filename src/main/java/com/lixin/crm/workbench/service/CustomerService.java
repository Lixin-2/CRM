package com.lixin.crm.workbench.service;

import com.lixin.crm.settings.domain.User;
import com.lixin.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    Map<String, Object> selectCustomerPageList(Integer pageNo, Integer pageSize, Customer customer);

    List<User> selectUsers();

    void insertCustomer(Customer customer);

    Customer selectCustomerById(String id);

    void updateCustomer(Customer customer);

    void deleteCustomerByIds(String[] id);

    Customer selectCustomerByIdForOwner(String id);
}
