package com.rain.zhihui_community.entity;

import java.io.Serializable;

/**
 * author : Rain
 * time : 2017/10/19 0019
 * explain :
 */

public class MyCommunity implements Serializable {
    private String commid;

    private String commname;

    private String zonecode;

    private String id;

    private String uid;

    private String cid;

    private Integer state;

    private String createdate;

    private String roomnumber;

    private String unitnumber;

    private boolean isSelect = false;

    @Override
    public String toString() {
        return "MyCommunity{" +
                "commid='" + commid + '\'' +
                ", commname='" + commname + '\'' +
                ", zonecode='" + zonecode + '\'' +
                ", id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", cid='" + cid + '\'' +
                ", state=" + state +
                ", createdate='" + createdate + '\'' +
                ", roomnumber='" + roomnumber + '\'' +
                ", unitnumber='" + unitnumber + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getCommid() {
        return commid;
    }

    public void setCommid(String commid) {
        this.commid = commid;
    }

    public String getCommname() {
        return commname;
    }

    public void setCommname(String commname) {
        this.commname = commname;
    }

    public String getZonecode() {
        return zonecode;
    }

    public void setZonecode(String zonecode) {
        this.zonecode = zonecode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getUnitnumber() {
        return unitnumber;
    }

    public void setUnitnumber(String unitnumber) {
        this.unitnumber = unitnumber;
    }
}
