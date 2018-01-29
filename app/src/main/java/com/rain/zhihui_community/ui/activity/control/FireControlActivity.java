package com.rain.zhihui_community.ui.activity.control;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.BannerItem;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.activity.village.addhousing.AddHousingActivity;
import com.rain.zhihui_community.ui.adapter.ControlServiceAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消防
 */
public class FireControlActivity extends BaseAppAvtivity {


    private ControlServiceAdapter adapter;

    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    @OnClick(R.id.ll_community_control)
    void contorl() {
        startActiviys(CommunityControlActivity.class);
    }

    @OnClick(R.id.ll_community_complaint)
    void complaint() {
        if (myHousing == null && myHousing.size() == 0) {
            startActiviys(AddHousingActivity.class);
        } else {
            startActiviys(ComplaintActivity.class);
        }
    }

    @OnClick(R.id.ll_control_knowledge)
    void knowledge() {
        startActiviys(TipsActivity.class);
    }

    @OnClick(R.id.ll_control_zhihui)
    void zhihui() {
//        startActiviys(WisdomControlActivity.class);
        WinToast.toast(this, "暂未开通，敬请期待!");
    }

    @BindView(R.id.lv_community)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_control);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "消防服务");
        adapter = new ControlServiceAdapter(this);
        mListView.setAdapter(adapter);
        myHousing = GloData.getPersons().getMiddleueDTOS();
        initData();
    }

    private void initData() {
        RetrofitClient.getInstance(this).createBaseApi().postControNews(0, new BaseSubscriber<List<BannerItem>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
//                WinToast.toast(FireControlActivity.this, e.Message);
                rl_none.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<BannerItem> bannerItems) {
                super.onNext(bannerItems);
                rl_none.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                adapter.setList(bannerItems);
            }
        });
    }
}
