package com.lixin.crm.settings.service;

import com.lixin.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User selectUser(User user,String ip);

    List<User> selectUsers();
}
