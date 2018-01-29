package com.rain.zhihui_community.ui.activity.house;

import com.rain.zhihui_community.base.BaseContract;
import com.rain.zhihui_community.entity.MyHouse;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/23 0023
 * explain :
 */

public class HouseContract {


    public interface IHouseView extends BaseContract.IBaseView {

        String getUid();

        String getId();

        void onHouseResult(List<MyHouse> request);

        void onDeleteHouseResult(String request);
    }

    public interface IHousePersenter extends BaseContract.IBasePresenter {
        void getResult(List<MyHouse> request);
        void onError(String request);

    }

    public interface IDeleteHousePersenter extends BaseContract.IBasePresenter {
        void getDeleteResult(String request);

        void onDeleteError(String request);
    }

    public interface IHouseModule extends BaseContract.IBaseModule {
        void house(String uid, IHousePersenter iHousePersenter);

        void delete(String id, IDeleteHousePersenter iDeleteHousePersenter);

    }
}
