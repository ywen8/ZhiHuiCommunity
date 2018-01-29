package com.rain.zhihui_community.entity;

/**
 * Created by Administrator on 2017/10/30.
 */

public class ControlPerson {
    private String id;

    private String commid;

    private String name;

    private String headimg;

    private String phone;

    private Integer type;

    private String position;

    @Override
    public String toString() {
        return "ControlPerson{" +
                "id='" + id + '\'' +
                ", commid='" + commid + '\'' +
                ", name='" + name + '\'' +
                ", headimg='" + headimg + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", position='" + position + '\'' +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
