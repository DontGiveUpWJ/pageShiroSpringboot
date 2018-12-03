package com.sitech.paas.controller;

import com.github.pagehelper.PageInfo;
import com.sitech.paas.core.JsonResult;
import com.sitech.paas.core.ResultCode;
import com.sitech.paas.entity.Instance;
import com.sitech.paas.service.InstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @类描述：实例管理服务接口
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.controller
 * @类名称：InstanceController
 * @创建人：guoqq_paas
 * @创建时间：2018/9/26 16:46
 * @修改人：guoqq_paas
 * @修改时间：2018/9/26 16:46
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
@RestController
@RequestMapping("/instance")
public class InstanceController {

    private static final Logger logger = LoggerFactory.getLogger(InstanceController.class);

    @Autowired
    private InstanceService instanceService;

    /**
     * @描述: 获取实例列表（分页）
     * @方法名: queryAll
     * @参数: [draw, start, length]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 9:40
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 9:40
     * @修改备注
     * @since
     * @throws
     */
    @GetMapping(value = "/all")
    public JsonResult queryAll(Instance instance, String draw,
                               @RequestParam(required = false, defaultValue = "1") int start,
                               @RequestParam(required = false, defaultValue = "10") int length) {

        logger.debug("入参：draw={},start={},length={}",draw,start,length);

        JsonResult result = new JsonResult();
        PageInfo<Instance> pageInfo = instanceService.selectByPage(instance,start, length);
        result.setCode(ResultCode.SUCCESS);
        result.setData(pageInfo);

        return result;
    }

    /**
     * @描述: 获取实例列表（分页）
     * @方法名: queryAll
     * @参数: [draw, start, length]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 9:40
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 9:40
     * @修改备注
     * @since
     * @throws
     */
    @GetMapping(value = "/all/salver")
    public JsonResult querySalver(Instance instance,String draw,
                               @RequestParam(required = false, defaultValue = "1") int start,
                               @RequestParam(required = false, defaultValue = "10") int length) {

        logger.debug("入参：draw={},start={},length={}",draw,start,length);

        JsonResult result = new JsonResult();
        PageInfo<Instance> pageInfo = instanceService.getSalverByPage(instance,start,length);
        result.setCode(ResultCode.SUCCESS);
        result.setData(pageInfo);

        return result;
    }

    /**
     * @描述: 添加新实例
     * @方法名: addInstance
     * @参数: [instance]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 9:42
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 9:42
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/add")
    public JsonResult addInstance(@RequestBody Instance instance) {

        logger.debug("入参：instance={}",instance.toString());

        JsonResult result = new JsonResult();

        int instanceId = instanceService.saveOrUpdateInstance(instance);

        if (instanceId > 0) {
            result.setCode(ResultCode.SUCCESS);
        } else {
            result.setCode(ResultCode.PARAMS_ERROR);
        }

        return result;
    }

    /**
     * @描述: 查询指定ID的实例
     * @方法名: queryInstance
     * @参数: [id]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 9:43
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 9:43
     * @修改备注
     * @since
     * @throws
     */
    @GetMapping("/{id}")
    public JsonResult queryInstance(@PathVariable Long id) {

        logger.debug("入参：id={}",id);

        JsonResult result = new JsonResult();

        Instance instance = instanceService.getInstanceById(id);

        if (instance != null && instance.getId() != null) {
            result.setCode(ResultCode.SUCCESS);
            result.setData(instance);
        } else {
            result.setCode(ResultCode.PARAMS_ERROR);
            result.setMessage("未查找到实例信息");
        }

        return result;
    }

    /**
     * @描述: 根据ids批量删除实例
     * @方法名: queryInstance
     * @参数: [ids]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 9:43
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 9:43
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/delete")
    public Map<String, Object> delInstance(@RequestBody Long[] ids) {

        logger.debug("入参：ids={}", Arrays.toString(ids));
        Map<String,Object> result = new HashMap<>();

        String status = instanceService.deleteInstance(ids);
        if (status != null) {
            result.put("status","success");
            result.put("msg",status);
        } else {
            result.put("status","error");
            result.put("msg","内部错误");
        }

        return result;
    }

    @PostMapping("/disabled")
    public JsonResult disabledInstance(@RequestBody Long[] ids) {

        logger.debug("入参：ids={}", Arrays.toString(ids));
        JsonResult result = new JsonResult();

        try {
            instanceService.disabledInstance(ids);
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * @描述: 根据ids，批量检测实例的状态
     * @方法名: checkNodeStatus
     * @参数: [ids]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 9:44
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 9:44
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/node/status")
    public JsonResult checkNodeStatus(@RequestBody Long[] ids) {

        logger.debug("入参：ids={}", Arrays.toString(ids));

        JsonResult result = new JsonResult();

        Map<Long,String> status = instanceService.checkStatus(ids);

        result.setCode(ResultCode.SUCCESS);
        result.setData(status);

        return result;
    }

    /**
     * @描述: 根据ids，批量检测实例所在主机的连接状态
     * @方法名: checkHostStatus
     * @参数: [ids]
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 9:46
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 9:46
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/host/status")
    public JsonResult checkHostStatus(@RequestBody Long[] ids) {

        logger.debug("入参：ids={}", Arrays.toString(ids));

        JsonResult result = new JsonResult();

        Map<Long,String> status = instanceService.checkHostStatus(ids);

        result.setCode(ResultCode.SUCCESS);
        result.setData(status);

        return result;
    }

    /**
     * @描述: 获取实例信息以及流程rev同步状态
     * @方法名: getInstanceWithStatus
     * @参数: []
     * @返回类型 com.sitech.paas.core.JsonResult
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 10:11
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 10:11
     * @修改备注
     * @since
     * @throws
     */
    @PostMapping("/status")
    public JsonResult queryStatus(@RequestBody Long[] ids) {

        logger.debug("入参：ids={}", Arrays.toString(ids));

        JsonResult result = new JsonResult();

        if (ids == null || ids.length == 0) {
            result.setCode(ResultCode.PARAMS_ERROR);
            return  result;
        }

        Map<Long, String> revStatus = null;
        try {
            revStatus = instanceService.getRevStatus(ids);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (revStatus == null || revStatus.size() == 0) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        } else {
            result.setCode(ResultCode.SUCCESS);
            result.setData(revStatus);
        }

        return result;
    }
}
