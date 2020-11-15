package com.lixin.crm.workbench.dao;

import com.lixin.crm.workbench.domain.Activity;

import java.util.List;

public interface ActivityDao {
    int insertActivity(Activity activity);

    List<Activity> selectActivityPageList(Activity activity);

    int selectCountTotal(Activity activity);

    int deleteActivityByIds(String[] id);

    Activity selectActivityById(String id);

    int updateActivity(Activity activity);

    Activity selectActivityByIdForOwner(String id);
}
