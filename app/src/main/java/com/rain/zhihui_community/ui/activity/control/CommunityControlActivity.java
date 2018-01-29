package com.rain.zhihui_community.ui.activity.control;

import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommunityControlActivity extends BaseAppAvtivity {


    @OnClick(R.id.ll_community_build)
    void build() {
        startActiviys(ControlBuildActivity.class);
    }

    @OnClick(R.id.ll_community_survey)
    void survey() {
        startActiviys(CommunityBasicsActivity.class, 1);
    }

    @OnClick(R.id.ll_fire_control)
    void fire_control() {
        startActiviys(CommunityBasicsActivity.class, 2);
    }

    @OnClick(R.id.ll_fire_control_person)
    void fire_person() {
        startActiviys(ControlPersonActivity.class);
    }

    @OnClick(R.id.ll_fire_control_pact)
    void pact() {
        startActiviys(CommunityBasicsActivity.class, 3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_control);
        ButterKnife.bind(this);
        TitleUtls.TitleItemView(this, "社区消防情况");
        AppManager.getAppManager().addActivity(this);
    }
}
