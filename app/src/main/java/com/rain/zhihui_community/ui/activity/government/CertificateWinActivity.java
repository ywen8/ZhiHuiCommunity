package com.rain.zhihui_community.ui.activity.government;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.ui.adapter.CertificateAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CertificateWinActivity extends BaseAppAvtivity {

    @BindView(R.id.lv_certificate)
    ListView mListView;
    @BindView(R.id.tv_text)
    TextView tv_text;
    private CertificateAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_win);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        int type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            tv_text.setVisibility(View.GONE);
            TitleUtls.TitleItemView(this, "快处快赔服务点");
        } else {
            tv_text.setVisibility(View.VISIBLE);
            TitleUtls.TitleItemView(this, "办证窗口");
        }
        adapter = new CertificateAdapter(this, type);
        mListView.setAdapter(adapter);
    }
}
