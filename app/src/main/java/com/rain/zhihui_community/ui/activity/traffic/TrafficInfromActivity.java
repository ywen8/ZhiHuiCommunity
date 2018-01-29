package com.rain.zhihui_community.ui.activity.traffic;

import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;

public class TrafficInfromActivity extends BaseAppAvtivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_infrom);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.TitleItemView(this, "违法查缴");
    }
}
