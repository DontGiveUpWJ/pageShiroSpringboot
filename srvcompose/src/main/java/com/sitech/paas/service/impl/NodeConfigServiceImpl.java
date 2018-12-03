package com.sitech.paas.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sitech.paas.service.NodeConfigService;


@Service
public class NodeConfigServiceImpl implements NodeConfigService{
	
	@Value("${node.url}")
	public  String nodeUrl;
	
	@Value("${node.port}")
	public  String nodePort;
	
	@Value("${node.username}")
	public String nodeName;
	
	@Value("${node.password}")
	public String password;

}
