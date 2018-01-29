package com.rain.zhihui_community.ui.activity.government;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CertificateNoticeActivity extends BaseAppAvtivity {


    private boolean isShow = false;

    @BindView(R.id.cb_select)
    CheckBox cb_select;
    @BindView(R.id.tv_next)
    TextView tv_next;

    @OnClick(R.id.cb_select)
    void select() {
        if (isShow) {
            isShow = false;
            cb_select.setChecked(false);
            tv_next.setVisibility(View.GONE);
        } else {
            isShow = true;
            cb_select.setChecked(true);
            tv_next.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tv_next)
    void next() {
        startActiviys(SubscribeCertificateActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_notice);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.TitleItemView(this, "办证预约");
    }
}
