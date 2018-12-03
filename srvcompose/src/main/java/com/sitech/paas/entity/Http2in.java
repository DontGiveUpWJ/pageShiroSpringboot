package com.sitech.paas.entity;

/**
 * 
 * @类描述：流程服务实体类
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.entity
 * @类名称：Http2in
 * @创建人：dongkw
 * @创建时间：2018年9月25日下午2:17:06
 * @修改人：dongkw
 * @修改时间：2018年9月25日下午2:17:06
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
public class Http2in {


    private Integer id;

    private String name;

    private String url;

    private String method;

    private String z;

    private String tabname;

    private String wires;

    private String childs;

    private boolean isValid;

    private String httpId;

    public String getHttpId() {
        return httpId;
    }

    public void setHttpId(String httpId) {
        this.httpId = httpId;
    }

    public String getChilds() {
        return childs;
    }

    public void setChilds(String childs) {
        this.childs = childs;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z == null ? null : z.trim();
    }

    public String getTabname() {
        return tabname;
    }

    public void setTabname(String tabname) {
        this.tabname = tabname == null ? null : tabname.trim();
    }

    public String getWires() {
        return wires;
    }

    public void setWires(String wires) {
        this.wires = wires == null ? null : wires.trim();
    }



    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Http2in){
            Http2in s = (Http2in) obj;
            boolean flag;
            flag = this.name.equals(s.name)
                    &&this.httpId.equals(s.httpId)
                    &&this.url.equals(s.url)
                    &&this.method.equals(s.method)
                    &&this.z.equals(s.z)
                    &&this.tabname.equals(s.tabname)
                    &&this.wires.equals(s.wires)
                    &&this.childs.equals(s.childs);
            return flag;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = id.hashCode() + name.hashCode() + childs.hashCode() + url.hashCode() + z.hashCode()+wires.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "Http2in{" +
                "id='" + id + '\'' +
                ", httpId='" + httpId + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", z='" + z + '\'' +
                ", tabname='" + tabname + '\'' +
                ", wires='" + wires + '\'' +
                ", childs='" + childs + '\'' +
                '}';
    }
}