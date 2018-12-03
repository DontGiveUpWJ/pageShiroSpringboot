package com.sitech.paas.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Microservice;
import com.sitech.paas.entity.RequestParam;
import com.sitech.paas.service.impl.MicroseviceServerImp;
import com.sitech.paas.util.HttpUtils;
import com.sitech.paas.util.JsonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 72707 on 2018/11/12.
 */
@Controller
@RequestMapping("/sync")
public class EsbMicroserviceController {

    private static final Logger log = LoggerFactory.getLogger(EsbMicroserviceController.class);

    @Autowired
    private MicroseviceServerImp microseviceServerImp;


    /**
     *
     * @描述:查询esb中的原子服务http\hsf
     * @方法名: showEsbMicros
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年11月12日下午2:13:00
     * @修改人 dongkw
     * @修改时间 2018年11月12日下午2:13:24
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/showEsbMicros")
    @ResponseBody
    public JsonResult showEsbMicros(){   	
        JsonResult result = new JsonResult();
        List<Microservice> microsFromEsb = null;
        String s = getEsbInfoWithHttp();
        microsFromEsb = setMicroservice(s);
        try {
            //microsFromEsb = microseviceServerImp.queryServerFormEsbList(null);
            result.setData(microsFromEsb);
            result.setMessage("SUCCESS");
            result.setCode(ResultCode.SUCCESS);
            log.info(">>>>查询ESB中的服务信息，返回成功<<<<");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("FAIL");
            log.info(">>>>查询ESB中的服务信息，返回失败<<<<");
        }
        return result;
    }


    @GetMapping("/searchEsbMicros")
    @ResponseBody
    public JsonResult searchEsbMicros(RequestParam param){
        JsonResult result = new JsonResult();
        try {
            List<Microservice> microsFromEsb = microseviceServerImp.queryServerFormEsbList(param);
            result.setData(microsFromEsb);
            result.setMessage("SUCCESS");
            result.setCode(ResultCode.SUCCESS);
            log.info(">>>>查询ESB中的服务信息，返回成功<<<<");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("FAIL");
            log.info(">>>>查询ESB中的服务信息，返回失败<<<<");
        }
        return result;
    }


    @RequestMapping("/syncMicrosFromEsbWithOne")
    @ResponseBody
    public JsonResult syncMicrosFromEsbWithOne(RequestParam param){
        JsonResult result = new JsonResult();
        try {
            Microservice microservice = microseviceServerImp.selectSyncMicros(param);
            int count = microseviceServerImp.getCountByUniqueId(microservice.getServerUniqueid());
            if (count > 0){
                microseviceServerImp.updateMircosevice(microservice);
            }else {
                microseviceServerImp.saveMircosevice(microservice);
            }
            result.setMessage("SUCCESS");
            result.setCode(ResultCode.SUCCESS);
            result.setData(microservice);
            log.info(">>>>成功同步<<"+microservice.getServerName()+">>原子服务<<<<");
        } catch (Exception e) {
            log.error(">>>>同步原子服务失败<<<<",e);
            e.printStackTrace();
            result.setMessage("FAIL");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping("/syncMicrosFromEsb")
    @ResponseBody
    public JsonResult syncMicrosFromEsb(){
        JsonResult result = new JsonResult();
        String s = getEsbInfoWithHttp();
        List<Microservice> microservices = setMicroservice(s);
        try {
            for (Microservice microservice : microservices) {
                int count = microseviceServerImp.getCountByUniqueId(microservice.getServerUniqueid());
                if (count > 0){
                    microseviceServerImp.updateMircosevice(microservice);
                }else {
                    microseviceServerImp.saveMircosevice(microservice);
                }
            }
            result.setData(s);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("SUCCESS");
        } catch (Exception e) {
            result.setData(s);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("FAIL");
            log.error("数据插入异常........");
            e.printStackTrace();
        }
        result.setData(s);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("SUCCESS");
        return result;
    }
    
	@Value("${zk.address}")
	private String ZK_ADDRESS;
	
    @RequestMapping("/syncFromZookeeper")
    @ResponseBody
    public JsonResult syncFromZookeeper(String zookeeperIp){
    	if(null == zookeeperIp || "".equals(zookeeperIp)){
    		zookeeperIp = ZK_ADDRESS;
    	}
        JsonResult result = new JsonResult();
        try {
            microseviceServerImp.syncMicroserviceFormZookeeper(zookeeperIp);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("SUCCESS");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("同步异常");
            e.printStackTrace();
        }
        return result;

    }


    private List<Microservice> setMicroservice(String s){
        JsonObject jsonObj = JsonUtils.changeStr2JsonObj(s);
        JsonObject root = jsonObj.get("ROOT").getAsJsonObject();
        List<Microservice> microservices = null;
        if (root != null){
            JsonObject body = root.get("BODY").getAsJsonObject();
            if (body !=null){
                JsonArray outData = body.get("OUT_DATA").getAsJsonArray();
                if (outData.size() > 0){
                    microservices = new ArrayList<Microservice>(outData.size());
                    for (int i = 0 ; i < outData.size(); i++){
                        JsonObject oneDate = outData.get(i).getAsJsonObject();
                        if (oneDate != null){
                            Microservice microservice = new Microservice();
                            microservice.setServerApp(oneDate.get("APP_NAME").getAsString());
                            microservice.setServerName(oneDate.get("SRV_NAME").getAsString());
                            microservice.setServerUniqueid(oneDate.get("SRV_NAME").getAsString());
                            microservice.setServerChName(oneDate.get("SRV_CH_NAME").getAsString());
                            microservice.setServerTimeout(oneDate.get("REQ_TIMEOUT").getAsInt());
                            microservice.setServerFrom("ESB");
                            microservice.setServerVersion(oneDate.get("SRV_VER").getAsString());
                            String serverType = oneDate.get("SRVTYPE_NAME").getAsString();
                            microservice.setServerSystem(serverType);
                            if ("原子服务_HSF".equals(serverType)){
                                //服务名字转变成接口名字和方法名字
                                String serverName = oneDate.get("SRV_NAME").getAsString();
                                String source = serverName.replace("_", ".");
                                int index = source.lastIndexOf(".");
                                if (source.indexOf(".") == -1){
                                    continue;
                                }
                                String method = source.substring(index+1);
                                String api = source.substring(0,index);
                                microservice.setServerType(1);
                                microservice.setServerMethod(method);
                                microservice.setServerOrginName(api);
                                //zk地址去http://
                                String EPR1 = oneDate.get("EPR1").getAsString();
                                String register = EPR1.replace("http://", "");
                                microservice.setServerRegister(register);
                            }else {
                                microservice.setServerType(0);
                                microservice.setServerUrl(oneDate.get("EPR1").getAsString());
                            }
                            microservice.setServerSystem(oneDate.get("SYSTEM_NAME").getAsString());
                            microservice.setServerDatainitMode(true);
                            microservice.setServerAuthor("ESB");
                            microservice.setServerCtime(new Date());
                            microservice.setServerUpdatetime(new Date());
                            microservices.add(microservice);
                        }
                    }
                }
            }
        }
        return microservices;
    }
    
    private String getEsbInfoWithHttp(){
        String url = "http://172.18.231.90:51000/esbWS/jersey/invoke";
        //String url = "http://127.0.0.1:8089/microS/searchMicrs";
        String data = "{\n" +
                "    \"ROOT\": {\n" +
                "        \"BODY\": {\n" +
                "            \"BUSI_INFO\": {\n" +
                "                \"CLASS_NAME\": \"com.sitech.esb.rs.service.SrvBreakInfoService\",\n" +
                "                \"METHOD\": \"getAllSrvInfos\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"HEADER\": {}\n" +
                "    }\n" +
                "}";
        String s = HttpUtils.doPost(url, data);
        return s;
    }
}
