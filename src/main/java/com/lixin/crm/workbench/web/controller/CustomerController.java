package com.lixin.crm.workbench.web.controller;

import com.lixin.crm.settings.domain.User;
import com.lixin.crm.utils.DateTimeUtil;
import com.lixin.crm.utils.UUIDUtil;
import com.lixin.crm.workbench.domain.Clue;
import com.lixin.crm.workbench.domain.Customer;
import com.lixin.crm.workbench.domain.CustomerRemark;
import com.lixin.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService = null;

    @RequestMapping("/pageList.do")
    @ResponseBody
    public Map<String,Object> pageList(Integer pageNo, Integer pageSize, Customer customer){
        Map<String,Object> customerPageList = customerService.selectCustomerPageList(pageNo,pageSize,customer);
        return customerPageList;
    }

    @RequestMapping("/getUsers.do")
    @ResponseBody
    public List<User> getUsers(){
        List<User> users = customerService.selectUsers();
        return users;
    }

    @RequestMapping("/saveCustomer.do")
    @ResponseBody
    public Map<String,Object> saveCustomer(HttpServletRequest request,Customer customer){
        customer.setId(UUIDUtil.getUUID());
        customer.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        customer.setCreateTime(DateTimeUtil.getSysTime());
        customerService.insertCustomer(customer);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;

    }

    @RequestMapping("/getUsersAndCustomerById.do")
    @ResponseBody
    public Map<String,Object> getUsersAndCustomerById(String id){
        Map<String,Object> usersAndCus = new HashMap<>();
        usersAndCus.put("users",customerService.selectUsers());
        usersAndCus.put("customer",customerService.selectCustomerById(id));
        return usersAndCus;
    }

    @RequestMapping("/updateCustomer.do")
    @ResponseBody
    public Map<String,Object> updateCustomer(HttpServletRequest request,Customer customer){
        customer.setEditBy(((User) request.getSession().getAttribute("user")).getName());
        customer.setEditTime(DateTimeUtil.getSysTime());
        customerService.updateCustomer(customer);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/deleteCustomerByIds.do")
    @ResponseBody
    public Map<String,Object> deleteCustomerByIds(String[] id){
        customerService.deleteCustomerByIds(id);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }


    @RequestMapping("/selectCustomerById.do")
    public String selectCustomerById(HttpServletRequest request,String id){
        Customer customer = customerService.selectCustomerByIdForOwner(id);
        request.setAttribute("customer",customer);
        return "forward:/workbench/customer/detail.jsp";
    }

    @RequestMapping("/getRemarkListByCusId.do")
    @ResponseBody
    public List<CustomerRemark> getRemarkListByCusId(String customerId){
        List<CustomerRemark> customerRemarks = customerService.selectCustomerRemarkByCusId(customerId);
        return customerRemarks;
    }

    @RequestMapping("/deleteRemarkById.do")
    @ResponseBody
    public Map<String,Object> deleteRemarkById(String id){
        customerService.deleteCustomerRemarkById(id);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(HttpServletRequest request,CustomerRemark customerRemark){
        customerRemark.setEditBy(((User) request.getSession().getAttribute("user")).getName());
        customerRemark.setEditTime(DateTimeUtil.getSysTime());
        customerRemark.setEditFlag("1");
        customerService.updateCustomerRemark(customerRemark);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        info.put("customerRemark",customerRemark);
        return info;
    }

    @RequestMapping("/saveCustomerRemark.do")
    @ResponseBody
    public Map<String,Object> saveCustomerRemark(HttpServletRequest request,CustomerRemark customerRemark){
        customerRemark.setId(UUIDUtil.getUUID());
        customerRemark.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        customerRemark.setCreateTime(DateTimeUtil.getSysTime());
        customerRemark.setEditFlag("0");
        customerService.insertCustomerRemark(customerRemark);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        info.put("customerRemark",customerRemark);
        return info;
    }
}
