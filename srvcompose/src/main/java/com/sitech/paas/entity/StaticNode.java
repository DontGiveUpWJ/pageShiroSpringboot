/**
 * @标题: StaticNode.java
 * @包名： com.sitech.model
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年11月9日 上午9:23:08
 * @version v1.0
 */
package com.sitech.paas.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.model
 * @类名称：StaticNode
 * @创建人：NeverGiveUp-WJ
 * @创建时间：2018年11月9日上午9:23:08
 * @修改人：NeverGiveUp-WJ
 * @修改时间：2018年11月9日上午9:23:08
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Table(name = "staticflow")
public class StaticNode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer nid;
	
	@Column(name = "noname")
	private String noName;
	
	@Column(name = "nodesc")
	private String noDesc;
	
	
	@Column(name = "flowdata")
	private String flowData;
	
	@Transient
	@JsonProperty("PAGE_SIZE")
	private int length = 10;
	
	@Transient
	@JsonProperty("PAGE_NUM")
	private int pageNum;
	
	
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public String getNoDesc() {
		return noDesc;
	}
	public void setNoDesc(String noDesc) {
		this.noDesc = noDesc;
	}
	
	public Integer getNid() {
		return nid;
	}
	public void setNid(Integer nid) {
		this.nid = nid;
	}
	public String getNoName() {
		return noName;
	}
	public void setNoName(String noName) {
		this.noName = noName;
	}
	public String getFlowData() {
		return flowData;
	}
	public void setFlowData(String flowData) {
		this.flowData = flowData;
	}
	

}
