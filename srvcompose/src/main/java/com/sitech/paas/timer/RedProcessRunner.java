package com.sitech.paas.timer;

import com.sitech.paas.service.NodeRedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @version v1.0
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.runner
 * @类名称：RedProcessRunner
 * @创建人：guoqq_paas
 * @创建时间：2018/11/8 10:25
 * @修改人：guoqq_paas
 * @修改时间：2018/11/8 10:25
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
@Component
public class RedProcessRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(RedProcessRunner.class);

    @Autowired
    private NodeRedService nodeRedService;

    @Override
    public void run(String... strings) throws Exception {

        logger.info("执行RedProcessRunner方法");
        List<String> appNameList = nodeRedService.getPm2List();

        if (appNameList != null && appNameList.size() > 0) {
            logger.info("关闭用户实例");
            nodeRedService.stopUserInstanceByAppNameList(appNameList);
        }
    }
}
