package com.rain.zhihui_community.ui.activity.lethouse;

import com.rain.zhihui_community.base.BasePresenter;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.MyHouse;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/24 0024
 * explain :
 */

public class LetPresenter extends BasePresenter<LetModule, LetHouseActivity> implements LetContract.ICommunityPresenter, LetContract.IHousePersenter {

    public void community() {
        view.showProgress();
        module.community(this);
    }

    public void house() {
        view.showProgress();
        module.house(view.getCommid(), this);
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
    public void getCommunityResult(List<Community> communityList) {
        view.hideProgress();
        view.onCommunityResult(communityList);
    }

    @Override
    public void onCommunityError(String request) {
//        view.onCommunityResult(null);
        view.showToast(request);
        view.hideProgress();
    }
}
