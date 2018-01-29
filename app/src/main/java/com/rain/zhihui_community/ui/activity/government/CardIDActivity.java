package com.rain.zhihui_community.ui.activity.government;

import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;

public class CardIDActivity extends BaseAppAvtivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_id);
        ButterKnife.bind(this);
        TitleUtls.TitleItemView(this, "身份证查询");
        AppManager.getAppManager().addActivity(this);
    }
}
