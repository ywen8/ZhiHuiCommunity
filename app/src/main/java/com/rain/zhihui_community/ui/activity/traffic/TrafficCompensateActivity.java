package com.rain.zhihui_community.ui.activity.traffic;

import android.content.Intent;
import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.ui.activity.WebActivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrafficCompensateActivity extends BaseAppAvtivity {

    @OnClick(R.id.ll_traffic_payfor)
    void payfor() {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("id", "0");
        intent.putExtra("title", "事故快处快赔");
        intent.putExtra("type", 4);
        startActivity(intent);
    }

    @OnClick(R.id.ll_traffic_payfor_help)
    void help() {
        startActiviys(TrafficHelpActivity.class);
    }

    @OnClick(R.id.ll_traffic_payfor_query)
    void query() {
        WinToast.toast(this, "暂未开通,敬请期待");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_compensate);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.TitleItemView(this, "事故快处快赔");
    }
}
