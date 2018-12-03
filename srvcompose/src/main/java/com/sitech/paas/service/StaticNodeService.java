/**
 * @标题: StaticNodeService.java
 * @包名： com.sitech.service
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年11月9日 上午9:28:28
 * @version v1.0
 */
package com.sitech.paas.service;

import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.StaticNode;

/**
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.service
 * @类名称：StaticNodeService
 * @创建人：NeverGiveUp-WJ
 * @创建时间：2018年11月9日上午9:28:28
 * @修改人：NeverGiveUp-WJ
 * @修改时间：2018年11月9日上午9:28:28
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
public interface StaticNodeService extends IService<StaticNode>{
	PageInfo<StaticNode> selectByPage(StaticNode node, int start, int length);

	/**
	 * @描述:
	 * @方法名: findNodeById
	 * @param node
	 * @return
	 * @返回类型 StaticNode
	 * @创建人 NeverGiveUp-WJ
	 * @创建时间 2018年11月9日上午11:21:15
	 * @修改人 NeverGiveUp-WJ
	 * @修改时间 2018年11月9日上午11:21:15
	 * @修改备注
	 * @since
	 * @throws
	 */
	StaticNode findNodeById(StaticNode node);

	/**
	 * @描述:
	 * @方法名: addStaticNode
	 * @param node
	 * @返回类型 void
	 * @创建人 NeverGiveUp-WJ
	 * @创建时间 2018年11月9日下午1:58:00
	 * @修改人 NeverGiveUp-WJ
	 * @修改时间 2018年11月9日下午1:58:00
	 * @修改备注
	 * @since
	 * @throws
	 */
	void addStaticNode(StaticNode node);
}
