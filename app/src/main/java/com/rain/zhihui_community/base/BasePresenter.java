package com.rain.zhihui_community.base;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain :
 */

public class BasePresenter<M extends BaseModule, V extends BaseContract.IBaseView> {
    public M module;
    public V view;

    void attatchWindow(M m, V v) {
        this.module = m;
        this.view = v;
    }

    void detachWindow() {
        this.module = null;
        this.view = null;
    }
}
