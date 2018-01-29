package com.rain.zhihui_community.ui.activity.tenement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.BannerItem;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.activity.WebActivity;
import com.rain.zhihui_community.ui.adapter.CommunityServiceAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 社区服务
 */
public class CommunityServiceActivity extends BaseAppAvtivity {

    private CommunityServiceAdapter adapter;

    @BindView(R.id.lv_community)
    ListView mListView;

    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    @OnItemClick(R.id.lv_community)
    void item(int position) {
        BannerItem item = (BannerItem) adapter.getItem(position);
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("type", 2);
        intent.putExtra("id", item.getId());
        intent.putExtra("title", item.getTitle());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_service);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "社区服务");
        adapter = new CommunityServiceAdapter(this);
        mListView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        loading.showLoading();
        RetrofitClient.getInstance(this).createBaseApi().communityNews(new BaseSubscriber<List<BannerItem>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
//                WinToast.toast(CommunityServiceActivity.this, e.Message);
                loading.dismissLoading();
                rl_none.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<BannerItem> bannerItems) {
                super.onNext(bannerItems);
                rl_none.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                adapter.setList(bannerItems);
                loading.dismissLoading();
            }
        });

    }
}
