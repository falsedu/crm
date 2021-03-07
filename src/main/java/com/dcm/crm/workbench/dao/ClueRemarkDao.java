package com.dcm.crm.workbench.dao;

import com.dcm.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {
    List<ClueRemark> showClueRemark(String clueId);

    int deleteByCid(String clueId);

    int addRemark(ClueRemark clueRemark);
}
