package com.rain.zhihui_community.ui.activity.traffic;

import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrafficActivity extends BaseAppAvtivity {

    @OnClick(R.id.ll_transact_dispost)
    void dispost() {
        startActiviys(TrafficDispostActivity.class);
    }

    @OnClick(R.id.ll_transact_compensate)
    void compensate() {
        startActiviys(TrafficCompensateActivity.class);
    }

    @OnClick(R.id.ll_transact_inspect)
    void inspect() {

    }

    @OnClick(R.id.ll_transact_newcar)
    void newcar() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "交警服务");
    }
}
