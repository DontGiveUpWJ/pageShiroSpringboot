package com.sitech.paas.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.entity.Flows;
import com.sitech.paas.entity.Instance;
import com.sitech.paas.service.RestService;
import com.sitech.paas.entity.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version v1.0
 * @类描述：负责与node-red交互的服务接口
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.service.impl
 * @类名称：RestServiceImpl
 * @创建人：guoqq_paas
 * @创建时间：2018/9/26 16:50
 * @修改人：guoqq_paas
 * @修改时间：2018/9/26 16:50
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
@Service
public class RestServiceImpl implements RestService {

    private final Logger logger = LoggerFactory.getLogger(RestServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${node.username}")
    private String username;

    @Value("${node.password}")
    private String password;

    private Map<Long,Token> tokenMap = new ConcurrentHashMap<>();


//    /**
//     * @描述: 获取实例的流程列表
//     * @方法名: getInstanceFlows
//     * @参数: [instance]
//     * @返回类型 java.util.List<com.sitech.paas.vo.Flow>
//     * @创建人 guoqq_paas
//     * @创建时间 2018/9/27 15:13
//     * @修改人 guoqq_paas
//     * @修改时间 2018/9/27 15:13
//     * @修改备注
//     * @since
//     * @throws
//     */
//    @Override
//    public List<Flow> getInstanceFlows(Instance instance) {
//
//        List<Flow> list = new ArrayList<>();
//
//        String uri = instance.getUrl() + "/flow/all";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        headers.add("Node-RED-API-Version", "v2");
//        Token token = getTokenByMap(instance);
//        if (token != null && token.getToken() != null && token.getValid()) {
//            headers.add("Authorization", "Bearer " + token.getToken());
//        }
//        HttpEntity httpEntity = new HttpEntity(headers);
//        ResponseEntity<String> result;
//        try {
//            result = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
//
//            String body = result.getBody();
//            JSONArray array = JSON.parseArray(body);
//            Flow flow;
//
//            for (int i = 0; i < array.size(); i++) {
//                Object object = array.get(i);
//                JSONObject obj = JSON.parseObject(object.toString());
//                flow = new Flow();
//                flow.setId(i + 1);
//                flow.setFlowId(obj.get("id").toString());
//                flow.setFlowTab(obj.get("label").toString());
//                list.add(flow);
//            }
//        } catch (HttpClientErrorException e) {
//            if ("401 Unauthorized".equals(e.getMessage())) {
//                token = getToken(instance);
//                tokenMap.put(instance.getId(),token);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }

    /**
     * @描述: 以指定的部署方式，更新实例的的流程信息
     * @方法名: updateInstanceFlows
     * @参数: [master, instance, type]
     * @返回类型 com.sitech.paas.entity.FlowFile
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 15:15
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 15:15
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public Flows updateInstanceFlows(Flows master, Instance instance, String type) throws HttpClientErrorException {

        // 获取实例流程信息
        Flows flows = getFlows(instance);

        String currentRev = flows.getRev();

        String uri = instance.getUrl() + "/flows";

        if (type == null || (!"full".equals(type) && !"flows".equals(type) && !"nodes".equals(type))){
            type = "flows";
        }

        // 设置全量更新的header信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Node-RED-API-Version","v2");
        headers.add("Node-RED-Deployment-Type",type);
        Token token = getTokenByMap(instance);
        if (token != null && token.getToken() != null && token.getValid())  {
            headers.add("Authorization","Bearer " + token.getToken());
        }
        // 获取master节点的flows
        String data = master.getFlows();

        // 通过正则表达式，将master的rev替换为当前实例的rev
        String regex = "[\\'\\\"]rev[\\'\\\"]:\\s*[\\'\\\"]\\w*[\\'\\\"]";
        String replace = "\"rev\": \"" + currentRev + "\"";
        String flowsDate = data.replaceFirst(regex,replace);

        HttpEntity<String> httpEntity = new HttpEntity<>(flowsDate, headers);
        ResponseEntity<String> result;
        try {
            result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

            String body = result.getBody();

            JSONObject obj = JSON.parseObject(body);

            String newRev = obj.getString("rev");

            flows.setRev(newRev);

            flows.setFlows(master.getFlows());
        } catch (HttpClientErrorException e) {
            if ("401 Unauthorized".equals(e.getMessage())) {
                token = getToken(instance);
                tokenMap.put(instance.getId(),token);
            }
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            flows.setRev("error");
        }

        return flows;
    }

    /**
     * @描述: 更新管理节点实例的的流程信息
     * @方法名: updateMasterFlows
     * @参数: [master, newFlows]
     * @返回类型 com.sitech.paas.entity.FlowFile
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 15:18
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 15:18
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public Flows updateMasterFlows(Instance master,Flows newFlows) throws Exception {

        String uri = master.getUrl() + "/flows";
        // 设置全量更新的header信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Node-RED-API-Version","v2");
        headers.add("Node-RED-Deployment-Type","full");
        Token token = getTokenByMap(master);
        if (token != null && token.getToken() != null && token.getValid())  {
            headers.add("Authorization","Bearer " + token.getToken());
        }
        // 获取master节点的flows
        String newData = newFlows.getFlows();
        // 获取master节点的rev
        String oldFlowsRev = getFlowsRev(master);

        // 通过正则表达式，将master的rev替换为当前实例的rev
        String regex = "[\\'\\\"]rev[\\'\\\"]:\\s*[\\'\\\"]\\w*[\\'\\\"]";
        String replace = "\"rev\": \"" + oldFlowsRev + "\"";
        String flowsDate = newData.replaceFirst(regex,replace);

        HttpEntity<String> httpEntity = new HttpEntity<>(flowsDate, headers);
        ResponseEntity<String> result;
        try {
            result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

            String body = result.getBody();

            JSONObject obj = JSON.parseObject(body);

            String newRev = obj.getString("rev");

            newFlows.setRev(newRev);

            newFlows.setFlows(body);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }

        return newFlows;
    }

//    /**
//     * @描述: 在指定主机，添加单个流程
//     * @方法名: addOneFlow
//     * @参数: [master, instance, flowId]
//     * @返回类型 java.lang.String
//     * @创建人 guoqq_paas
//     * @创建时间 2018/9/27 15:25
//     * @修改人 guoqq_paas
//     * @修改时间 2018/9/27 15:25
//     * @修改备注
//     * @since
//     * @throws
//     */
//    @Override
//    public String addOneFlow(Instance master,Instance instance,String flowId) {
//
//
//        String flowInfo = getFlow(master, flowId);
//        if(flowInfo != null) {
//            String returnId = addFlow(instance,flowInfo);
//
//            if (returnId != null) {
//                return "新增成功";
//            }
//        }
//
//        return null;
//    }

//    /**
//     * @描述: 新增流程
//     * @方法名: addFlow
//     * @参数: [instance, flowInfo]
//     * @返回类型 java.lang.String
//     * @创建人 guoqq_paas
//     * @创建时间 2018/9/27 15:26
//     * @修改人 guoqq_paas
//     * @修改时间 2018/9/27 15:26
//     * @修改备注
//     * @since
//     * @throws
//     */
//    private String addFlow(Instance instance, String flowInfo) {
//
//
//        String uri = instance.getUrl() + "/flow";
//
//        // 设置全量更新的header信息
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        Token token = getTokenByMap(instance);
//        if (token != null && token.getToken() != null && token.getValid())  {
//            headers.add("Authorization","Bearer " + token.getToken());
//        }
//
//        HttpEntity<String> httpEntity = new HttpEntity<>(flowInfo, headers);
//        String retFlowId = null;
//        try {
//            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//            String body = result.getBody();
//
//            JSONObject obj = JSON.parseObject(body);
//
//            retFlowId = obj.getString("id");
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        return retFlowId;
//    }

//    /**
//     * @描述: 在指定主机，更新单个流程
//     * @方法名: updateOneFlow
//     * @参数: [master, instance, flowId]
//     * @返回类型 java.lang.String
//     * @创建人 guoqq_paas
//     * @创建时间 2018/9/27 15:28
//     * @修改人 guoqq_paas
//     * @修改时间 2018/9/27 15:28
//     * @修改备注
//     * @since
//     * @throws
//     */
//    @Override
//    public String updateOneFlow(Instance master,Instance instance,String flowId) {
//
//        String flowInfo = getFlow(master, flowId);
//        if(flowInfo != null) {
//            String returnId = updateFlow(instance, flowId, flowInfo);
//
//            if (returnId != null && returnId.equals(flowId)) {
//                // 确保master节点的rev 与 其它节点一致
//                updateFlow(master, flowId, flowInfo);
//                return "更新成功";
//            }
//
//        }
//
//        return "更新失败";
//    }

//    /**
//     * @描述: 根据流程Id，获取对应流程信息
//     * @方法名: getFlow
//     * @参数: [instance, flowId]
//     * @返回类型 java.lang.String
//     * @创建人 guoqq_paas
//     * @创建时间 2018/9/27 15:31
//     * @修改人 guoqq_paas
//     * @修改时间 2018/9/27 15:31
//     * @修改备注
//     * @since
//     * @throws
//     */
//    private String getFlow(Instance instance,String flowId) {
//
//        String uri = instance.getUrl() + "/flow/" + flowId;
//
//        // 设置全量更新的header信息
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        Token token = getTokenByMap(instance);
//        if (token != null && token.getToken() != null && token.getValid())  {
//            headers.add("Authorization","Bearer " + token.getToken());
//        }
//        String flowInfo = null;
//        HttpEntity httpEntity = new HttpEntity(headers);
//        try {
//            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
//            flowInfo = result.getBody();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        return flowInfo;
//    }
//    /**
//     * @描述: 更新流程
//     * @方法名: updateFlow
//     * @参数: [instance, flowId, flowInfo]
//     * @返回类型 java.lang.String
//     * @创建人 guoqq_paas
//     * @创建时间 2018/9/27 15:33
//     * @修改人 guoqq_paas
//     * @修改时间 2018/9/27 15:33
//     * @修改备注
//     * @since
//     * @throws
//     */
//    private String updateFlow(Instance instance,String flowId,String flowInfo) {
//
//
//        String uri = instance.getUrl() + "/flow/" +flowId;
//
//        // 设置全量更新的header信息
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        Token token = getTokenByMap(instance);
//        if (token != null && token.getToken() != null && token.getValid())  {
//            headers.add("Authorization","Bearer " + token.getToken());
//        }
//
//        HttpEntity<String> httpEntity = new HttpEntity<>(flowInfo, headers);
//        String retFlowId = null;
//        try {
//            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, String.class);
//            String body = result.getBody();
//
//            JSONObject obj = JSON.parseObject(body);
//
//            retFlowId = obj.getString("id");
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        return retFlowId;
//    }


    /**
     * @描述: 获取实例的完整的流程信息
     * @方法名: getFlows
     * @参数: [instance]
     * @返回类型 com.sitech.paas.entity.FlowFile
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 15:34
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 15:34
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public Flows getFlows(Instance instance) {

        String uri = instance.getUrl() + "/flows";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Node-RED-API-Version","v2");
        Token token = getTokenByMap(instance);
        if (token != null && token.getToken() != null && token.getValid()) {
            headers.add("Authorization","Bearer " + token.getToken());
        }
        HttpEntity httpEntity = new HttpEntity(headers);
        Flows flows = new Flows();
        try {
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
            String body = result.getBody();

            JSONObject obj = JSON.parseObject(body);

            String rev = obj.getString("rev");

            flows.setRev(rev);
            flows.setFlows(body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return flows;
    }

    /**
     * @描述: 获取实例的流程rev信息
     * @方法名: getFlowsRev
     * @参数: [instance]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 15:45
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 15:45
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public String getFlowsRev(Instance instance) {

        String revUrl = instance.getUrl() + "/flows/rev";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Node-RED-API-Version","v2");
        Token token = getTokenByMap(instance);
        if (token != null && token.getToken() != null && token.getValid()) {
            headers.add("Authorization","Bearer " + token.getToken());
        }
        HttpEntity httpEntity = new HttpEntity(headers);
        String result = null;
        try{
            ResponseEntity<String> entity = restTemplate.exchange(revUrl, HttpMethod.GET, httpEntity, String.class);
            String body = entity.getBody();

            JSONObject obj = JSON.parseObject(body);

            result = obj.getString("rev");

        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }


    /**
     * @描述: 触发实例加载流程文件
     * @方法名: reloadFlows
     * @参数: [instance]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 15:48
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 15:48
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public String reloadFlows(Instance instance) {

        String uri = instance.getUrl() + "/flows";
        // 设置全量更新的header信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Node-RED-API-Version","v2");
        headers.add("Node-RED-Deployment-Type","reload");
        Token token = getTokenByMap(instance);
        if (token != null && token.getToken() != null && token.getValid())  {
            headers.add("Authorization","Bearer " + token.getToken());
        }

        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<String> result;
        String rev = null;
        try {
            result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

            String body = result.getBody();

            JSONObject obj = JSON.parseObject(body);

            rev = obj.getString("rev");

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return rev;
    }

    /**
     * @描述: 检测实例状态
     * @方法名: getStatus
     * @参数: [instance]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 16:07
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 16:07
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public String checkStatus(Instance instance) {

        String url = instance.getUrl() + "/auth/login";
        String result = null;
        try{
            result = restTemplate.getForObject(url, String.class);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }


    /**
     * @描述: 优先通过Map获取实例Token
     * @方法名: getTokenByMap
     * @参数: [instance]
     * @返回类型 com.sitech.paas.entity.Token
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 16:10
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 16:10
     * @修改备注
     * @since
     * @throws
     */
    private Token getTokenByMap(Instance instance) {


        Token token = tokenMap.get(instance.getId());

        // token处于有效期，则直接返回
        if(token != null && token.getValid() && token.getEndTime() != null && token.getEndTime().getTime() > System.currentTimeMillis()) {
            return token;
        }

        token = getToken(instance);
        tokenMap.put(instance.getId(),token);

        return token;
    }

    /**
     * @描述: 获取实例token
     * @方法名: getToken
     * @参数: [instance]
     * @返回类型 com.sitech.paas.entity.Token
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 16:11
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 16:11
     * @修改备注
     * @since
     * @throws
     */
    private Token getToken(Instance instance) {


        Token token = new Token();
        token.setInstanceId(instance.getId());
        try{
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add("client_id", "node-red-admin");
            requestEntity.add("grant_type","password");
            requestEntity.add("scope","*");
            requestEntity.add("username",username);
            requestEntity.add("password",password);

            String tokenUrl = instance.getUrl() + "/auth/token";

            String result = restTemplate.postForObject(tokenUrl, requestEntity, String.class);

            JSONObject obj = JSON.parseObject(result);

            String accessToken = obj.getString("access_token");
            Long expires = obj.getLong("expires_in");

            Date endTime = new Date(System.currentTimeMillis() + expires);

            token.setToken(accessToken);
            token.setExpires(expires);
            token.setEndTime(endTime);
            token.setValid(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            token.setValid(false);
        }

        logger.info("Token:" + token.getToken());

        return token;
    }
}
