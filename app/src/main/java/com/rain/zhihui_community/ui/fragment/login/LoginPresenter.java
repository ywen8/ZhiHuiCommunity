package com.rain.zhihui_community.ui.fragment.login;

import com.rain.zhihui_community.base.BasePresenter;

import okhttp3.ResponseBody;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain :
 */

public class LoginPresenter extends BasePresenter<LoginModule, LoginFragment> implements LoginContract.ILoginPresenter {


    public void toLogin() {
        module.toLogin(view.getMap(), this);
    }


    @Override
    public void getResult(ResponseBody persons) {
        view.onLoginResult(persons);
    }

    @Override
    public void onError(String request) {
        view.showToast(request);
        view.dissVoid();
    }
}
