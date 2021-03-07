package com.dcm.crm.workbench.dao;


import com.dcm.crm.workbench.domain.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClueActivityRelationDao {


    List<String> getAidbyCid(String clueId);

    int unbund(Map<String, String> map);

    int bund(@Param("cid") String cid,@Param("aid") String id,@Param("id")String rid);

    int count(@Param("cid") String cid,@Param("id") String id);

    int deleteByCid(String clueId);

    List<ClueActivityRelation> getListByClueId(String clueId);
}
