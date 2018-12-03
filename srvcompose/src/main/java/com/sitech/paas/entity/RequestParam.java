package com.sitech.paas.entity;

/**
 * 
 * @类描述：请求参数映射类
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.entity
 * @类名称：RequestParam
 * @创建人：dongkw
 * @创建时间：2018年9月25日下午2:18:27
 * @修改人：dongkw
 * @修改时间：2018年9月25日下午2:18:27
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
public class RequestParam {

    private String name;

    private String type;

    private String uniqueId;

    private int offset;

    private int rows;

    private String http2inId;
    
    private String tabName;
    
    public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getHttp2inId() {
        return http2inId;
    }

    public void setHttp2inId(String http2inId) {
        this.http2inId = http2inId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }


    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "RequestParam{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", offset=" + offset +
                ", rows=" + rows +
                '}';
    }
}
