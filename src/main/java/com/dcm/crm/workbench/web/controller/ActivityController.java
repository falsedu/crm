package com.dcm.crm.workbench.web.controller;

import com.dcm.crm.settings.domain.User;
import com.dcm.crm.settings.service.UserService;
import com.dcm.crm.settings.service.impl.UserServiceImpl;
import com.dcm.crm.utils.DateTimeUtil;
import com.dcm.crm.utils.PrintJson;
import com.dcm.crm.utils.ServiceFactory;
import com.dcm.crm.utils.UUIDUtil;
import com.dcm.crm.workbench.domain.Activity;
import com.dcm.crm.workbench.service.ActivityService;
import com.dcm.crm.workbench.vo.PaginationVO;
import com.dcm.crm1.utils.*;
import com.dcm.crm.workbench.domain.ActivityRemark;
import com.dcm.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入到活动控制界面");
        String path=req.getServletPath();
        if("/workbench/activity/getUserList.do".equals(path)){
            System.out.println("获取用户信息");
            List<User> list=getUserList(req,resp);

            PrintJson.printJsonObj(resp,list);



        }
        else if("/workbench/activity/saveActivity.do".equals(path)){
            saveActivity(req,resp);

        }else if("/workbench/activity/pageList.do".equals(path)){
           pageList(req,resp);
        }
        else if("/workbench/activity/deleteActivity.do".equals(path)){

            deleteActivities(req,resp);

        }else if("/workbench/activity/getActivity.do".equals(path)){

            getActivity(req,resp);

        }else if("/workbench/activity/updateActivity.do".equals(path)){

            updateActivity(req,resp);

        }else if("/workbench/activity/detail.do".equals(path)){

            detail(req,resp);

        }else if("/workbench/activity/getRemark.do".equals(path)){

            getRemark(req,resp);

        }else if("/workbench/activity/removeRemark.do".equals(path)){
            removeRemark(req,resp);


        }else if("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(req,resp);

        }else if("/workbench/activity/updateRemark.do".equals(path)){

            updateRemark(req,resp);

        }else if("/workbench/activity/getUserListAndActivity.do".equals(path)){
            getUserListAndActivity(req,resp);

        }else if("/workbench/activity/xxx.do".equals(path)){

        }
    }

    private void getUserListAndActivity(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入查询用户信息和活动信息操作");
        String id=req.getParameter("id");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String,Object> map=activityService.getUserListAndActivity(id);
        PrintJson.printJsonObj(resp,map);


    }

    private void updateRemark(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入备注修改操作");
        String noteContent=req.getParameter("noteContent");
        String editBy=req.getParameter("editBy");
        String editTime= DateTimeUtil.getSysTime();
        String editFlag="1";
        String id=req.getParameter("id");


        ActivityRemark activityRemark=new ActivityRemark();
        activityRemark.setEditFlag(editFlag);
        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditTime(editTime);
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean success=activityService.updateRemark(activityRemark);

        if(success){
            Map<String ,Object> map=new HashMap<>();
            map.put("success",success);
            map.put("remark",activityRemark);
            PrintJson.printJsonObj(resp,map);
        }
        else{
            PrintJson.printJsonFlag(resp,success);

        }

    }

    private void saveRemark(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入添加备注操作");
        String activityId=req.getParameter("activityId");
        String createBy=req.getParameter("createBy");
        String noteContent=req.getParameter("noteContent");
        String createTime= DateTimeUtil.getSysTime();
        String editFlag="0";
        String id= UUIDUtil.getUUID();
        ActivityRemark activityRemark=new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setActivityId(activityId);
        activityRemark.setCreateBy(createBy);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setCreateTime(createTime);
        activityRemark.setEditFlag(editFlag);

        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success=activityService.saveRemark(activityRemark);

        if(success){
            Map<String ,Object> map=new HashMap<>();
            map.put("success",success);
            map.put("remark",activityRemark);
            PrintJson.printJsonObj(resp,map);
        }
        else{
            PrintJson.printJsonFlag(resp,success);

        }

    }

    private void removeRemark(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入备注删除操作");
        String id=req.getParameter("id");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success=activityService.removeRemark(id);

        PrintJson.printJsonFlag(resp,success);

    }

    private void getRemark(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("获取备注信息列表");
        String id=req.getParameter("id");//获取了当前活动的id
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> remarks=activityService.getRemark(id);
        PrintJson.printJsonObj(resp,remarks);


    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入详情页面");
        String id=req.getParameter("id");//获取了当前活动的id
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a=activityService.detail(id);
        req.setAttribute("a",a);
        req.getRequestDispatcher("/workbench/activity/detail.jsp").forward(req,resp);




    }

    private void updateActivity(HttpServletRequest req, HttpServletResponse resp) {
        String id=req.getParameter("id");
        String  owner   	   =req.getParameter(         "owner");
        String  name		   =req.getParameter(         "name");
        String  startDate	   =req.getParameter(         "startDate");
        String  endDate	       =req.getParameter(         "endDate");
        String  cost		   =req.getParameter(         "cost");
        String  description    =req.getParameter(        "description");
        String editBy =req.getParameter("editBy");

        Activity activity=new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(DateTimeUtil.getSysTime());
        activity.setEditBy(editBy);

        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success=activityService.updateById(activity);
        PrintJson.printJsonFlag(resp,success);




    }

    private void getActivity(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入获取活动操作");
        String id=req.getParameter("id");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity activity=activityService.getActivity(id);





        PrintJson.printJsonObj(resp,activity);

    }

    private void deleteActivities(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入删除活动操作");
        String[] ids = req.getParameterValues("id");
//        System.out.println(ids.length);
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success=activityService.deleteActivities(ids);
        PrintJson.printJsonFlag(resp,success);


    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入列表查询");

        String  pageNo=req.getParameter("pageNo");
        int skipCount=Integer.valueOf(pageNo)-1;

        String  pageSize=req.getParameter("pageSize");
        int size=Integer.valueOf(pageSize);
        skipCount=skipCount*size;

        String  name=req.getParameter("name"	);
        String  owner=req.getParameter("owner");
        String  startDate=req.getParameter("startDate");
        String  endDate=req.getParameter("endDate");

        Map<String ,Object > map=new HashMap<>();
        map.put("skipCount",skipCount	);
        map.put("pageSize",size	);
        map.put("name",name		);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate"	,endDate	);
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
       PaginationVO<Activity> vo= activityService.pageList(map);
       PrintJson.printJsonObj(resp,vo);



    }

    private void saveActivity(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行市场活动添加操作");
        String  id= UUIDUtil.getUUID();
        String  owner =req.getParameter(  "owner"      );
        String  name=req.getParameter(  "name"       );
        String  startDate =req.getParameter(  "satrtDate"  );
        String  endDate =req.getParameter(  "endDate"    );
        String  cost =req.getParameter(  "cost"       );
        String  description =req.getParameter(  "description");
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)req.getSession().getAttribute("user")).getName();

//封装对象
        Activity activity=new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);


        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=activityService.save(activity);
        PrintJson.printJsonFlag(resp,flag);


    }

    private List<User> getUserList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("获取用户信息");
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> users=userService.findAll();
        return users;
    }

}
