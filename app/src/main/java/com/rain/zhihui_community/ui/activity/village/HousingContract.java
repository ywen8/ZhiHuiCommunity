package com.rain.zhihui_community.ui.activity.village;

import com.rain.zhihui_community.base.BaseContract;
import com.rain.zhihui_community.entity.MyCommunity;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/19 0019
 * explain :
 */

public class HousingContract {

    public interface IHousingView extends BaseContract.IBaseView {

        String getUid();

        String getid();

        void onHousingResult(List<MyCommunity> communityList);

        void onDeleteResult(String result);

    }

    public interface IHousingPresenter extends BaseContract.IBasePresenter {
        void getResult(List<MyCommunity> communityList);

        void onError(String request);
    }

    public interface IDeletePresenter extends BaseContract.IBasePresenter {
        void getDeleteResult(String result);

        void onDeleteError(String request);
    }

    public interface IHousingModule extends BaseContract.IBaseModule {
        void getHousing(String uid, IHousingPresenter iHousingPresenter);

        void delete(String id, IDeletePresenter iDeletePresenter);
    }

}
