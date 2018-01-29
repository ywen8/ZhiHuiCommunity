package com.rain.zhihui_community.ui.fragment.login;

import android.content.Context;

import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.Persons;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;

import java.util.Map;

import okhttp3.ResponseBody;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain :
 */

public class LoginModule extends BaseModule implements LoginContract.ILoginModule {

    private Context mContext;

    public LoginModule(Context context) {
        this.mContext = context;
    }

    @Override
    public void toLogin(Map<String, String> map, final LoginContract.ILoginPresenter iLoginPresenter) {
        RetrofitClient.getInstance(mContext).createBaseApi().getLogin(map, new BaseSubscriber<ResponseBody>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                iLoginPresenter.onError(e.Message);
            }

            @Override
            public void onNext(ResponseBody persons) {
                super.onNext(persons);
                iLoginPresenter.getResult(persons);
            }
        });
    }
}
