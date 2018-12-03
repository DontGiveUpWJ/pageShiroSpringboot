package com.sitech.paas.mapper;

import java.util.List;

import com.sitech.paas.entity.UserRole;
import com.sitech.paas.util.MyMapper;

public interface UserRoleMapper extends MyMapper<UserRole> {
    public List<Integer> findUserIdByRoleId(Integer roleId);
}