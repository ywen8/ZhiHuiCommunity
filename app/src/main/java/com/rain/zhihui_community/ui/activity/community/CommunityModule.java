package com.rain.zhihui_community.ui.activity.community;

import android.content.Context;

import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.UnitNumber;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class CommunityModule extends BaseModule implements CommunityContract.ICommunityModule {
    private Context mContext;

    public CommunityModule(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void community(final CommunityContract.ICommunityPresenter iCommunityPresenter) {
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

    @Override
    public void unitNumber(String commid, final CommunityContract.IUnitNumberPresenter iUnitNumberPresenter) {
        RetrofitClient.getInstance(mContext).createBaseApi().postUnit(commid, new BaseSubscriber<List<UnitNumber>>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                iUnitNumberPresenter.onUnitNumberError(e.Message);
            }

            @Override
            public void onNext(List<UnitNumber> unitNumberList) {
                super.onNext(unitNumberList);
                iUnitNumberPresenter.getUnitNumberResult(unitNumberList);
            }
        });
    }
}
