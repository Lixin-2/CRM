package com.lixin.crm.workbench.dao;

public interface ClueActivityRelationDao {


    void deleteClueActRelByActivityIds(String[] id);

    void deleteClueActRelByClueIds(String[] id);
}
