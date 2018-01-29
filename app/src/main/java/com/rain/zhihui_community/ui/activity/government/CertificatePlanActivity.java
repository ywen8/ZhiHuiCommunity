package com.rain.zhihui_community.ui.activity.government;

import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;

public class CertificatePlanActivity extends BaseAppAvtivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_plan);
        ButterKnife.bind(this);
        TitleUtls.TitleItemView(this, "办证进度查询");
        AppManager.getAppManager().addActivity(this);
    }
}