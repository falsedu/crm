package com.dcm.crm.workbench.dao;

import com.dcm.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int addTran(Tran tran);

    List<Tran> pageList(Map<String, Object> map);

    int countAll();

    Tran getTranById(String id);

    int update(Tran t);

    List<Map<String, String>> getCharts();
}
