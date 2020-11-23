package com.lixin.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.lixin.crm.settings.dao.UserDao;
import com.lixin.crm.settings.domain.User;
import com.lixin.crm.workbench.dao.CustomerDao;
import com.lixin.crm.workbench.dao.CustomerRemarkDao;
import com.lixin.crm.workbench.domain.Customer;
import com.lixin.crm.workbench.exception.CustomerException;
import com.lixin.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao = null;
    @Autowired
    UserDao userDao = null;
    @Autowired
    CustomerRemarkDao customerRemarkDao = null;


    @Override
    public Map<String, Object> selectCustomerPageList(Integer pageNo, Integer pageSize, Customer customer) {
        int total = customerDao.selectCountTotal(customer);
        PageHelper.startPage(pageNo,pageSize);
        List<Customer> customers = customerDao.selectCustomerPageList(customer);
        Map<String,Object> customerPageList = new HashMap<>();
        customerPageList.put("dataList",customers);
        customerPageList.put("total",total);
        return customerPageList;
    }

    @Override
    public List<User> selectUsers() {
        List<User> users = userDao.selectUsers();
        return users;
    }

    @Override
    public void insertCustomer(Customer customer) {
        int num = customerDao.insertCustacts(customer);
        if (num!=1){
            throw new CustomerException("添加客户信息失败！");
        }
    }

    @Override
    public Customer selectCustomerById(String id) {
        Customer customer = customerDao.selectCustomerById(id);
        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) {
        int num = customerDao.updateCustomer(customer);
        if(num!=1){
            throw new CustomerException("更新客户信息失败！");
        }
    }

    @Override
    public void deleteCustomerByIds(String[] id) {
        customerRemarkDao.deleteRemarkByCusIds(id);
        int num = customerDao.deleteCustomerByIds(id);
        if(num!=id.length){
            throw new CustomerException("删除客户信息失败!");
        }
    }

    @Override
    public Customer selectCustomerByIdForOwner(String id) {
        Customer customer = customerDao.selectCustomerByIdForOwner(id);
        return customer;
    }
}
