package com.dcm.crm.workbench.web.controller;



import com.dcm.crm.workbench.domain.*;
import com.dcm.crm.workbench.service.impl.TranServiceImpl;
import com.dcm.crm.settings.domain.User;
import com.dcm.crm.utils.DateTimeUtil;
import com.dcm.crm.utils.PrintJson;
import com.dcm.crm.utils.ServiceFactory;
import com.dcm.crm.utils.UUIDUtil;
import com.dcm.crm1.workbench.domain.*;
import com.dcm.crm.workbench.service.ActivityService;
import com.dcm.crm.workbench.service.ContactsService;
import com.dcm.crm.workbench.service.CustomerService;
import com.dcm.crm.workbench.service.TranService;
import com.dcm.crm.workbench.service.impl.ActivityServiceImpl;
import com.dcm.crm.workbench.service.impl.ContactsServiceImpl;
import com.dcm.crm.workbench.service.impl.CustomerServiceImpl;
import com.dcm.crm.workbench.vo.PaginationVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TranController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入到交易控制界面");
        String path = req.getServletPath();
        if ("/workbench/transaction/add.do".equals(path)) {
            add(req,resp);



        } else if ("/workbench/transaction/save.do".equals(path)) {

            save(req,resp);

        }else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            getCustomerName(req,resp);

        }else if ("/workbench/transaction/getActivity.do".equals(path)) {
            getActivity(req,resp);

        }else if ("/workbench/transaction/getContacts.do".equals(path)) {
            getContacts(req,resp);
        }else if ("/workbench/transaction/pageList.do".equals(path)) {
            pageList(req,resp);

        }else if ("/workbench/transaction/detail.do".equals(path)) {
            detail(req,resp);

        }else if ("/workbench/transaction/showHistory.do".equals(path)) {
            showHistory(req,resp);

        }else if ("/workbench/transaction/edit.do".equals(path)) {
            edit(req,resp);

        }else if ("/workbench/transaction/changeStage.do".equals(path)) {
            changeStage(req,resp);

        }else if ("/workbench/transaction/getCharts.do".equals(path)) {
            getCharts(req,resp);


        }else if ("/workbench/transaction/xxx.do".equals(path)) {

        }

    }

    private void getCharts(HttpServletRequest req, HttpServletResponse resp) {


        System.out.println("取得交易阶段的数据");
        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        Map<String,Object> map=tranService.getCharts();
        PrintJson.printJsonObj(resp,map);
    }

    private void changeStage(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入改变状态操作");
        String id=req.getParameter("id");
        String stage=req.getParameter("stage");
        String expectedDate=req.getParameter("expectedDate");
        String money=req.getParameter("money");
        String editBy=((User)req.getSession().getAttribute("user")).getName();
        String editTime=DateTimeUtil.getSysTime();

        Tran t=new Tran();
        t.setId(id);
        t.setStage(stage);
        t.setEditBy(editBy);
        t.setEditTime(editTime);

        TranHistory th=new TranHistory();
        th.setTranId(id);
        th.setStage(stage);
        th.setExpectedDate(expectedDate);
        th.setMoney(money);
        th.setCreateTime(editTime);
        th.setCreateBy(editBy);


        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        Map<String ,Object> map=tranService.updateTranAndTranHistory(t,th);
        Map<String,String> pmap= (Map<String, String>) req.getServletContext().getAttribute("pMap");
        ((StageChangeReturn)map.get("t")).setPossibility(pmap.get(((StageChangeReturn) map.get("t")).getStage()));

        PrintJson.printJsonObj(resp,map);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        System.out.println("进入修改页面");
        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<User> users=tranService.getUserList();
        req.setAttribute("uList",users);
        req.getRequestDispatcher("/workbench/transaction/edit.jsp").forward(req,resp);

    }

    private void showHistory(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("获取交易历史记录");
        String tranId=req.getParameter("tranId");
        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<TranHistory> list=tranService.showHistory(tranId);
        Map<String,String> pmap= (Map<String, String>) req.getServletContext().getAttribute("pMap");
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPossibility(pmap.get(list.get(i).getStage()));
        }

        PrintJson.printJsonObj(resp,list);

    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入交易详情页面");
        String id=req.getParameter("id");
        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());
        Tran t=tranService.getTranById(id);

        String stage=t.getStage();
        Map<String,String> pmap= (Map<String, String>) req.getServletContext().getAttribute("pMap");
        t.setPossibility(pmap.get(stage));
        req.setAttribute("t",t);


        req.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(req,resp);

    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("交易列表操作");
        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        String pageNo=req.getParameter("pageNo");
        String pageSize=req.getParameter("pageSize");
        int pageno=Integer.valueOf(pageNo)-1;
        int pagesize=Integer.valueOf(pageSize);
        int skipCount=pageno*pagesize;

        Map<String ,Object> map=new HashMap<>();

        map.put("skipCount",skipCount);
        map.put("pageSize",pagesize);
        PaginationVO<Tran> trans=tranService.pageList(map);

        PrintJson.printJsonObj(resp,trans);


    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("执行添加交易操作");
        String id= UUIDUtil.getUUID();
        String owner=req.getParameter("owner");
        String money=req.getParameter("money");
        String name=req.getParameter("name");
        String expectedDate=req.getParameter("expectedDate");
        String customerName=req.getParameter("customerName");
        String stage=req.getParameter("stage");
        String type=req.getParameter("type");
        String source=req.getParameter("source");
        String activityId=req.getParameter("activityId");
        String contactsId=req.getParameter("contactsId");
        String description=req.getParameter("description");
        String contactSummary=req.getParameter("contactSummary");
        String nextContactTime=req.getParameter("nextContactTime");
        String createBy=((User)req.getSession().getAttribute("user")).getName();
        String createTime= DateTimeUtil.getSysTime();
        Tran t=new Tran();
        t.setType(type);
        t.setActivityId(activityId);
        t.setMoney(money);
        t.setName(name);
        t.setStage(stage);
        t.setExpectedDate(expectedDate);
        t.setId(id);
        t.setCreateTime(createTime);
        t.setCreateBy(createBy);
        t.setContactsId(contactsId);
        t.setContactSummary(contactSummary);
        t.setSource(source);
        t.setOwner(owner);
        t.setNextContactTime(nextContactTime);
        t.setDescription(description);

        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag=tranService.save(t,customerName);

        if(flag){
            resp.sendRedirect(req.getContextPath()+"/workbench/transaction/index.jsp");
        }







    }

    private void getContacts(HttpServletRequest req, HttpServletResponse resp) {
        String name=req.getParameter("name");
        ContactsService contactsService= (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());
        List<Contacts> list=contactsService.getContactsByName(name);
        PrintJson.printJsonObj(resp,list);

    }

    private void getActivity(HttpServletRequest req, HttpServletResponse resp) {

        String name=req.getParameter("name");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> list=activityService.getActivityByName(name);
        PrintJson.printJsonObj(resp,list);

    }

    private void getCustomerName(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("取得客户名称列表");
        String name=req.getParameter("name");

        CustomerService customerService= (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<String> customerNames=customerService.getCustomerNameByName(name);
        PrintJson.printJsonObj(resp,customerNames);


    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入创建页面");
        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<User> users=tranService.getUserList();
        req.setAttribute("uList",users);
        req.getRequestDispatcher("/workbench/transaction/save.jsp").forward(req,resp);



    }
}
