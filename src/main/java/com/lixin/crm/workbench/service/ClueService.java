package com.lixin.crm.workbench.service;

import com.lixin.crm.settings.domain.User;
import com.lixin.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueService {
    List<User> selectUsers();

    void insertClue(Clue clue);
}
