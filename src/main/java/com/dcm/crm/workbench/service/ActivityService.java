package com.dcm.crm.workbench.service;

import com.dcm.crm.workbench.domain.Activity;
import com.dcm.crm.workbench.vo.PaginationVO;
import com.dcm.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    boolean save(Activity activity);


    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean deleteActivities(String[] ids);

    Activity getActivity(String id);

    boolean updateById(Activity activity);


    Activity detail(String id);

    List<ActivityRemark> getRemark(String id);

    boolean removeRemark(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);

    Map<String, Object> getUserListAndActivity(String id);

    List<Activity> getActivityByName(String name);
}
