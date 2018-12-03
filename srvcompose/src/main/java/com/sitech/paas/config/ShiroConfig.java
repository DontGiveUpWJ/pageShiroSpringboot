package com.sitech.paas.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.util.StringUtil;
import com.sitech.paas.entity.Resources;
import com.sitech.paas.service.ResourcesService;
import com.sitech.paas.shiro.MyShiroRealm;

/*import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;*/


/**
 * 
 * @类描述：
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.config
 * @类名称：ShiroConfig
 * @创建人：wangjun_paas
 * @创建时间：2018年9月25日下午5:41:16
 * @修改人：wangjun_paas
 * @修改时间：2018年9月25日下午5:41:16
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Configuration
public class ShiroConfig {
    @Autowired(required = false)
    private ResourcesService resourcesService;
    
/*
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;*/

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    
    /**
     * 
     * @描述:ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @方法名: shiroDialect
     * @return
     * @返回类型 ShiroDialect
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:04:04
     * @修改人 NeverGiveUp-WJ
     * @修改时间 2018年9月26日上午10:04:04
     * @修改备注
     * @since
     * @throws
     */
   /* @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }*/
    
    /**
     * 
     * @描述:
     * ShiroFilterFactoryBean 处理拦截资源文件问题。注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
      * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     * @方法名: shirFilter
     * @param securityManager
     * @return
     * @返回类型 ShiroFilterFactoryBean
     * @创建人 wangjun_paas
     * @创建时间 2018年9月25日下午5:43:58
     * @修改人 wangjun_paas
     * @修改时间 2018年9月25日下午5:43:58
     * @修改备注
     * @since
     * @throws
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/indexwj");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/font-awesome/**","anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/defaultKaptcha", "anon");
        filterChainDefinitionMap.put("/login/**", "anon");
        filterChainDefinitionMap.put("/indexwj/**", "anon");
        filterChainDefinitionMap.put("/**", "anon");
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //自定义加载权限资源关系
        List<Resources> resourcesList = resourcesService.queryAll();
         for(Resources resources:resourcesList){

            if (StringUtil.isNotEmpty(resources.getResurl())) {
                String permission = "perms[" + resources.getResurl()+ "]";
                filterChainDefinitionMap.put(resources.getResurl(),permission);
            }
        }
        //filterChainDefinitionMap.put("/**", "authc");
        


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        //securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 
     * @描述:凭证匹配器(由于我们的密码校验交给shiro的SimpleAuthenticationInfo进行了处理，
     * 所以我们需要修改doGetAuthenticationInfo中的代码。
     * @方法名: hashedCredentialsMatcher
     * @return
     * @返回类型 HashedCredentialsMatcher
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:10:41
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:10:41
     * @修改备注
     * @since
     * @throws
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }


   
    /**
     * 
     * @描述:开启shiro aop 注解支持。使用代理方式，所以需要开启代码支持。
     * @方法名: authorizationAttributeSourceAdvisor
     * @param securityManager
     * @return
     * @返回类型 AuthorizationAttributeSourceAdvisor
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午10:17:11
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午10:17:11
     * @修改备注
     * @since
     * @throws
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     */
    /*public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(timeout);
       // redisManager.setPassword(password);
        return redisManager;
    }*/

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    /*public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }*/


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    /*@Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }*/

    @Bean
    public MemorySessionDAO memorySessionDAO() {
        return new MemorySessionDAO();
    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(memorySessionDAO());
        //sessionManager.setSessionIdCookieEnabled(false);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

}
