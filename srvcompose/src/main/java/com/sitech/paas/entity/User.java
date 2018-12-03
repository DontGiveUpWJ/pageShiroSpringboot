package com.sitech.paas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "userInfo")
public class User implements Serializable {
	private static final long serialVersionUID = -8736616045315083846L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Column(name = "user_name")
	private String username;

	@Column(name = "user_pwd")
	private String password;

	@Column(name = "user_group")
    private String userGroup;
    
    @Column(name = "user_desc")
    private String userDesc;
    

    @Column(name = "user_create")
    private String userCreate;
    
    @Column(name = "create_time")
    private String createTime;
    
    @Column(name = "update_time")
    private String updateTime;
	
    /**
	 * 是否启用
	 */
    @Column(name = "is_valid")
	private Integer enable;
	@Transient
	private int start = 1;
	@Transient
	@JsonProperty("PAGE_SIZE")
	private int length = 10;
	
	@Transient
	@JsonProperty("PAGE_NUM")
	private int pageNum;

	
	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取是否启用
	 *
	 * @return enable - 是否启用
	 */
	public Integer getEnable() {
		return enable;
	}

	/**
	 * 设置是否启用
	 *
	 * @param enable 是否启用
	 */
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", enable="
				+ enable + '}';
	}
}