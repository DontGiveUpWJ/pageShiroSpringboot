package com.sitech.paas.entity;

import java.util.Date;

public class Microservice {

    private Integer serverId;

    private String serverName;

    private String serverUniqueid;

    private String serverChName;

    private String serverApp;

    private String serverOrginName;

    private String serverUrl;

    private String serverRegister;

    private String serverBalanceloadUrl;

    private Integer serverType;

    private String serverVersion;

    private String serverGroup;

    private Integer serverTimeout;

    private String serverBalanceload;

    private Integer serverFailover;

    private String serverParamsIn;

    private String serverParamsOut;

    private String serverStatusNormal;

    private String serverStatusError;

    private String serverFrom;

    private String serverSystem;

    private String serverBusiness;

    private String serverAuthor;

    private String serverUpdateAuthor;

    private Date serverCtime;

    private Date serverUpdatetime;

    private Boolean serverDatainitMode;

    private Boolean serverDatamodMode;

    private String serverMethod;

    private String serverDesc;

    private boolean isValid;


    public String getServerDesc() {
        return serverDesc;
    }

    public void setServerDesc(String serverDesc) {
        this.serverDesc = serverDesc;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getServerUniqueid() {
        return serverUniqueid;
    }

    public void setServerUniqueid(String serverUniqueid) {
        this.serverUniqueid = serverUniqueid == null ? null : serverUniqueid.trim();
    }

    public String getServerChName() {
        return serverChName;
    }

    public void setServerChName(String serverChName) {
        this.serverChName = serverChName == null ? null : serverChName.trim();
    }

    public String getServerApp() {
        return serverApp;
    }

    public void setServerApp(String serverApp) {
        this.serverApp = serverApp == null ? null : serverApp.trim();
    }

    public String getServerOrginName() {
        return serverOrginName;
    }

    public void setServerOrginName(String serverOrginName) {
        this.serverOrginName = serverOrginName == null ? null : serverOrginName.trim();
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl == null ? null : serverUrl.trim();
    }

    public String getServerRegister() {
        return serverRegister;
    }

    public void setServerRegister(String serverRegister) {
        this.serverRegister = serverRegister == null ? null : serverRegister.trim();
    }

    public String getServerBalanceloadUrl() {
        return serverBalanceloadUrl;
    }

    public void setServerBalanceloadUrl(String serverBalanceloadUrl) {
        this.serverBalanceloadUrl = serverBalanceloadUrl == null ? null : serverBalanceloadUrl.trim();
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion == null ? null : serverVersion.trim();
    }

    public String getServerGroup() {
        return serverGroup;
    }

    public void setServerGroup(String serverGroup) {
        this.serverGroup = serverGroup == null ? null : serverGroup.trim();
    }

    public Integer getServerTimeout() {
        return serverTimeout;
    }

    public void setServerTimeout(Integer serverTimeout) {
        this.serverTimeout = serverTimeout;
    }

    public String getServerBalanceload() {
        return serverBalanceload;
    }

    public void setServerBalanceload(String serverBalanceload) {
        this.serverBalanceload = serverBalanceload;
    }

    public Integer getServerFailover() {
        return serverFailover;
    }

    public void setServerFailover(Integer serverFailover) {
        this.serverFailover = serverFailover;
    }

    public String getServerParamsIn() {
        return serverParamsIn;
    }

    public void setServerParamsIn(String serverParamsIn) {
        this.serverParamsIn = serverParamsIn == null ? null : serverParamsIn.trim();
    }

    public String getServerParamsOut() {
        return serverParamsOut;
    }

    public void setServerParamsOut(String serverParamsOut) {
        this.serverParamsOut = serverParamsOut == null ? null : serverParamsOut.trim();
    }

    public String getServerStatusNormal() {
        return serverStatusNormal;
    }

    public void setServerStatusNormal(String serverStatusNormal) {
        this.serverStatusNormal = serverStatusNormal == null ? null : serverStatusNormal.trim();
    }

    public String getServerStatusError() {
        return serverStatusError;
    }

    public void setServerStatusError(String serverStatusError) {
        this.serverStatusError = serverStatusError == null ? null : serverStatusError.trim();
    }

    public String getServerFrom() {
        return serverFrom;
    }

    public void setServerFrom(String serverFrom) {
        this.serverFrom = serverFrom == null ? null : serverFrom.trim();
    }

    public String getServerSystem() {
        return serverSystem;
    }

    public void setServerSystem(String serverSystem) {
        this.serverSystem = serverSystem == null ? null : serverSystem.trim();
    }

    public String getServerBusiness() {
        return serverBusiness;
    }

    public void setServerBusiness(String serverBusiness) {
        this.serverBusiness = serverBusiness == null ? null : serverBusiness.trim();
    }

    public String getServerAuthor() {
        return serverAuthor;
    }

    public void setServerAuthor(String serverAuthor) {
        this.serverAuthor = serverAuthor == null ? null : serverAuthor.trim();
    }

    public String getServerUpdateAuthor() {
        return serverUpdateAuthor;
    }

    public void setServerUpdateAuthor(String serverUpdateAuthor) {
        this.serverUpdateAuthor = serverUpdateAuthor == null ? null : serverUpdateAuthor.trim();
    }

    public Date getServerCtime() {
        return serverCtime;
    }

    public void setServerCtime(Date serverCtime) {
        this.serverCtime = serverCtime;
    }

    public Date getServerUpdatetime() {
        return serverUpdatetime;
    }

    public void setServerUpdatetime(Date serverUpdatetime) {
        this.serverUpdatetime = serverUpdatetime;
    }

    public Boolean getServerDatainitMode() {
        return serverDatainitMode;
    }

    public void setServerDatainitMode(Boolean serverDatainitMode) {
        this.serverDatainitMode = serverDatainitMode;
    }

    public Boolean getServerDatamodMode() {
        return serverDatamodMode;
    }

    public void setServerDatamodMode(Boolean serverDatamodMode) {
        this.serverDatamodMode = serverDatamodMode;
    }

    public String getServerMethod() {
        return serverMethod;
    }

    public void setServerMethod(String serverMethod) {
        this.serverMethod = serverMethod == null ? null : serverMethod.trim();
    }
}