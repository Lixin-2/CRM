package com.lixin.crm.web.listener;



import com.lixin.crm.settings.domain.DicValue;
import com.lixin.crm.settings.service.DicService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DicService dicService = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(DicService.class);
        Map<String, List<DicValue>> map = dicService.getAll();
        ServletContext application = sce.getServletContext();
        Set<String> set = map.keySet();
        for (String key:set){
            System.out.println(map.get(key));
            application.setAttribute(key,map.get(key));
        }
    }

}
