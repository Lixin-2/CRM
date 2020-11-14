package com.lixin.crm.settings.service.impl;

import com.lixin.crm.settings.dao.UserDao;
import com.lixin.crm.settings.domain.User;
import com.lixin.crm.settings.exception.UserException;
import com.lixin.crm.settings.service.UserService;
import com.lixin.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao = null;

    @Override
    public User selectUser(User user,String ip) {
        user = userDao.selectUser(user);
        if (user == null){
            throw new UserException("用户名或密码错误！");
        }
        if(user.getExpireTime().compareTo(DateTimeUtil.getSysTime())<0){
            throw new UserException("账户已失效！");
        }
        if("0".equals(user.getLockState())){
            throw new UserException("账户已锁定！");
        }
        if(!user.getAllowIps().contains(ip)){
            throw new UserException("IP地址受到限制！");
        }
        return user;
    }

    @Override
    public List<User> selectUsers() {
        List<User> users = userDao.selectUsers();
        return users;
    }
}
