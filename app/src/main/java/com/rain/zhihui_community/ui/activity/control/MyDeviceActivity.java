package com.rain.zhihui_community.ui.activity.control;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.ui.adapter.DeviceAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDeviceActivity extends BaseAppAvtivity {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private DeviceAdapter adapter;
    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    @OnClick(R.id.rl_add_housing)
    public void add() {
        WinToast.toast(this, "===");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(true);
        TitleUtls.TitleItemView(this, "我的设备");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DeviceAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }
}
