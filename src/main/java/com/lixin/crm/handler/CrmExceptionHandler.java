package com.lixin.crm.handler;

import com.lixin.crm.settings.exception.UserException;
import com.lixin.crm.workbench.exception.ActivityException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CrmExceptionHandler {

    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public Map<String,Object> userLoginException(Exception ex){
        Map<String,Object> info = new HashMap<>();
        info.put("success",false);
        info.put("msg",ex.getMessage());
        return info;
    }

    @ExceptionHandler(value = ActivityException.class)
    @ResponseBody
    public Map<String,Object> ActivityException(Exception ex){
        Map<String,Object> info = new HashMap<>();
        info.put("success",false);
        info.put("msg",ex.getMessage());
        return info;
    }
}
