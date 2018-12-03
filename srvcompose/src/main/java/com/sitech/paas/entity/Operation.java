/**
 * @标题: Operation.java
 * @包名： com.sitech.model
 * @功能描述：TODO
 * @作者： NeverGiveUp-WJ
 * @创建时间： 2018年11月13日 下午1:54:54
 * @version v1.0
 */
package com.sitech.paas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sitech.paas.util.DateHelper;


/**
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.model
 * @类名称：Operation
 * @创建人：NeverGiveUp-WJ
 * @创建时间：2018年11月13日下午1:54:54
 * @修改人：NeverGiveUp-WJ
 * @修改时间：2018年11月13日下午1:54:54
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
@Table(name = "optionRecord_201811")
public class Operation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "opt_id")
	private Integer oid;
	
	@Column(name = "opt_model")
	private String opeName;
	
	@Column(name = "opt_login")
	private String username;
	
	@Column(name = "opt_time")
	private String dateTime = DateHelper.format(new Date());

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

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getOpeName() {
		return opeName;
	}

	public void setOpeName(String opeName) {
		this.opeName = opeName;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	

}
