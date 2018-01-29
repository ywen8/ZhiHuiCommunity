package com.rain.zhihui_community.entity;

/**
 * Created by Rain on 2017/10/30.
 */

public class BuildData {
    private String id;

    private String img;

    private String commid;
    private String build;

    private String cgeneralization;//小区概况

    private String xfgeneralization;//消防站

    private String convention;//公约

    private String floorname;

    @Override
    public String toString() {
        return "BuildData{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", commid='" + commid + '\'' +
                ", build='" + build + '\'' +
                ", cgeneralization='" + cgeneralization + '\'' +
                ", xfgeneralization='" + xfgeneralization + '\'' +
                ", convention='" + convention + '\'' +
                ", floorname='" + floorname + '\'' +
                '}';
    }

    public String getFloorname() {
        return floorname;
    }

    public void setFloorname(String floorname) {
        this.floorname = floorname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCommid() {
        return commid;
    }

    public void setCommid(String commid) {
        this.commid = commid;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getCgeneralization() {
        return cgeneralization;
    }

    public void setCgeneralization(String cgeneralization) {
        this.cgeneralization = cgeneralization;
    }

    public String getXfgeneralization() {
        return xfgeneralization;
    }

    public void setXfgeneralization(String xfgeneralization) {
        this.xfgeneralization = xfgeneralization;
    }

    public String getConvention() {
        return convention;
    }

    public void setConvention(String convention) {
        this.convention = convention;
    }
}
