package com.sitech.paas.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jcraft.jsch.SftpException;
import com.sitech.paas.entity.Instance;
import com.sitech.paas.entity.User;
import com.sitech.paas.mapper.InstanceMapper;
import com.sitech.paas.service.InstanceService;
import com.sitech.paas.service.RestService;
import com.sitech.paas.util.SFTPUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @version v1.0
 * @类描述：实例管理服务管理
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.service.impl
 * @类名称：InstanceServiceImpl
 * @创建人：guoqq_paas
 * @创建时间：2018/9/26 16:31
 * @修改人：guoqq_paas
 * @修改时间：2018/9/26 16:31
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
@Service
public class InstanceServiceImpl implements InstanceService {
    private final Logger logger = LoggerFactory.getLogger(InstanceServiceImpl.class);

    private static final int TYPE_MANAGER = 0;
    private static final int ISValid_ENABLED = 0;

    private static final int TYPE_NODE = 1;
    private static final int ISValid_DISABLED = 0;
    @Autowired
    private InstanceMapper instanceMapper;

    @Autowired
    private RestService restService;


    /**
     * @描述: 获取管理节点的实例信息
     * @方法名: getMasterInstance
     * @参数: []
     * @返回类型 com.sitech.paas.entity.Instance
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:42
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:42
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public Instance getMasterInstance() throws Exception {

        logger.debug("执行getMasterInstance()方法");

        Example example = new Example(Instance.class);
        Example.Criteria criteria = example.createCriteria();
        // 运行节点
        criteria.andEqualTo("type",TYPE_MANAGER);
        // 未被禁用
        criteria.andEqualTo("isValid",ISValid_ENABLED);
        List<Instance> instances = instanceMapper.selectByExample(example);
        if (instances.size() == 0) {
            throw new Exception("未查找到可用的管理节点");
        } else if (instances.size() > 1) {
            logger.warn("存在多个管理节点");
        }
        return instances.get(0);
    }

    /**
     * @描述: 获取运行节点的实例信息
     * @方法名: getSalverInstance
     * @参数: []
     * @返回类型 java.util.List<com.sitech.paas.entity.Instance>
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:43
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:43
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public List<Instance> getSalverInstance() {

        logger.debug("执行getSalverInstance()方法");
        Example example = new Example(Instance.class);
        Example.Criteria criteria = example.createCriteria();
        // 运行节点
        criteria.andEqualTo("type",TYPE_NODE);
        // 未被禁用
        criteria.andEqualTo("isValid",ISValid_ENABLED);

        return instanceMapper.selectByExample(example);
    }


    /**
     * @描述: 获取运行节点的实例信息
     * @方法名: getSalverInstance
     * @参数: []
     * @返回类型 java.util.List<com.sitech.paas.entity.Instance>
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:43
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:43
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public PageInfo<Instance> getSalverByPage(Instance instance, int start, int length) {

        logger.debug("getSalverInstanceByPage({},{},{})方法",instance.toString(),start,length);
        Example example = new Example(Instance.class);
        Example.Criteria criteria = example.createCriteria();
        // 运行节点
        criteria.andEqualTo("type",TYPE_NODE);
        // 未被禁用
        criteria.andEqualTo("isValid",ISValid_ENABLED);

        if (instance.getId() != null) {
            criteria.andEqualTo("id", instance.getId());
        }
        if (instance.getType() != null) {
            criteria.andEqualTo("type", instance.getType());
        }
        if (StringUtils.isNotBlank(instance.getIp())) {
            criteria.andLike("ip", instance.getIp() + "%");
        }
        if (StringUtils.isNotBlank(instance.getUrl())) {
            criteria.andLike("url", "%" + instance.getUrl() + "%");
        }

        int page = start/length+1;

        //分页查询
        PageHelper.startPage(page, length);
        List<Instance> instances = instanceMapper.selectByExample(example);
        return new PageInfo<>(instances);
    }


    /**
     * @描述: 获取指定Id的实例信息
     * @方法名: getInstanceById
     * @参数: [id]
     * @返回类型 com.sitech.paas.entity.Instance
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:44
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:44
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public Instance getInstanceById(Long id) {

        logger.debug("执行getInstanceById({})方法",id);
        Instance instance = instanceMapper.selectByPrimaryKey(id);
        byte[] bytes = Base64Utils.decodeFromString(instance.getPassword());

        instance.setPassword(new String(bytes));
        return instance;
    }


    /**
     * @描述: 批量删除指定id的实例
     * @方法名: deleteInstance
     * @参数: [instanceIds]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:45
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:45
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public String deleteInstance(Long[] instanceIds) {

        logger.debug("执行deleteInstance({})方法",Arrays.toString(instanceIds));

        String result = null;
        try {
            if (instanceIds.length > 0){
                for (Long instanceId:instanceIds) {
                    instanceMapper.deleteByPrimaryKey(instanceId);
                }
            }
            result = "success";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    @Override
    public void disabledInstance(Long[] instanceIds) {

        logger.debug("执行disabledInstance({})方法",Arrays.toString(instanceIds));

        if (instanceIds.length > 0){
            for (Long id: instanceIds) {
                Instance instance = instanceMapper.selectByPrimaryKey(id);
                if (instance != null) {
                    Integer isValid = instance.getIsValid();
                    if (isValid == 0) {
                        instanceMapper.disabledById(id.intValue());
                    } else if (isValid == 1) {
                        instanceMapper.enabledById(id.intValue());
                    }
                }
            }
        }
    }

    /**
     * @描述: 获取运行节点的实例信息以及流程rev同步信息
     * @方法名: getListWithStatus
     * @参数: []
     * @返回类型 java.util.List<com.sitech.paas.vo.InstanceWithStatus>
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:47
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:47
     * @修改备注
     * @since
     * @throws
     */
    public Map<Long, String> getRevStatus(Long[] ids) throws Exception {

        logger.debug("执行getRevStatus({})方法",Arrays.toString(ids));

        Example example = new Example(Instance.class);
        Example.Criteria criteria = example.createCriteria();

        List<Long> list = Arrays.asList(ids);

        criteria.andIn("id",list);

        List<Instance> instances = instanceMapper.selectByExample(example);

        Map<Long,String> statusMap = new HashMap<>();
        if (instances.size() > 0) {
            Instance master = getMasterInstance();

            String masterRev = restService.getFlowsRev(master);

            // 通过http方式获取rev失败，则返回空
            if (masterRev == null ) {
                return null;
            }

            for (Instance instance: instances) {
                String rev = restService.getFlowsRev(instance);

                String status = masterRev.equals(rev) ? "已同步" : "未同步";

                statusMap.put(instance.getId(),status);
            }
        }

        return statusMap;
    }

