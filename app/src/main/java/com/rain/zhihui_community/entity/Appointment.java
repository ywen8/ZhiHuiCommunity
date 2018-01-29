package com.rain.zhihui_community.entity;

/**
 * author : Rain
 * time : 2017/10/24 0024
 * explain :
 */

public class Appointment {

    private String commid;

    private String phone;

    @Override
    public String toString() {
        return "Appointment{" +
                "commid='" + commid + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getCommid() {
        return commid;
    }

    public void setCommid(String commid) {
        this.commid = commid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
