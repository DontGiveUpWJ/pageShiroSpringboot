package com.sitech.paas.timer;


import com.sitech.paas.entity.Role;
import com.sitech.paas.entity.User;
import com.sitech.paas.service.NodeRedService;
import com.sitech.paas.service.RoleService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @version v1.0
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.core
 * @类名称：InstanceStatusJob
 * @创建人：guoqq_paas
 * @创建时间：2018/11/7 15:54
 * @修改人：guoqq_paas
 * @修改时间：2018/11/7 15:54
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
@Component
public class InstanceScheduleTask {

    private final Logger logger = LoggerFactory.getLogger(InstanceScheduleTask.class);

    @Value("${user.instance.base.port:40000}")
    private int BASE_PORT;

    @Autowired
    private NodeRedService nodeRedService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MemorySessionDAO sessionDAO;

    @Scheduled(fixedRate = 2*60*1000,initialDelay = 60*1000 )
    public void cleanUserInstanceJob() {

        logger.info("执行定时任务:cleanUserInstanceJob");

        // 需要停止的node-red实例
        List<String> stopList = new ArrayList<>();

        // 需要启动的node-red实例
        List<User> startList = new ArrayList<>();

        List<User> activeUserList = getActiveUserList();

        Map<String,User> activeAppNameMap = new HashMap<>();

        for (User user :activeUserList) {
            activeAppNameMap.put(user.getUsername() + "-" + (BASE_PORT +user.getId()),user);
        }
        Set<String> appNameSet = activeAppNameMap.keySet();

        logger.info("在线的普通用户数：" + activeUserList.size() );

        List<String> onlineAppName = nodeRedService.getPm2List();

        // 获取待停止的node-red
        if (onlineAppName != null && onlineAppName.size() > 0) {
            for (String appName:onlineAppName) {
                if (!appNameSet.contains(appName)) {
                    stopList.add(appName);
                }
            }
        }
        // 获取待启动的node-red
        if (onlineAppName != null && appNameSet.size() > 0) {
            for (String appName:appNameSet) {
                if (!onlineAppName.contains(appName)) {
                    startList.add(activeAppNameMap.get(appName));
                }
            }
        }


        if (stopList.size() > 0) {
            logger.info("清理空闲的实例");
            nodeRedService.stopUserInstanceByAppNameList(stopList);
        }

        if (startList.size() > 0) {
            logger.info("启动缺失的实例");
            for (User user:startList) {
                try {
                    nodeRedService.startUserInstance(user);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private List<User> getActiveUserList() {
        List<User> activeUsers = new ArrayList<>();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        User user;
        for (Session session:activeSessions) {
            if ((user = (User)session.getAttribute("userSession")) != null) {
                List<Role> roles = roleService.queryRoleListWithSelected(user.getId());
                for (Role role:roles) {
                    if ((role.getSelected() == 1) && (role.getRoledesc().equals("普通用户"))) {
                        activeUsers.add(user);
                    }
                }
            }
        }
        return activeUsers;
    }
}