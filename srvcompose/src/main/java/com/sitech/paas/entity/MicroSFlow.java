package com.sitech.paas.entity;

/**
 * 
 * @类描述：流程服务和原子服务之间的映射关系
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.entity
 * @类名称：MicroSFlow
 * @创建人：dongkw
 * @创建时间：2018年9月25日下午2:17:42
 * @修改人：dongkw
 * @修改时间：2018年9月25日下午2:17:42
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
public class MicroSFlow {
    private Integer id;

    private String httpinid;

    private String uniqueid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHttpinid() {
        return httpinid;
    }

    public void setHttpinid(String httpinid) {
        this.httpinid = httpinid == null ? null : httpinid.trim();
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid == null ? null : uniqueid.trim();
    }
}