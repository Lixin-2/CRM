package com.lixin.crm.settings.service.impl;

import com.lixin.crm.settings.dao.UserDao;
import com.lixin.crm.settings.domain.User;
import com.lixin.crm.settings.exception.UserLoginException;
import com.lixin.crm.settings.service.UserService;
import com.lixin.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao = null;

    @Override
    public User selectUser(User user,String ip) {
        user = userDao.selectUser(user);
        if (user == null){
            throw new UserLoginException("用户名或密码错误！");
        }
        if(user.getExpireTime().compareTo(DateTimeUtil.getSysTime())<0){
            throw new UserLoginException("账户已失效！");
        }
        if("0".equals(user.getLockState())){
            throw new UserLoginException("账户已锁定！");
        }
        if(!user.getAllowIps().contains(ip)){
            throw new UserLoginException("IP地址受到限制！");
        }
        return user;
    }
}
