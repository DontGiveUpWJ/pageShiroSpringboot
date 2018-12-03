/**
 * @标题: DateHelper.java
 * @包名： com.sitech.util
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年11月13日 下午2:03:47
 * @version v1.0
 */
package com.sitech.paas.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.util
 * @类名称：DateHelper
 * @创建人：NeverGiveUp-WJ
 * @创建时间：2018年11月13日下午2:03:47
 * @修改人：NeverGiveUp-WJ
 * @修改时间：2018年11月13日下午2:03:47
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
public class DateHelper {
	
	public static String format(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = format.format(date);
		return dateTime;
	}

}
