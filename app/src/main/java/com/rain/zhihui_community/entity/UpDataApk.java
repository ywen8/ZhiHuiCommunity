package com.rain.zhihui_community.entity;

/**
 * author : Rain
 * time : 2017/11/10 0010
 * explain :
 */

public class UpDataApk {

    private String id;

    private String vnumber;

    private String vdetails;

    private String download;

    private String createtime;

    private Integer status;

    @Override
    public String toString() {
        return "UpDataApk{" +
                "id='" + id + '\'' +
                ", vnumber='" + vnumber + '\'' +
                ", vdetails='" + vdetails + '\'' +
                ", download='" + download + '\'' +
                ", createtime='" + createtime + '\'' +
                ", status=" + status +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVnumber() {
        return vnumber;
    }

    public void setVnumber(String vnumber) {
        this.vnumber = vnumber;
    }

    public String getVdetails() {
        return vdetails;
    }

    public void setVdetails(String vdetails) {
        this.vdetails = vdetails;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
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
}
