package com.rain.zhihui_community.ui.activity.village.addhousing;

import com.rain.zhihui_community.base.BaseContract;
import com.rain.zhihui_community.entity.MyCommunity;

import java.util.Map;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class AddHousingContract {

    public interface IAdHousingView extends BaseContract.IBaseView {
        /**
         *
         * @return
         */
        Map<String, String> getMap();

        /**
         *
         * @param request
         */
        void onHousingResult(MyCommunity request);
    }
    public  interface IAddHousingPresenter extends BaseContract.IBasePresenter{
        void getResult(MyCommunity request);
        void onError(String request);
    }
    public interface IAddHousingModule extends BaseContract.IBaseModule{
        void  addHousing(Map<String,String> map , IAddHousingPresenter iAddHousingPresenter);
    }
}
