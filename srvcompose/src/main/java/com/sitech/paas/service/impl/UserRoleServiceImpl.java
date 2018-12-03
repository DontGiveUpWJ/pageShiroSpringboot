package com.sitech.paas.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sitech.paas.entity.UserRole;
import com.sitech.paas.service.UserRoleService;

import tk.mybatis.mapper.entity.Example;

/**
 * Created by wangjun_paas on 2018/8/30.
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends BaseService<UserRole> implements UserRoleService {
   /* @Autowired
    private MyShiroRealm myShiroRealm;*/

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void addUserRole(UserRole userRole) {
        //删除
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid",userRole.getUserid());
        mapper.deleteByExample(example);
        //添加
        String[] roleids = userRole.getRoleid().split(",");
        for (String roleId : roleids) {
            UserRole u = new UserRole();
            u.setUserid(userRole.getUserid());
            u.setRoleid(roleId);
            mapper.insert(u);
        }
        //更新当前登录的用户的权限缓存
        /*List<Integer> userid = new ArrayList<Integer>();
        userid.add(userRole.getUserid());*/
       /* myShiroRealm.clearUserAuthByUserId(userid);*/
    }
}
