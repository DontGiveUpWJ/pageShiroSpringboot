/**
 * @标题: Http.java
 * @包名： com.sitech.paas.entity
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年9月28日 下午1:30:37
 * @version v1.0
 */
package com.sitech.paas.entity;

/**
 * @类描述：
 * 
 * @项目名称：srvcompose @包名： com.sitech.paas.entity
 * @类名称：Http
 * @创建人：NeverGiveUp-WJ
 * @创建时间：2018年9月28日下午1:30:37
 * @修改人：NeverGiveUp-WJ
 * @修改时间：2018年9月28日下午1:30:37 @修改备注：
 * @version v1.0
 * @see
 * @bug
 * @Copyright
 * @mail
 */
public class Http {

	private String url;
	private String paramter;
	private String soapAction;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParamter() {
		return paramter;
	}

	public void setParamter(String paramter) {
		this.paramter = paramter;
	}

	public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

}
