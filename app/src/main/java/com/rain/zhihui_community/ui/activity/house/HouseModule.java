package com.rain.zhihui_community.ui.activity.house;

import android.content.Context;

import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.MyHouse;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * author : Rain
 * time : 2017/10/23 0023
 * explain :
 */

public class HouseModule extends BaseModule implements HouseContract.IHouseModule {

    private Context mContext;

    public HouseModule(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void house(String uid, final HouseContract.IHousePersenter iHousePersenter) {

        RetrofitClient.getInstance(mContext).createBaseApi().house(uid, new BaseSubscriber<List<MyHouse>>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                iHousePersenter.onError(e.Message);
            }

            @Override
            public void onNext(List<MyHouse> myHouses) {
                super.onNext(myHouses);
                iHousePersenter.getResult(myHouses);
            }
        });

    }

    @Override
    public void delete(String id, final HouseContract.IDeleteHousePersenter iDeleteHousePersenter) {
        RetrofitClient.getInstance(mContext).createBaseApi().deleteHouse(id, new BaseSubscriber<ResponseBody>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                iDeleteHousePersenter.onDeleteError(e.Message);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                iDeleteHousePersenter.getDeleteResult(responseBody.source().toString());
            }
        });
    }
}
