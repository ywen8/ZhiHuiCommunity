package com.rain.zhihui_community.ui.activity.lethouse;

import com.rain.zhihui_community.base.BaseContract;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.MyHouse;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/24 0024
 * explain :
 */

public class LetContract {

    public interface ILetView extends BaseContract.IBaseView {
        //获取小区房子信息
        String getCommid();

        //返回当前所有小区
        void onCommunityResult(List<Community> communityList);

        //返回当前小区的房源
        void onHouseResult(List<MyHouse> request);
    }

    public interface IHousePersenter extends BaseContract.IBasePresenter {
        void getResult(List<MyHouse> request);

        void onError(String request);
    }

    public interface ICommunityPresenter extends BaseContract.IBasePresenter {
        void getCommunityResult(List<Community> communityList);

        void onCommunityError(String request);
    }

    public interface ILetModule extends BaseContract.IBaseModule {
        void house(String Commid, IHousePersenter iHousePersenter);

        void community(ICommunityPresenter iCommunityPresenter);
    }
}
