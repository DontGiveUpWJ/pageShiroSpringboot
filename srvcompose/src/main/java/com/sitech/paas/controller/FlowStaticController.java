package com.sitech.paas.controller;

import com.github.pagehelper.PageInfo;
import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Operation;
import com.sitech.paas.entity.StaticNode;
import com.sitech.paas.entity.User;
import com.sitech.paas.service.OperationService;
import com.sitech.paas.service.StaticNodeService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @类描述：流程同步服务接口
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：FlowController
 * @创建人：guoqq_paas
 * @创建时间：2018/9/26 16:42
 * @修改人：guoqq_paas
 * @修改时间：2018/9/26 16:42
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
@RestController
@RequestMapping("/staticNode")
public class FlowStaticController {

    private static final Logger logger = LoggerFactory.getLogger(FlowStaticController.class);

    @Autowired
    private StaticNodeService nodeService;
    
    @Autowired
    private OperationService operation;


    /**
     * 
     * @描述:查询所有的静态node信息
     * @方法名: queryAll
     * @param flowid
     * @param flowdata
     * @return
     * @返回类型 Object
     * @创建人 NeverGiveUp-WJ
     * @创建时间 2018年11月9日上午9:14:34
     * @修改人 NeverGiveUp-WJ
     * @修改时间 2018年11月9日上午9:14:34
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/flowupdate")
    public Object queryAll(@RequestBody StaticNode node){
    	int start = node.getPageNum();
    	int length = node.getLength();
    	PageInfo<StaticNode> pageInfo = nodeService.selectByPage(node, start, length);
    	JsonResult result = new JsonResult();
    	result.setMessage("success");
    	result.setCode(ResultCode.SUCCESS);
    	result.setData(pageInfo);
    	return result;
    }
    
    /**
     * 
     * @描述:通过nid查找node信息
     * @方法名: findNodeById
     * @param node
     * @return
     * @返回类型 Object
     * @创建人 NeverGiveUp-WJ
     * @创建时间 2018年11月9日下午2:15:18
     * @修改人 NeverGiveUp-WJ
     * @修改时间 2018年11月9日下午2:15:18
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/findNodeById")
    public Object findNodeById(StaticNode node) {
    	StaticNode findNode = nodeService.findNodeById(node);
    	JsonResult result = new JsonResult();
    	result.setData(findNode);
    	result.setCode(ResultCode.SUCCESS);
    	result.setMessage("success");
    	return result;
    }
    
    /**
     * 
     * @描述:静态node页面保存按钮
     * @方法名: staticNodeUpdate
     * @param node
     * @return
     * @返回类型 Object
     * @创建人 NeverGiveUp-WJ
     * @创建时间 2018年11月9日下午2:14:54
     * @修改人 NeverGiveUp-WJ
     * @修改时间 2018年11月9日下午2:14:54
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/StaticNodeUpdate")
    public Object staticNodeUpdate(StaticNode node) {
    	nodeService.updateNotNull(node);
    	JsonResult result = new JsonResult();
    	result.setCode(ResultCode.SUCCESS);
    	result.setMessage("success");
    	 Session session = SecurityUtils.getSubject().getSession();
         User sUser = (User)session.getAttribute("userSession");
         Operation o = new Operation();
         o.setUsername(sUser.getUsername());
         o.setOpeName("更改静态node信息");
         operation.save(o);
    	return result;
    }
    /**
     * 
     * @描述:添加静态node信息
     * @方法名: addNode
     * @param node
     * @return
     * @返回类型 Object
     * @创建人 NeverGiveUp-WJ
     * @创建时间 2018年11月9日下午2:14:37
     * @修改人 NeverGiveUp-WJ
     * @修改时间 2018年11月9日下午2:14:37
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/add")
    public Object addNode(StaticNode node) {
    	JsonResult result = new JsonResult();
    	node.setFlowData("{}");
    	nodeService.addStaticNode(node);
    	result.setMessage("success");
    	 Session session = SecurityUtils.getSubject().getSession();
         User sUser = (User)session.getAttribute("userSession");
         Operation o = new Operation();
         o.setUsername(sUser.getUsername());
         o.setOpeName("添加静态node信息");
         operation.save(o);
    	return result;
    }
    
    
    @RequestMapping("/delete")
    public Object delNode(StaticNode node) {
    	int key = node.getNid();
    	nodeService.delete(key);
    	JsonResult result = new JsonResult();
    	result.setMessage("success");
    	 Session session = SecurityUtils.getSubject().getSession();
         User sUser = (User)session.getAttribute("userSession");
         Operation o = new Operation();
         o.setUsername(sUser.getUsername());
         o.setOpeName("删除静态node信息");
         operation.save(o);
    	return result;
    }

    
    


}
