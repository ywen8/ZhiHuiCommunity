package com.rain.zhihui_community.ui.fragment.register;

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

public class RegisterModule extends BaseModule implements RegisterContract.IRegisterModule {

    private Context mContext;

    public RegisterModule(Context context) {
        this.mContext = context;
    }

    @Override
    public void toRegister(Map<String, String> map, final RegisterContract.IRegisterPresenter iLoginPresenter) {

        RetrofitClient.getInstance(mContext).createBaseApi().register(map.get("phone"), map.get("password"), map.get("checkCode"), map.get("deviceid"), new BaseSubscriber<Persons>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (e.State == 1008) {
                    iLoginPresenter.onError("验证码错误");
                } else {
                    iLoginPresenter.onError("网络异常");
                }
            }

            @Override
            public void onNext(Persons persons) {
                super.onNext(persons);
                iLoginPresenter.getResult(persons);
            }
        });
    }

    @Override
    public void getCode(String phone, int type, final RegisterContract.IcodePresenter icodePresenter) {
        RetrofitClient.getInstance(mContext).createBaseApi().getCode(phone, type, new BaseSubscriber<ResponseBody>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                icodePresenter.onCodeError("验证码发出失败");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                icodePresenter.getCodeResult(responseBody.source().toString());
            }
        });
    }
}
