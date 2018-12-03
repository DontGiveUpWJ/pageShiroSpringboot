package com.sitech.paas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonArray;
import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Http2in;
import com.sitech.paas.entity.RequestParam;
import com.sitech.paas.service.impl.Http2inServerImp;

/**
 * 
 * @类描述：流程服务接口
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：Http2inController
 * @创建人：dongkw
 * @创建时间：2018年9月25日下午2:08:31
 * @修改人：dongkw
 * @修改时间：2018年9月25日下午2:08:31
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Controller
@RequestMapping(value = "/http2in")
public class Http2inController {

    private static final Logger log = LoggerFactory.getLogger(Http2inController.class);

    @Autowired
    private Http2inServerImp http2inServerImp;

    /**
     * 弃用 返回页面
     * @return
     */
    @Deprecated
    @RequestMapping("/show-all")
    public ModelAndView init(){
        ModelAndView model = new ModelAndView();
        List<Http2in> http2ins = http2inServerImp.getHttp2inAll();
        model.addObject("http2ins",http2ins);
        model.setViewName("node/show-all-http2in");
        return model;
    }

    /**
     * 
     * @描述: 构建流程服务的执行链路的信息
     * @方法名: relativeDate
     * @param param
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:09:12
     * @修改人 dongkw
     * @修改时间 2018年9月25日下午2:09:12
     * @修改备注
     * @since
     * @throws
     */
    @GetMapping("/show-relative")
    @ResponseBody
    public JsonResult relativeDate(@RequestBody RequestParam param){
        log.debug("《====开始执行/microS/show-relative请求");
        JsonResult result= new JsonResult();
        Map<String ,Object> map = new HashMap<>();
        if (param !=null){
            String http2inId = param.getHttp2inId();
            if (http2inId != null && !"".equals(http2inId)){
                log.info("链路ID为:"+ http2inId);
                Http2in http2in = http2inServerImp.getByHttp2inId(http2inId);
                JsonArray relative = http2inServerImp.getRelative(http2in.getHttpId(), http2in.getChilds(), http2in.getWires());
                String relativeStr = relative.toString();
                map.put("child",http2in.getChilds());
                map.put("relative",relativeStr);
                result.setCode(ResultCode.SUCCESS);
                result.setMessage("请求成功");
                result.setData(map);
                log.info("/http2in/show-relative返回结果为:"+ map);
                return result;
            }else {
            	result.setCode(ResultCode.PARAMS_ERROR);
            	result.setMessage("入参错误");
            	return result;
            }
        }else{
        	result.setCode(ResultCode.PARAMS_ERROR);
        	result.setMessage("入参错误");
        	return result;
        }
        
    }

    /**
     * 
     * @描述:根据条件获取http2in信息列表
     * @方法名: queryOpt
     * @param param
     * @return
     * @返回类型 JsonResult
     * @创建人 dongkw
     * @创建时间 2018年9月25日下午2:10:20
     * @修改人 dongkw
     * @修改时间 2018年9月25日下午2:10:20
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/show-query-data")
    @ResponseBody
    public JsonResult queryOpt(@RequestBody RequestParam param){
    	JsonResult result = new JsonResult();
    	//入参为null创建一个
    	RequestParam newParam = param != null ? param : new RequestParam();
        log.info("/http2in/show-query-data入参为"+ param.toString());
        List<Http2in> http2ins = http2inServerImp.getHttp2inDataByRequestParam(newParam);
        PageInfo<Http2in> pageInfo = new PageInfo<Http2in>(http2ins);
        log.info("/http2in/show-query-data返回结果为:"+ pageInfo);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("请求成功");
        result.setData(pageInfo);
        
        return result;
    }

}
