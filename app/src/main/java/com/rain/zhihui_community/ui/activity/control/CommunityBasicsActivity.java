package com.rain.zhihui_community.ui.activity.control;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.BuildData;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.activity.traffic.TrafficInfromActivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommunityBasicsActivity extends BaseAppAvtivity {

    private int type = 0;
    private MyCommunity myCommunity;

    @BindView(R.id.tv_data)
    TextView tv_data;
    @BindView(R.id.ll_traffic_violate_dispose)
    LinearLayout ll_traffic;
    private boolean isShow = false;
    @BindView(R.id.cb_select)
    CheckBox cb_select;

    @OnClick(R.id.cb_select)
    void select() {
        if (isShow) {
            isShow = false;
            cb_select.setChecked(false);
        } else {
            isShow = true;
            cb_select.setChecked(true);
        }
    }

    @OnClick(R.id.btn_violate_dispose)
    void dispose() {
        if (isShow)
            startActiviys(TrafficInfromActivity.class);
        else
            WinToast.toast(this, "请先同意使用须知");
    }

    @OnClick(R.id.btn_violate_record)
    void record() {
//        if(isShow)
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_basics);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        type = getIntent().getIntExtra("type", 0);
        myHousing = GloData.getPersons().getMiddleueDTOS();
        if (type == 1) {
            TitleUtls.TitleItemView(this, "社区概况");
            init();
        } else if (type == 2) {
            TitleUtls.TitleItemView(this, "社区微型消防站");
            init();
        } else if (type == 3) {
            TitleUtls.TitleItemView(this, "消防管理公约");
            init();
        } else if (type == 4) {
            ll_traffic.setVisibility(View.VISIBLE);
            TitleUtls.TitleItemView(this, "交通违法处理运行使用须知");
            tv_data.setText("     " + getResources().getString(R.string.traffic_plan));
        }
    }


    void init() {
        for (MyCommunity community :
                myHousing) {
            if (community.isSelect()) {
                myCommunity = community;
                Log.i(TAG, "initData: " + myCommunity.toString());
            }
        }
        initData();
    }

    private void initData() {
        RetrofitClient.getInstance(this).createBaseApi().postBuild(myCommunity.getCommid(), new BaseSubscriber<BuildData>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(CommunityBasicsActivity.this, e.Message);
            }

            @Override
            public void onNext(BuildData buildData) {
                super.onNext(buildData);
                if (type == 1) {
                    tv_data.setText(buildData.getCgeneralization());
                } else if (type == 2) {
                    tv_data.setText(buildData.getXfgeneralization());
                } else if (type == 3) {
                    tv_data.setText(buildData.getConvention());
                }
            }
        });
    }
}
