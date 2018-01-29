package com.rain.zhihui_community.ui.activity.village;

import com.rain.zhihui_community.base.BasePresenter;
import com.rain.zhihui_community.entity.MyCommunity;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/19 0019
 * explain :
 */

public class HousingPresenter extends BasePresenter<HousingModule, HousingActivity> implements HousingContract.IHousingPresenter, HousingContract.IDeletePresenter {


    public void housing() {
        view.showProgress();
        module.getHousing(view.getUid(), this);
    }

    public void delete() {
        view.showProgress();
        module.delete(view.getid(), this);
    }

    @Override
    public void getResult(List<MyCommunity> communityList) {
        view.hideProgress();
        view.hideNullLayout();
        view.onHousingResult(communityList);
    }

    @Override
    public void onError(String request) {
        view.hideProgress();
        view.showNullLayout();
        view.showToast(request);
    }

    @Override
    public void getDeleteResult(String result) {
        view.hideProgress();
        view.onDeleteResult(result);
    }

    @Override
    public void onDeleteError(String request) {
        view.hideProgress();
        view.showToast(request);
    }
}
