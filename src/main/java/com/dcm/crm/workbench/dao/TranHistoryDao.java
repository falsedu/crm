package com.dcm.crm.workbench.dao;

import com.dcm.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int addTranHistory(TranHistory th);

    List<TranHistory> showHistory(String tranId);
}
