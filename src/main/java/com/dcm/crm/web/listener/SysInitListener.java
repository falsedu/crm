package com.dcm.crm.web.listener;

import com.dcm.crm.settings.domain.DicValue;
import com.dcm.crm.settings.service.DicService;
import com.dcm.crm.settings.service.impl.DicServiceImpl;
import com.dcm.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener  implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("上下文域对象创建");
        ServletContext application = event.getServletContext();

        DicService dicService= (DicService) ServiceFactory.getService(new DicServiceImpl());


        Map<String, List<DicValue>> map= dicService.getAll();
        Set<String> set=map.keySet();
        for(String s:set){
            application.setAttribute(s,map.get(s));
        }

        System.out.println("服务器缓存处理数据字典结束");




        System.out.println("阶段可能性存储开始");
        ResourceBundle resourceBundle=ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> keys=resourceBundle.getKeys();
        Map<String,String> pmap=new HashMap<>();
        while(keys.hasMoreElements()){
            String key=keys.nextElement();
            pmap.put(key,resourceBundle.getString(key));
        }

        application.setAttribute("pMap",pmap);
        System.out.println("阶段可能性存储结束");


    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

    private void stage2possiblity(String fileName,ServletContext servletContext){

    }
}
