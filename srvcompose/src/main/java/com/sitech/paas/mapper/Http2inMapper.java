package com.sitech.paas.mapper;


import java.util.List;
import java.util.Map;

import com.sitech.paas.entity.Http2in;
import com.sitech.paas.entity.RequestParam;

public interface Http2inMapper {

    int deleteByPrimaryKey(String id);

    int insert(Http2in record);

    int insertSelective(Http2in record);

    Http2in selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Http2in record);

    int updateByPrimaryKeyWithBLOBs(Http2in record);

    int updateByPrimaryKey(Http2in record);

    List<Http2in> findLikeChildId(Map<String, String> map);

    List<Http2in> findAll();

    List<Http2in> findHttp2inByRequestParam(RequestParam param);

}