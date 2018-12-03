package com.sitech.paas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Operation;
import com.sitech.paas.entity.Resources;
import com.sitech.paas.entity.RoleResources;
import com.sitech.paas.entity.User;
import com.sitech.paas.service.OperationService;
import com.sitech.paas.service.ResourcesService;
import com.sitech.paas.shiro.ShiroService;


/**
 * 
 * @类描述：资源信息的相关操作
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：ResourcesController
 * @创建人：wangjun_paas
 * @创建时间：2018年9月26日上午10:44:08
 * @修改人：wangjun_paas
 * @修改时间：2018年9月26日上午10:44:08
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@RestController
@RequestMapping("/resources")
public class ResourcesController {
	private static final Logger log = LoggerFactory.getLogger(ResourcesController.class);

    @Resource
    private ResourcesService resourcesService;
    @Resource
    private ShiroService shiroService;
    
    @Autowired
    private OperationService operation;

    @RequestMapping
    public Object getAll(@RequestBody Resources resources){
    	JsonResult result = new JsonResult();
    	log.info("查询权限入参："+resources.toString());
    	int start = resources.getStart();
    	int length = resources.getLength();
        PageInfo<Resources> pageInfo = resourcesService.selectByPage(resources, start, length);
        log.info("查询权限出参：" + pageInfo.toString());
        result.setData(pageInfo);
        return result;
    }

    /**
     * 
     * @描述:查看角色下已经有的资源
     * @方法名: resourcesWithSelected
     * @param roleResources
     * @return
     * @返回类型 List<Resources>
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:44:42
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:44:42
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/resourcesWithSelected")
    public Object resourcesWithSelected(@RequestBody RoleResources roleResources){
    	JsonResult result = new JsonResult();
    	log.info("查看角色下的权限入参" + roleResources.toString());
    	if(StringUtils.isEmpty(roleResources)) {
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
    	Integer rid = roleResources.getRoleid();
    	List<Resources> queryResourcesListWithSelected = resourcesService.queryResourcesListWithSelected(rid);
    	result.setCode(ResultCode.SUCCESS);
    	result.setMessage("success");
    	result.setData(queryResourcesListWithSelected);
        return result;
    }

    /**
     * 
     * @描述:加载该用户下的菜单
     * @方法名: loadMenu
     * @return
     * @返回类型 List<Resources>
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:45:32
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:45:32
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/loadMenu")
    public Object loadMenu(HttpServletRequest request){
    	//Integer userid = (Integer) request.getSession().getAttribute("userId");
    	JsonResult result = new JsonResult();
        Map<String,Object> map = new HashMap<>();
        Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
        map.put("type",1);
        
        map.put("userid", userid);
        List<Resources> resourcesList = resourcesService.loadUserResources(map);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("success");
        result.setData(resourcesList);
        return resourcesList;
    }

    /**
     * 
     * @描述:加载二级菜单
     * @方法名: loadMenu1
     * @return
     * @返回类型 List<Resources>
     * @创建人 Administrator
     * @创建时间 2018年11月21日下午5:05:40
     * @修改人 Administrator
     * @修改时间 2018年11月21日下午5:05:40
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/loadMenu1")
    public List<Resources> loadMenu1(HttpServletRequest request){
    	//Integer userid = (Integer) request.getSession().getAttribute("userId");
    	Map<String,Object> map = new HashMap<>();
    	Session session = SecurityUtils.getSubject().getSession();
    	Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
    	//Integer userid = user.getId();
    	map.put("type",2);
    	map.put("userid",userid);
    	List<Resources> resourcesList = resourcesService.loadUserResources(map);
    	return resourcesList;
    }
    
    /**
     * 
     * @描述:加载三级菜单
     * @方法名: loadMenu2
     * @return
     * @返回类型 List<Resources>
     * @创建人 Administrator
     * @创建时间 2018年11月21日下午5:05:51
     * @修改人 Administrator
     * @修改时间 2018年11月21日下午5:05:51
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/loadMenu2")
    public List<Resources> loadMenu2(HttpServletRequest request){
    	Integer userid = (Integer) request.getSession().getAttribute("userId");
    	Map<String,Object> map = new HashMap<>();
    	map.put("type",3);
    	map.put("userid",userid);
    	List<Resources> resourcesList = resourcesService.loadUserResources(map);
    	return resourcesList;
    }
    /**
     * 
     * @描述:资源的添加功能
     * @方法名: add
     * @param resources
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:49:51
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:49:51
     * @修改备注
     * @since
     * @throws
     */
    //@CacheEvict(cacheNames="resources", allEntries=true)
    @RequestMapping(value = "/add")
    public Object add(@RequestBody Resources resources){
    	JsonResult result = new JsonResult();
    	log.info("添加权限入参：" + resources.toString());
    	if(StringUtils.isEmpty(resources)) {
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
        try{
            resourcesService.save(resources);
            //更新权限
            shiroService.updatePermission();
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("success");
            Session session = SecurityUtils.getSubject().getSession();
            User sUser = (User)session.getAttribute("userSession");
            Operation o = new Operation();
            o.setUsername(sUser.getUsername());
            o.setOpeName("添加资源信息");
            operation.save(o);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("error");
            return result;
        }
    }
    
    /**
     * 
     * @描述:资源的删除功能
     * @方法名: delete
     * @param resources
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:50:35
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:50:35
     * @修改备注
     * @since
     * @throws
     */
    //@CacheEvict(cacheNames="resources", allEntries=true)
    @RequestMapping(value = "/delete")
    public Object delete(@RequestBody Resources resources){
    	JsonResult result = new JsonResult();
    	log.info("删除权限入参：" + resources.toString());
    	if(StringUtils.isEmpty(resources)) {
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
        try{
        	Integer id = resources.getId();
            resourcesService.delete(id);
            //更新权限
            shiroService.updatePermission();
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("success");
            Session session = SecurityUtils.getSubject().getSession();
            User sUser = (User)session.getAttribute("userSession");
            Operation o = new Operation();
            o.setUsername(sUser.getUsername());
            o.setOpeName("删除资源信息");
            operation.save(o);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("error");
            return result;
        }
    }
    
    /**
     * 
     * @描述:查找单个资源的信息
     * @方法名: findResourceById
     * @param resources
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:51:02
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:51:02
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping(value = "/findResourceById")
    public Object findResourceById(@RequestBody Resources resources) {
    	JsonResult result = new JsonResult();
    	if(StringUtils.isEmpty(resources)) {
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
    	log.info("查询权限入参：" + resources.toString());
    	Integer id = resources.getId();
    	Resources resource = resourcesService.findResourceById(id);
    	result.setCode(ResultCode.SUCCESS);
    	result.setMessage("success");
    	result.setData(resource);
    	return result;
    }
    
    /**
     * 
     * @描述:修改资源信息
     * @方法名: updateResource
     * @param resource
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:51:41
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:51:41
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping(value = "updateResource")
    public Object updateResource(@RequestBody Resources resource) {
    	JsonResult result = new JsonResult();
    	if(StringUtils.isEmpty(resource)) {
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
    	log.info("修改权限信息：" + resource.toString());
    	resourcesService.updateResource(resource);
    	//更新权限
        shiroService.updatePermission();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("success");
        Session session = SecurityUtils.getSubject().getSession();
        User sUser = (User)session.getAttribute("userSession");
        Operation o = new Operation();
        o.setUsername(sUser.getUsername());
        o.setOpeName("更新资源信息");
        operation.save(o);
    	return result;
    }
    
}
