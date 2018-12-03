package com.sitech.paas.entity;

import java.io.Serializable;

/**
 * @version v1.0
 * @类描述：
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.entity
 * @类名称：Flows
 * @创建人：guoqq_paas
 * @创建时间：2018/9/26 16:16
 * @修改人：guoqq_paas
 * @修改时间：2018/9/26 16:16
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
public class Flows implements Serializable {

    private String rev;

    private String filename;

    private String flows;

    public Flows() {
    }

    public Flows(String rev, String filename, String flows) {
        this.rev = rev;
        this.filename = filename;
        this.flows = flows;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFlows() {
        return flows;
    }

    public void setFlows(String flows) {
        this.flows = flows;
    }

    @Override
    public String toString() {
        return "FlowFile{" +
                ", rev='" + rev + '\'' +
                ", filename='" + filename + '\'' +
                ", flows='" + flows + '\'' +
                '}';
    }
}
