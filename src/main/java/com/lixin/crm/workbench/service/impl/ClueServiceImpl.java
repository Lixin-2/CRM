package com.lixin.crm.workbench.service.impl;

import com.lixin.crm.settings.dao.UserDao;
import com.lixin.crm.settings.domain.User;
import com.lixin.crm.workbench.dao.ClueDao;
import com.lixin.crm.workbench.domain.Clue;
import com.lixin.crm.workbench.exception.ClueException;
import com.lixin.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueDao clueDao = null;
    @Autowired
    private UserDao userDao = null;

    @Override
    public List<User> selectUsers() {
        List<User> users = userDao.selectUsers();
        return users;
    }

    @Override
    public void insertClue(Clue clue) {
        int num = clueDao.insertClue(clue);
        if (num != 1){
            throw new ClueException("添加线索失败！");
        }
    }
}
