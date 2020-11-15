package com.lixin.crm.settings.dao;

import com.lixin.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
