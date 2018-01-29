package com.rain.zhihui_community.ui.activity;

import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;

public class AboutActivity extends BaseAppAvtivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "关于我们");
    }
}
