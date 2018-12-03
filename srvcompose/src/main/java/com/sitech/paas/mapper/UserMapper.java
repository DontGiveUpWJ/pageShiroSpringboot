package com.sitech.paas.mapper;

import java.util.List;

import com.sitech.paas.entity.User;
import com.sitech.paas.util.MyMapper;

public interface UserMapper extends MyMapper<User> {

	void changeByUser(User user);

	void changeByUser1(User user);

	void updateUserByName(User user);

	List<User> findDontHaveMy(User user);
}