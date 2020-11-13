package com.lixin.crm.handler;

import com.lixin.crm.settings.exception.UserLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CrmExceptionHandler {

    @ExceptionHandler(value = UserLoginException.class)
    @ResponseBody
    public Map<String,Object> userLoginException(Exception ex){
        Map<String,Object> info = new HashMap<>();
        info.put("success",false);
        info.put("msg",ex.getMessage());
        return info;
    }
}
