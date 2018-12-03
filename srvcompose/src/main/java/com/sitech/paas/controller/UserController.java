package com.sitech.paas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Operation;
import com.sitech.paas.entity.User;
import com.sitech.paas.entity.UserRole;
import com.sitech.paas.service.OperationService;
import com.sitech.paas.service.UserRoleService;
import com.sitech.paas.service.UserService;
import com.sitech.paas.util.PasswordHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * Created by wangjun_paas on 2018/8/30.
 */
/**
 * 
 * @类描述：该类定义了用户的相关操作
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：UserController
 * @创建人：wangjun_paas
 * @创建时间：2018年9月26日上午10:31:31
 * @修改人：wangjun_paas
 * @修改时间：2018年9月26日上午10:31:31
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    
    @Autowired
    private OperationService operation; 

    @RequestMapping
    public Object getAll(@RequestBody User user /*@RequestBody Page page*/){
    	Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
         
    	JsonResult result = new JsonResult();
    	int start = user.getStart();
    	int length = user.getLength();
    	log.info("查询用户信息的入参："+user.toString());
        PageInfo<User> pageInfo = userService.selectByPage(user, start, length);
        result.setData(pageInfo);
        log.info("查询用户信息出参："+pageInfo.getList().toString());
        return result;
    }


    /**
     * 
     * @描述:保存用户角色，此处获取的角色id是以“,”分隔的字符串
     * @方法名: saveUserRoles
     * @param userRole
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:32:35
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:32:35
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/saveUserRoles")
    public Object saveUserRoles(@RequestBody UserRole userRole){
    	JsonResult result = new JsonResult();
    	log.info("保存用户角色信息入参："+userRole.toString());
        if(StringUtils.isEmpty(userRole.getUserid()) || StringUtils.isEmpty(userRole.getRoleid())) {
        	log.debug("保存用户角色入参错误");
        	//403入参错误
        	result.setCode(ResultCode.PARAMS_ERROR);
        	result.setMessage("fail");
            return result;
        }
        try {
            userRoleService.addUserRole(userRole);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("success");
            Session session = SecurityUtils.getSubject().getSession();
            User sUser = (User)session.getAttribute("userSession");
            Operation o = new Operation();
            o.setUsername(sUser.getUsername());
            o.setOpeName("更新用户角色信息");
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
     * @描述:用户的添加操作
     * @方法名: add
     * @param user
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:34:03
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:34:03
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping(value = "/add")
    public Object add(@RequestBody User user) {
    	JsonResult result = new JsonResult();
    	log.info("保存用户信息入参："+user.toString());
        User u = userService.selectByUsername(user.getUsername());
        if(u != null) {
        	log.debug("保存用户信息错误：该用户已存在。");
        	//参数错误
        	result.setCode(ResultCode.PARAMS_ERROR);
        	result.setMessage("fail");
            return result;
        }
        	
        try {
            PasswordHelper passwordHelper = new PasswordHelper();
            passwordHelper.encryptPassword(user);
            userService.save(user);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("success");
            Session session = SecurityUtils.getSubject().getSession();
            User sUser = (User)session.getAttribute("userSession");
            Operation o = new Operation();
            o.setUsername(sUser.getUsername());
            o.setOpeName("添加用户");
            operation.save(o);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //500服务器异常
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("error");
            return result;
        }
    }

    /**
     * 
     * @描述:用户删除操作
     * @方法名: delete
     * @param user
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:35:21
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:35:21
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping(value = "/delete")
    public Object delete(@RequestBody User user){
    	JsonResult result = new JsonResult();
    	log.info("删除用户信息入参信息："+user.toString());
    	if(StringUtils.isEmpty(user.getId())) {
    		log.debug("删除用户信息错误：未传入用户Id。");
    		//参数异常
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
      try{
    	  Integer id = user.getId();
          userService.delUser(id);
          result.setCode(ResultCode.SUCCESS);
          result.setMessage("success");
          Session session = SecurityUtils.getSubject().getSession();
          User sUser = (User)session.getAttribute("userSession");
          Operation o = new Operation();
          o.setUsername(sUser.getUsername());
          o.setOpeName("删除用户信息");
          operation.save(o);
          return result;
      }catch (Exception e){
    	  log.debug("删除失败");
          e.printStackTrace();
          //500服务器异常
          result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
          result.setMessage("error");
          return result;
      }
    }
    
    /**
     * 
     * @描述:修改用户开启/锁定信息
     * @方法名: changeByUserId
     * @param user
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:35:54
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:35:54
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping(value = "/change")
    public Object changeByUserId(@RequestBody User user){
    	JsonResult result = new JsonResult();
    	log.info("改变用户状态入参："+user.toString());
    	if(StringUtils.isEmpty(user.getId())) {
    		//参数异常
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
      try{
    	  Integer id = user.getId();
    	  userService.changeByUserId(id);
    	  result.setCode(ResultCode.SUCCESS);
    	  result.setMessage("success");
    	  Session session = SecurityUtils.getSubject().getSession();
          User sUser = (User)session.getAttribute("userSession");
          Operation o = new Operation();
          o.setUsername(sUser.getUsername());
          o.setOpeName("更改用户状态");
          operation.save(o);
          return result;
      }catch (Exception e){
    	  log.debug("改变用户状态失败。");
          e.printStackTrace();
          //500服务器异常
          result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
          result.setMessage("error");
          return result;
      }
    }
    
    /**
     * 
     * @描述:查询用户信息
     * @方法名: findUserByUid
     * @param user
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:36:40
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:36:40
     * @修改备注
     * @since
     * @throws
     */
    @RequestMapping("/findUserByUid")
    public Object findUserByUid(@RequestBody User user) {
    	JsonResult result = new JsonResult();
    	log.info("通过userId查询用户信息入参："+user.toString());
    	if(StringUtils.isEmpty(user.getId())) {
    		//参数异常
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
    	Integer uid = user.getId();
    	Example e = new Example(User.class);
    	Criteria criteria = e.createCriteria();
    	criteria.andEqualTo("id", uid);
    	List<User> userList = userService.selectByExample(e);
    	result.setData(userList.get(0));
    	return result;
    }
    
    /**
     * 
     * @描述:修改用户信息
     * @方法名: updateUser
     * @param user
     * @return
     * @返回类型 Object
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:37:12
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:37:12
     * @修改备注
     * @since
     * @throws
     */
    /*@RequestMapping("/update")
    public Object updateUser(@RequestBody User user) {
    	JsonResult result = new JsonResult();
    	log.info("修改用户信息入参："+user.toString());
    	if(StringUtils.isEmpty(user)) {
    		//参数异常
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
    	int count = 0;
    	List<User> uList = userService.findDontHaveMy(user);
    	for (User u : uList) {
			if(user.getUsername() != null && user.getUsername().equals(u.getUsername())) {
				count++;
			}
		}
		if(count > 0) {
			log.debug("用户信息已存在。");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMessage("error");
			return result;
		}
    	else {
    		PasswordHelper passwordHelper = new PasswordHelper();
            passwordHelper.encryptPassword(user);
    		userService.updateUserByName(user);
    		result.setCode(ResultCode.SUCCESS);
    		result.setMessage("success");
    		return result;
    	}
    	
    }*/
    
    @RequestMapping("/update")
    public Object updateUser(@RequestBody User user) {
    	JsonResult result = new JsonResult();
    	if(StringUtils.isEmpty(user)) {
    		//参数异常
    		result.setCode(ResultCode.PARAMS_ERROR);
    		result.setMessage("fail");
    		return result;
    	}
    	log.info("修改用户信息入参："+user.toString());
    	Example e = new Example(User.class);
    	Criteria criteria = e.createCriteria();
    	criteria.andEqualTo("username", user.getUsername());
    	criteria.andNotEqualTo("id", user.getId());
    	List<User> userList = userService.selectByExample(e);
		if(userList.size() > 0) {
			log.debug("用户信息已存在。");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMessage("error");
			return result;
		}
		Example e1 = new Example(User.class);
		Criteria c1 = e1.createCriteria();
		c1.andEqualTo("id", user.getId());
    	List<User> uList = userService.selectByExample(e1);
		User findUser = uList.get(0);
		if(user.getUsername().equals(findUser.getUsername()) && user.getPassword().equals(findUser.getPassword())) {
			user.setPassword(null);
			userService.updateNotNull(user);
			result.setCode(ResultCode.SUCCESS);
    		result.setMessage("success");
    		 Session session = SecurityUtils.getSubject().getSession();
             User sUser = (User)session.getAttribute("userSession");
             Operation o = new Operation();
             o.setUsername(sUser.getUsername());
             o.setOpeName("修改用户信息");
             operation.save(o);
    		return result;
		}else if(!user.getUsername().equals(findUser.getUsername())  && user.getPassword().equals(findUser.getPassword())) {

			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMessage("fail");
			return result;
		}else {
			PasswordHelper passwordHelper = new PasswordHelper();
            passwordHelper.encryptPassword(user);
			userService.updateNotNull(user);
			result.setCode(ResultCode.SUCCESS);
    		result.setMessage("success");
    		 Session session = SecurityUtils.getSubject().getSession();
             User sUser = (User)session.getAttribute("userSession");
             Operation o = new Operation();
             o.setUsername(sUser.getUsername());
             o.setOpeName("修改用户信息");
             operation.save(o);
    		return result;
		}
		
    }
    	

    @RequestMapping(value = "/operation")
    public Object findOperation(@RequestBody Operation ope){
    	int start = ope.getPageNum();
    	int length = ope.getLength();
    	PageInfo<Operation> pageInfo = operation.selectByPage(ope, start, length);
    	//Map<Object,Object> map = new HashMap<Object, Object>();
    	JsonResult result = new JsonResult();
    	result.setCode(ResultCode.SUCCESS);
    	result.setMessage("success");
    	result.setData(pageInfo);
    	/*map.put("draw", draw);
    	map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());*/
    	return result;
    }
    

}
