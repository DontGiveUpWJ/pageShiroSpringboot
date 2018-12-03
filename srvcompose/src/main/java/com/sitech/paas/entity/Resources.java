package com.sitech.paas.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@Table(name = "resourceInfo")
public class Resources implements Serializable{
    private static final long serialVersionUID = -6812242071705361506L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private Integer id;

    /**
     * 资源名称
     */
    @Column(name = "res_name")
    private String name;


    /**
     * 资源url
     */
    @Column(name = "res_path")
    private String resurl;

    /**
     * 资源类型   1:菜单    2：按钮
     */
    @Column(name = "res_type")
    private Integer type;

    /**
     * 父资源
     */
    @Column(name = "res_parent")
    private Integer parentid;

    @Column(name = "res_group")
    private String resGroup;

    @Column(name = "res_desc")
    private String resDesc;
    
    @Column(name = "is_valid")
    private Integer isValid;
    
    @Column(name = "res_create")
    private String resCreate;
    
    @Column(name = "create_time")
    private String createTime;
    
    @Column(name = "update_time")
    private String updateTime;
   

    @Transient
    private String checked;//是否选中
    
    @Transient
	private int start = 1;
	
	@Transient
	@JsonProperty("PAGE_SIZE")
	private int length = 10;
	    
	@Transient
	@JsonProperty("PAGE_NUM")
	private int pageNum = 1;


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
    
    
    public String getResGroup() {
		return resGroup;
	}

	public void setResGroup(String resGroup) {
		this.resGroup = resGroup;
	}

	public String getResDesc() {
		return resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getResCreate() {
		return resCreate;
	}

	public void setResCreate(String resCreate) {
		this.resCreate = resCreate;
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
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * 获取资源url
     *
     * @return resUrl - 资源url
     */
    public String getResurl() {
        return resurl;
    }

    /**
     * 设置资源url
     *
     * @param resurl 资源url
     */
    public void setResurl(String resurl) {
        this.resurl = resurl;
    }

    /**
     * 获取资源类型   1:菜单    2：按钮
     *
     * @return type - 资源类型   1:菜单    2：按钮
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置资源类型   1:菜单    2：按钮
     *
     * @param type 资源类型   1:菜单    2：按钮
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取父资源
     *
     * @return parentId - 父资源
     */
    public Integer getParentid() {
        return parentid;
    }

    /**
     * 设置父资源
     *
     * @param parentid 父资源
     */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }



   

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resurl='" + resurl + '\'' +
                ", type=" + type +
                ", parentid=" + parentid +     
                '}';
    }
}