package com.lixin.crm.workbench.service;

import com.lixin.crm.workbench.domain.Activity;
import com.lixin.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    void insertActivity(Activity activity);

    Map<String, Object> selectActivityPageList(Integer pageNo,Integer pageSize,Activity activity);

    void deleteActivityByIds(String[] id);

    Activity selectActivityById(String id);

    void updateActivity(Activity activity);

    Activity selectActivityByIdForOwner(String id);

    List<ActivityRemark> selectActivityRemarkByAId(String activityId);

    void deleteActivityRemarkById(String id);

    void insertActivityRemark(ActivityRemark activityRemark);

    void updateActivityRemark(ActivityRemark activityRemark);
}
