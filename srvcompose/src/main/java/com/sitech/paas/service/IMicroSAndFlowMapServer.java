package com.sitech.paas.service;


import java.util.List;

import com.sitech.paas.entity.MicroSFlow;

/**
 * Created by 72707 on 2018/9/11.
 */
public interface IMicroSAndFlowMapServer {

    List<MicroSFlow> getMircsFlowAll();

    List<MicroSFlow> findMicroFlowByUniqueId(String uniqueId);
}
