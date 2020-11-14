package com.lixin.crm.handler;

import com.lixin.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrmLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if(user != null || request.getServletPath().contains("login")){
            return true;
        }else{
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }

    }
}
