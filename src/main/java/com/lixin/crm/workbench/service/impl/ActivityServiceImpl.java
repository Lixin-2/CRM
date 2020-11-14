package com.lixin.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.lixin.crm.workbench.dao.ActivityDao;
import com.lixin.crm.workbench.dao.ActivityRemarkDao;
import com.lixin.crm.workbench.domain.Activity;
import com.lixin.crm.workbench.exception.ActivityException;
import com.lixin.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao = null;
    @Autowired
    private ActivityRemarkDao activityRemarkDao = null;

    @Override
    public void insertActivity(Activity activity) {
        int num = activityDao.insertActivity(activity);
        if (num == 0){
            throw new ActivityException("添加市场活动失败！");
        }
    }

    @Override
    public Map<String, Object> selectActivityPageList(Integer pageNo,Integer pageSize,Activity activity) {
        int total = activityDao.selectCountTotal(activity);
        PageHelper.startPage(pageNo,pageSize);
        List<Activity> activities = activityDao.selectActivityPageList(activity);
        Map<String, Object> activityPageList = new HashMap<>();
        activityPageList.put("total",total);
        activityPageList.put("dataList",activities);
        return activityPageList;
    }

    @Override
    public void deleteActivityByIds(String[] id) {
        activityRemarkDao.deleteActivityRemarkByActivityIds(id);
        int num = activityDao.deleteActivityByIds(id);
        if (num != id.length){
            throw new ActivityException("删除市场活动失败!");
        }
    }

    @Override
    public Activity selectActivityById(String id) {
        Activity activity = activityDao.selectActivityById(id);
        return activity;
    }

    @Override
    public void updateActivity(Activity activity) {
        int num = activityDao.updateActivity(activity);
        if(num!=1){
            throw new ActivityException("更新市场活动失败！");
        }
    }
}
