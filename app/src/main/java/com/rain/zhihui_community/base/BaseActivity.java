package com.rain.zhihui_community.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rain.zhihui_community.utils.DialogLoading;
import com.rain.zhihui_community.utils.WinToast;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain :
 */

public abstract class BaseActivity<V extends BaseContract.IBaseView, P extends BasePresenter> extends BaseAppAvtivity implements BaseContract.IBaseView {

    public P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attatchWindow(initModule(), this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachWindow();
    }


    protected abstract P initPresenter();

    protected abstract BaseModule initModule();

    @Override
    public void showToast(String msg) {
        WinToast.toast(this, msg);
    }

    @Override
    public void showProgress() {
        loading.showLoading();
    }

    @Override
    public void hideProgress() {
        loading.dismissLoading();
    }

    @Override
    public void showNullLayout() {

    }

    @Override
    public void hideNullLayout() {

    }
}
