package com.rain.zhihui_community.ui.activity.traffic;

import android.content.Intent;
import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.ui.activity.WebActivity;
import com.rain.zhihui_community.ui.activity.government.CertificateWinActivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrafficHelpActivity extends BaseAppAvtivity {


    @OnClick(R.id.ll_traffic_payfor_scope)
    void scope() {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("id", "0");
        intent.putExtra("title", "事故快处快赔");
        intent.putExtra("type", 4);
        startActivity(intent);
    }

    @OnClick(R.id.ll_traffic_insurance)
    void insurance() {
        startActiviys(CertificateWinActivity.class, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_help);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.TitleItemView(this, "快处快赔帮助");
    }
}
