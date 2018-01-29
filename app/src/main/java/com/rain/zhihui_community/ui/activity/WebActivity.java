package com.rain.zhihui_community.ui.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseAppAvtivity {

    private String id, title;
    private int type;

    @BindView(R.id.wb_web)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(false);
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", 0);
        TitleUtls.TitleItemView(this, title);
        initData();
    }

    private void initData() {
        if (type == 1) {
            webView.loadUrl(BaseApiService.BASE_URL + "/html5/news.html?id=" + id);
        } else if (type == 2) {
            webView.loadUrl(BaseApiService.BASE_URL + "/html5/communitynews.html?id=" + id);
        } else if (type == 3) {
            webView.loadUrl(BaseApiService.BASE_URL + "/html5/xfnotice.html?id=" + id);
        } else if (type == 4) {
            webView.loadUrl(BaseApiService.BASE_URL + "/jiaojing/index.html");
        }
        WebSettings setting = webView.getSettings();
        setting.setBuiltInZoomControls(false);
        setting.setUseWideViewPort(false);
        setting.setJavaScriptEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }
}
