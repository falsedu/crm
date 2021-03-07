package com.dcm.crm.settings.service;

import com.dcm.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {


    Map<String, List<DicValue>> getAll();
}
