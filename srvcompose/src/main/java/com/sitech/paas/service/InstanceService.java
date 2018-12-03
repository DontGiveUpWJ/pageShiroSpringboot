package com.sitech.paas.service;



import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.Instance;

import java.util.List;
import java.util.Map;

/**
 * Created by guoqing on 2018/8/29.
 */
public interface InstanceService {

    Instance getMasterInstance() throws Exception;

    Instance getInstanceById(Long id);

    void disabledInstance(Long[] instanceIds);

    List<Instance> getSalverInstance();

    PageInfo<Instance> getSalverByPage(Instance instance, int start, int length);

    String deleteInstance(Long[] instanceIds);

    Map<Long, String> getRevStatus(Long[] ids) throws Exception;

    PageInfo<Instance> selectByPage(Instance instance, int start, int length);

    int saveOrUpdateInstance(Instance instance);

    Map<Long,String> checkStatus(Long[] ids);

    Map<Long,String> checkHostStatus(Long[] ids);
}
