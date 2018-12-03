package com.sitech.paas.controller;

import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Instance;
import com.sitech.paas.entity.User;
import com.sitech.paas.service.FlowService;
import com.sitech.paas.service.InstanceService;
import com.sitech.paas.service.NodeRedService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
@RequestMapping("/flow")
public class FlowController {

    private static final Logger logger = LoggerFactory.getLogger(FlowController.class);

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private FlowService flowService;

    @Autowired
    private NodeRedService nodeRedService;

    /**
     * @描述: 获取历史revision的流程文件列表
     * @方法名: getRevisionList
     * @参数: []
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 10:15
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 10:15
     * @修改备注
     * @since
     * @throws
     */
    @GetMapping("/revision")
    public JsonResult getRevisionList() {

        JsonResult result = new JsonResult();

        List revisionList = flowService.getFlowsFileList();

        String currentRevision;
        try {
            currentRevision = flowService.getFileName();
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            currentRevision = "error";
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }

        HashMap<String, Object> data = new HashMap<>();

        data.put("list",revisionList);
        data.put("current",currentRevision);

        result.setData(data);

        return result;
    }
    

    /**
     * @描述: 更新指定实例的所有流程
     * @方法名: updateFlows
     * @参数: [instanceId]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 10:24
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 10:24
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/deploy")
    public JsonResult updateFlows(@RequestBody Long[] ids, String type) {

        logger.debug("入参：ids={},type={}",Arrays.toString(ids),type);

        JsonResult result = new JsonResult();
        Map<Long,String> map = new HashMap<>();
        if(ids != null && ids.length >0) {
            for (Long id:ids) {
                Instance instance= instanceService.getInstanceById(id);
                String status = null;
                try {
                    status = flowService.deployFlows(instance,type);
                } catch (Exception e) {
                    status = "同步失败";
                }
                map.put(id,status);
            }
        }

        result.setCode(ResultCode.SUCCESS);

        result.setData(map);
        return result;
    }

    /**
     * @描述: 将（管理实例的）流程文件切换至指定文件
     * @方法名: switchFlows
     * @参数: [fileName]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 10:26
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 10:26
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/switch/{fileName}")
    public JsonResult switchFlows(@PathVariable String fileName) {

        logger.debug("入参：fileName={}",fileName);

        JsonResult result = new JsonResult();
        String status = null;
        if(fileName != null) {
            try {
                status = flowService.switchFlows(fileName);
            } catch (Exception e) {
                status = "管理节点不存在";
            }
        }

        if (status != null && status.equals("success")) {
            result.setCode(ResultCode.SUCCESS);
            result.setData(status);
        } else {
            result.setCode(ResultCode.PARAMS_ERROR);
            result.setData(status);
        }

        return result;
    }

    /**
     * @描述: 向指定的主机列表，上传流程文件
     * @方法名: uploadFlows
     * @参数: [ids]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 10:30
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 10:30
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/upload")
    public JsonResult uploadFlows(@RequestBody Long[] ids) {

        logger.debug("入参：ids={}", Arrays.toString(ids));

        JsonResult result = new JsonResult();

        Map<Long, String> status;

        try {
            status = flowService.uploadFlows(ids);
            result.setCode(ResultCode.SUCCESS);
            result.setData(status);
        } catch (Exception e) {
            result.setCode(ResultCode.PARAMS_ERROR);
            result.setMessage("上传失败");
        }

        return result;
    }

    /**
     * @描述: 让指定主机列表，重新加载各自的流程文件
     * @方法名: reloadFlows
     * @参数: [ids]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 10:32
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 10:32
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/reload")
    public JsonResult reloadFlows(@RequestBody Long[] ids) {

        logger.debug("入参：ids={}", Arrays.toString(ids));

        JsonResult result = new JsonResult();

        Map<Long, String> status = flowService.reloadFlows(ids);

        if (status != null && status.size() > 0) {
            result.setCode(ResultCode.SUCCESS);
            result.setData(status);
        } else {
            result.setCode(ResultCode.PARAMS_ERROR);
            result.setMessage("加载失败");
        }

        return result;
    }

    @PostMapping("/push/userFlow")
    public JsonResult pushUserFlow() {
        logger.debug("执行pushUserFlow()");
        JsonResult result = new JsonResult();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("普通用户")) {
            result.setCode(ResultCode.PARAMS_ERROR);
            result.setMessage("用户角色错误");
            return result;
        }
        Object userSession = subject.getSession().getAttribute("userSession");
        if (userSession != null && userSession instanceof User) {
            try {
                boolean isSuccess = nodeRedService.pushUserFlow((User) userSession);
                if (isSuccess) {
                    result.setCode(ResultCode.SUCCESS);
                    result.setData("提交成功");
                } else {
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                    result.setMessage("流程文件文件不存在");
                }
                return result;
            } catch (IOException e) {
                logger.error("流程文件提交失败");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                result.setMessage("流程文件提交失败");
                return result;
            }
        }
        result.setCode(ResultCode.NOT_LOGIN);
        return result;
    }

    @PostMapping("/pull/userFlow")
    public JsonResult pullUserFlow() {

        logger.debug("执行pullUserFlow()");
        JsonResult result = new JsonResult();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("管理员")) {
            result.setCode(ResultCode.PARAMS_ERROR);
            result.setMessage("用户角色错误");
            return result;
        }

        try {
            String flow = nodeRedService.pullUserFlow();
            if (flow != null) {
                boolean isSuccess = flowService.updateMasterFlow(flow);
                if (isSuccess) {
                    result.setCode(ResultCode.SUCCESS);
                    result.setData("已获取最新流程");
                    nodeRedService.setNeedUpdate(false);
                } else {
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                    result.setMessage("获取流程信息失败");
                }
                return result;
            }
            result.setCode(ResultCode.SUCCESS);
            result.setData("获取的流程信息为空");
            logger.debug("获取的流程信息为空");
        } catch (IOException e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("获取流程信息失败");
        }
        return result;
    }
}
