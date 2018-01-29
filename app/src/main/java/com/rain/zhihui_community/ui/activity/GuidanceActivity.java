package com.rain.zhihui_community.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.ui.activity.login.LoginActivity;
import com.rain.zhihui_community.ui.adapter.SamplePagerAdapter;
import com.rain.zhihui_community.ui.view.CircleIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuidanceActivity extends BaseAppAvtivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
        ButterKnife.bind(this);
        viewpager.setAdapter(new SamplePagerAdapter());
        indicator.setViewPager(viewpager);

        SamplePagerAdapter.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    startActiviys(LoginActivity.class);
                    finish();
                }
            }
        };
    }
}
