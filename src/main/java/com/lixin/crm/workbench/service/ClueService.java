package com.lixin.crm.workbench.service;

import com.lixin.crm.settings.domain.User;
import com.lixin.crm.workbench.domain.Clue;

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
}
