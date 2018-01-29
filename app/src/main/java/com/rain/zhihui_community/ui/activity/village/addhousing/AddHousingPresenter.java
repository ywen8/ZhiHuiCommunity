package com.rain.zhihui_community.ui.activity.village.addhousing;

import com.rain.zhihui_community.base.BasePresenter;
import com.rain.zhihui_community.entity.MyCommunity;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class AddHousingPresenter extends BasePresenter<AddHousingModule, AddHousingActivity> implements AddHousingContract.IAddHousingPresenter {

    public void addHousing() {
        view.showProgress();
        module.addHousing(view.getMap(), this);
    }


    @Override
    public void getResult(MyCommunity request) {
        view.hideProgress();
        view.onHousingResult(request);
    }

    @Override
    public void onError(String request) {
        view.hideProgress();
        view.showToast(request);
    }
}
