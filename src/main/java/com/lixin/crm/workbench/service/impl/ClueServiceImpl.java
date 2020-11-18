package com.lixin.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.lixin.crm.settings.dao.UserDao;
import com.lixin.crm.settings.domain.User;
import com.lixin.crm.utils.UUIDUtil;
import com.lixin.crm.workbench.dao.*;
import com.lixin.crm.workbench.domain.Activity;
import com.lixin.crm.workbench.domain.Clue;
import com.lixin.crm.workbench.domain.ClueActivityRelation;
import com.lixin.crm.workbench.domain.ClueRemark;
import com.lixin.crm.workbench.exception.ClueException;
import com.lixin.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueDao clueDao = null;
    @Autowired
    private UserDao userDao = null;
    @Autowired
    private ClueRemarkDao clueRemarkDao = null;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao = null;
    @Autowired
    private ActivityDao activityDao = null;

    @Override
    public List<User> selectUsers() {
        List<User> users = userDao.selectUsers();
        return users;
    }

    @Override
    public void insertClue(Clue clue) {
        int num = clueDao.insertClue(clue);
        if (num != 1){
            throw new ClueException("添加线索失败！");
        }
    }

    @Override
    public Map<String, Object> selectCluePageList(Integer pageNo, Integer pageSize, Clue clue) {
        int total = clueDao.selectCountTotal(clue);
        PageHelper.startPage(pageNo,pageSize);
        List<Clue> clues = clueDao.selectCluePageList(clue);
        Map<String, Object> activityPageList = new HashMap<>();
        activityPageList.put("total",total);
        activityPageList.put("dataList",clues);
        return activityPageList;
    }

    @Override
    public void deleteClueByIds(String[] id) {
        clueRemarkDao.deleteClueRemarkByActivityIds(id);
        clueActivityRelationDao.deleteClueActRelByClueIds(id);
        int num = clueDao.deleteClueByIds(id);
        if (num != id.length){
            throw new ClueException("删除线索失败!");
        }
    }

    @Override
    public Clue selectClueById(String id) {
        Clue clue = clueDao.selectClueById(id);
        return clue;
    }

    @Override
    public void updateClue(Clue clue) {
        clueDao.updateClue(clue);
    }

    @Override
    public Clue selectClueByIdForOwner(String id) {
        Clue clue = clueDao.selectClueByIdForOwner(id);
        return clue;
    }

    @Override
    public List<ClueRemark> selectClueRemarkByCId(String clueId) {
        List<ClueRemark> clueRemarks = clueRemarkDao.selectClueRemarkByCId(clueId);
        return clueRemarks;
    }

    @Override
    public void deleteClueRemarkById(String id) {
        int num = clueRemarkDao.deleteClueRemarkById(id);
        if (num != 1){
            throw new ClueException("删除线索备注失败！");
        }
    }

    @Override
    public void insertClueRemark(ClueRemark clueRemark) {
        int num = clueRemarkDao.insertClueRemark(clueRemark);
        if (num != 1){
            throw new ClueException("添加线索备注失败！");
        }
    }

    @Override
    public void updateClueRemark(ClueRemark clueRemark) {
        int num = clueRemarkDao.updateClueRemark(clueRemark);
        if (num != 1){
            throw new ClueException("更新线索备注失败！");
        }
    }

    @Override
    public List<Activity> selectRelationListByCid(String clueId) {
        List<Activity> activities = activityDao.selectRelationListByCid(clueId);
        return activities;
    }

    @Override
    public void deleteRelationByCAId(ClueActivityRelation clueActivityRelation) {
        int num = clueActivityRelationDao.deleteRelationByCAId(clueActivityRelation);
        if (num != 1){
            throw new ClueException("解除市场活动关联失败！");
        }
    }

    @Override
    public List<Activity> selectActivityByNameAndNotClueIdForOwner(String name,String clueId) {
        List<Activity> activities = activityDao.selectActivityByNameAndNotClueIdForOwner(name,clueId);
        return activities;
    }

    @Override
    public void insertActClueRelation(String clueId, String[] activityId) {
        int num = 0;
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(clueId);
        for (String aid : activityId){
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityId(aid);
            num += clueActivityRelationDao.insertActClueRelation(clueActivityRelation);
        }
        if (num!=activityId.length){
            throw new ClueException("关联市场活动失败！");
        }
    }

    @Override
    public List<Activity> selectActivityByName(String name) {
        List<Activity> activities = activityDao.selectActivityByNameForOwner(name);
        return activities;
    }


}
