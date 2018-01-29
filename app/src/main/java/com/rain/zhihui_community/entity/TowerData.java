package com.rain.zhihui_community.entity;

/**
 * Created by Rain on 2017/11/1.
 */

public class TowerData {
    private String id;

    private String commid;

    private String floorname;

    private String imga;

    private String imgb;

    private String imgc;

    private String imgd;

    private String imge;

    private String details;

    @Override
    public String toString() {
        return "TowerData{" +
                "id='" + id + '\'' +
                ", commid='" + commid + '\'' +
                ", floorname='" + floorname + '\'' +
                ", imga='" + imga + '\'' +
                ", imgb='" + imgb + '\'' +
                ", imgc='" + imgc + '\'' +
                ", imgd='" + imgd + '\'' +
                ", imge='" + imge + '\'' +
                ", details='" + details + '\'' +
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

    public String getFloorname() {
        return floorname;
    }

    public void setFloorname(String floorname) {
        this.floorname = floorname;
    }

    public String getImga() {
        return imga;
    }

    public void setImga(String imga) {
        this.imga = imga;
    }

    public String getImgb() {
        return imgb;
    }

    public void setImgb(String imgb) {
        this.imgb = imgb;
    }

    public String getImgc() {
        return imgc;
    }

    public void setImgc(String imgc) {
        this.imgc = imgc;
    }

    public String getImgd() {
        return imgd;
    }

    public void setImgd(String imgd) {
        this.imgd = imgd;
    }

    public String getImge() {
        return imge;
    }

    public void setImge(String imge) {
        this.imge = imge;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
