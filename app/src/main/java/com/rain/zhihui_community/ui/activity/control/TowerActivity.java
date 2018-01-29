package com.rain.zhihui_community.ui.activity.control;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.TowerData;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.ImageUtils;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TowerActivity extends BaseAppAvtivity {


    @BindView(R.id.tv_data)
    TextView tv_data;
    @BindView(R.id.iv_image1)
    ImageView iv_image1;
    @BindView(R.id.iv_image2)
    ImageView iv_image2;
    @BindView(R.id.iv_image3)
    ImageView iv_image3;
    @BindView(R.id.iv_image4)
    ImageView iv_image4;
    @BindView(R.id.iv_image5)
    ImageView iv_image5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        String title = getIntent().getStringExtra("title");
        String id = getIntent().getStringExtra("id");
        TitleUtls.TitleItemView(this, title);
        initData(id);
    }

    private void initData(String id) {

        RetrofitClient.getInstance(this).createBaseApi().postTower(id, new BaseSubscriber<TowerData>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(TowerActivity.this, e.Message);
            }

            @Override
            public void onNext(TowerData towerData) {
                super.onNext(towerData);
                tv_data.setText(towerData.getDetails());
                ImageUtils.image(TowerActivity.this, BaseApiService.BASE_URL + towerData.getImga(), iv_image1);
                ImageUtils.image(TowerActivity.this, BaseApiService.BASE_URL + towerData.getImgb(), iv_image2);
                ImageUtils.image(TowerActivity.this, BaseApiService.BASE_URL + towerData.getImgc(), iv_image3);
                ImageUtils.image(TowerActivity.this, BaseApiService.BASE_URL + towerData.getImgd(), iv_image4);
                ImageUtils.image(TowerActivity.this, BaseApiService.BASE_URL + towerData.getImge(), iv_image5);
            }
        });

    }
}
