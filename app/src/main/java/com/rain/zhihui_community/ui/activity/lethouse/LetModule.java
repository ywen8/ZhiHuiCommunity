package com.rain.zhihui_community.ui.activity.lethouse;

import android.content.Context;

import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.MyHouse;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/24 0024
 * explain :
 */

public class LetModule extends BaseModule implements LetContract.ILetModule {
    private Context mContext;

    public LetModule(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void house(String Commid, final LetContract.IHousePersenter iHousePersenter) {
        RetrofitClient.getInstance(mContext).createBaseApi().letData(Commid, new BaseSubscriber<List<MyHouse>>(mContext) {
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
    public void community(final LetContract.ICommunityPresenter iCommunityPresenter) {
        RetrofitClient.getInstance(mContext).createBaseApi().postCommunity(new BaseSubscriber<List<Community>>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                iCommunityPresenter.onCommunityError(e.Message);
            }

            @Override
            public void onNext(List<Community> communityList) {
                super.onNext(communityList);
                iCommunityPresenter.getCommunityResult(communityList);
            }
        });
    }
}