    /**
     * @描述: 获取实例信息列表（分页）
     * @方法名: selectByPage
     * @参数: [start, length]
     * @返回类型 com.github.pagehelper.PageInfo<com.sitech.paas.entity.Instance>
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:51
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:51
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public PageInfo<Instance> selectByPage(Instance instance, int start, int length) {

        logger.debug("执行selectByPage({},{})方法",start,length);
        Example example = new Example(Instance.class);
        Example.Criteria criteria = example.createCriteria();
        if (instance.getId() != null) {
            criteria.andEqualTo("id", instance.getId());
        }
        if (instance.getType() != null) {
            criteria.andEqualTo("type", instance.getType());
        }
        if (StringUtils.isNotBlank(instance.getIp())) {
            criteria.andLike("ip", instance.getIp() + "%");
        }
        if (StringUtils.isNotBlank(instance.getUrl())) {
            criteria.andLike("url", "%" + instance.getUrl() + "%");
        }
        if (instance.getIsValid() != null) {
            criteria.andEqualTo("isValid", instance.getIsValid());
        }
//        example.setOrderByClause("create_time DESC");
        int page = start/length+1;

        //分页查询
        PageHelper.startPage(page, length);
        List<Instance> instances = instanceMapper.selectByExample(example);
        return new PageInfo<>(instances);
    }


    /**
     * @描述: 新增或更新的实例信息
     * @方法名: saveOrUpdateInstance
     * @参数: [instance]
     * @返回类型 int
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:53
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:53
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public int saveOrUpdateInstance(Instance instance) {

        logger.debug("执行saveOrUpdateInstance({})方法",instance.toString());

        int result;

        String url = instance.getUrl().trim();
        if (!url.equals("")) {
            String substring = url.substring(url.length() - 1);

            if (substring.equals("/")) {
                instance.setUrl(url.substring(0,url.length()-1));
            }
        }

        // base64加密
        String password = instance.getPassword().trim();

        if (StringUtils.isNotBlank(password)) {
            password = Base64Utils.encodeToString(password.getBytes());
            instance.setPassword(password);
        }

        Date current = new Date();
        if (instance.getId() != null && instance.getId().intValue() > 0) {
            instance.setUpdateTime(current);
            result = instanceMapper.updateByPrimaryKeySelective(instance);
        } else {
            Subject subject = SecurityUtils.getSubject();
            User user = (User)subject.getSession().getAttribute("userSession");
            if (user == null) {
                instance.setResCreate("unknown");
            } else {
                instance.setResCreate(user.getUsername());
            }

            instance.setCreateTime(current);
            instance.setUpdateTime(current);
            instance.setIsValid(ISValid_ENABLED);
            result = instanceMapper.insert(instance);
        }

        return result;
    }

    /**
     * @描述: 根据ids列表，检测实例状态
     * @方法名: checkStatus
     * @参数: [ids]
     * @返回类型 java.util.Map<java.lang.Long,java.lang.String>
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:55
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:55
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public Map<Long,String> checkStatus(Long[] ids) {

        logger.debug("执行checkStatus({})方法", Arrays.toString(ids));

        Map<Long,String> result = new HashMap<>();

        if (ids.length > 0){
            for (Long instanceId:ids) {
                Instance instance = instanceMapper.selectByPrimaryKey(instanceId);
                result.put(instanceId,checkStatus(instance));
            }
        }
        return result;
    }

    /**
     * @描述: 根据ids列表，检测实例所在主机的连接状态
     * @方法名: checkHostStatus
     * @参数: [ids]
     * @返回类型 java.util.Map<java.lang.Long,java.lang.String>
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 14:58
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 14:58
     * @修改备注
     * @since
     * @throws
     */
    @Override
    public Map<Long, String> checkHostStatus(Long[] ids) {
        logger.debug("执行checkHostStatus({})方法", Arrays.toString(ids));

        Map<Long,String> result = new HashMap<>();
        try {
            if (ids.length > 0){
                for (Long instanceId:ids) {
                    Instance instance = instanceMapper.selectByPrimaryKey(instanceId);

                    result.put(instanceId,checkHostStatus(instance));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return result;
    }


    /**
     * @描述: 检测（实例的所在）主机的连接状态，以及流程文件的是否存在
     * @方法名: checkHostStatus
     * @参数: [instance]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 15:00
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 15:00
     * @修改备注
     * @since
     * @throws
     */
    private String checkHostStatus(Instance instance) {

        byte[] bytes = Base64Utils.decodeFromString(instance.getPassword());

        String password = new String(bytes);

        SFTPUtil sftpSrc = new SFTPUtil(instance.getUsername(),password,instance.getIp(),Integer.valueOf(instance.getPort()));

        try {
            sftpSrc.login();
            sftpSrc.listFiles(instance.getFilepath() + "/" + instance.getFilename());
            return "连接成功";
        } catch (SftpException e) {
            logger.error(e.getMessage());
            return "未找到流程文件";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "连接失败";
        } finally {
            sftpSrc.logout();
        }
    }

    /**
     * @描述: 检测实例的连接状态
     * @方法名: checkStatus
     * @参数: [instance]
     * @返回类型 java.lang.String
     * @创建人 guoqq_paas
     * @创建时间 2018/9/27 15:03
     * @修改人 guoqq_paas
     * @修改时间 2018/9/27 15:03
     * @修改备注
     * @since
     * @throws
     */
    private String checkStatus(Instance instance) {


        String status = restService.checkStatus(instance);

        if (status != null) {
            return "运行中";
        }

        return "已停止";
    }

//    /**
//     * @描述: 获取实例信息以及流程rev同步信息
//     * @方法名: getInstanceWithStatus
//     * @参数: [slave]
//     * @返回类型 com.sitech.paas.vo.InstanceWithStatus
//     * @创建人 guoqq_paas
//     * @创建时间 2018/9/27 15:06
//     * @修改人 guoqq_paas
//     * @修改时间 2018/9/27 15:06
//     * @修改备注
//     * @since
//     * @throws
//     */
//    private InstanceWithStatus getInstanceWithStatus(Instance slave) {
//
//        String slaveRev = restService.getFlowsRev(slave);
//
//        return new InstanceWithStatus(slave,slaveRev);
//    }

}
