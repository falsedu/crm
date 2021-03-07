package com.dcm.crm.workbench.dao;


import com.dcm.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int saveClue(Clue clue);

    int countAll(Map<String, Object> map);

    List<Clue> pageList(Map<String, Object> map);

    Clue getClueById(String id);

    Clue getClueById2(String id);

    int updateClue(Clue clue);

    int count(String[] ids);

    int deleteClue(String[] ids);



    int deleteClueById(String clueId);
}
