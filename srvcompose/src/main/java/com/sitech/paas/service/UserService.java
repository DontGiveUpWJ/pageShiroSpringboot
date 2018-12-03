package com.sitech.paas.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.User;

/**
 * Created by wangjun_paas on 2018/8/30.
 */
public interface UserService extends IService<User>{
    PageInfo<User> selectByPage(User user, int start, int length);

    User selectByUsername(String username);

    void delUser(Integer userid);

	void changeByUserId(Integer id);

	void updateUserByName(User user);

	List<User> findDontHaveMy(User user);

}
