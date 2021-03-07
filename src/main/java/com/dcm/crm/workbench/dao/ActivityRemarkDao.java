package com.dcm.crm.workbench.dao;

import com.dcm.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    int selectAids(String[] ids);

    int deleteRemark(String[] ids);

    List<ActivityRemark> getRemark(String id);

    int removeRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
