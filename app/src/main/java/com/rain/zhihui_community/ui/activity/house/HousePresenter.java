package com.rain.zhihui_community.ui.activity.house;

import com.rain.zhihui_community.base.BasePresenter;
import com.rain.zhihui_community.entity.MyHouse;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/23 0023
 * explain :
 */

public class HousePresenter extends BasePresenter<HouseModule, HouseActivity> implements HouseContract.IHousePersenter, HouseContract.IDeleteHousePersenter {


    public void house() {
        view.showProgress();
        module.house(view.getUid(), this);
    }

    public void delete() {
        view.showProgress();
        module.delete(view.getId(), this);
    }


    @Override
    public void getResult(List<MyHouse> request) {
        view.hideProgress();
        view.hideNullLayout();
        view.onHouseResult(request);
    }

    @Override
    public void onError(String request) {
        view.hideProgress();
        view.showNullLayout();
        view.showToast(request);
    }

    @Override
    public void getDeleteResult(String request) {
        view.hideProgress();
        view.onDeleteHouseResult(request);
    }

    @Override
    public void onDeleteError(String request) {
        view.hideProgress();
        view.showToast(request);
    }
}
