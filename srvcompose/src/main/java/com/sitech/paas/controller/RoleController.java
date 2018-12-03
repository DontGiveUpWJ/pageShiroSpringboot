package com.sitech.paas.controller;

import java.util.List;

import javax.annotation.Resource;

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
import com.sitech.paas.entity.Role;
import com.sitech.paas.entity.RoleResources;
import com.sitech.paas.entity.User;
import com.sitech.paas.service.OperationService;
import com.sitech.paas.service.RoleResourcesService;
import com.sitech.paas.service.RoleService;


/**
 * 
 * @类描述：该类定义了角色的相关操作
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：RoleController
 * @创建人：wangjun_paas
 * @创建时间：2018年9月26日上午10:41:26
 * @修改人：wangjun_paas
 * @修改时间：2018年9月26日上午10:41:26
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
	private static final Logger log = LoggerFactory.getLogger(RoleController.class);
    @Resource
    private RoleService roleService;
    @Resource
    private RoleResourcesService roleResourcesService;
    
    @Autowired
    private OperationService operation;

    @RequestMapping
    public Object getAll(@RequestBody Role role){
    	JsonResult result = new JsonResult();
    	log.info("查询角色信息入参："+role.toString());
    	int start = role.getStart();
    	int length = role.getLength();
    	PageInfo<Role> pageInfo = roleService.selectByPage(role, start, length);
        result.setData(pageInfo);
        return result;
    }

    /**
     * 
     * @描述:查询用户被勾选的角色
     * @方法名: rolesWithSelected
     * @param role
     * @return
     * @返回类型 List<Role>
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:42:05
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:42:05
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/rolesWithSelected")
    public Object rolesWithSelected(@RequestBody Role role){
    	JsonResult result = new JsonResult();
    	log.info("用户查询角色勾选信息："+role.toString());
    	if(StringUtils.isEmpty(role.getId())){
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
    	Integer uid = role.getId();
    	List<Role> queryRoleListWithSelected = roleService.queryRoleListWithSelected(uid);
    	result.setData(queryRoleListWithSelected);
    	result.setCode(ResultCode.SUCCESS);
    	result.setMessage("success");
        return result;
    }

    /**
     * 
     * @描述:分配角色的权限
     * @方法名: saveRoleResources
     * @param roleResources
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:42:44
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:42:44
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/saveRoleResources")
    public Object saveRoleResources(@RequestBody RoleResources roleResources){
    	JsonResult result = new JsonResult();
    	log.info("角色分配权限入参："+roleResources.toString());
        if(StringUtils.isEmpty(roleResources.getRoleid())) {
        	//参数异常
        	result.setCode(ResultCode.PARAMS_ERROR);
        	result.setMessage("fail");
            return result;
        }
        try {
            roleResourcesService.addRoleResources(roleResources);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("success");
            Session session = SecurityUtils.getSubject().getSession();
            User sUser = (User)session.getAttribute("userSession");
            Operation o = new Operation();
            o.setUsername(sUser.getUsername());
            o.setOpeName("更改角色资源信息");
            operation.save(o);
            return result;
        } catch (Exception e) {
        	log.debug("用户分配权限失败。");
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("error");
            return result;
            
        }
    }

    /**
     * 
     * @描述:添加角色信息
     * @方法名: add
     * @param role
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:43:09
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:43:09
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping(value = "/add")
    public Object add(@RequestBody Role role) {
    	JsonResult result = new JsonResult();
    	log.info("添加角色信息入参："+role.toString());
    	if(StringUtils.isEmpty(role)) {
    		//参数异常
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
        try {
            roleService.save(role);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("success");
            Session session = SecurityUtils.getSubject().getSession();
            User sUser = (User)session.getAttribute("userSession");
            Operation o = new Operation();
            o.setUsername(sUser.getUsername());
            o.setOpeName("添加角色信息");
            operation.save(o);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("error");
            return result;
        }
    }

    /**
     * 
     * @描述:删除角色信息
     * @方法名: delete
     * @param role
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:43:32
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:43:32
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping(value = "/delete")
    public Object delete(@RequestBody Role role){
    	JsonResult result = new JsonResult();
    	log.info("删除角色信息入参："+role.toString());
    	if(StringUtils.isEmpty(role)) {
    		//参数异常
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
        try{
        	Integer id = role.getId();
            roleService.delRole(id);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("success");
            Session session = SecurityUtils.getSubject().getSession();
            User sUser = (User)session.getAttribute("userSession");
            Operation o = new Operation();
            o.setUsername(sUser.getUsername());
            o.setOpeName("删除角色信息");
            operation.save(o);
            return result;
        }catch (Exception e){
        	log.info("删除角色信息失败。");
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("error");
            return result;
        }
    }

    /**
     * 
     * @描述:查找角色信息
     * @方法名: findRoleById
     * @param role
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年10月10日下午5:24:13
     * @修改人 wangjun_paas
     * @修改时间 2018年10月10日下午5:24:13
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping(value = "/findRoleById")
    public Object findRoleById(@RequestBody Role role){
    	JsonResult result = new JsonResult();
    	try{
    		Role role2 = roleService.selectByKey(role.getId());
    		result.setData(role2);
    		return result;
    	}catch (Exception e){
    		e.printStackTrace();
    		result.setMessage("error");
    		return result;
    	}
    }
    
    @RequestMapping(value = "/updateRole")
    public Object updateRole(@RequestBody Role role){
    	JsonResult result = new JsonResult();
    	try{
    		roleService.updateNotNull(role);
    		result.setMessage("success");
			 Session session = SecurityUtils.getSubject().getSession();
	         User sUser = (User)session.getAttribute("userSession");
	         Operation o = new Operation();
	         o.setUsername(sUser.getUsername());
	         o.setOpeName("更新角色信息");
	         operation.save(o);
    		return result;
    	}catch (Exception e){
    		e.printStackTrace();
    		result.setMessage("error");
    		return result;
    	}
    }


}
