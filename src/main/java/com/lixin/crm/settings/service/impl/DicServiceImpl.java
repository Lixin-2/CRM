package com.lixin.crm.settings.service.impl;


import com.lixin.crm.settings.dao.DicTypeDao;
import com.lixin.crm.settings.dao.DicValueDao;
import com.lixin.crm.settings.domain.DicType;
import com.lixin.crm.settings.domain.DicValue;
import com.lixin.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {

    @Autowired
    private DicTypeDao dicTypeDao = null;
    @Autowired
    private DicValueDao dicValueDao = null;

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<>();
        List<DicType> dicTypes = dicTypeDao.getTypeList();
        for (DicType dicType : dicTypes){
            String code = dicType.getCode();
            List<DicValue> dicValues = dicValueDao.getListByCode(code);
            map.put(code,dicValues);
        }
        return map;
    }
}
