package com.sitech.paas.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Instance;
import com.sitech.paas.entity.User;
import com.sitech.paas.service.NodeRedService;
import com.sitech.paas.service.impl.NodeConfigServiceImpl;
import com.sitech.paas.util.HttpUtils;

/**
 * Created by wangjun_paas on 2018/8/30.
 */
/**
 * 
 * @类描述：该类中定义了页面的跳转
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：HomeController
 * @创建人：wangjun_paas
 * @创建时间：2018年9月26日上午10:23:03
 * @修改人：wangjun_paas
 * @修改时间：2018年9月26日上午10:23:03
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Value("${user.instance.base.url:http://localhost}")
    private String userInstanceUrl;

    @Autowired
    private NodeRedService nodeRedService;


    @Autowired
	private NodeConfigServiceImpl node;
	
    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){
        return "login";
    }
    @RequestMapping("/")
    public String login1() {
    	return "login";
    }
    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, @RequestBody User user, Model model){
    	JsonResult result = new JsonResult();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());

        try {
            subject.login(token);
            request.setAttribute("username", user.getUsername());

            // 登陆成功，启动用户实例
            try {
                if (subject.hasRole("普通用户")) {
                    User userSession = (User)subject.getSession().getAttribute("userSession");
                    nodeRedService.startUserInstance(userSession);
                }
            } catch (IOException | InterruptedException e) {
                logger.error("启动用户-" + user.getUsername() + "的实例失败");
            }

            result.setCode(ResultCode.SUCCESS);
            result.setMessage("success");
            result.setData(user);
            return result;
        }catch (LockedAccountException lae) {	
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            result.setMessage("fail");
            return result;
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            result.setMessage("error");
            return result;
        }
    }
    
    /*@RequestMapping(value={"/usersPage",""})*/
    @RequestMapping(value="/usersPage")
    public String usersPage(){
        return "user/users";
    }

    @RequestMapping("/rolesPage")
    public String rolesPage(){
        return "role/roles";
    	
    }

    @RequestMapping("/resourcesPage")
    public String resourcesPage(){
        return "resources/resources";
    }

    @RequestMapping("/403")
    public String forbidden(){
        return "403";
    }
    
    @RequestMapping("/404")
    public String error404(){
        return "404";
    }
    @RequestMapping("/node")
    public Object showNode(User user) {
    	
    	return "node/node";
    }
    @RequestMapping("/indexwj")
    public Object index(User user) {
    	
    	return "index";
    }

    @RequestMapping("/nodered")
    @ResponseBody
    public Map<String, Object> nodered(String flow) {
        String baseUrl = null;
        Map<String, Object> model = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.hasRole("普通用户")) {
            Session session = currentUser.getSession();
            User user = (User) session.getAttribute("userSession");
            Instance instance = null;

            if (user != null) {
                instance = nodeRedService.getUserInstance(user);
            }

            if (instance != null && instance.getPort() != null && instance.getPort().length() > 0) {
                baseUrl = userInstanceUrl + ":" + instance.getPort();
                model.put("isUserInstance", true);
            } else {
                logger.error("未获取到用户实例信息");
            }
        } else {
            model.put("isNeedUpdate", nodeRedService.isNeedUpdate());
        }

        if (baseUrl == null) {
            baseUrl = node.nodeUrl + ":" + node.nodePort;
        }
        String url = baseUrl + "/auth/token";
        Map<String, String> m = new HashMap<>();
        m.put("client_id", "node-red-editor");
        m.put("grant_type", "password");
        m.put("scope", "");
        m.put("username", node.nodeName);
        m.put("password", node.password);
        String data = JSON.toJSONString(m);
        String doPost = HttpUtils.doPost(url, data);
        JSONObject j = JSON.parseObject(doPost);
        String token = j.getString("access_token");
        String nToken;
        nToken = baseUrl + "/?access_token=" + token;
        if (flow != null && flow.length() > 0) {
            nToken = nToken + "#flow/" + flow;
        }
        model.put("nToken", nToken);

        return model;

    }
    @RequestMapping(value = "/testLogin")
    @ResponseBody
    public Object testLogin() {
    	Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
    	JsonResult result = new JsonResult();
    	
    	if("".equals(userid) || userid == null) {
    		result.setMessage("success");
    	}
    	return result;
    }
    
}
