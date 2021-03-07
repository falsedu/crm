package com.dcm.crm.workbench.service.impl;

import com.dcm.crm.workbench.dao.CustomerDao;
import com.dcm.crm.workbench.dao.TranDao;
import com.dcm.crm.settings.dao.UserDao;
import com.dcm.crm.settings.domain.User;
import com.dcm.crm.utils.SqlSessionUtil;
import com.dcm.crm.utils.UUIDUtil;
import com.dcm.crm.workbench.dao.TranHistoryDao;
import com.dcm.crm.workbench.domain.Customer;
import com.dcm.crm.workbench.domain.StageChangeReturn;
import com.dcm.crm.workbench.domain.Tran;
import com.dcm.crm.workbench.domain.TranHistory;
import com.dcm.crm.workbench.service.TranService;
import com.dcm.crm.workbench.vo.PaginationVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {


    private TranDao tranDao= SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao= SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private CustomerDao customerDao=SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    @Override
    public List<User> getUserList() {
        return userDao.selectAll();
    }

    @Override
    public boolean save(Tran t, String customerName) {
        boolean flag=true;

        Customer customer = customerDao.getCustomerByName(customerName);
        if(customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(t.getOwner());
            customer.setDescription(t.getDescription());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setName(customerName);
            customer.setCreateBy(t.getCreateBy());
            customer.setCreateTime(t.getCreateTime());

            int count=customerDao.addCustomer(customer);
            if(count!=1){
                flag=false;
            }
        }
        t.setCustomerId(customer.getId());
        int count1=tranDao.addTran(t);
        if(count1!=1){
            flag=false;
        }

        TranHistory th=new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());
        th.setCreateBy(t.getCreateBy());
        th.setCreateTime(t.getCreateTime());
        th.setStage(t.getStage());
        th.setTranId(t.getId());
        int count2=tranHistoryDao.addTranHistory(th);
        if(count2!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Tran> pageList(Map<String,Object> map) {

        PaginationVO<Tran> vo=new PaginationVO<>();
        int total=tranDao.countAll();
        List<Tran> trans=tranDao.pageList(map);

        vo.setTotal(total);
        vo.setDataList(trans);

        return vo;

    }

    @Override
    public Tran getTranById(String id) {

        return tranDao.getTranById(id);

    }


    @Override
    public List<TranHistory> showHistory(String tranId) {

        List<TranHistory> histories=tranHistoryDao.showHistory(tranId);

        return histories;
    }

    @Override
    public Map<String, Object> updateTranAndTranHistory(Tran t, TranHistory th) {
        Map<String,Object> map=new HashMap<>();
        boolean success=true;
        int count=tranDao.update(t);
        if(count!=1){
            success=false;
        }
        th.setId(UUIDUtil.getUUID());
        int count2=tranHistoryDao.addTranHistory(th);
        if(count2!=1){
            success=false;
        }
        map.put("success",success);
        StageChangeReturn re=new StageChangeReturn();
        re.setEditBy(t.getEditBy());
        re.setEditTime(t.getEditTime());
        re.setStage(t.getStage());

        map.put("t",re);

        return map;
    }

    @Override
    public Map<String, Object> getCharts() {
        Map<String ,Object> map=new HashMap<>();
        int total=tranDao.countAll();
        map.put("total",total);
        List<Map<String,String>> datalist=tranDao.getCharts();


        map.put("dataList",datalist);
        return map;
    }
}
