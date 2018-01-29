package com.rain.zhihui_community.ui.fragment.register;

import com.rain.zhihui_community.base.BasePresenter;
import com.rain.zhihui_community.entity.Persons;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain :
 */

public class RegisterPresenter extends BasePresenter<RegisterModule, RegisterFragment> implements RegisterContract.IRegisterPresenter, RegisterContract.IcodePresenter {


    public void toRegister() {
        module.toRegister(view.getMap(), this);
    }

    public void getCode() {
        module.getCode(view.getCode(), view.getType(), this);
    }


    @Override
    public void getResult(Persons persons) {
        view.onRegisterResult(persons);
    }

    @Override
    public void onError(String request) {
        view.showToast(request);
        view.dissVoid();
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
