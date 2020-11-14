package com.lixin.crm.workbench.service;

import com.lixin.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    void insertActivity(Activity activity);

    Map<String, Object> selectActivityPageList(Integer pageNo,Integer pageSize,Activity activity);

    void deleteActivityByIds(String[] id);

    Activity selectActivityById(String id);

    void updateActivity(Activity activity);
}
