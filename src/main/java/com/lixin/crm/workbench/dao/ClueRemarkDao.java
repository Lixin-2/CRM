package com.lixin.crm.workbench.dao;

import com.lixin.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    void deleteClueRemarkByActivityIds(String[] id);

    List<ClueRemark> selectClueRemarkByCId(String clueId);

    int deleteClueRemarkById(String id);

    int insertClueRemark(ClueRemark clueRemark);

    int updateClueRemark(ClueRemark clueRemark);
}
