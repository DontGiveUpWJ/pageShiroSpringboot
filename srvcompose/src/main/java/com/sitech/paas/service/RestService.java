package com.sitech.paas.service;


import com.sitech.paas.entity.Flows;
import com.sitech.paas.entity.Instance;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by guoqing on 2018/9/4.
 */
public interface RestService {

    Flows updateInstanceFlows(Flows master, Instance instance, String type)  throws HttpClientErrorException;

    Flows getFlows(Instance instance);

    String getFlowsRev(Instance instance);

    Flows updateMasterFlows(Instance master, Flows newFlows) throws Exception;

    String reloadFlows(Instance instance);

    String checkStatus(Instance instance);
}
