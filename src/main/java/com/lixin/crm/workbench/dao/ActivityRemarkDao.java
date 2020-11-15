package com.lixin.crm.workbench.dao;

import com.lixin.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    void deleteActivityRemarkByActivityIds(String[] id);

    List<ActivityRemark> selectActivityRemarkByAId(String activityId);

    int deleteActivityRemarkById(String id);

    int insertActivityRemark(ActivityRemark activityRemark);

    int updateActivityRemark(ActivityRemark activityRemark);
}
