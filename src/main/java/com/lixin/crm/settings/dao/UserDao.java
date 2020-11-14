package com.lixin.crm.settings.dao;

import com.lixin.crm.settings.domain.User;

import java.util.List;

public interface UserDao {
    User selectUser(User user);

    List<User> selectUsers();
}
