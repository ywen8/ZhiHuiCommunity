package com.rain.zhihui_community.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rain.zhihui_community.utils.DialogLoading;
import com.rain.zhihui_community.utils.WinToast;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain : 网络请求fragment 的基类
 */

public abstract class BaseFragment<V extends BaseContract.IBaseView, P extends BasePresenter> extends BaseAppFragment implements BaseContract.IBaseView {

    public P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attatchWindow(initModule(), this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachWindow();
    }

    protected abstract P initPresenter();

    protected abstract BaseModule initModule();


    @Override
    public void showToast(String msg) {
        WinToast.toast(getActivity(), msg);
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showNullLayout() {

    }

    @Override
    public void hideNullLayout() {

    }
}
