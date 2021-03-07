package com.dcm.crm.workbench.dao;

import com.dcm.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {


    int insertOne(Activity activity);


    int countAll(Map<String,Object> map);

    List<Activity> pageList(Map<String, Object> map);

    int deleteActivities(String[] ids);


    Activity getActivity(String id);

    int updateById(Activity activity);


    Activity detail(String id);


    Activity getActivity2(String id);

    List<Activity> getActivityByName(String name);
}

