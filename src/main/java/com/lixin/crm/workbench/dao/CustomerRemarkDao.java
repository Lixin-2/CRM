package com.lixin.crm.workbench.dao;

import com.lixin.crm.workbench.domain.CustomerRemark;
import com.lixin.crm.workbench.service.CustomerService;

import java.util.List;

public interface CustomerRemarkDao {

    int deleteRemarkByCusIds(String[] id);

    List<CustomerRemark> selectCustomerRemarkByCusId(String customerId);

    int deleteRemarkById(String id);

    int updateCustomerRemark(CustomerRemark customerRemark);

    int insertCustomerRemark(CustomerRemark customerRemark);
}
