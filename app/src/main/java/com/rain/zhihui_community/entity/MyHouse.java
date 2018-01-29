package com.rain.zhihui_community.entity;

/**
 * author : Rain
 * time : 2017/10/20 0020
 * explain :
 */

public class MyHouse {

    private String id;

    private String commid;

    private String commname;

    private String details;

    private String imgs;

    private String uid;

    private Integer status;

    private String username;

    private String headimg;

    @Override
    public String toString() {
        return "MyHouse{" +
                "id='" + id + '\'' +
                ", commid='" + commid + '\'' +
                ", commname='" + commname + '\'' +
                ", details='" + details + '\'' +
                ", imgs='" + imgs + '\'' +
                ", uid='" + uid + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", headimg='" + headimg + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
