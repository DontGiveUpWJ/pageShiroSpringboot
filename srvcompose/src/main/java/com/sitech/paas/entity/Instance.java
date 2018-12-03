package com.sitech.paas.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @version v1.0
 * @类描述：
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.entity
 * @类名称：Instance
 * @创建人：guoqq_paas
 * @创建时间：2018/9/26 16:18
 * @修改人：guoqq_paas
 * @修改时间：2018/11/21 16:18
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
@Table(name ="cluster_info")
public class Instance implements Serializable {
    @Id
    @Column(name="cluster_id")
    private Long id;

    @Column(name="cluster_ip")
    private String ip;

    @Column(name="cluster_port")
    private String port;

    @Column(name="user_name")
    private String username;

    @Column(name="user_pwd")
    private String password;

    @Column(name="file_name")
    private String filename;

    @Column(name="file_path")
    private String filepath;

    @Column(name="view_url")
    private String url;

    private Integer type;

    private Integer status;

    private Integer isValid;

    private String resCreate;

    private Date createTime;

    private Date updateTime;


    public Instance() {
    }

    public Instance(String ip, String port, String username, String password, String filename, String filepath, String url, Integer type) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.filename = filename;
        this.filepath = filepath;
        this.url = url;
        this.type = type;
    }

    public Instance(Long id, String ip, String port, String username, String password, String filename, String filepath, String url, Integer type, Integer status, Integer isValid, String resCreate, Date createTime, Date updateTime) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.filename = filename;
        this.filepath = filepath;
        this.url = url;
        this.type = type;
        this.status = status;
        this.isValid = isValid;
        this.resCreate = resCreate;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", filename='" + filename + '\'' +
                ", filepath='" + filepath + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", isValid=" + isValid +
                ", resCreate='" + resCreate + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
