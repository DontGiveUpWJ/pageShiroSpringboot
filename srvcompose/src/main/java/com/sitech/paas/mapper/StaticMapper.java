/**
 * @标题: StaticMapper.java
 * @包名： com.sitech.mapper
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年11月9日 上午9:43:10
 * @version v1.0
 */
package com.sitech.paas.mapper;

import com.sitech.paas.entity.StaticNode;
import com.sitech.paas.util.MyMapper;

/**
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.mapper
 * @类名称：StaticMapper
 * @创建人：NeverGiveUp-WJ
 * @创建时间：2018年11月9日上午9:43:10
 * @修改人：NeverGiveUp-WJ
 * @修改时间：2018年11月9日上午9:43:10
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
public interface StaticMapper extends MyMapper<StaticNode> {

	/**
	 * @描述:
	 * @方法名: addStaticNode
	 * @param node
	 * @返回类型 void
	 * @创建人 NeverGiveUp-WJ
	 * @创建时间 2018年11月9日下午1:59:29
	 * @修改人 NeverGiveUp-WJ
	 * @修改时间 2018年11月9日下午1:59:29
	 * @修改备注
	 * @since
	 * @throws
	 */
	void addStaticNode(StaticNode node);

}
