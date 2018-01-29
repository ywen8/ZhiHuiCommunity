package com.rain.zhihui_community.ui.activity.life;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.TMessage;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.adapter.TenementAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物业
 *
 * @author : Rain
 * @data : 2017/10/25 0025
 * @Description :
 */
public class TenementActivity extends BaseAppAvtivity {

    @OnClick(R.id.ll_tenement)
    void tenement() {
        startActiviys(RepairsActivity.class);
    }

    private TenementAdapter adapter;

    @OnClick(R.id.tv_life_money)
    void life() {
        WinToast.toast(this, "暂未开通");
    }

    @BindView(R.id.lv_community)
    ListView listview;
    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenement);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "物业服务");
        persons = GloData.getPersons().getUser();
        initData();
    }

    private void initData() {

        RetrofitClient.getInstance(this).createBaseApi().messageData(3, persons.getId(), new BaseSubscriber<List<TMessage>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
//                WinToast.toast(TenementActivity.this, e.Message);
                rl_none.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<TMessage> tMessages) {
                super.onNext(tMessages);
                rl_none.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                adapter = new TenementAdapter(TenementActivity.this, tMessages);
                listview.setAdapter(adapter);
            }
        });
    }
}
