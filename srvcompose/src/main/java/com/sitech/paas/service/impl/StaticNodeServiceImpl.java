/**
 * @标题: StaticNodeServiceImpl.java
 * @包名： com.sitech.service.impl
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年11月9日 上午9:30:32
 * @version v1.0
 */
package com.sitech.paas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.StaticNode;
import com.sitech.paas.mapper.StaticMapper;
import com.sitech.paas.service.StaticNodeService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

/**
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.service.impl
 * @类名称：StaticNodeServiceImpl
 * @创建人：NeverGiveUp-WJ
 * @创建时间：2018年11月9日上午9:30:32
 * @修改人：NeverGiveUp-WJ
 * @修改时间：2018年11月9日上午9:30:32
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Service("staticService")
public class StaticNodeServiceImpl extends BaseService<StaticNode>implements StaticNodeService {

	@Autowired
	private StaticMapper mapper;
	
	/**
	 * @描述:
	 * @方法名: selectByPage
	 * @param node
	 * @param start
	 * @param length
	 * @return
	 * @创建人：NeverGiveUp-WJ
	 * @创建时间：2018年11月9日上午9:30:32
	 * @修改人：NeverGiveUp-WJ
	 * @修改时间：2018年11月9日上午9:30:32
	 * @修改备注：
	 * @throws
	 */
	@Override
	public PageInfo<StaticNode> selectByPage(StaticNode node, int start, int length) {

		int page = start;
		Example example = new Example(StaticNode.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(node.getNoName())) {
			criteria.andLike("noName", "%" + node.getNoName() + "%");
		}
		if (node.getNid() != null) {
			criteria.andEqualTo("nid", node.getNid());
		}
		if (StringUtil.isNotEmpty(node.getNoDesc())) {
			criteria.andLike("noDesc", "%" + node.getNoDesc() + "%");
		}
		// 分页查询
		PageHelper.startPage(page, length);
		List<StaticNode> nodeList = selectByExample(example);
		return new PageInfo<>(nodeList);
	}

	/**
	 * @描述:
	 * @方法名: findNodeById
	 * @param node
	 * @return
	 * @创建人：NeverGiveUp-WJ
	 * @创建时间：2018年11月9日上午11:21:30
	 * @修改人：NeverGiveUp-WJ
	 * @修改时间：2018年11月9日上午11:21:30
	 * @修改备注：
	 * @throws
	 */
	@Override
	public StaticNode findNodeById(StaticNode node) {
		StaticNode selectByKey = selectByKey(node.getNid());
		return selectByKey;
	}

	/**
	 * @描述:
	 * @方法名: addStaticNode
	 * @param node
	 * @创建人：NeverGiveUp-WJ
	 * @创建时间：2018年11月9日下午1:58:16
	 * @修改人：NeverGiveUp-WJ
	 * @修改时间：2018年11月9日下午1:58:16
	 * @修改备注：
	 * @throws
	 */
	@Override
	public void addStaticNode(StaticNode node) {
		mapper.addStaticNode(node);
		
	}

}
