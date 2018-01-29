package com.rain.zhihui_community.ui.activity.forget;

import android.content.Context;

import com.rain.zhihui_community.base.BaseModule;
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

public class ForgetModule extends BaseModule implements ForgetContract.IForgetModule {

    private Context mContext;

    public ForgetModule(Context context) {
        this.mContext = context;
    }

    /**
     * 忘记密码
     *
     * @param map
     * @param iForgetPresenter
     */
    @Override
    public void toForget(Map<String, String> map, final ForgetContract.IForgetPresenter iForgetPresenter) {

        RetrofitClient.getInstance(mContext).createBaseApi().postForget(map.get("phone"), map.get("password"), map.get("checkCode"), new BaseSubscriber<ResponseBody>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                iForgetPresenter.onError(e.Message);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                iForgetPresenter.getResult(responseBody.source().toString());
            }
        });
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @param icodePresenter
     */
    @Override
    public void getCode(String phone, int type, final ForgetContract.IcodePresenter icodePresenter) {
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
