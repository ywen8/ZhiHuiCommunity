package com.rain.zhihui_community.ui.activity.government;

import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公安
 */
public class SecurityActivity extends BaseAppAvtivity {

    @OnClick(R.id.ll_transact)
    void transact() {
        startActiviys(ExitTransactActivity.class);
    }

    @OnClick(R.id.tv_offer_money)
    void offer() {
        startActiviys(OfferRewardActivity.class);
    }

    @OnClick(R.id.tv_idcard)
    void card() {
        startActiviys(CardIDActivity.class);
    }

    @OnClick(R.id.tv_complete)
    void complete() {
        startActiviys(GovernmentComplaintActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "公安服务");
        AppManager.getAppManager().addActivity(this);
    }
}
