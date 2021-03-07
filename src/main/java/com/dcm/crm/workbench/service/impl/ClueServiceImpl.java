package com.dcm.crm.workbench.service.impl;

import com.dcm.crm.workbench.dao.*;
import com.dcm.crm.settings.dao.UserDao;
import com.dcm.crm.settings.domain.User;
import com.dcm.crm.utils.DateTimeUtil;
import com.dcm.crm.utils.SqlSessionUtil;
import com.dcm.crm.utils.UUIDUtil;
import com.dcm.crm.workbench.domain.*;
import com.dcm.crm1.workbench.dao.*;
import com.dcm.crm1.workbench.domain.*;
import com.dcm.crm.workbench.service.ClueService;
import com.dcm.crm.workbench.vo.PaginationVO;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    private ActivityDao activityDao=SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ClueActivityRelationDao cad=SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private CustomerDao customerDao=SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao=SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
    private ContactsDao contactsDao=SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private TranDao tranDao=SqlSessionUtil.getSqlSession().getMapper(TranDao.class);

    private TranHistoryDao tranHistoryDao=SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private ClueRemarkDao clueRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao=SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);



    @Override
    public List<User> getUserList() {
        return userDao.selectAll();
    }

    @Override
    public boolean saveClue(Clue clue) {

        int i=clueDao.saveClue(clue);
        return i==1;
    }

    @Override
    public PaginationVO<Clue> pageList(Map<String, Object> map) {

        int total=clueDao.countAll(map);
        List<Clue> dataList=clueDao.pageList(map);

        PaginationVO<Clue> vo=new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public Clue getClueById(String id) {

       return  clueDao.getClueById(id);
    }

    @Override
    public Clue getClueById2(String id) {
        return  clueDao.getClueById2(id);
    }

    @Override
    public boolean updateClue(Clue clue) {
        return clueDao.updateClue(clue)==1;
    }

    @Override
    public boolean deleteClue(String[] ids) {
        int count=clueDao.count(ids);
        if(count!=ids.length){
            return false;
        }
        int j=clueDao.deleteClue(ids);
        if(count!=j){
            return false;
        }
        return true;
    }

    @Override
    public List<ClueRemark> showClueRemark(String clueId) {
        return clueRemarkDao.showClueRemark(clueId);
    }

    @Override
    public List<Activity> getRelativeActivity(String clueId) {
        List<String> aids=cad.getAidbyCid(clueId);
        List<Activity>as=new LinkedList<>();
        if(aids!=null){
            for(String id:aids){
                as.add(activityDao.getActivity2(id));
            }

        }

        return as;



    }

    @Override
    public boolean unbund(Map<String, String> map) {
        int i=cad.unbund(map);
        return i==1;
    }

    @Override
    public List<Activity> getActivityByName(String name) {

        return activityDao.getActivityByName(name);
    }

    @Override
    public boolean bund(Map<String, Object> map) {
        String cid= (String) map.get("cid");

        String[]ids= (String[]) map.get("ids");


        for(String id:ids){
            int j=cad.count(cid,id);
            if(j==1){
                return false;
            }
            String rid= UUIDUtil.getUUID();
           int i= cad.bund( cid, id,rid);
           if(i!=1){
               return false;
           }
        }

        return true;


    }

    @Override
    public boolean  convert(String clueId, Tran tran,String createBy) {
        boolean flag=true;
        String createTime= DateTimeUtil.getSysTime();
        Clue clue=clueDao.getClueById2(clueId);

        String company = clue.getCompany();

        Customer costomer=customerDao.getCustomerByName(company);
        if(costomer==null){
            costomer=new Customer();
            costomer.setId(UUIDUtil.getUUID());
            costomer.setAddress(clue.getAddress());
            costomer.setPhone(clue.getPhone());
            costomer.setOwner(clue.getOwner());
            costomer.setNextContactTime(clue.getNextContactTime());
            costomer.setName(clue.getCompany());
            costomer.setWebsite(clue.getWebsite());
            costomer.setCreateTime(createTime);
            costomer.setCreateBy(createBy);
            costomer.setContactSummary(clue.getContactSummary());
            costomer.setDescription(clue.getDescription());

            int count1=customerDao.addCustomer(costomer);
            if(count1!=1){
                flag=false;
            }
        }

        Contacts con=new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setSource(clue.getSource());
        con.setOwner(clue.getOwner());
        con.setNextContactTime(clue.getNextContactTime());
        con.setMphone(clue.getMphone());
        con.setJob(clue.getJob());
        con.setFullname(clue.getFullname());
        con.setAppellation(clue.getAppellation());
        con.setEmail(clue.getEmail());
        con.setDescription(clue.getDescription());
        con.setAddress(clue.getAddress());
        con.setCustomerId(costomer.getId());
        con.setCreateBy(createBy);
        con.setCreateTime(createTime);
        int count2=contactsDao.addContacts(con);
        if(count2!=1){
            flag=false;
        }
        List<ClueRemark> clueRemarks=clueRemarkDao.showClueRemark(clueId);
        if(clueRemarks!=null){
            for(ClueRemark clueRemark:clueRemarks){
               String noteContent=clueRemark.getNoteContent();
               CustomerRemark customerRemark=new CustomerRemark();
               customerRemark.setCreateBy(createBy);
               customerRemark.setCreateTime(createTime);
               customerRemark.setCustomerId(costomer.getId());
               customerRemark.setId(UUIDUtil.getUUID());
               customerRemark.setNoteContent(noteContent);
               customerRemark.setEditFlag("0");
               int count3=customerRemarkDao.addRemark(customerRemark);
               if(count3!=1){
                   flag=false;
               }

               ContactsRemark contactsRemark=new ContactsRemark();
               contactsRemark.setContactsId(con.getId());
               contactsRemark.setNoteContent(noteContent);
               contactsRemark.setId(UUIDUtil.getUUID());
               contactsRemark.setCreateBy(createBy);
               contactsRemark.setCreateTime(createTime);
               contactsRemark.setEditFlag("0");
               int count4=contactsRemarkDao.addRemark(contactsRemark);
                if(count4!=1){
                    flag=false;
                }

            }
            List<ClueActivityRelation> clueActivityRelations= cad.getListByClueId(clueId);
            for(ClueActivityRelation c:clueActivityRelations){
                String activityId = c.getActivityId();
                ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
                contactsActivityRelation.setActivityId(activityId);
                contactsActivityRelation.setContactsId(con.getId());
                contactsActivityRelation.setId(UUIDUtil.getUUID());
                int count5=contactsActivityRelationDao.addRelation(contactsActivityRelation);
                if(count5!=1){
                    flag=false;
                }
            }
            if(tran!=null){
                tran.setCreateTime(createTime);
                tran.setCreateBy(createBy);
                tran.setContactsId(con.getId());
                tran.setCustomerId(costomer.getId());
                tran.setSource(clue.getSource());
                tran.setOwner(clue.getOwner());
                tran.setNextContactTime(clue.getNextContactTime());
                tran.setDescription(clue.getDescription());
                tran.setContactSummary(clue.getContactSummary());
                int count6=tranDao.addTran(tran);

                TranHistory th=new TranHistory();
                th.setCreateBy(createBy);
                th.setCreateTime(createTime);
                th.setId(UUIDUtil.getUUID());
                th.setTranId(tran.getId());
                th.setStage(tran.getStage());
                th.setMoney(tran.getMoney());
                th.setExpectedDate(tran.getExpectedDate());

                if(count6!=1){
                    flag=false;
                }
                int count7=tranHistoryDao.addTranHistory(th);
                if(count7!=1){
                    flag=false;
                }
            }

            int count8=clueRemarkDao.deleteByCid(clueId);
            if(count8!=clueRemarks.size()){
                flag=false;
            }
            int count9=cad.deleteByCid(clueId);
            if(count9!=clueActivityRelations.size()){
                flag=false;
            }
            int count10=clueDao.deleteClueById(clueId);
            if(count10!=1){
                flag=false;
            }
        }


        return flag;
    }

    @Override
    public boolean addRemark(ClueRemark clueRemark) {
        return clueRemarkDao.addRemark(clueRemark)==1;
    }


}
