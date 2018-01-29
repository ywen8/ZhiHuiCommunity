package com.rain.zhihui_community.ui.activity.traffic;

import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrafficDispostActivity extends BaseAppAvtivity {

    @OnClick(R.id.ll_traffic_violate_dispose)
    void dispose() {
        startActiviys(TrafficViolateActivity.class);
    }

    @OnClick(R.id.ll_traffic_violate_query)
    void query() {
        startActiviys(TrafficMotorVehiclesActivity.class);
    }

    @OnClick(R.id.ll_traffic_violate_pay)
    void pay() {

    }

    @OnClick(R.id.ll_traffic_person_pay)
    void person_pay() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_dispost);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.TitleItemView(this, "违法处理");
    }
}
