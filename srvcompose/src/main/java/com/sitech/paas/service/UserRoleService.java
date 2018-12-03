package com.sitech.paas.service;

import com.sitech.paas.entity.UserRole;

/**
 * Created by wangjun_paas on 2018/8/30.
 */
public interface UserRoleService extends IService<UserRole> {

    public void addUserRole(UserRole userRole);
}
