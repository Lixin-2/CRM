package com.lixin.crm.workbench.service.impl;

import com.lixin.crm.workbench.dao.ClueDao;
import com.lixin.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueDao clueDao = null;
    
}
