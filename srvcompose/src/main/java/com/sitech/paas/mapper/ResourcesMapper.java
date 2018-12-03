package com.sitech.paas.mapper;

import java.util.List;
import java.util.Map;

import com.sitech.paas.entity.Resources;
import com.sitech.paas.util.MyMapper;

public interface ResourcesMapper extends MyMapper<Resources> {

	public List<Resources> queryAll();

    public List<Resources> loadUserResources(Map<String,Object> map);

    public List<Resources> queryResourcesListWithSelected(Integer rid);

	public Resources findResourceById(Integer id);

	public void updateResource(Resources resource);
}