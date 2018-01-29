package com.rain.zhihui_community.entity;

import java.io.Serializable;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class Community implements Serializable {

    private String cid;
    private String commid;
    private String commname;
    private String zonecode;
    private String address;
    private String longitude;
    private String latitude;
    private String status;

    @Override
    public String toString() {
        return "Community{" +
                "cid='" + cid + '\'' +
                ", commid='" + commid + '\'' +
                ", commname='" + commname + '\'' +
                ", zonecode='" + zonecode + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
