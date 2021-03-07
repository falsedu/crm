package com.dcm.crm.workbench.service;

import com.dcm.crm.settings.domain.User;
import com.dcm.crm.workbench.domain.Activity;
import com.dcm.crm.workbench.domain.Clue;
import com.dcm.crm.workbench.domain.ClueRemark;
import com.dcm.crm.workbench.domain.Tran;
import com.dcm.crm.workbench.vo.PaginationVO;

import java.util.List;
import java.util.Map;

public interface ClueService {
    List<User> getUserList();

    boolean saveClue(Clue clue);

    PaginationVO<Clue> pageList(Map<String, Object> map);

    Clue getClueById(String id);
    Clue getClueById2(String id);

    boolean updateClue(Clue clue);

    boolean deleteClue(String[] ids);

    List<ClueRemark> showClueRemark(String clueId);

    List<Activity> getRelativeActivity(String clueId);

    boolean unbund(Map<String, String> map);

    List<Activity> getActivityByName(String name);

    boolean bund(Map<String, Object> map);





    boolean convert(String clueId, Tran tran,String createBy);

    boolean addRemark(ClueRemark clueRemark);
}
