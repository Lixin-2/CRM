package com.lixin.crm.settings.web.controller;

import com.lixin.crm.settings.domain.User;
import com.lixin.crm.settings.service.UserService;
import com.lixin.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")
public class UserController {

    @Autowired
    UserService userService = null;

    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, User user){
        user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));
        String ip = request.getRemoteAddr();
        request.getSession().setAttribute("user",userService.selectUser(user,ip));
        Map<String,Object> info = new HashMap<>();
        info.put("success",true);
        return info;
    }


}
