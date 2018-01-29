package com.rain.zhihui_community.ui.activity.forget;

import com.rain.zhihui_community.base.BasePresenter;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain :
 */

public class ForgetPresenter extends BasePresenter<ForgetModule, ForgetPasswordActivity> implements ForgetContract.IForgetPresenter, ForgetContract.IcodePresenter {


    public void toForget() {
        view.showProgress();
        module.toForget(view.getMap(), this);
    }

    public void getCode() {
        module.getCode(view.getCode(), view.getType(), this);
    }


    @Override
    public void getResult(String request) {
        view.hideProgress();
        view.onForgetResult(request);
    }

    @Override
    public void onError(String request) {
        view.hideProgress();
        view.showToast(request);
    }

    @Override
    public void getCodeResult(String code) {
        view.getCode(code);
    }

    @Override
    public void onCodeError(String request) {
        view.showToast(request);
    }
}
