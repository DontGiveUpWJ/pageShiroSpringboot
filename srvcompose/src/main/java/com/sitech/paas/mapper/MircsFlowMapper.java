package com.sitech.paas.mapper;



import java.util.List;
import java.util.Map;

import com.sitech.paas.entity.MicroSFlow;

public interface MircsFlowMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(MicroSFlow record);

    int insertSelective(MicroSFlow record);

    MicroSFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MicroSFlow record);

    int updateByPrimaryKey(MicroSFlow record);

    int getCount(Map<String, String> map);

    List<MicroSFlow> selectByUniqueId(String uniqueId);

    List<MicroSFlow> findAll();
    
    void deleteBatch(List<String> list);
}