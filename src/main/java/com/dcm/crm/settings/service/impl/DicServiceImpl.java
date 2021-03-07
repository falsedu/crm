package com.dcm.crm.settings.service.impl;

import com.dcm.crm.settings.dao.DicTypeDao;
import com.dcm.crm.settings.dao.DicValueDao;
import com.dcm.crm.settings.domain.DicType;
import com.dcm.crm.settings.domain.DicValue;
import com.dcm.crm.settings.service.DicService;
import com.dcm.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {


    private DicTypeDao dicTypeDao= SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao=SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);


    @Override
    public Map<String, List<DicValue>> getAll() {

        Map <String ,List<DicValue>> map=new HashMap<>();
        List<DicType> types=dicTypeDao.getTypeList();

        for(DicType dt:types){
            String code=dt.getCode();
            List<DicValue> dicValues=dicValueDao.getListByCode(code);
            map.put(code,dicValues);
        }



        return map;
    }
}
