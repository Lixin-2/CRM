package com.lixin.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.lixin.crm.settings.dao.UserDao;
import com.lixin.crm.settings.domain.User;
import com.lixin.crm.workbench.dao.ClueActivityRelationDao;
import com.lixin.crm.workbench.dao.ClueDao;
import com.lixin.crm.workbench.dao.ClueRemarkDao;
import com.lixin.crm.workbench.dao.ContactsActivityRelationDao;
import com.lixin.crm.workbench.domain.Clue;
import com.lixin.crm.workbench.exception.ClueException;
import com.lixin.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueDao clueDao = null;
    @Autowired
    private UserDao userDao = null;
    @Autowired
    private ClueRemarkDao clueRemarkDao = null;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao = null;

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

    @Override
    public Map<String, Object> selectCluePageList(Integer pageNo, Integer pageSize, Clue clue) {
        int total = clueDao.selectCountTotal(clue);
        PageHelper.startPage(pageNo,pageSize);
        List<Clue> clues = clueDao.selectCluePageList(clue);
        Map<String, Object> activityPageList = new HashMap<>();
        activityPageList.put("total",total);
        activityPageList.put("dataList",clues);
        return activityPageList;
    }

    @Override
    public void deleteClueByIds(String[] id) {
        clueRemarkDao.deleteClueRemarkByActivityIds(id);
        clueActivityRelationDao.deleteClueActRelByClueIds(id);
        int num = clueDao.deleteClueByIds(id);
        if (num != id.length){
            throw new ClueException("删除线索失败!");
        }
    }

    @Override
    public Clue selectClueById(String id) {
        Clue clue = clueDao.selectClueById(id);
        return clue;
    }
}
