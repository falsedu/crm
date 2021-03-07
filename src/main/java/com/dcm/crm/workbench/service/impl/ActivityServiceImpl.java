package com.dcm.crm.workbench.service.impl;

import com.dcm.crm.settings.domain.User;
import com.dcm.crm.workbench.dao.ActivityDao;
import com.dcm.crm.workbench.dao.ActivityRemarkDao;
import com.dcm.crm.workbench.domain.Activity;
import com.dcm.crm.workbench.domain.ActivityRemark;
import com.dcm.crm.workbench.service.ActivityService;
import com.dcm.crm.workbench.vo.PaginationVO;
import com.dcm.crm.settings.dao.UserDao;
import com.dcm.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao=SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao= (ActivityRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Activity activity) {
        int i=activityDao.insertOne(activity);
        if(i==1){
            return true;
        }
        return false;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        int total=activityDao.countAll(map);
        List<Activity> activities=activityDao.pageList(map);


        PaginationVO<Activity> paginationVO=new PaginationVO<>();
        paginationVO.setTotal(total);
        paginationVO.setDataList(activities);

        return paginationVO;

    }

    @Override
    public boolean deleteActivities(String[] ids) {
        boolean flag=true;
        int countAids=activityRemarkDao.selectAids(ids);

        int deleteAids=activityRemarkDao.deleteRemark(ids);

        if(countAids!=deleteAids){
            flag=false;
        }


        int i=activityDao.deleteActivities(ids);
        if(i!=ids.length){
            flag=false;
        }

        return flag;
    }

    @Override
    public Activity getActivity(String id) {
        return activityDao.getActivity(id);
    }

    @Override
    public boolean updateById(Activity activity) {
       int i= activityDao.updateById(activity);

        return i==1;
    }

    @Override
    public Activity detail(String id) {
        return activityDao.detail(id);
    }

    @Override
    public List<ActivityRemark> getRemark(String id) {

        return activityRemarkDao.getRemark(id);

    }

    @Override
    public boolean removeRemark(String id) {


        return activityRemarkDao.removeRemark(id)==1;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        return activityRemarkDao.saveRemark(activityRemark)==1;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        return activityRemarkDao.updateRemark(activityRemark)==1;


    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> users=userDao.selectAll();
        Activity a=activityDao.getActivity(id);
        Map<String ,Object> map=new HashMap<>();
        map.put("uList",users);
        map.put("a",a);
        return map;
    }

    @Override
    public List<Activity> getActivityByName(String name) {
        return activityDao.getActivityByName(name);

    }


}
