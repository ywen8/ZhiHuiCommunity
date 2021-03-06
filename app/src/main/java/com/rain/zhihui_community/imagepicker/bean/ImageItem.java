package com.rain.zhihui_community.imagepicker.bean;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：dangyujia(雨)
 * 版    本：1.0
 * 创建日期：2017年2月7日
 * 描    述：图片信息
 * ================================================
 */
public class ImageItem implements Serializable {

    public String name;       //图片的名字
    public String path;       //图片的路径
    public long size;         //图片的大小
    public int width;         //图片的宽度
    public int height;        //图片的高度
    public String mimeType;   //图片的类型
    public long addTime;      //图片的创建时间

    @Override
    public String toString() {
        return "ImageItem{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", width=" + width +
                ", height=" + height +
                ", mimeType='" + mimeType + '\'' +
                ", addTime=" + addTime +
                '}';
    }

    /** 图片的路径和创建时间相同就认为是同一张图片 */
    @Override
    public boolean equals(Object o) {
        try {
            ImageItem other = (ImageItem) o;
            return this.path.equalsIgnoreCase(other.path) && this.addTime == other.addTime;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}

