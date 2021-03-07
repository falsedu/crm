package com.dcm.crm.workbench.web.controller;

import com.dcm.crm.settings.domain.User;
import com.dcm.crm.utils.DateTimeUtil;
import com.dcm.crm.utils.PrintJson;
import com.dcm.crm.utils.ServiceFactory;
import com.dcm.crm.utils.UUIDUtil;
import com.dcm.crm.workbench.domain.Activity;
import com.dcm.crm.workbench.domain.Clue;
import com.dcm.crm.workbench.domain.ClueRemark;
import com.dcm.crm.workbench.domain.Tran;
import com.dcm.crm1.utils.*;
import com.dcm.crm1.workbench.domain.*;
import com.dcm.crm.workbench.service.ClueService;
import com.dcm.crm.workbench.service.impl.ClueServiceImpl;
import com.dcm.crm.workbench.vo.PaginationVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入到线索控制界面");
        String path              =req.getServletPath();
        if("/workbench/clue/getUserList.do".equals(path)){
            getUserList(req,resp);

        }
        else if("/workbench/clue/saveClue.do".equals(path)){

            saveClue(req,resp);
        }else if("/workbench/clue/pageList.do".equals(path)){

            pageList(req,resp);

        }else if("/workbench/clue/detail.do".equals(path)){

            detail(req,resp);

        }else if("/workbench/clue/getClue.do".equals(path)){

            getClue(req,resp);

        }else if("/workbench/clue/updateClue.do".equals(path)){

            updateClue(req,resp);

        }else if("/workbench/clue/deleteClue.do".equals(path)){
            deleteClue(req,resp);

        }else if("/workbench/clue/showClueRemark.do".equals(path)){
            showClueRemark(req,resp);

        }else if("/workbench/clue/showRelativeActivity.do".equals(path)){
            showRelativeActivity(req,resp);


        }else if("/workbench/clue/unbund.do".equals(path)){
            unbund(req,resp);

        }else if("/workbench/clue/getActivityByName.do".equals(path)){
            getActivityByName(req,resp);

        }else if("/workbench/clue/bund.do".equals(path)){
            bund(req,resp);

        }else if("/workbench/clue/convert.do".equals(path)){

            convert(req,resp);

        }else if("/workbench/clue/addRemark.do".equals(path)){
            addRemark(req,resp);

        }else if("/workbench/clue/xxx.do".equals(path)){

        }



    }

    private void addRemark(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入添加备注操作");
        String clueId=req.getParameter("clueId");
        String createBy=req.getParameter("createBy");
        String noteContent=req.getParameter("noteContent");
        String createTime= DateTimeUtil.getSysTime();
        String editFlag="0";
        String id= UUIDUtil.getUUID();
       ClueRemark clueRemark=new ClueRemark();
        clueRemark.setId(id);
        clueRemark.setClueId(clueId);
        clueRemark.setCreateBy(createBy);
        clueRemark.setNoteContent(noteContent);
        clueRemark.setCreateTime(createTime);
        clueRemark.setEditFlag(editFlag);

        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean success=clueService.addRemark(clueRemark);

        if(success){
            Map<String ,Object> map=new HashMap<>();
            map.put("success",success);
            map.put("remark",clueRemark);
            PrintJson.printJsonObj(resp,map);
        }
        else{
            PrintJson.printJsonFlag(resp,success);

        }
    }

    private void convert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("跳转到convert页面");

        String clueId=req.getParameter("cid");
        String needTran=req.getParameter("needTran");

        String createBy=((User)req.getSession().getAttribute("user")).getName();
        String createTime=DateTimeUtil.getSysTime();

        Tran tran=null;
        if("a".equals(needTran)){
            //需要添加交易
            String money =req.getParameter("money");
            String name=req.getParameter("name");
            String stage=req.getParameter("stage");
            String expectedDate=req.getParameter("expectedDate");
            String activityId=req.getParameter("aid");
            tran=new Tran();
//            tran.setCreateBy(createBy);
//            tran.setCreateTime(createTime);
//            Clue clue= (Clue) map.get("clue");
//            tran.setSource(clue.getSource());
//            tran.setOwner(clue.getOwner());
//            tran.setNextContactTime(clue.getNextContactTime());
//            tran.setDescription(clue.getDescription());
//            tran.setId(UUIDUtil.getUUID());
//            tran.setCustomerId((String) map.get("customerId"));
//            tran.setContactsId((String) map.get("contactsId"));
//            tran.setContactSummary(clue.getContactSummary());

            String id=UUIDUtil.getUUID();
            tran.setId(id);
                tran.setExpectedDate(expectedDate);
                tran.setMoney(money);
                tran.setActivityId(activityId);
                tran.setStage(stage);
                tran.setName(name);






        }

        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=clueService.convert(clueId,tran,createBy);
        if(flag)resp.sendRedirect(req.getContextPath()+"/workbench/clue/index.jsp");


    }

    private void bund(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入绑定操作");
        String cid=req.getParameter("cid");
        String []ids=req.getParameterValues("id");


        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Map<String,Object> map=new HashMap<>();
        map.put("cid",cid);
        map.put("ids",ids);

        boolean success=clueService.bund(map);
        PrintJson.printJsonFlag(resp,success);

    }

    private void getActivityByName(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("通过名字查找活动信息");
        String name=req.getParameter("name");

        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Activity> activities=clueService.getActivityByName(name);
        PrintJson.printJsonObj(resp,activities);
    }

    private void unbund(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("执行解除关联操作");

        String clueId=req.getParameter("clueId");
        String activityId=req.getParameter("activityId");
        Map<String ,String >map=new HashMap<>();
        map.put("clueId",clueId);
        map.put("activityId",activityId);

        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean success=clueService.unbund(map);


        PrintJson.printJsonFlag(resp,success);

    }

    private void showRelativeActivity(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入展示相关活动操作");

        String clueId=req.getParameter("clueId");
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        List<Activity> activities=clueService.getRelativeActivity(clueId);


        PrintJson.printJsonObj(resp,activities);


    }

    private void showClueRemark(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("展示线索备注");
        String clueId=req.getParameter("clueId");
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<ClueRemark> clueRemarks=clueService.showClueRemark(clueId);
        PrintJson.printJsonObj(resp,clueRemarks);

    }

    private void deleteClue(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("删除线索");
        String[]ids=req.getParameterValues("id");

        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean success=clueService.deleteClue(ids);
        PrintJson.printJsonFlag(resp,success);

    }

    private void updateClue(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入线索更新");
        String id               =req.getParameter("id");
        String fullname         =req.getParameter("fullname");
        System.out.println(fullname);
        String appellation      =req.getParameter("appellation");
        String owner            =req.getParameter("owner");
        String company          =req.getParameter("company");
        String job              =req.getParameter("job");
        String email            =req.getParameter("email");
        String phone            =req.getParameter("phone");
        String website          =req.getParameter("website");
        String mphone           =req.getParameter("mphone");
        String state            =req.getParameter("state");
        String source           =req.getParameter("source");
        String description      =req.getParameter("description");
        String contactSummary   =req.getParameter("contactSummary");
        String nextContactTime  =req.getParameter("nextContactTime");
        String address          =req.getParameter("address");

        Clue clue=new Clue();
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setEditBy(((User) req.getSession().getAttribute("user")).getName());
        clue.setEditTime(DateTimeUtil.getSysTime());

        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);
        clue.setId(id);

        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean success=clueService.updateClue(clue);
        PrintJson.printJsonFlag(resp,success);



    }

    private void getClue(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进入修改线索页面");
        String id=req.getParameter("id");
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue=clueService.getClueById2(id);
        PrintJson.printJsonObj(resp,clue);
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入线索详情页面");
        String id=req.getParameter("id");
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue=clueService.getClueById(id);
        req.setAttribute("c",clue);
        req.getRequestDispatcher("/workbench/clue/detail.jsp").forward(req,resp);


    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {


        System.out.println("进入线索列表获取");
        String fullname=req.getParameter("fullname");
        String company=req.getParameter("company" );
        String phone=req.getParameter("phone"   );
        String source=req.getParameter("source");
        String owner=req.getParameter("owner"	);
        String mphone=req.getParameter("mphone");
        String state=req.getParameter("state"	);
        String pageNo=req.getParameter("pageNo");
        String pageSize=req.getParameter("pageSize");
        int pageno=Integer.valueOf(pageNo)-1;
        int pagesize=Integer.valueOf(pageSize);
        int skipCount=pageno*pagesize;

        Map<String ,Object> map=new HashMap<>();
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone  );
        map.put("source",source);
        map.put("owner",owner);
        map.put("mphone",mphone);
        map.put("state",state);
        map.put("skipCount",skipCount);
        map.put("pageSize",pagesize);

        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        PaginationVO<Clue> clueList=clueService.pageList(map);

        PrintJson.printJsonObj(resp,clueList);


    }

    private void saveClue(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("进行线索添加操作");

            String  fullname              =req.getParameter(  "fullname"          );
            String  appellation              =req.getParameter(  "appellation"       );
            String  owner              =req.getParameter(  "owner"             );
            String  company              =req.getParameter(  "company"           );
            String  job              =req.getParameter(  "job"               );
            String  email              =req.getParameter(  "email"             );
            String  phone              =req.getParameter(  "phone"             );
            String  website              =req.getParameter(  "website"           );
            String  mphone              =req.getParameter(  "mphone"            );
            String  state              =req.getParameter(  "state"             );
            String  source              =req.getParameter(  "source"            );
            String  createBy              =req.getParameter(  "createBy"          );
            String  description              =req.getParameter(  "description"       );
            String  contactSummary              =req.getParameter(  "contactSummary"    );
            String  nextContactTime              =req.getParameter(  "nextContactTime"   );
            String  address              =req.getParameter(  "address"           );
            String  id                   = UUIDUtil.getUUID();
            String  createTime            =DateTimeUtil.getSysTime();

            Clue clue=new Clue();
            clue.setFullname(fullname);
            clue.setAppellation(appellation);
            clue.setOwner(owner);
            clue.setCompany(company);
            clue.setJob(job);
            clue.setEmail(email);
            clue.setPhone(phone);
            clue.setWebsite(website);
            clue.setMphone(mphone);
            clue.setState(state);
            clue.setSource(source);
            clue.setCreateBy(createBy);
            clue.setCreateTime(createTime);
            clue.setDescription(description);
            clue.setContactSummary(contactSummary);
            clue.setNextContactTime(nextContactTime);
            clue.setAddress(address);
            clue.setId(id);

            ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
            boolean success=clueService.saveClue(clue);
            PrintJson.printJsonFlag(resp,success);
    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("查找所用用户");
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<User> users=clueService.getUserList();

        PrintJson.printJsonObj(resp,users);


    }

}
