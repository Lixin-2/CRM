package com.lixin.crm.workbench.web.controller;

import com.lixin.crm.settings.domain.User;
import com.lixin.crm.utils.DateTimeUtil;
import com.lixin.crm.utils.UUIDUtil;
import com.lixin.crm.workbench.domain.Activity;
import com.lixin.crm.workbench.domain.ActivityRemark;
import com.lixin.crm.workbench.domain.Clue;
import com.lixin.crm.workbench.domain.ClueRemark;
import com.lixin.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    @Autowired
    private ClueService clueService = null;

    @RequestMapping("/getUsers.do")
    @ResponseBody
    private List<User> getUserList(){
        List<User> users = clueService.selectUsers();
        return users;
    }

    @RequestMapping("/saveClue.do")
    @ResponseBody
    private Map<String,Object> saveClue(HttpServletRequest request,Clue clue){
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clueService.insertClue(clue);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    private Map<String,Object> pageList(Integer pageNo,Integer pageSize,Clue clue){
        Map<String,Object> cluePageList = clueService.selectCluePageList(pageNo,pageSize,clue);
        return cluePageList;
    }

    @RequestMapping("/deleteClueByIds.do")
    @ResponseBody
    private Map<String,Object> deleteClueByIds(String[] id){
        clueService.deleteClueByIds(id);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/getUsersAndClueById.do")
    @ResponseBody
    private Map<String,Object> getUsersAndClueById(String id){
        Map<String,Object> usersAndClue = new HashMap<>();
        usersAndClue.put("users",clueService.selectUsers());
        usersAndClue.put("clue",clueService.selectClueById(id));
        return usersAndClue;
    }


    @RequestMapping("/updateClue.do")
    @ResponseBody
    private Map<String,Object> updateClue(HttpServletRequest request , Clue clue){
        clue.setEditBy(((User) request.getSession().getAttribute("user")).getName());
        clue.setEditTime(DateTimeUtil.getSysTime());
        clueService.updateClue(clue);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/selectClueById.do")
    private String selectClueById(HttpServletRequest request,String id){
        Clue clue = clueService.selectClueByIdForOwner(id);
        request.setAttribute("clue",clue);
        return "forward:/workbench/clue/detail.jsp";
    }

    @RequestMapping("/getRemarkListByCid.do")
    @ResponseBody
    private List<ClueRemark> getRemarkListByCid(String clueId){
        List<ClueRemark> clueRemarks = clueService.selectClueRemarkByCId(clueId);
        return clueRemarks;
    }

    @RequestMapping("/deleteRemarkById.do")
    @ResponseBody
    private Map<String,Object> deleteRemarkById(String id){
        clueService.deleteClueRemarkById(id);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }

    @RequestMapping("/saveClueRemark.do")
    @ResponseBody
    private Map<String,Object> saveClueRemark(HttpServletRequest request,ClueRemark clueRemark){
        clueRemark.setId(UUIDUtil.getUUID());
        clueRemark.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        clueRemark.setCreateTime(DateTimeUtil.getSysTime());
        clueRemark.setEditFlag("0");
        clueService.insertClueRemark(clueRemark);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        info.put("clueRemark",clueRemark);
        return info;
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    private Map<String,Object> updateRemark(HttpServletRequest request,ClueRemark clueRemark){
        clueRemark.setEditBy(((User) request.getSession().getAttribute("user")).getName());
        clueRemark.setEditTime(DateTimeUtil.getSysTime());
        clueRemark.setEditFlag("1");
        clueService.updateClueRemark(clueRemark);
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        info.put("clueRemark",clueRemark);
        return info;
    }

}
