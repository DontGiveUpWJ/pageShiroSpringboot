package com.sitech.paas.timer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sitech.paas.service.impl.Http2inServerImp;
import com.sitech.paas.service.impl.SyncNodeInfoServer;


@Component
public class ScheduleRefreshNode2DB {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduleRefreshNode2DB.class);
	
	
    @Autowired
    private SyncNodeInfoServer syncNodeInfoServer;
    
    @Autowired
    private Http2inServerImp http2inServerImp;
    
    
    @Value("${node.path}")
    private String path;
    
    @Scheduled(cron = "0 0/30 * * * *")
    public void scheduled(){
        log.info("=====>>>>>开始执行定时任务，刷新节点数据至DB中",new Date().toString());
		http2inServerImp.saveHttp2ins(path);
		syncNodeInfoServer.synNodes(path);
		log.info("=====>>>>>执行定时任务结束",new Date().toString());
    }
    
}	
