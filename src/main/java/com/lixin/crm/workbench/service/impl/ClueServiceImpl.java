package com.lixin.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.lixin.crm.settings.dao.UserDao;
import com.lixin.crm.settings.domain.User;
import com.lixin.crm.utils.UUIDUtil;
import com.lixin.crm.workbench.dao.*;
import com.lixin.crm.workbench.domain.*;
import com.lixin.crm.workbench.exception.ClueException;
import com.lixin.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private ActivityDao activityDao = null;
    @Autowired
    private TranDao tranDao = null;
    @Autowired
    private TranHistoryDao tranHistoryDao = null;
    @Autowired
    private ContactsDao contactsDao = null;
    @Autowired
    private CustomerDao customerDao = null;

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

    @Override
    public void updateClue(Clue clue) {
        clueDao.updateClue(clue);
    }

    @Override
    public Clue selectClueByIdForOwner(String id) {
        Clue clue = clueDao.selectClueByIdForOwner(id);
        return clue;
    }

    @Override
    public List<ClueRemark> selectClueRemarkByCId(String clueId) {
        List<ClueRemark> clueRemarks = clueRemarkDao.selectClueRemarkByCId(clueId);
        return clueRemarks;
    }

    @Override
    public void deleteClueRemarkById(String id) {
        int num = clueRemarkDao.deleteClueRemarkById(id);
        if (num != 1){
            throw new ClueException("删除线索备注失败！");
        }
    }

    @Override
    public void insertClueRemark(ClueRemark clueRemark) {
        int num = clueRemarkDao.insertClueRemark(clueRemark);
        if (num != 1){
            throw new ClueException("添加线索备注失败！");
        }
    }

    @Override
    public void updateClueRemark(ClueRemark clueRemark) {
        int num = clueRemarkDao.updateClueRemark(clueRemark);
        if (num != 1){
            throw new ClueException("更新线索备注失败！");
        }
    }

    @Override
    public List<Activity> selectRelationListByCid(String clueId) {
        List<Activity> activities = activityDao.selectRelationListByCid(clueId);
        return activities;
    }

    @Override
    public void deleteRelationByCAId(ClueActivityRelation clueActivityRelation) {
        int num = clueActivityRelationDao.deleteRelationByCAId(clueActivityRelation);
        if (num != 1){
            throw new ClueException("解除市场活动关联失败！");
        }
    }

    @Override
    public List<Activity> selectActivityByNameAndNotClueIdForOwner(String name,String clueId) {
        List<Activity> activities = activityDao.selectActivityByNameAndNotClueIdForOwner(name,clueId);
        return activities;
    }

    @Override
    public void insertActClueRelation(String clueId, String[] activityId) {
        int num = 0;
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(clueId);
        for (String aid : activityId){
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityId(aid);
            num += clueActivityRelationDao.insertActClueRelation(clueActivityRelation);
        }
        if (num!=activityId.length){
            throw new ClueException("关联市场活动失败！");
        }
    }

    @Override
    public List<Activity> selectActivityByName(String name) {
        List<Activity> activities = activityDao.selectActivityByNameForOwner(name);
        return activities;
    }

    @Override
    public void convertClue(boolean flag, String clueId, Tran tran) {
        int num=0;
        Clue clue = clueDao.selectClueById(clueId);

        //客户
        Customer customer = new Customer();
        customer.setId(UUIDUtil.getUUID());
        customer.setOwner(clue.getOwner());
        customer.setName(clue.getCompany());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateBy(tran.getCreateBy());
        customer.setCreateTime(tran.getCreateTime());
        customer.setContactSummary(clue.getContactSummary());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setDescription(clue.getDescription());
        customer.setAddress(clue.getAddress());

        //联络人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(tran.getCreateBy());
        contacts.setCreateTime(tran.getCreateTime());
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());

        if (flag){
            //交易
            tran.setOwner(clue.getOwner());
            tran.setDescription(clue.getDescription());
            tran.setContactSummary(clue.getContactSummary());
            tran.setNextContactTime(clue.getNextContactTime());

            //交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateBy(tran.getCreateBy());
            tranHistory.setCreateTime(tran.getCreateTime());
            tranHistory.setTranId(tran.getId());

            num = tranDao.insertTran(tran);
            if (num!=1){
                throw new ClueException("转换线索时，添加交易记录失败！");
            }
            num = tranHistoryDao.insertTranHistory(tranHistory);
            if (num!=1){
                throw new ClueException("转换线索时，添加交易历史失败！");
            }
        }

        num = contactsDao.insertContacts(contacts);
        if (num!=1){
            throw new ClueException("转换线索时，添加联系人失败！");
        }
        num = customerDao.insertCustacts(customer);
        if (num!=1){
            throw new ClueException("转换线索时，添加客户失败！");
        }
        clueRemarkDao.deleteClueRemarkByCId(clueId);
        String[] clueIds = {clueId};
        clueActivityRelationDao.deleteClueActRelByClueIds(clueIds);
        num = clueDao.deleteClueByIds(clueIds);
        if(num!=1){
            throw new ClueException("转换线索时，删除线索失败！");
        }

    }


}
