package com.rain.zhihui_community.ui.activity.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.TMessage;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.adapter.MessageAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends BaseAppAvtivity {

    private int type;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private MessageAdapter adapter;
    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(false);
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            TitleUtls.TitleItemView(this, "系统消息");
        } else if (type == 2) {
            TitleUtls.TitleItemView(this, "政务消息");
        } else if (type == 3) {
            TitleUtls.TitleItemView(this, "社区消息");
        }
        persons = GloData.getPersons().getUser();

        initData();
    }

    private void initData() {
        loading.showLoading();
        RetrofitClient.getInstance(this).createBaseApi().messageData(type, persons.getId(), new BaseSubscriber<List<TMessage>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
//                WinToast.toast(MessageActivity.this, e.Message);
                rl_none.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                loading.dismissLoading();
            }

            @Override
            public void onNext(List<TMessage> tMessages) {
                super.onNext(tMessages);
                rl_none.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                adapter = new MessageAdapter(MessageActivity.this, tMessages);
                mRecyclerView.setAdapter(adapter);
                loading.dismissLoading();
            }
        });
    }
}
