package com.rain.zhihui_community.entity;

import java.io.File;
import java.io.Serializable;

/**
 * author : Rain
 * time : 2017/10/25 0025
 * explain :
 */

public class ImageIssueData implements Serializable {

    private File file;
    private int path;
    private String strPath;
    private boolean isFile;

    @Override
    public String toString() {
        return "ImageIssueData{" +
                "file=" + file +
                ", path=" + path +
                ", strPath='" + strPath + '\'' +
                ", isFile=" + isFile +
                '}';
    }

    public ImageIssueData(String strPath, boolean isFile) {
        this.strPath = strPath;
        this.isFile = isFile;
    }

    public ImageIssueData(int path, boolean isFile) {
        this.path = path;
        this.isFile = isFile;
    }

    public ImageIssueData(File file, boolean isFile) {
        this.file = file;
        this.isFile = isFile;
    }

    public String getStrPath() {
        return strPath;
    }

    public void setStrPath(String strPath) {
        this.strPath = strPath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }
}
