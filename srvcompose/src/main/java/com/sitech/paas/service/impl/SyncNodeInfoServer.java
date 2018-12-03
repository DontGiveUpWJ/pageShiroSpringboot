package com.sitech.paas.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sitech.paas.entity.Microservice;
import com.sitech.paas.mapper.MicroserviceMapper;
import com.sitech.paas.service.ISyncNodeInfoServer;
import com.sitech.paas.util.FileUtils;
import com.sitech.paas.util.JsonUtils;

/**
 * 
 * @类描述：节点信息同步服务
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.service.impl
 * @类名称：SyncNodeInfoServer
 * @创建人：dongkw
 * @创建时间：2018年9月25日下午2:30:49
 * @修改人：dongkw
 * @修改时间：2018年9月25日下午2:30:49
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Service
public class SyncNodeInfoServer implements ISyncNodeInfoServer{

    private static final Logger log = LoggerFactory.getLogger(SyncNodeInfoServer.class);

    private static boolean isMicroRefresh = false;
    
    @Autowired
    private MicroserviceMapper microserviceMapper;
    /**
     * 
     * @描述: 同步服务节点列表（dubbo2和http request 节点列表）
     * @方法名: synNodes
     * @param path
     * @创建人：dongkw
     * @创建时间：2018年9月25日下午2:31:38
     * @修改人：dongkw
     * @修改时间：2018年10月24日下午2:31:38
     * @修改备注：
     * @throws
     */
    @Override
    public synchronized void synNodes(String path) {
        try {
            if (!isMicroRefresh) {
                log.debug("《========开始同步原子服务");
                isMicroRefresh = true;
                String jsonString = FileUtils.readerFile(path);
                JsonArray array = JsonUtils.changeStr2JsonArray(jsonString);
                Map<String, String> isExits = new HashMap<String, String>();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject node = array.get(i).getAsJsonObject();
                    String type = node.get("type").getAsString();
                    if (("dubbo2".equals(type) || "http request".equals(type))) {
                        String uniqueId = node.get("uniqueId").getAsString();
                        if (!isExits.containsKey(uniqueId)) {
                        	isExits.put(uniqueId, uniqueId);
                            Microservice micros = null;
                            if ("dubbo2".equals(type)) {
                                micros = synDubbo2Node(node);
                            } else {
                                micros = synHttpNode(node);
                            }
                            Integer count = microserviceMapper.getCountByUniqueId(node.get("uniqueId").getAsString());
                            if (count > 0 && micros != null) {
                                micros.setServerUpdateAuthor("system");
                                micros.setServerUpdatetime(new Date());
                                micros.setServerDatamodMode(false);
                                microserviceMapper.updateByUniqueId(micros);
                            } else if (micros != null && count <= 0) {
                                micros.setServerDatainitMode(false);
                                micros.setServerCtime(new Date());
                                micros.setServerAuthor("system");
                                microserviceMapper.insert(micros);
                            }
                        }
                    }
                }
                log.debug("原子服务同步结束=========》");
            }
        }catch (Exception e){
            log.info(">>>>>>>>>>>>>>原子服务同步异常<<<<<<<<<<<<<<<"+e.getMessage());
        }finally {
            isMicroRefresh = false;
        }
    }
    
    /**
     * 
     * @描述: 给dubbo类型的服务设值
     * @方法名: synDubbo2Node
     * @param node
     * @return
     * @返回类型 Microservice
     * @创建人 dongkw
     * @创建时间 2018年10月24日下午2:34:58
     * @修改人 dongkw
     * @修改时间 2018年10月24日下午2:34:58
     * @修改备注
     * @since
     * @throws
     */
    private Microservice synDubbo2Node(JsonObject node){
        Microservice micros = new Microservice();
        micros.setServerName(node.get("name").getAsString());
        micros.setServerRegister(node.get("registry").getAsString());
        micros.setServerOrginName(node.get("interface").getAsString());
        micros.setServerUniqueid(node.get("uniqueId").getAsString());
        micros.setServerApp(node.get("app").getAsString());
        micros.setServerGroup(node.get("group").getAsString());
        micros.setServerTimeout(node.get("timeout").getAsInt()*1000);
        micros.setServerVersion(node.get("serverVer").getAsString());
        micros.setServerChName(node.get("name").getAsString());
        micros.setServerAuthor("system");
        micros.setServerDatainitMode(false);
        micros.setServerFrom("zookeeper");
        micros.setServerSystem("");
        micros.setServerType(1);
        micros.setServerCtime(new Date());
        micros.setServerMethod(node.get("methodName").getAsString());
        return micros;
    }

    
    /**
     * 
     * @描述: rest 原子服务设值
     * @方法名: synHttpNode
     * @param node
     * @return
     * @返回类型 Microservice
     * @创建人 72707
     * @创建时间 2018年10月24日下午2:35:39
     * @修改人 72707
     * @修改时间 2018年10月24日下午2:35:39
     * @修改备注
     * @since
     * @throws
     */
    private Microservice synHttpNode(JsonObject node){
        Microservice micros = new Microservice();
        micros.setServerMethod(node.get("method").getAsString());
        micros.setServerType(0);
        micros.setServerUrl(node.get("url").getAsString());
        micros.setServerTimeout(node.get("timeout").getAsInt());
        micros.setServerUniqueid(node.get("uniqueId").getAsString());
        micros.setServerChName("");
        micros.setServerName(node.get("name").getAsString());
        if (node.has("loadbalanced")){
            micros.setServerBalanceload(node.get("loadbalanced").getAsString());
        }
        micros.setServerFrom("");
        return micros;
    }
}
