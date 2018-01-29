package com.rain.zhihui_community.ui.activity.government;

import android.os.Bundle;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExitTransactActivity extends BaseAppAvtivity {


    @OnClick(R.id.ll_government_notice)
    void notice() {
        startActiviys(CertificateKnowActivity.class);
    }

    @OnClick(R.id.ll_government_subscribe)
    void subscribe() {
        startActiviys(CertificateNoticeActivity.class);
    }

    @OnClick(R.id.ll_government_cancel)
    void cancel() {
        startActiviys(BusinessCancelActivity.class);
    }

    @OnClick(R.id.ll_government_plan)
    void plan() {
        startActiviys(CertificatePlanActivity.class);
    }

    @OnClick(R.id.ll_government_win)
    void win() {
        startActiviys(CertificateWinActivity.class);
    }

    @OnClick(R.id.ll_government_consult)
    void consult() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_transact);
        ButterKnife.bind(this);
        TitleUtls.TitleItemView(this, "出入境办理");
        AppManager.getAppManager().addActivity(this);
    }
}
