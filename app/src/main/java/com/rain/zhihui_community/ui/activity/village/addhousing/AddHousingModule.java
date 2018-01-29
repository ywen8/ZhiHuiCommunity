package com.rain.zhihui_community.ui.activity.village.addhousing;

import android.content.Context;

import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;

import java.util.Map;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class AddHousingModule extends BaseModule implements AddHousingContract.IAddHousingModule {
    private Context mContext;

    public AddHousingModule(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void addHousing(Map<String, String> map, final AddHousingContract.IAddHousingPresenter iAddHousingPresenter) {

        RetrofitClient.getInstance(mContext).createBaseApi().addHousing(map, new BaseSubscriber<MyCommunity>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (e.State == 1008) {
                    iAddHousingPresenter.onError("请勿重复添加");
                } else {
                    iAddHousingPresenter.onError(e.Message);
                }
            }

            @Override
            public void onNext(MyCommunity responseBody) {
                super.onNext(responseBody);
                iAddHousingPresenter.getResult(responseBody);
            }
        });
    }
}
