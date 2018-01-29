package com.rain.zhihui_community.ui.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppFragment;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.ui.activity.AboutActivity;
import com.rain.zhihui_community.ui.activity.house.HouseActivity;
import com.rain.zhihui_community.ui.activity.information.InformationActivity;
import com.rain.zhihui_community.ui.activity.settting.SettingActivity;
import com.rain.zhihui_community.ui.activity.village.HousingActivity;
import com.rain.zhihui_community.ui.activity.wallet.WalletActivity;
import com.rain.zhihui_community.utils.ImageUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class MyFragment extends BaseAppFragment {

    @BindView(R.id.iv_photo)
    ImageView iv_photo;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.iv_sex)
    ImageView iv_sex;
    @BindView(R.id.title_text)
    TextView tv_title;

    @OnClick(R.id.iv_photo)
    public void information() {
        startActiviys(InformationActivity.class);
    }

    @OnClick(R.id.ll_village)
    public void village() {
        startActiviys(HousingActivity.class);
    }

    @OnClick(R.id.ll_house)
    public void house() {
        startActiviys(HouseActivity.class);
    }

    @OnClick(R.id.ll_about)
    public void about() {
        startActiviys(AboutActivity.class);
    }

    @OnClick(R.id.ll_setting)
    public void setting() {
        startActiviys(SettingActivity.class);
    }

    @OnClick(R.id.ll_my_bround)
    void bround() {
        startActiviys(WalletActivity.class);
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View parentView) {

    }

    @Override
    public void finishCreateView(Bundle bundle) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!isVisible || !isPrepared) {
            return;
        }
        isPrepared = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        tv_title.setText("我的");
        persons = GloData.getPersons();
        tv_username.setText(persons.getUser().getUsername());
        if (persons.getUser().getSex() != null) {
            if (persons.getUser().getSex().equals("0")) {
                iv_sex.setImageResource(R.mipmap.iv_my_boy);
            } else if (persons.getUser().getSex().equals("1")) {
                iv_sex.setImageResource(R.mipmap.iv_my_girl);
            }
        }
        if (persons.getUser().getHeadimg() != null) {
            Log.i(TAG, "initData: " + BaseApiService.BASE_URL + persons.getUser().getHeadimg());
            ImageUtils.imageCircle(getActivity(), BaseApiService.BASE_URL + persons.getUser().getHeadimg(), iv_photo);
        }
    }
}
