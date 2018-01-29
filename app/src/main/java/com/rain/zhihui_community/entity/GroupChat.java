package com.rain.zhihui_community.entity;

/**
 * Created by Rain on 2017/10/26.
 */

public class GroupChat {
    private String message;

    private String createtime;

    private String username;

    private String sex;

    private String headimg;
    private String uid;


    public GroupChat() {
    }

    public GroupChat(String username, String headimg, String uid) {
        this.username = username;
        this.headimg = headimg;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "GroupChat{" +
                "message='" + message + '\'' +
                ", createtime='" + createtime + '\'' +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", headimg='" + headimg + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public boolean isMsgType() {
        String id = GloData.getPersons().getUser().getId();
        if (id.equals(uid))
            return true;
        return false;
    }
}
