/**
 * @标题: HttpController.java
 * @包名： com.sitech.paas.controller
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年9月28日 下午1:19:22
 * @version v1.0
 */
package com.sitech.paas.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sitech.paas.entity.Http;
import com.sitech.paas.util.HttpUtils;

/**
 * @类描述：
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：HttpController
 * @创建人：wangjun_paas
 * @创建时间：2018年9月28日下午1:19:22
 * @修改人：wangjun_paas
 * @修改时间：2018年9月28日下午1:19:22
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@RestController
@RequestMapping(value = "/http")
public class HttpController {
	
	/**
	 * 
	 * @描述:发送get请求
	 * @方法名: doGet
	 * @param http
	 * @return
	 * @返回类型 String
	 * @创建人 wangjun_paas
	 * @创建时间 2018年9月28日下午1:54:36
	 * @修改人 wangjun_paas
	 * @修改时间 2018年9月28日下午1:54:36
	 * @修改备注
	 * @since
	 * @throws
	 */
	@RequestMapping(value = "/doGet")
	public String doGet(@RequestBody Http http) {
		String doGet = HttpUtils.doGet(http.getUrl());
		return doGet;
	}
	
	/**
	 * 
	 * @描述:发送post请求
	 * @方法名: doPost
	 * @param http
	 * @return
	 * @返回类型 String
	 * @创建人 wangjun_paas
	 * @创建时间 2018年9月28日下午1:55:46
	 * @修改人 wangjun_paas
	 * @修改时间 2018年9月28日下午1:55:46
	 * @修改备注	
	 * @since
	 * @throws
	 */
	@RequestMapping(value = "/doPost")
	public String doPost(@RequestBody Http http) {
		String doPost = HttpUtils.doPost(http.getUrl(), http.getParamter());
		return doPost;
	}
	
	/**
	 * 
	 * @描述:发送soap请求
	 * @方法名: doPostSoap
	 * @param http
	 * @return
	 * @返回类型 String
	 * @创建人 wangjun_paas
	 * @创建时间 2018年9月28日下午1:56:07
	 * @修改人 wangjun_paas
	 * @修改时间 2018年9月28日下午1:56:07
	 * @修改备注
	 * @since
	 * @throws
	 */
	@RequestMapping(value = "/doPostSoap")
	public String doPostSoap(Http http) {
		String doPostSoap = HttpUtils.doPostSoap(http.getUrl(), http.getParamter());
		return doPostSoap;
	}
	
	

}
