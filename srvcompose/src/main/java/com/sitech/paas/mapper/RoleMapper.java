package com.sitech.paas.mapper;

import java.util.List;

import com.sitech.paas.entity.Role;
import com.sitech.paas.util.MyMapper;

public interface RoleMapper extends MyMapper<Role> {
    public List<Role> queryRoleListWithSelected(Integer id);
}