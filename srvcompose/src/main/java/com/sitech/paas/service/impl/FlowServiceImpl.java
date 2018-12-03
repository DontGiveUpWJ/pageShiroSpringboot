package com.sitech.paas.service.impl;

import com.jcraft.jsch.SftpException;
import com.sitech.paas.entity.Flows;
import com.sitech.paas.entity.Instance;
import com.sitech.paas.service.FlowService;
import com.sitech.paas.service.InstanceService;
import com.sitech.paas.service.RestService;
import com.sitech.paas.util.FileUtils;
import com.sitech.paas.util.SFTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version v1.0
 * @类描述：流程同步服务接口
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.service.impl
 * @类名称：FlowServiceImpl
 * @创建人：guoqq_paas
 * @创建时间：2018/9/26 16:35
 * @修改人：guoqq_paas
 * @修改时间：2018/9/26 16:35
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
@Service
public class FlowServiceImpl implements FlowService {

    private final Logger logger = LoggerFactory.getLogger(FlowServiceImpl.class);

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private RestService restService;

    @Value("${node.flows.path:../flows/}")
    private String FILE_PATH;

    // key:流程的rev，value：创建时间
    private volatile Map<String,Long> historyFlows = new LinkedHashMap<>();

    /**
     * @描述: 获取管理节点的流程文件名
     * @方法名: getFileName
     * @参数: []
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 13:16
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 13:16
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public String getFileName() throws Exception {

        logger.debug("执行getFileName()方法");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

        Instance master = instanceService.getMasterInstance();
        String flowsRev = restService.getFlowsRev(master);
        Long time = historyFlows.get(flowsRev);
        if (time != null) {
            return sdf.format(time);
        }
        return "error";
    }

    /**
     * @描述: 根据指定部署方式，对单个实例进行部署
     * @方法名: deployFlows
     * @参数: [instance, type]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 13:18
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 13:18
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public String deployFlows(Instance instance,String type) throws Exception {

        logger.debug("执行deployFlows({},{})方法",instance.toString(),type);

        Instance master = instanceService.getMasterInstance();
        Flows masterFlows = restService.getFlows(master);

        if (masterFlows.getRev() == null) {
            return "同步失败";
        }

        Flows flows;

        try {
            flows = restService.updateInstanceFlows(masterFlows, instance,type);
        } catch (HttpClientErrorException e) {
            // 认证失败，则重新获取token后，再次调用
            flows = restService.updateInstanceFlows(masterFlows, instance,type);
        }

        if(flows.getRev() != null && flows.getRev().equals(masterFlows.getRev())) {

            // 查看历史history中，是否存在改rev
            Long time = historyFlows.get(masterFlows.getRev());
            if(time == null) {
                logger.info("保存新的流程信息");
                String rev = masterFlows.getRev();
                long currentTime = System.currentTimeMillis();
                flows.setFilename(rev + "-" + currentTime);
                // 保存该rev的流程信息
                saveFlows(flows);
                // 将改流程添加到缓存map中
                historyFlows.putIfAbsent(rev,currentTime);
            }
            return "同步成功";
        }
        return "同步失败";
    }

    /**
     * @描述: 切换管理节点运行的流程文件
     * @方法名: switchFlows
     * @参数: [fileName]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 13:20
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 13:20
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public String switchFlows(String fileName) throws Exception {


        logger.debug("执行switchFlows({})方法",fileName);


        String[] elements = fileName.split("-");

        if(elements.length == 2) {
            return "";
        }
        String rev = elements[0];

        Instance master = instanceService.getMasterInstance();

        String flowsData = getFlows(fileName);

        if(flowsData == null) {
            return "获取该版本流程失败";
        }
        Flows flows = new Flows();
        flows.setFlows(flowsData);
        flows.setRev(rev);
        Flows newFlows = restService.updateMasterFlows(master,flows);
        if (newFlows.getRev().equals("error")) {
            return "切换该版本流程失败";
        }
        return "success";
    }

    @Override
    public List<String> getFlowsFileList() {

        logger.debug("getFlowsFile()");

        File dir = new File(FILE_PATH);

        if (!dir.exists()) {
            historyFlows.clear();
            return null;
        }
        File[] files = dir.listFiles();

        if (files == null || files.length < 1) {
            historyFlows.clear();
            return null;
        }

        Map<String,Long> historyMap = new HashMap<>();

        for (File file : files) {
            String fileName = file.getName();
            String[] elements = fileName.split("-");
            if (elements.length == 2) {
                String rev = elements[0];
                long time = Long.valueOf(elements[1]);
                historyMap.put(rev,time);
            }
        }

        List<Map.Entry<String,Long>> list = new ArrayList<>(historyMap.entrySet());
        // 根据时间大小排序（降序）
        list.sort((o1, o2) -> ((o2.getValue() - o1.getValue() == 0) ?
                0 : (o2.getValue() - o1.getValue() > 0) ? 1 : -1));

        Map<String,Long> historyTmp = new LinkedHashMap<>();
        List<String> flowNameList = new ArrayList<>();
        List<String> removeFlowsList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");


        for (int i = 0; i < list.size(); i++) {
            if (i >= 10) {
                removeFlowsList.add(list.get(i).getKey() + "-" + list.get(i).getValue());
            } else {
                historyTmp.put(list.get(i).getKey(),list.get(i).getValue());
                flowNameList.add(sdf.format(list.get(i).getValue()));
            }
        }

        if (removeFlowsList.size() > 0) {
            for (String fileName: removeFlowsList) {
                File flows = new File(FILE_PATH + fileName);
                if (flows.exists()) {
                    flows.delete();
                }
            }
        }

        historyFlows = historyTmp;

        return  flowNameList;
    }


    private void saveFlows(Flows flows) {
        logger.debug("执行saveFlows({})方法",flows);
        try {
            FileUtils.writeStringToFile(flows.getFlows(),FILE_PATH,flows.getFilename());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * @描述: 根据文件名，获取流程信息
     * @方法名: getFlows
     * @参数: [fileName]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 13:27
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 13:27
     * @修改备注
     * @since
     * @throws
     */
    private String getFlows(String fileName) {

        logger.debug("执行getFlows({})方法",fileName);

        String path = "../flows/";
        if (FILE_PATH != null && !FILE_PATH.equals("")) {
            path = FILE_PATH;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

        Date date;
        try {
            date = sdf.parse(fileName);
        } catch (ParseException e) {
            logger.error("{}，文件名不符合要求",fileName);
            return null;
        }

        Set<Map.Entry<String, Long>> entries = historyFlows.entrySet();
        for (Map.Entry<String, Long> entry : entries) {
            if (date.getTime() == entry.getValue()) {
                String rev = entry.getKey();
                fileName = rev + "-" + date.getTime();
                break;
            }
        }

        String filePath = path + '/' + fileName;

        byte[] bytes = FileUtils.getBytes(filePath);

        String flowsData = null;

        if (bytes != null) {
            flowsData = new String(bytes);
            logger.info("文件读取成功");
        }
        return flowsData;
    }


    /**
     * @描述:
     * @方法名: reloadFlows
     * @参数: [instanceIds]
     * @返回类型 java.util.Map<java.lang.Long,java.lang.String>
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 13:39
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 13:39
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public Map<Long, String> reloadFlows(Long[] instanceIds) {


        Map<Long,String> status = new HashMap<>();

        if (instanceIds.length > 0) {

            for (Long instanceId:instanceIds) {
                Instance instance = instanceService.getInstanceById(instanceId);
                String rev = restService.reloadFlows(instance);
                if (rev != null) {
                    status.put(instanceId,"加载成功");
                } else {
                    status.put(instanceId,"error");
                }
            }
        }

        return status;
    }

    /**
     * @描述: 向指定的实例列表，上传流程文件
     * @方法名: uploadFlows
     * @参数: [instanceIds]
     * @返回类型 java.util.Map<java.lang.Long,java.lang.String>
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 13:35
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 13:35
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public Map<Long, String> uploadFlows(Long[] instanceIds) throws Exception {


        Instance master = instanceService.getMasterInstance();

        // 对密码进行解码
        byte[] bytes = Base64Utils.decodeFromString(master.getPassword());

        master.setPassword(new String(bytes));

        Map<Long,String> status = new HashMap<>();

        byte[] fileByte = getFileByte(master);

        if (fileByte != null) {

            for (Long instanceId:instanceIds) {
                Instance instance = instanceService.getInstanceById(instanceId);

                try {
                    upload(instance,fileByte);
                    status.put(instanceId,"上传成功");
                } catch (SftpException e) {
                    logger.error(e.getMessage());
                    status.put(instanceId,"上传失败");
                }
            }
        }
        return status;
    }



    /**
     * @描述: 获取指定实例的流程文件字节码
     * @方法名: getFileByte
     * @参数: [instance]
     * @返回类型 byte[]
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 13:37
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 13:37
     * @修改备注
     * @since
     * @throws
     */
    private byte[] getFileByte(Instance instance) {

        SFTPUtil sftpSrc = new SFTPUtil(instance.getUsername(),instance.getPassword(),instance.getIp(),Integer.valueOf(instance.getPort()));
        sftpSrc.login();

        try {
            return sftpSrc.download(instance.getFilepath(), instance.getFilename());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            sftpSrc.logout();
        }

        return null;
    }

    /**
     * @描述: 向指定实例，上传文件的字节码
     * @方法名: upload
     * @参数: [instance, fileByte]
     * @返回类型 void
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 13:38
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 13:38
     * @修改备注
     * @since
     * @throws
     */
    private void upload(Instance instance,byte[] fileByte) throws SftpException {

        SFTPUtil sftp = new SFTPUtil(instance.getUsername(),instance.getPassword(),instance.getIp(),Integer.valueOf(instance.getPort()));
        sftp.login();
        try {
            sftp.upload(instance.getFilepath(), instance.getFilename(), fileByte);
        } finally {
            sftp.logout();
        }
    }


    /**
     * @描述: 更新管理节点的流程信息
     * @方法名: updateMasterFlow
     * @参数: [flow]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 13:20
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 13:20
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public boolean updateMasterFlow(String flow){

        logger.debug("updateMasterFlow({})方法",flow);

        Instance master = null;
        try {
            master = instanceService.getMasterInstance();
            Flows flows = new Flows();
            flow = "{ \"rev\": \"139e2a6a3ae9c7c3c56fbbb0ebff3e2e\",\"flows\":" + flow + "}";
            flows.setFlows(flow);
            Flows newFlows = restService.updateMasterFlows(master,flows);
            if (newFlows.getRev().equals("error")) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;

    }

}
