package com.rain.zhihui_community.ui.activity.control;

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
import com.rain.zhihui_community.ui.adapter.ControlServiceAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 消防知识
 */
public class TipsActivity extends BaseAppAvtivity {

    @BindView(R.id.lv_community)
    ListView mListView;
    private ControlServiceAdapter adapter;
    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    @OnItemClick(R.id.lv_community)
    void community(int position) {
        BannerItem item = (BannerItem) adapter.getItem(position);
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("type", 3);
        intent.putExtra("id", item.getId());
        intent.putExtra("title", item.getTitle());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "消防知识");
        AppManager.getAppManager().addActivity(this);
        adapter = new ControlServiceAdapter(this);
        mListView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        RetrofitClient.getInstance(this).createBaseApi().postControNews(1, new BaseSubscriber<List<BannerItem>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(TipsActivity.this, e.Message);
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
