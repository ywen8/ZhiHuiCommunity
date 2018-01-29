package com.rain.zhihui_community.entity;

import java.io.Serializable;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class UnitNumber implements Serializable {
    private String id;

    private String commid;

    private String gatename;

    private Integer gatetype;

    @Override
    public String toString() {
        return "UnitNumber{" +
                "id='" + id + '\'' +
                ", commid='" + commid + '\'' +
                ", gatename='" + gatename + '\'' +
                ", gatetype=" + gatetype +
                '}';
    }

    public String getCommid() {
        return commid;
    }

    public void setCommid(String commid) {
        this.commid = commid;
    }

    public String getGatename() {
        return gatename;
    }

    public void setGatename(String gatename) {
        this.gatename = gatename;
    }

    public Integer getGatetype() {
        return gatetype;
    }

    public void setGatetype(Integer gatetype) {
        this.gatetype = gatetype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
