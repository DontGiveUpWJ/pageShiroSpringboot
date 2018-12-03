package com.sitech.paas.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.Resources;
import com.sitech.paas.mapper.ResourcesMapper;
import com.sitech.paas.service.ResourcesService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

/**
 * Created by wangjun_paas on 2018/8/30.
 */
@Service("resourcesService")
public class ResourcesServiceImpl extends BaseService<Resources> implements ResourcesService {
   @Resource
    private ResourcesMapper resourcesMapper;

    @Override
    public PageInfo<Resources> selectByPage(Resources resources, int start, int length) {
    	/*int page = start / length + 1;*/
    	int page = resources.getPageNum();
		Example example = new Example(Resources.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(resources.getName())) {
			criteria.andLike("name", "%" + resources.getName() + "%");
		}
		if (resources.getId() != null) {
			criteria.andEqualTo("id", resources.getId());
		}
		if (resources.getParentid() != null) {
			criteria.andEqualTo("parentid", resources.getParentid());
		}
		if (StringUtil.isNotEmpty(resources.getResurl())) {
			criteria.andLike("resurl", "%" + resources.getResurl() + "%");
		}
		if (resources.getType() != null) {
			criteria.andEqualTo("type", resources.getType());
		}
		// 分页查询
		PageHelper.startPage(page, length);
		List<Resources> resourcesList = selectByExample(example);
		return new PageInfo<>(resourcesList);
	
    }

    @Override
    public List<Resources> queryAll(){
        return resourcesMapper.queryAll();
    }

    @Override
    //@Cacheable(cacheNames="resources",key="#map['userid'].toString()+#map['type']")
    public List<Resources> loadUserResources(Map<String, Object> map) {
        return resourcesMapper.loadUserResources(map);
    }

    @Override
    public List<Resources> queryResourcesListWithSelected(Integer rid) {
        return resourcesMapper.queryResourcesListWithSelected(rid);
    }

	@Override
	public Resources findResourceById(Integer id) {
		// TODO Auto-generated method stub
		Resources resource  = resourcesMapper.findResourceById(id);
		return resource;
	}

	@Override
	public void updateResource(Resources resource) {
		// TODO Auto-generated method stub
		resourcesMapper.updateResource(resource);
	}
}
