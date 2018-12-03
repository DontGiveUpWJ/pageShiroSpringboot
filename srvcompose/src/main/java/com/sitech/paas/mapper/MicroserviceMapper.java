package com.sitech.paas.mapper;


import java.util.List;

import com.sitech.paas.entity.Microservice;
import com.sitech.paas.entity.RequestParam;

public interface MicroserviceMapper {
    int deleteByPrimaryKey(Integer serverId);

    int insert(Microservice record);

    int insertSelective(Microservice record);

    Microservice selectByPrimaryKey(Integer serverId);

    int updateByPrimaryKeySelective(Microservice record);

    int updateByPrimaryKey(Microservice record);

    int updateByUniqueId(Microservice record);

    List<Microservice> searchMicrs(RequestParam param);

    Integer getCountByUniqueId(String uniqueId);

    Microservice getMicroserviceByUniqueId(String uniqueId);

    List<Microservice> getMicroserviceAll();
    
    void deleteBatch(List<String> list);
    
    Microservice queryServerFormEsbHsf(String name);

    Microservice queryServerFormEsbHttp(String name);

    List<Microservice> queryServerFormEsbList(RequestParam param);
}