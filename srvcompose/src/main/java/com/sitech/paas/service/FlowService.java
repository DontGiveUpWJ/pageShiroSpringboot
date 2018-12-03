package com.sitech.paas.service;



import com.sitech.paas.entity.Instance;

import java.util.List;
import java.util.Map;

/**
 * Created by guoqing on 2018/8/29.
 */
public interface FlowService {

    String deployFlows(Instance instance, String type) throws Exception;

    List<String> getFlowsFileList();

    String switchFlows(String fileName) throws Exception;

    String getFileName() throws Exception;

    Map<Long, String> uploadFlows(Long[] instanceIds) throws Exception;

    Map<Long, String> reloadFlows(Long[] instanceIds);

    boolean updateMasterFlow(String flow);
}
