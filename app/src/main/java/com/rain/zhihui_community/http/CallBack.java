package com.rain.zhihui_community.http;

/**
 * Created by Rain on 2017/6/13 0013.
 */

public abstract class CallBack {
    public void onStart(){}

    public void onCompleted(){}

    abstract public void onError(Throwable e);

    public void onProgress(long fileSizeDownloaded){}

    abstract public void onSucess(String path, String name, long fileSize);
}
