package com.rain.zhihui_community.entity;

import java.util.List;

import static com.rain.zhihui_community.R.mipmap.code;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain : 用户信息
 */

public class Persons {

    private UserBean user;
    private List<MyCommunity> middleueDTOS;

    @Override
    public String toString() {
        return "Persons{" +
                "user=" + user +
                ", middleueDTOS=" + middleueDTOS +
                '}';
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<MyCommunity> getMiddleueDTOS() {
        return middleueDTOS;
    }

    public void setMiddleueDTOS(List<MyCommunity> middleueDTOS) {
        this.middleueDTOS = middleueDTOS;
    }

    public static class UserBean {

        private String id;
        private String username;
        private String password;
        private int age;
        private String idcard;
        private String sex;
        private int status;
        private String createdate;
        private String deviceid;
        private String phone;
        private String headimg;
        private String reservedone;
        private String reservedtwo;

        @Override
        public String toString() {
            return "UserBean{" +
                    "id='" + id + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", age=" + age +
                    ", idcard='" + idcard + '\'' +
                    ", sex='" + sex + '\'' +
                    ", status=" + status +
                    ", createdate='" + createdate + '\'' +
                    ", deviceid='" + deviceid + '\'' +
                    ", phone='" + phone + '\'' +
                    ", headimg='" + headimg + '\'' +
                    ", reservedone='" + reservedone + '\'' +
                    ", reservedtwo='" + reservedtwo + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getReservedone() {
            return reservedone;
        }

        public void setReservedone(String reservedone) {
            this.reservedone = reservedone;
        }

        public String getReservedtwo() {
            return reservedtwo;
        }

        public void setReservedtwo(String reservedtwo) {
            this.reservedtwo = reservedtwo;
        }
    }
}

