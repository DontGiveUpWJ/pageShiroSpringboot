package com.sitech.paas.mapper;


import com.sitech.paas.entity.Instance;
import com.sitech.paas.util.MyMapper;

import java.util.List;

/**
 * Created by guoqing on 2018/9/7.
 */
public interface InstanceMapper extends MyMapper<Instance> {

    List<Instance> selectAllOrderByType();

    List<Instance> selectByType(int type);

    void disabledById(int id);

    void enabledById(int id);
}
