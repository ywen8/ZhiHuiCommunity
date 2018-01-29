package com.rain.zhihui_community.ui.activity.control;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.ui.adapter.ControlMessageAdapter;
import com.rain.zhihui_community.ui.adapter.MessageAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ControlMessageActivity extends BaseAppAvtivity {


    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private MessageAdapter adapter;
    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_message);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "消防消息");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ControlMessageAdapter adapter = new ControlMessageAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }
}
