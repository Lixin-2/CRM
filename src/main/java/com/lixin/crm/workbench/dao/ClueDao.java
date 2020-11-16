package com.lixin.crm.workbench.dao;


import com.lixin.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {


    int insertClue(Clue clue);

    int selectCountTotal(Clue clue);

    List<Clue> selectCluePageList(Clue clue);

    int deleteClueByIds(String[] id);

    Clue selectClueById(String id);

    void updateClue(Clue clue);

    Clue selectClueByIdForOwner(String id);
}
