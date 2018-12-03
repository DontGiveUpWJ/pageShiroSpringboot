package com.sitech.paas.service;

import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.Resources;

import java.util.List;
import java.util.Map;

/**
 * Created by wangjun_paas on 2018/8/30.
 */
public interface ResourcesService extends IService<Resources> {
    PageInfo<Resources> selectByPage(Resources resources, int start, int length);

    public List<Resources> queryAll();

    public List<Resources> loadUserResources(Map<String,Object> map);

    public List<Resources> queryResourcesListWithSelected(Integer rid);

    //根据id查询权限信息
	Resources findResourceById(Integer id);
	//更新权限信息
	void updateResource(Resources resource);
}
