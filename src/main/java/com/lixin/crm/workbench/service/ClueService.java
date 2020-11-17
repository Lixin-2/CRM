package com.lixin.crm.workbench.service;

import com.lixin.crm.settings.domain.User;
import com.lixin.crm.workbench.domain.Activity;
import com.lixin.crm.workbench.domain.Clue;
import com.lixin.crm.workbench.domain.ClueActivityRelation;
import com.lixin.crm.workbench.domain.ClueRemark;

import java.util.List;
import java.util.Map;

public interface ClueService {
    List<User> selectUsers();

    void insertClue(Clue clue);

    Map<String, Object> selectCluePageList(Integer pageNo, Integer pageSize, Clue clue);

    void deleteClueByIds(String[] id);

    Clue selectClueById(String id);

    void updateClue(Clue clue);

    Clue selectClueByIdForOwner(String id);

    List<ClueRemark> selectClueRemarkByCId(String clueId);

    void deleteClueRemarkById(String id);

    void insertClueRemark(ClueRemark clueRemark);

    void updateClueRemark(ClueRemark clueRemark);

    List<Activity> selectRelationListByCid(String clueId);

    void deleteRelationByCAId(ClueActivityRelation clueActivityRelation);

    List<Activity> selectActivityByNameAndNotClueIdForOwner(String name,String clueId);

    void insertActClueRelation(String clueId, String[] activityId);
}
