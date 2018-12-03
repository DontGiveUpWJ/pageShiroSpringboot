/**
 * @标题: OperationService.java
 * @包名： com.sitech.service
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年11月13日 下午2:07:37
 * @version v1.0
 */
package com.sitech.paas.service;

import com.github.pagehelper.PageInfo;
import com.sitech.paas.entity.Operation;


/**
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.service
 * @类名称：OperationService
 * @创建人：NeverGiveUp-WJ
 * @创建时间：2018年11月13日下午2:07:37
 * @修改人：NeverGiveUp-WJ
 * @修改时间：2018年11月13日下午2:07:37
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
public interface OperationService extends IService<Operation>{

	/**
	 * @描述:
	 * @方法名: selectByPage
	 * @param ope
	 * @param start
	 * @param length
	 * @return
	 * @返回类型 PageInfo<Operation>
	 * @创建人 NeverGiveUp-WJ
	 * @创建时间 2018年11月13日下午3:24:39
	 * @修改人 NeverGiveUp-WJ
	 * @修改时间 2018年11月13日下午3:24:39
	 * @修改备注
	 * @since
	 * @throws
	 */
	PageInfo<Operation> selectByPage(Operation ope, int start, int length);

}
