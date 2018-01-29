package com.rain.zhihui_community.ui.activity.community;

import com.rain.zhihui_community.base.BaseContract;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.UnitNumber;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class CommunityContract {

    public interface ICommunityView extends BaseContract.IBaseView {

        String getCommid();

        void onCommunityResult(List<Community> communityList);

        void onUnitNumberResult(List<UnitNumber> unitNumberList);
    }

    public interface ICommunityModule extends BaseContract.IBaseModule {

        void community(ICommunityPresenter iCommunityPresenter);

        void unitNumber(String commid, IUnitNumberPresenter iUnitNumberPresenter);

    }

    public interface IUnitNumberPresenter extends BaseContract.IBasePresenter {
        void getUnitNumberResult(List<UnitNumber> unitNumberList);

        void onUnitNumberError(String request);
    }


    public interface ICommunityPresenter extends BaseContract.IBasePresenter {
        void getCommunityResult(List<Community> communityList);

        void onCommunityError(String request);
    }
}
