package com.sitech.paas.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sitech.paas.entity.Http2in;
import com.sitech.paas.entity.MicroSFlow;
import com.sitech.paas.entity.RequestParam;
import com.sitech.paas.mapper.Http2inMapper;
import com.sitech.paas.mapper.MircsFlowMapper;
import com.sitech.paas.service.IHttp2inServer;
import com.sitech.paas.util.FileUtils;
import com.sitech.paas.util.JsonUtils;

/**
 * 
 * @类描述：流程服务的相关操作服务。
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.service.impl
 * @类名称：Http2inServerImp
 * @创建人：dongkw
 * @创建时间：2018年9月25日下午2:20:28
 * @修改人：dongkw
 * @修改时间：2018年9月25日下午2:20:28
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Service
public class Http2inServerImp implements IHttp2inServer {

    private final static Logger log = LoggerFactory.getLogger(Http2inServerImp.class);

    //数据是否在刷新
    private static volatile boolean isRefresh = false;

    @Autowired
    private Http2inMapper http2inMapper;

    @Autowired
    private MircsFlowMapper mircsFlowMapper;

    /**
     * 
     * @描述:从文件中加载所有的数据至数据库中
     * @方法名: saveHttp2ins
     * @param path json文件位置
     * @创建人：dongkw
     * @创建时间：2018年9月25日下午2:24:47
     * @修改人：dongkw
     * @修改时间：2018年9月25日下午2:24:47
     * @修改备注：
     * @throws
     */
    @Override
    public void saveHttp2ins(String path) {
        try{
            if(!isRefresh){
                isRefresh =true;
                long startTime = System.currentTimeMillis();
                log.debug("《=====开始同步http2in数据");
                JsonArray tabList = builderTabList(path);
                List<String> isExit = new ArrayList<>();
                //tab
                for (int i = 0; i < tabList.size(); i++) {
                    JsonObject tab = tabList.get(i).getAsJsonObject();
                    JsonArray nodes = tab.get("nodes").getAsJsonArray();
                    //流程名字
                    String label = tab.get("name").getAsString();
                    //node
                    for (int j = 0; j < nodes.size(); j++) {
                        JsonObject node = nodes.get(j).getAsJsonObject();
                        if ("http in".equals(node.get("type").getAsString())) {
                            saveHttp2in(isExit, nodes, label, node);
                        }
                    }
                }
                log.debug("http2in数据同步结束========》" +  (System.currentTimeMillis()-startTime));
            }else {
                log.info("《=========数据正在同步========》");
            }
        }catch (Exception e){
            log.info("《=========数据异常========》" + e.getMessage());
        }finally {
            isRefresh = false;
        }

    }

    @Override
    public List<Http2in> getHttp2inAll() {
        List<Http2in> http2ins = http2inMapper.findHttp2inByRequestParam(null);
        return http2ins;
    }


    @Override
    public Http2in getByHttp2inId(String id) {
        return http2inMapper.selectByPrimaryKey(id);
    }

    /**
     * 
     * @描述: 根据http2in的名称和所在的tab关键字进行检索
     * @方法名: getHttp2inDataByRequestParam
     * @param param
     * @return
     * @创建人：dongkw
     * @创建时间：2018年9月25日下午2:25:12
     * @修改人：dongkw
     * @修改时间：2018年9月25日下午2:25:12
     * @修改备注：
     * @throws
     */
    @Override
    public List<Http2in> getHttp2inDataByRequestParam(RequestParam param) {
        if (param != null) {
            int rows = param.getRows() == 0 ? 10 : param.getRows();
            int offset = param.getOffset() == 0 ? 0 : param.getOffset();
            PageHelper.startPage(offset, rows);
            List<Http2in> http2inByRequestParam = http2inMapper.findHttp2inByRequestParam(param);
            return http2inByRequestParam;
        }
        return null;
    }

    /**
     * 
     * @描述: 获取关系
     * @方法名: getRelative
     * @param http2inId
     * @param http2inChild 所有链下的节点
     * @param wires http2in的 wires。
     * @return
     * @创建人：72707
     * @创建时间：2018年9月25日下午2:25:35
     * @修改人：72707
     * @修改时间：2018年9月25日下午2:25:35
     * @修改备注：
     * @throws
     */
    @Override
    public JsonArray getRelative(String http2inId, String http2inChild,String wires) {
        JsonArray allChild = JsonUtils.changeStr2JsonArray(http2inChild);
        JsonArray wiresArray = JsonUtils.changeStr2JsonArray(wires);
        JsonArray result = null;
        if (allChild.size() > 0 && wiresArray.size() > 0){
            result = new JsonArray();
            List<String> isExists = new ArrayList<>();
            for (int i = 0; i < wiresArray.size(); i++ ){
                findRelativeWithWires(wiresArray.get(0).getAsJsonArray(),allChild,http2inId,isExists,result);
            }
        }
        return result;
    }


    /**
     * 
     * @描述: 
     * @方法名: builderTabList
     * @param path
     * @return
     * @throws IOException
     * @返回类型 JsonArray
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:27:25
     * @修改人 dongkw
     * @修改时间 2018年9月25日下午2:27:25
     * @修改备注
     * @since
     * @throws
     */
    private JsonArray builderTabList(String path) throws IOException {
        JsonArray result = new JsonArray();
        String jsonString = FileUtils.readerFile(path);
        JsonArray array = JsonUtils.changeStr2JsonArray(jsonString);
        for(int i = 0; i < array.size();i++ ){
            JsonObject node = array.get(i).getAsJsonObject();
            String type = node.get("type").getAsString();
            if("tab".equals(type)){
                JsonObject object = tabBuilder(array, node);
                result.add(object);
            }
        }
        return result;
    }

    /**
     * 
     * @描述: tab构建
     * @方法名: tabBuilder
     * @param array
     * @param node
     * @return
     * @返回类型 JsonObject
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:26:59
     * @修改人 dongkw
     * @修改时间 2018年9月25日下午2:26:59
     * @修改备注
     * @since
     * @throws
     */
    private static JsonObject tabBuilder(JsonArray array,JsonObject node){
        JsonObject tab = new JsonObject();
        JsonArray nodes = new JsonArray();
        String id = node.get("id").getAsString();
        String name = node.get("label").getAsString();
        tab.addProperty("id", id);
        tab.addProperty("name", name);
        for (int j = 0; j < array.size(); j++) {
            JsonObject gNode = array.get(j).getAsJsonObject();
            String isTab = gNode.get("type").getAsString();
            if(!"tab".equals(isTab)){
                String z = gNode.get("z").getAsString();
                if(z !=null && z.equals(id)){
                    nodes.add(gNode);
                }
            }
        }
        tab.add("nodes", nodes);
        return tab;
    }

    /**
     * 
     * @描述: 解析链路
     * @方法名: handleRelative
     * @param id
     * @param nodes
     * @param isExit
     * @param childArray
     * @param httpinId
     * @返回类型 void
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:28:39
     * @修改人 dongkw
     * @修改时间 2018年9月25日下午2:28:39
     * @修改备注
     * @since
     * @throws
     */
    private void handleRelative(String id, JsonArray nodes, List<String> isExit, JsonArray childArray, String httpinId) {
        for (int i = 0; i < nodes.size(); i++) {
            JsonObject node = nodes.get(i).getAsJsonObject();
            String nodeId = node.get("id").getAsString();
            if (id.equals(nodeId)) {
                childArray.add(node);
                JsonArray wires = node.get("wires").getAsJsonArray();
                if (wires.size() > 0){
                    JsonArray wire = wires.get(0).getAsJsonArray();
                    for (int j = 0; j < wire.size(); j++) {
                        String nextId = wire.get(j).getAsString();
                        if (isExit.contains(nextId)) {
                            continue;
                        } else {
                            isExit.add(id);
                            String type = node.get("type").getAsString();
                            if ("http request".equals(type) || "dubbo2".equals(type)) {
                                saveWithMicroserviceMapper(httpinId, node);
                            }
                            handleRelative(nextId, nodes, isExit, childArray, httpinId);
                        }
                    }
                }
            }
        }
    }


    /**
     * 解析返回的http2in 的关系链
     *
     * @param wire      当前node 的wires
     * @param allNode   http2in 下的所有node
     * @param id        当前NodeId
     * @param isExists  标记关系链是否已经存在了
     * @param result    链路结果集
     * @return
     */
    private void findRelativeWithWires(JsonArray wire, JsonArray allNode, String id, List<String> isExists, JsonArray result) {
        for (int a = 0; a < wire.size(); a++) {
            String nextId = wire.get(a).getAsString();
            String key = id + "_" + nextId;
            if (!isExists.contains(key)) {
                isExists.add(key);
                JsonObject link = new JsonObject();
                link.addProperty("source", id);
                link.addProperty("target", nextId);
                result.add(link);
                for (int b = 0; b < allNode.size(); b++) {
                    JsonObject node = allNode.get(b).getAsJsonObject();
                    String nodeId = node.get("id").getAsString();
                    if (nodeId.equals(nextId)) {
                        JsonArray wires = node.get("wires").getAsJsonArray();
                        if (wires.size() > 0) {
                            JsonArray asJsonArray = wires.get(0).getAsJsonArray();
                            findRelativeWithWires(asJsonArray, allNode, nodeId, isExists, result);
                        }
                    }
                }
            }
        }
    }


    /**
     * 
     * @描述: 更新或新增Http2in
     * @方法名: saveHttp2in
     * @param isExit
     * @param nodes
     * @param label
     * @param node
     * @返回类型 void
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:29:21
     * @修改人 dongkw
     * @修改时间 2018年9月25日下午2:29:21
     * @修改备注
     * @since
     * @throws
     */
    private void saveHttp2in(List<String> isExit, JsonArray nodes, String label, JsonObject node) {
        Http2in http2in = new Http2in();
        http2in.setZ(node.get("z").getAsString());
        String url = node.get("url").getAsString();
        String name = node.get("name").getAsString();
        if (name ==null ||"".equals(name)){
            http2in.setName(url);
        }else {
            http2in.setName(name);
        }
        http2in.setUrl(url);
        http2in.setMethod(node.get("method").getAsString());
        String wires = node.get("wires").getAsJsonArray() == null ? null : node.get("wires").getAsJsonArray().toString();
        http2in.setWires(wires);
        http2in.setTabname(label);
        String id = node.get("id").getAsString();
        http2in.setHttpId(id);
        JsonArray childArray = new JsonArray();
        handleRelative(id,nodes,isExit,childArray,id);
        http2in.setChilds(childArray.toString());
        Http2in select = http2inMapper.selectByPrimaryKey(id);
        if (select == null){
            http2inMapper.insert(http2in);
        }else if (select.equals(http2in)){
            log.info(http2in.getUrl() +"-->节点没有变化不用更新");
        }else {
            http2inMapper.updateByPrimaryKeySelective(http2in);
        }
    }

    
    /**
     * 
     * @描述: 保存原子服务同流程服务之间的映射
     * @方法名: saveWithMicroserviceMapper
     * @param httpinId
     * @param node
     * @返回类型 void
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:29:41
     * @修改人 dongkw
     * @修改时间 2018年9月25日下午2:29:41
     * @修改备注
     * @since
     * @throws
     */
    private void saveWithMicroserviceMapper(String httpinId, JsonObject node) {
        MicroSFlow microSFlow = new MicroSFlow();
        String uniqueId = node.get("uniqueId").getAsString();
        if (uniqueId != null && !"".equals(uniqueId)){
            Map<String, String> map = new HashMap<>();
            map.put("uniqueId",uniqueId);
            map.put("httpinId",httpinId);
            int count = mircsFlowMapper.getCount(map);
            if (count == 0){
                microSFlow.setHttpinid(httpinId);
                microSFlow.setUniqueid(uniqueId);
                mircsFlowMapper.insert(microSFlow);
            }
        }
    }
}
