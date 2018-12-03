package com.sitech.paas.service.impl;

import com.sitech.paas.entity.Instance;
import com.sitech.paas.entity.User;
import com.sitech.paas.service.NodeRedService;
import com.sitech.paas.util.ProcessUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version v1.0
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.service.impl
 * @类名称：NodeRedServiceImpl
 * @创建人：guoqq_paas
 * @创建时间：2018/11/7 9:59
 * @修改人：guoqq_paas
 * @修改时间：2018/11/7 9:59
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
@Service
public class NodeRedServiceImpl implements NodeRedService {

    private final Logger logger = LoggerFactory.getLogger(FlowServiceImpl.class);

    @Value("${user.instance.base.port:40000}")
    private int BASE_PORT;

    @Value("${user.node.red.path:../red.js}")
    private String redPath;

    @Value("${user.instance.workspace:../workspace-red/}")
    private String BASE_WORKSPACE;

    @Value("${user.flows.dir:../user-flows/}")
    private String USER_FLOWS_DIR;

    @Value("${user.history.dir:../history-flows/}")
    private String HISTORY_FLOW_DIR;

    private static final String UIPORT = "uiPort: process.env.PORT";

    private static final String SETTINGS_TEMPLATE = "settings.js";

    @Override
    public boolean isNeedUpdate() {
        return this.isNeedUpdate;
    }

    @Override
    public void setNeedUpdate(boolean isNeedUpdate) {
        this.isNeedUpdate = isNeedUpdate;
    }

    private volatile boolean isNeedUpdate = false;

    private Map<String,Instance> userInstanceMap = new ConcurrentHashMap<>();


    public List<String> getPm2List() {
        try {
            return ProcessUtils.pm2List();
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param appNames
     */
    public void stopUserInstanceByAppNameList(List<String> appNames) {

        if (appNames != null && appNames.size() > 0) {
            try {
                ProcessUtils.pm2Stop(appNames);
            } catch(InterruptedException e) {
                logger.error("批量关闭node-red失败");
            }
        }
    }

    private void stopUserInstanceByAppName(String appName) throws InterruptedException {

        boolean isStart = ProcessUtils.pm2List(appName);
        if (isStart) {
            logger.debug("关闭node-red：" + appName);
            ProcessUtils.pm2Stop(appName);
        } else {
            logger.debug("关闭node-red：" + appName);
        }

    }

    public void stopUserInstanceByUsername(String username) {
        if (username != null) {
            Instance instance = userInstanceMap.get(username);
            if (instance == null) {
                logger.debug("未发现用户：" + username + "的实例");
            } else {
                String appName = instance.getUsername();
                try {
                    stopUserInstanceByAppName(appName);
                } catch (InterruptedException e) {
                    logger.error("关闭node-red失败");
                }
            }
        }
    }

    public void startUserInstance(User user) throws InterruptedException, IOException {

        Instance instance = new Instance();

        int port = BASE_PORT + user.getId();
        String appName = user.getUsername() + "-" + port;

        try {
            boolean isStart = ProcessUtils.pm2List(appName);
            if (!isStart) {
                logger.info( "用户实例：" + appName + "未启动");
                if (searchUserSettings(appName)) {
                    logger.info("启动" + appName);
                    ProcessUtils.pm2Start(appName,BASE_WORKSPACE + appName,redPath);
                } else {
                    logger.info("创建用户配置文件");
                    if (createSettings(appName,port)) {
                        logger.info("启动" + appName);
                        ProcessUtils.pm2Start(appName,BASE_WORKSPACE + appName,redPath);
                    }
                }
            }
            instance.setPort(String.valueOf(port));
            instance.setUsername(appName);
            userInstanceMap.putIfAbsent(user.getUsername(), instance);
        } catch (InterruptedException e) {
            logger.error("执行命令失败",e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("创建用户配置文件失败",e.getMessage());
            throw e;
        }
    }

    public boolean pushUserFlow(User user) throws IOException {

        int port = BASE_PORT + user.getId();
        String appName = user.getUsername() + "-" + port;

        String flowPath = BASE_WORKSPACE + appName + "/flows.json";
        File source = new File(flowPath);
        if (source.exists() && source.isFile()) {
            File flowDir = new File(USER_FLOWS_DIR);
            if (!flowDir.exists()) {
                flowDir.mkdirs();
            }
            File dest = new File(USER_FLOWS_DIR + appName + ".json");
            FileUtils.copyFile(source,dest);
            setNeedUpdate(true);
            return true;
        }

        return false;
    }

    @Override
    public String pullUserFlow() throws IOException {

        File flowDir = new File(USER_FLOWS_DIR);
        if (flowDir.exists()) {
            File[] files = flowDir.listFiles();
            if (files != null && files.length > 0) {
                String totalFlow = null;
                for (File file:files) {
                    String flowJson = getFlowJson(file);
                    if (totalFlow == null) {
                        totalFlow = flowJson;
                    } else {
                        totalFlow = totalFlow.substring(0,totalFlow.lastIndexOf("]"));
                        flowJson = flowJson.substring(flowJson.indexOf("[")+1);

                        totalFlow = totalFlow + "," + flowJson;
                    }
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
                String fileName = sdf.format(new Date());
                com.sitech.paas.util.FileUtils.writeStringToFile(totalFlow,HISTORY_FLOW_DIR,fileName + ".json");
                return totalFlow;
            }

        } else {
            flowDir.mkdirs();
        }
        return null;
    }

    public Instance getUserInstance(User user) {
        if (user == null) return null;

        return userInstanceMap.get(user.getUsername());
    }

    private boolean searchUserSettings(String appName) {
        File file = new File(BASE_WORKSPACE + appName);
        if (!file.exists()) {
            logger.info("创建工作目录:" + file.getPath());
            file.mkdirs();
        }
        file = new File(BASE_WORKSPACE + appName + "/settings.js");

        return file.exists();
    }

    private boolean createSettings(String appName,int port) throws IOException {

        File userSetting = new File(BASE_WORKSPACE + appName + "/settings.js");
        logger.info("创建用户配置文件:" + userSetting.getPath());
        InputStream in = NodeRedServiceImpl.class.getClassLoader().getResourceAsStream(SETTINGS_TEMPLATE);

        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line ;
            StringBuilder sb = new StringBuilder();
            while ((line=bufferedReader.readLine()) != null) {
                if (line.indexOf(UIPORT) > 0) {
                    logger.info(line);
                    sb.append("    uiPort: ").append(port).append(",").append("\r\n");
                } else {
                    sb.append(line).append("\r\n");
                }
            }
            fileWriter = new FileWriter(userSetting);

            fileWriter.write(sb.toString());
            fileWriter.flush();
            return true;
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    private static String getFlowJson(File flow) throws FileNotFoundException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(flow));
        String line;
        StringBuilder flowsData = new StringBuilder();
        try {
            while ((line=bufferedReader.readLine()) != null) {
                flowsData.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flowsData.toString();
    }
}
