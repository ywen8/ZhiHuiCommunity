package com.rain.zhihui_community.entity;

/**
 * Created by Rain on 2017/10/28.
 */

public class BannerItem {
    private String id;

    private String title;

    private String newsimg;

    private String createtime;

    private Integer status;

    private String details;

    private Integer type;

    private String source;

    private String xfimg;
    private String img;

    @Override
    public String toString() {
        return "BannerItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", newsimg='" + newsimg + '\'' +
                ", createtime='" + createtime + '\'' +
                ", status=" + status +
                ", details='" + details + '\'' +
                ", type=" + type +
                ", source='" + source + '\'' +
                ", xfimg='" + xfimg + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getXfimg() {
        return xfimg;
    }

    public void setXfimg(String xfimg) {
        this.xfimg = xfimg;
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

    public String getNewsimg() {
        return newsimg;
    }

    public void setNewsimg(String newsimg) {
        this.newsimg = newsimg;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
