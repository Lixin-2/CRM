package com.lixin.crm.workbench.web.controller;

import com.github.pagehelper.PageHelper;
import com.lixin.crm.settings.domain.User;
import com.lixin.crm.settings.service.UserService;
import com.lixin.crm.utils.DateTimeUtil;
import com.lixin.crm.utils.UUIDUtil;
import com.lixin.crm.workbench.domain.Activity;
import com.lixin.crm.workbench.domain.ActivityRemark;
import com.lixin.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService = null;
    @Autowired
    private UserService userService = null;

    @RequestMapping("/getUsers.do")
    @ResponseBody
    public List<User> getUsers(){
        List<User> users = userService.selectUsers();
        return users;
    }

    @RequestMapping("/saveActivity.do")
    @ResponseBody
    private Map<String,Object> saveActivity(HttpServletRequest request,Activity activity){
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activityService.insertActivity(activity);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    private Map<String,Object> pageList(Integer pageNo,Integer pageSize,Activity activity){
        Map<String,Object> activityPageList = activityService.selectActivityPageList(pageNo,pageSize,activity);
        return activityPageList;
    }

    @RequestMapping("/deleteActivityByIds.do")
    @ResponseBody
    private Map<String,Object> deleteActivityByIds(String[] id){
        activityService.deleteActivityByIds(id);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/getUsersAndActivityById.do")
    @ResponseBody
    private Map<String,Object> getUsersAndActivityById(String id){
        Map<String,Object> usersAndActivity = new HashMap<>();
        usersAndActivity.put("users",userService.selectUsers());
        usersAndActivity.put("activity",activityService.selectActivityById(id));
        return usersAndActivity;
    }


    @RequestMapping("/updateActivity.do")
    @ResponseBody
    private Map<String,Object> updateActivity(HttpServletRequest request ,Activity activity){
        activity.setEditBy(((User) request.getSession().getAttribute("user")).getName());
        activity.setEditTime(DateTimeUtil.getSysTime());
        activityService.updateActivity(activity);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/selectActivityById.do")
    private String selectActivityById(HttpServletRequest request,String id){
        Activity activity = activityService.selectActivityByIdForOwner(id);
        request.setAttribute("activity",activity);
        return "forward:/workbench/activity/detail.jsp";
    }

    @RequestMapping("/getRemarkListByAid.do")
    @ResponseBody
    private List<ActivityRemark> getRemarkListByAid(String activityId){
        List<ActivityRemark> activityRemarks = activityService.selectActivityRemarkByAId(activityId);
        return activityRemarks;
    }

    @RequestMapping("/deleteRemarkById.do")
    @ResponseBody
    private Map<String,Object> deleteRemarkById(String id){
        activityService.deleteActivityRemarkById(id);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/saveActivityRemark.do")
    @ResponseBody
    private Map<String,Object> saveActivityRemark(HttpServletRequest request,ActivityRemark activityRemark){
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("0");
        activityService.insertActivityRemark(activityRemark);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        info.put("activityRemark",activityRemark);
        return info;
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    private Map<String,Object> updateRemark(HttpServletRequest request,ActivityRemark activityRemark){
        activityRemark.setEditBy(((User) request.getSession().getAttribute("user")).getName());
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("1");
        activityService.updateActivityRemark(activityRemark);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        info.put("activityRemark",activityRemark);
        return info;
    }


}
