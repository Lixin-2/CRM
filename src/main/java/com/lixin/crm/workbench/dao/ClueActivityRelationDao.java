package com.lixin.crm.workbench.dao;

import com.lixin.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {


    void deleteClueActRelByActivityIds(String[] id);

    void deleteClueActRelByClueIds(String[] id);


    int deleteRelationByCAId(ClueActivityRelation clueActivityRelation);

    int insertActClueRelation(ClueActivityRelation clueActivityRelation);
}
