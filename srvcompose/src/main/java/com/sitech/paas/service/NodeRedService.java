package com.sitech.paas.service;

import com.sitech.paas.entity.Instance;
import com.sitech.paas.entity.User;

import java.io.IOException;
import java.util.List;

/**
 * @version v1.0
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.service
 * @类名称：NodeRedService
 * @创建人：guoqq_paas
 * @创建时间：2018/11/7 9:58
 * @修改人：guoqq_paas
 * @修改时间：2018/11/7 9:58
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
public interface NodeRedService {

    List<String> getPm2List();
    void startUserInstance(User user) throws InterruptedException, IOException;
    Instance getUserInstance(User user);
    void stopUserInstanceByUsername(String username);
    void stopUserInstanceByAppNameList(List<String> appNames);
    boolean pushUserFlow(User user) throws IOException;
    boolean isNeedUpdate();
    void setNeedUpdate(boolean isNeedUpdate);
    String pullUserFlow() throws IOException;
}
