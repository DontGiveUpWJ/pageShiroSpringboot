package com.sitech.paas.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Http2in;
import com.sitech.paas.entity.MicroSFlow;
import com.sitech.paas.entity.Microservice;
import com.sitech.paas.service.impl.Http2inServerImp;
import com.sitech.paas.service.impl.MicroSAndFlowMapServerImp;
import com.sitech.paas.service.impl.MicroseviceServerImp;
import com.sitech.paas.service.impl.SyncNodeInfoServer;

/**
 * 
 * @类描述：服务全景接口
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：AllServerController
 * @创建人：dongkw
 * @创建时间：2018年9月25日下午2:01:34
 * @修改人：dongkw
 * @修改时间：2018年9月25日下午2:01:34
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */

@Controller
@RequestMapping("/allServer")
public class AllServerController {
	
	private static final Logger log = LoggerFactory.getLogger(AllServerController.class);
	
    @Autowired
    private Http2inServerImp http2inServerImp;

    @Autowired
    private MicroSAndFlowMapServerImp mircoSAndFlowMapServerImp;

    @Autowired
    private SyncNodeInfoServer syncNodeInfoServer;
    
    @Autowired
    private MicroseviceServerImp microseviceServerImp;
    
    /**
     * 
     * @描述:获取所有服务全景接口信息
     * @方法名: getAllServer
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:02:00
     * @修改人 dongkw
     * @修改时间 2018年10月24日下午2:02:00
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/all")
    @ResponseBody
    public JsonResult getAllServer(){
    	log.info("****服务全景图数据请求");
    	JsonResult result = new JsonResult();
        try {
			Map<String,Object> map = new HashMap<>();
			List<Microservice> microservices = microseviceServerImp.getMicroserviceAll();
			List<Http2in> http2ins = http2inServerImp.getHttp2inAll();
			List<MicroSFlow> microSFlowAll = mircoSAndFlowMapServerImp.getMircsFlowAll();
			map.put("micros",microservices);
			map.put("http2ins",http2ins);
			map.put("microSFlows", microSFlowAll);
			result.setCode(ResultCode.SUCCESS);
			result.setMessage("请求成功");
			result.setData(map);
			log.info("****服务全景图数据请求返回的数据为" + map.toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMessage("****服务器内部错误");
			log.error("****请求异常");
		} 
        return result;
    }

    @Value("${node.path}")
    private String path;
    
    /**
     * 
     * @描述:同步node-red 节点配置信息数据至管理系统中
     * @方法名: refreshJsonDate
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:06:49
     * @修改人 dongkw
     * @修改时间 2018年9月25日下午2:06:49
     * @修改备注
     * @since
     * @throws
     */
    //@GetMapping(value = "/refreshJsonData")
    @RequestMapping("/refreshJsonData")
    public JsonResult refreshJsonDate(){
    	log.info("****数据刷新");
    	JsonResult result = new JsonResult();
        try {
			http2inServerImp.saveHttp2ins(path);
			syncNodeInfoServer.synNodes(path);
			result.setCode(ResultCode.SUCCESS);
        	result.setMessage("数据更新成功");
		} catch (Exception e) {
			log.info("刷新数据异常" + e.getMessage());
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMessage("服务器异常刷新失败");
		}
        return result;
    }

}
