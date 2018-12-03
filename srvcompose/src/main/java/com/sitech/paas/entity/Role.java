package com.sitech.paas.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@Table(name = "roleInfo")
public class Role implements Serializable{
    private static final long serialVersionUID = -6140090613812307452L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role_name")
    private String roledesc;

    @Column(name = "role_group")
    private String roleGroup;
    
    @Column(name = "role_desc")
    private String rDescribe;
    
    @Column(name = "role_parent")
    private String roleParent;
    
    @Column(name = "is_valid")
    private Integer isValid;
    
    @Column(name = "role_create")
    private String roleCreate;
    
    @Column(name = "create_time")
    private String createTime;
    
    @Column(name = "update_time")
    private String updateTime;
    
    @Transient
    private Integer selected;
    
    
    @Transient
	private int start = 1;
    
    @Transient
    @JsonProperty("PAGE_SIZE")
    private int length = 10;
    
    @Transient
    @JsonProperty("PAGE_NUM")
    private int pageNum = 1;
    
    
   
    
	public String getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}

	public String getrDescribe() {
		return rDescribe;
	}

	public void setrDescribe(String rDescribe) {
		this.rDescribe = rDescribe;
	}

	public String getRoleParent() {
		return roleParent;
	}

	public void setRoleParent(String roleParent) {
		this.roleParent = roleParent;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getRoleCreate() {
		return roleCreate;
	}

	public void setRoleCreate(String roleCreate) {
		this.roleCreate = roleCreate;
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
     * @return roleDesc
     */
    public String getRoledesc() {
        return roledesc;
    }

    /**
     * @param roledesc
     */
    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

	@Override
	public String toString() {
		return "Role [id=" + id + ", roledesc=" + roledesc + ", selected=" + selected   + ", start="
				+ start + ", length=" + length + "]";
	}
    
}