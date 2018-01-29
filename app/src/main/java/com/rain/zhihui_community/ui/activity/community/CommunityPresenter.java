package com.rain.zhihui_community.ui.activity.community;

import com.rain.zhihui_community.base.BasePresenter;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.UnitNumber;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class CommunityPresenter extends BasePresenter<CommunityModule, CommunityActivity> implements CommunityContract.ICommunityPresenter, CommunityContract.IUnitNumberPresenter {

    public void community() {
        view.showProgress();
        module.community(this);
    }

    public void getUnit() {
        view.showProgress();
        module.unitNumber(view.getCommid(), this);
    }

    @Override
    public void getUnitNumberResult(List<UnitNumber> unitNumberList) {
        view.onUnitNumberResult(unitNumberList);
        view.hideProgress();
    }

    @Override
    public void onUnitNumberError(String request) {
        view.hideProgress();
        view.showToast(request);
    }

    @Override
    public void getCommunityResult(List<Community> communityList) {
        view.hideProgress();
        view.onCommunityResult(communityList);
    }

    @Override
    public void onCommunityError(String request) {
        view.hideProgress();
        view.showToast(request);
    }
}
