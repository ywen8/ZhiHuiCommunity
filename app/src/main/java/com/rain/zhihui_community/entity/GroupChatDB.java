package com.rain.zhihui_community.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Rain on 2017/10/26.
 */
@Entity
public class GroupChatDB implements Parcelable {

    @Id
    private Long id;

    @Property(nameInDb = "messages")
    private String messages;

    @Property(nameInDb = "createtime")
    private String createtime;

    @Property(nameInDb = "username")
    private String username;

    @Property(nameInDb = "sex")
    private String sex;

    @Property(nameInDb = "headimg")
    private String headimg;

    @Property(nameInDb = "uid")
    private String uid;

    @Generated(hash = 1463432601)
    public GroupChatDB(Long id, String uid, String username, String headimg, String messages, String createtime, String sex) {
        this.id = id;
        this.messages = messages;
        this.createtime = createtime;
        this.username = username;
        this.sex = sex;
        this.headimg = headimg;
        this.uid = uid;
    }

    @Generated(hash = 1557449535)
    public GroupChatDB(String username, String headimg, String uid, String sex) {
        this.username = username;
        this.headimg = headimg;
        this.uid = uid;
        this.sex = sex;
    }

    public GroupChatDB() {
    }

    protected GroupChatDB(Parcel in) {
        messages = in.readString();
        createtime = in.readString();
        username = in.readString();
        sex = in.readString();
        headimg = in.readString();
        uid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messages);
        dest.writeString(createtime);
        dest.writeString(username);
        dest.writeString(sex);
        dest.writeString(headimg);
        dest.writeString(uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GroupChatDB> CREATOR = new Creator<GroupChatDB>() {
        @Override
        public GroupChatDB createFromParcel(Parcel in) {
            return new GroupChatDB(in);
        }

        @Override
        public GroupChatDB[] newArray(int size) {
            return new GroupChatDB[size];
        }
    };

    @Override
    public String toString() {
        return "GroupChatDB{" +
                "id=" + id +
                ", message='" + messages + '\'' +
                ", createtime='" + createtime + '\'' +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", headimg='" + headimg + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return messages;
    }

    public void setMessage(String message) {
        this.messages = message;
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
