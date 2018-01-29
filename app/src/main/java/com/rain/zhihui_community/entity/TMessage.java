package com.rain.zhihui_community.entity;

/**
 * author : Rain
 * time : 2017/10/25 0025
 * explain :
 */

public class TMessage {
    private String id;

    private String title;

    private String details;

    private String createtime;

    private Integer type;

    private Integer status;

    private String cid;

    @Override
    public String toString() {
        return "TMessage{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", createtime='" + createtime + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", cid='" + cid + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
