package com.sitech.paas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sitech.paas.entity.MicroSFlow;
import com.sitech.paas.mapper.MircsFlowMapper;
import com.sitech.paas.service.IMicroSAndFlowMapServer;

/**
 * 
 * @类描述：流程服务同原子服务之间的映射信息操作
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.service.impl
 * @类名称：MicroSAndFlowMapServerImp
 * @创建人：dongkw
 * @创建时间：2018年9月25日下午2:22:18
 * @修改人：dongkw
 * @修改时间：2018年9月25日下午2:22:18
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Service
public class MicroSAndFlowMapServerImp implements IMicroSAndFlowMapServer {

    @Autowired
    private MircsFlowMapper mircsFlowMapper;
    
    /**
     * 
     * @描述: 获取所有原子服务与流程服务的映射关系
     * @方法名: getMircsFlowAll
     * @return
     * @创建人：dongkw
     * @创建时间：2018年9月25日下午2:24:04
     * @修改人：dongkw
     * @修改时间：2018年9月25日下午2:24:04
     * @修改备注：
     * @throws
     */
    @Override
    public List<MicroSFlow> getMircsFlowAll() {
        List<MicroSFlow> microSFlows = mircsFlowMapper.findAll();
        return microSFlows;
    }
    
    /**
     * 
     * @描述:通过原子服务的唯一ID进行查找引用它的所有流程服务
     * @方法名: findMicroFlowByUniqueId
     * @param uniqueId
     * @return
     * @创建人：dongkw
     * @创建时间：2018年9月25日下午2:22:52
     * @修改人：dongkw
     * @修改时间：2018年9月25日下午2:22:52
     * @修改备注：
     * @throws
     */
    @Override
    public List<MicroSFlow> findMicroFlowByUniqueId(String uniqueId) {

        return mircsFlowMapper.selectByUniqueId(uniqueId);
    }


}
