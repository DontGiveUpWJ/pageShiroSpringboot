package com.sitech.paas.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Http2in;
import com.sitech.paas.entity.MicroSFlow;
import com.sitech.paas.entity.Microservice;
import com.sitech.paas.entity.RequestParam;
import com.sitech.paas.service.impl.Http2inServerImp;
import com.sitech.paas.service.impl.MicroSAndFlowMapServerImp;
import com.sitech.paas.service.impl.MicroseviceServerImp;

/**
 * 
 * @类描述：hsf、rest原子服务管理接口
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：MicroserviceController
 * @创建人：dongkw
 * @创建时间：2018年9月25日下午2:12:07
 * @修改人：dongkw
 * @修改时间：2018年10月24日下午2:13:24
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Controller
@RequestMapping("/microS")
public class MicroserviceController {
	
	private static final Logger log  = LoggerFactory.getLogger(MicroserviceController.class);

    @Autowired
    private MicroSAndFlowMapServerImp microSAndFlowMapServerImp;

    @Autowired
    private Http2inServerImp http2inServerImp;
    
    @Autowired
    private MicroseviceServerImp microseviceServerImp;
    
    
    @Deprecated
    @RequestMapping("/show-all")
    public ModelAndView init(){
        ModelAndView model = new ModelAndView("node/show-all-mircoS");
        return model;
    }

    /**
     * 
     * @描述:根据查询条件返回对应的原子服务
     * @方法名: getMicroserviceWithOption
     * @param param
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:13:00
     * @修改人 dongkw
     * @修改时间 2018年10月24日下午2:13:24
     * @修改备注
     * @since
     * @throws
     */
    @GetMapping("/getWithOption")
    @ResponseBody
    public JsonResult getMicroserviceWithOption(RequestParam param){
    	JsonResult result = new JsonResult();
        try {
            if(param != null ){
                List<Microservice> microservices = microseviceServerImp.searchMicrosevice(param);
                PageInfo<Microservice> pageInfo = new PageInfo<Microservice>(microservices);
                result.setData(pageInfo);
                result.setCode(ResultCode.SUCCESS);
                result.setMessage("成功");
            }else {
            	result.setCode(ResultCode.PARAMS_ERROR);
            	result.setMessage("失败");           	 
                log.info("getWithOption-->>输入参数为null*****");
            }
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMessage("服务器异常");
			return result;
		}
        return result;
    }

    /**
     * 
     * @描述:原子服务关系数据
     * @方法名: showMircoserviceAndHttp2inRelative
     * @param param
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:13:24
     * @修改人 dongkw
     * @修改时间 2018年10月24日下午2:13:24
     * @修改备注
     * @since
     * @throws
     */
    @GetMapping("/show-relativeWithFlow")
    @ResponseBody
    public JsonResult showMircoserviceAndHttp2inRelative(@RequestBody RequestParam param){
    	JsonResult result = new JsonResult();
        Map<String,Object> map = new HashMap<>();
        if ( param != null && !StringUtils.isEmpty(param.getUniqueId())){
            try {
            	Microservice microservice = null;
				microservice = microseviceServerImp.getMicroserviceByUniqueId(param.getUniqueId());
				if (microservice != null){
				    List<MicroSFlow> microSFlows = microSAndFlowMapServerImp.findMicroFlowByUniqueId(param.getUniqueId());
				    List<Http2in> http2ins = new ArrayList<>();
				    for (MicroSFlow flow: microSFlows) {
				        Http2in http2in = http2inServerImp.getByHttp2inId(flow.getHttpinid());
				        if (http2in !=null){
				            http2ins.add(http2in);
				        }
				    }
				    map.put("mircoS", microservice);
				    map.put("http2in",http2ins);
				}
				result.setCode(ResultCode.SUCCESS);
				result.setData(map);
				result.setMessage("成功");
			} catch (Exception e) {
				log.error("showMircoserviceAndHttp2inRelative --> 请求异常***");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			    result.setMessage("服务器错误");
			    return result;
			}
        }else {
        	log.info("showMircoserviceAndHttp2inRelative --> 请求参数为空***");
        	result.setCode(ResultCode.PARAMS_ERROR);
        	result.setMessage("参数错误");
        }
        return result;
    }
    
    
    /**
     *  原子服务查询
     * @param param
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年10月24日下午2:13:24
     * @修改人 dongkw
     * @修改时间 2018年10月24日下午2:13:24
     * @修改备注
     * @since
     * @throws
     */
    @GetMapping("/searchMicros")
    @ResponseBody
    public JsonResult getMicrosDate(RequestParam params){
    	JsonResult result = new JsonResult();
        if (params == null){
            params = new RequestParam();
        }
        List<Microservice> microservices;
		try {
			microservices = microseviceServerImp.searchMicrosevice(params);
			PageInfo<Microservice> info = new PageInfo<Microservice>(microservices);
			result.setCode(ResultCode.SUCCESS);
			result.setData(info);
			result.setMessage("成功");
		} catch (Exception e) {
			log.error("searchMicrs-->> 出现异常",e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMessage("失败");
			return result;
		}
        return result;
    }
    
    
    /**
     * 
     * @描述: 根据id删除对应的记录
     * @方法名: deleteMicros
     * @param microservice
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年10月24日下午1:47:09
     * @修改人 dongkw
     * @修改时间 2018年10月24日下午1:47:09
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/deleteMicros")
    @ResponseBody
    public JsonResult deleteMicros(@RequestBody List<String> id){
    	JsonResult result = new JsonResult();
        if (id != null){
            try {
                microseviceServerImp.deleteMicrosevice(id);
                result.setCode(ResultCode.SUCCESS);
                result.setMessage("成功");
            } catch (Exception e) {
            	log.error("deleteMicros-->> 删除***id为" + id +"原子服务失败",e);
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                result.setMessage("失败");
            }
        } else {
            result.setCode(ResultCode.PARAMS_ERROR);
            result.setMessage("失败");   
        }
        return result;
    }
    
    /**
     * 
     * @描述: 保存原子服务信息
     * @方法名: saveMicrs
     * @param microservice
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw	
     * @创建时间 2018年10月24日下午1:58:41
     * @修改人 dongkw
     * @修改时间 2018年10月24日下午1:58:41
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/saveMicros")
    @ResponseBody
    public JsonResult saveMicros(@RequestBody Microservice microservice){
    	JsonResult result = new JsonResult();
        if (microservice != null){
            if (StringUtils.isEmpty(microservice.getServerUniqueid())){
                log.info("saveMicrs->>>>>> 服务标识不能为空 ***");
                result.setCode(ResultCode.PARAMS_ERROR);
                result.setMessage("失败"); 
            }else {
                int count = microseviceServerImp.getCountByUniqueId(microservice.getServerUniqueid());
                if (count <= 0){
                    microservice.setServerCtime(new Date());
                    microservice.setServerDatainitMode(true);
                    try {
                        microseviceServerImp.saveMircosevice(microservice);
                        result.setCode(ResultCode.SUCCESS);
                        result.setMessage("成功");
                    } catch (Exception e) {
                        log.error("saveMircosevice->>>>>>插入数据异常***",e);
                        result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                        result.setMessage("失败"); 
                    }
                }else {
                	log.info("saveMicrs->>>>>> 服务唯一标识已经存在 ***");
                    result.setCode(ResultCode.PARAMS_ERROR);
                    result.setMessage("失败"); 
                }
            }
        }else {
        	log.info("saveMicrs->>>>>> 保存的原子服务信息为空 ***");
            result.setCode(ResultCode.PARAMS_ERROR);
            result.setMessage("失败"); 
        }
        return  result;
    }
    
    
    /**
     * 
     * @描述: 更新原子服务
     * @方法名: updateMicrs
     * @param microservice
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年10月25日上午9:48:10
     * @修改人 dongkw
     * @修改时间 2018年10月25日上午9:48:10
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/updateMicros")
    @ResponseBody
    public JsonResult updateMicros(@RequestBody Microservice microservice){
        JsonResult result = new JsonResult();
        if (microservice !=null){
            if (StringUtils.isEmpty(microservice.getServerUniqueid())){
                log.info("updateMicrs-->> 服务标识不能为空 ***");
                result.setCode(ResultCode.PARAMS_ERROR);
                result.setMessage("失败");
            }else {
                microservice.setServerUpdatetime(new Date());
                microservice.setServerDatamodMode(true);
                try {
                    if (StringUtils.isEmpty(microservice.getServerName())){
                        microservice.setServerName(microservice.getServerUniqueid());
                    }
                    microseviceServerImp.updateMircosevice(microservice);
                    log.info("updateMicrs-->> 更新成功 ***");
                    result.setCode(ResultCode.SUCCESS);
                    result.setMessage("成功");
                } catch (Exception e) {
                    log.info("updateMicrs-->> 更新异常 ***");
                    result.setCode(ResultCode.SYS_ERROR);
                    result.setMessage("失败");
                }
            }
        }else {
            log.info("updateMicrs-->> 更新失败传入参数为空***");
            result.setCode(ResultCode.PARAMS_ERROR);
            result.setMessage("失败");
        }
        return result;
    }
    
}
