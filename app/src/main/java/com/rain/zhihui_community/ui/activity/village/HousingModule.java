package com.rain.zhihui_community.ui.activity.village;

import android.content.Context;

import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * author : Rain
 * time : 2017/10/19 0019
 * explain :
 */

public class HousingModule extends BaseModule implements HousingContract.IHousingModule {
    private Context mContext;

    public HousingModule(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getHousing(String uid, final HousingContract.IHousingPresenter iHousingPresenter) {
        RetrofitClient.getInstance(mContext).createBaseApi().postHouing(uid, new BaseSubscriber<List<MyCommunity>>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                iHousingPresenter.onError(e.Message);
            }

            @Override
            public void onNext(List<MyCommunity> communityList) {
                super.onNext(communityList);
                iHousingPresenter.getResult(communityList);
            }
        });
    }

    @Override
    public void delete(String id, final HousingContract.IDeletePresenter iDeletePresenter) {
        RetrofitClient.getInstance(mContext).createBaseApi().delete(id, new BaseSubscriber<ResponseBody>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
//                iDeletePresenter.onDeleteError(e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                iDeletePresenter.getDeleteResult(responseBody.source().toString());
            }
        });
    }
}
