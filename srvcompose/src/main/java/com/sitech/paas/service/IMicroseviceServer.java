package com.sitech.paas.service;


import java.util.List;

import com.sitech.paas.entity.Microservice;
import com.sitech.paas.entity.RequestParam;

/**
 * Created by 72707 on 2018/10/23.
 */
public interface IMicroseviceServer {

    List<Microservice> searchMicrosevice(RequestParam param) throws Exception;

    void deleteMicrosevice(List<String> uniqueId) throws Exception;

    void updateMircosevice(Microservice mics) throws Exception;

    void saveMircosevice(Microservice mics) throws Exception;

    Microservice getMicroserviceByUniqueId(String uniqueId) throws Exception;

    List<Microservice> getMicroserviceAll() throws Exception;

    int getCountByUniqueId(String uniqueId) throws Exception;
    
    void syncMicroserviceFormZookeeper(String ip) throws Exception;

    Microservice selectSyncMicros(RequestParam param) throws Exception;

    List<Microservice> queryServerFormEsbList(RequestParam param) throws Exception;
}
