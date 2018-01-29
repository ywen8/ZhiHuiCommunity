package com.rain.zhihui_community.ui.activity.login;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.ui.adapter.LoginViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseAppAvtivity {

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LoginViewAdapter adapter = new LoginViewAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adapter);
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
        mSlidingTabLayout.setIndicatorColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
