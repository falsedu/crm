package com.dcm.crm.workbench.service;

import com.dcm.crm.workbench.vo.PaginationVO;
import com.dcm.crm.settings.domain.User;
import com.dcm.crm.workbench.domain.Tran;
import com.dcm.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<User> getUserList();

    boolean save(Tran t, String customerName);

   PaginationVO<Tran> pageList(Map<String,Object> map);

    Tran getTranById(String id);

    List<TranHistory> showHistory(String tranId);


    Map<String, Object> updateTranAndTranHistory(Tran t, TranHistory th);

    Map<String, Object> getCharts();
}
