/**
 * @标题: OperationServiceImpl.java
 * @包名： com.sitech.service.impl
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年11月13日 下午2:08:32
 * @version v1.0
 */
package com.sitech.paas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.Operation;
import com.sitech.paas.service.OperationService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

/**
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.service.impl
 * @类名称：OperationServiceImpl
 * @创建人：NeverGiveUp-WJ
 * @创建时间：2018年11月13日下午2:08:32
 * @修改人：NeverGiveUp-WJ
 * @修改时间：2018年11月13日下午2:08:32
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Service
public class OperationServiceImpl extends BaseService<Operation> implements OperationService {

	/**
	 * @描述:
	 * @方法名: selectByPage
	 * @param ope
	 * @param start
	 * @param length
	 * @return
	 * @创建人：NeverGiveUp-WJ
	 * @创建时间：2018年11月13日下午3:24:51
	 * @修改人：NeverGiveUp-WJ
	 * @修改时间：2018年11月13日下午3:24:51
	 * @修改备注：
	 * @throws
	 */
	@Override
	public PageInfo<Operation> selectByPage(Operation ope, int start, int length) {
		int page = start;
		Example example = new Example(Operation.class);
		Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(ope.getOpeName())) {
			criteria.andLike("opeName", "%" + ope.getOpeName() + "%");
		}
		if (ope.getOid() != null) {
			criteria.andEqualTo("oid", ope.getOid());
		}
		if (StringUtil.isNotEmpty(ope.getUsername())) {
			criteria.andLike("username", "%" + ope.getUsername() + "%");
		}
		example.setOrderByClause("opt_time DESC");
		PageHelper.startPage(page, length);
		List<Operation> list = selectByExample(example);
		PageInfo<Operation> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
}


