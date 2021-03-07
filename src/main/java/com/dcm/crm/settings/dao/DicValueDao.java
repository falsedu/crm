package com.dcm.crm.settings.dao;

import com.dcm.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getListByCode(String code);
}
