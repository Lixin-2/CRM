package com.lixin.crm.handler;

import com.lixin.crm.settings.exception.DicException;
import com.lixin.crm.settings.exception.UserException;
import com.lixin.crm.workbench.exception.*;
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

    @ExceptionHandler(value = DicException.class)
    @ResponseBody
    public Map<String,Object> dicException(Exception ex){
        Map<String,Object> info = new HashMap<>();
        info.put("success",false);
        info.put("msg",ex.getMessage());
        return info;
    }

    @ExceptionHandler(value = ActivityException.class)
    @ResponseBody
    public Map<String,Object> activityException(Exception ex){
        Map<String,Object> info = new HashMap<>();
        info.put("success",false);
        info.put("msg",ex.getMessage());
        return info;
    }

    @ExceptionHandler(value = ClueException.class)
    @ResponseBody
    public Map<String,Object> clueException(Exception ex){
        Map<String,Object> info = new HashMap<>();
        info.put("success",false);
        info.put("msg",ex.getMessage());
        return info;
    }

    @ExceptionHandler(value = ContactsException.class)
    @ResponseBody
    public Map<String,Object> contactsException(Exception ex){
        Map<String,Object> info = new HashMap<>();
        info.put("success",false);
        info.put("msg",ex.getMessage());
        return info;
    }

    @ExceptionHandler(value = CustomerException.class)
    @ResponseBody
    public Map<String,Object> customerException(Exception ex){
        Map<String,Object> info = new HashMap<>();
        info.put("success",false);
        info.put("msg",ex.getMessage());
        return info;
    }

    @ExceptionHandler(value = TranException.class)
    @ResponseBody
    public Map<String,Object> tranException(Exception ex){
        Map<String,Object> info = new HashMap<>();
        info.put("success",false);
        info.put("msg",ex.getMessage());
        return info;
    }
}
