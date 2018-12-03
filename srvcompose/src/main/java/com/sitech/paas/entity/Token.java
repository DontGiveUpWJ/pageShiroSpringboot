package com.sitech.paas.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by guoqing on 2018/9/4.
 */
public class Token implements Serializable {

    private Long instanceId;

    private String token;

    private Long expires;

    private Date endTime;

    private Boolean valid;

    public Token() {
    }

    public Token(Long instanceId, String token, Long expires, Date endTime) {
        this.instanceId = instanceId;
        this.token = token;
        this.expires = expires;
        this.endTime = endTime;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
