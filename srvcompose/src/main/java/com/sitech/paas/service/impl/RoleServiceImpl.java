package com.sitech.paas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.Role;
import com.sitech.paas.entity.RoleResources;
import com.sitech.paas.mapper.RoleMapper;
import com.sitech.paas.mapper.RoleResourcesMapper;
import com.sitech.paas.service.RoleService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

@Service("roleService")
public class RoleServiceImpl extends BaseService<Role> implements RoleService{

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleResourcesMapper roleResourcesMapper;

    @Override
    public List<Role> queryRoleListWithSelected(Integer uid) {
        return roleMapper.queryRoleListWithSelected(uid);
    }

    @Override
    public PageInfo<Role> selectByPage(Role role, int start, int length) {
    	/*int page = start / length + 1;*/
    	int page = role.getPageNum();
		Example example = new Example(Role.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(role.getRoledesc())) {
			criteria.andLike("roledesc", "%" + role.getRoledesc() + "%");
		}
		if (role.getId() != null) {
			criteria.andEqualTo("id", role.getId());
		}
		
		// 分页查询
		PageHelper.startPage(page, length);
		List<Role> roleList = selectByExample(example);
		return new PageInfo<>(roleList);
    
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void delRole(Integer roleid) {
        //删除角色
        mapper.deleteByPrimaryKey(roleid);
        //删除角色资源
        Example example = new Example(RoleResources.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleid",roleid);
        roleResourcesMapper.deleteByExample(example);

    }
}
