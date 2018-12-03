package com.sitech.paas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.User;
import com.sitech.paas.entity.UserRole;
import com.sitech.paas.mapper.UserMapper;
import com.sitech.paas.mapper.UserRoleMapper;
import com.sitech.paas.service.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

/**
 * Created by wangjun_paas on 2018/8/30.
 */
@Service("userService")
public class UserServiceImpl extends BaseService<User> implements UserService {
	@Resource
	private UserRoleMapper userRoleMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public PageInfo<User> selectByPage(User user, int start, int length) {
		/*int page = start / length + 1;*/
		int page = user.getPageNum();
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(user.getUsername())) {
			criteria.andLike("username", "%" + user.getUsername() + "%");
		}
		if (user.getId() != null) {
			criteria.andEqualTo("id", user.getId());
		}
		if (user.getEnable() != null) {
			criteria.andEqualTo("enable", user.getEnable());
		}
		// 分页查询
		PageHelper.startPage(page, length);
		List<User> userList = selectByExample(example);
		return new PageInfo<>(userList);
	}

	@Override
	public User selectByUsername(String username) {
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("username", username);
		List<User> userList = selectByExample(example);
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void delUser(Integer userid) {
		// 删除用户表
		mapper.deleteByPrimaryKey(userid);
		// 删除用户角色表
		Example example = new Example(UserRole.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userid", userid);
		userRoleMapper.deleteByExample(example);
	}

	@Override
	public void changeByUserId(Integer id) {
		// TODO Auto-generated method stub
		User u = new User();
		u.setId(id);
		List<User> userList = userMapper.select(u);
		User user = userList.get(0);
		if(user.getEnable() == 0) {
			//u.setEnable(1);
			userMapper.changeByUser1(user);
		}else {
			//u.setEnable(0);
			userMapper.changeByUser(user);
		}
		//userMapper.changeByUser(user);

	}

	@Override
	public void updateUserByName(User user) {
		// TODO Auto-generated method stub
		userMapper.updateUserByName(user);
	}

	@Override
	public List<User> findDontHaveMy(User user) {
		// TODO Auto-generated method stub
		return userMapper.findDontHaveMy(user);
	}
}
