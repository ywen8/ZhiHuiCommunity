package com.rain.zhihui_community.ui.activity.settting;

import android.os.Bundle;
import android.view.View;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseAppAvtivity {


    @OnClick(R.id.btn_exit_login)
    public void exit() {
        //TODO
        DialogUtil.deleteDialog(this, "确定退出应用吗?");
        DialogUtil.getTv_sure().setText("确定");
        DialogUtil.getTv_cancle().setText("取消");
        DialogUtil.getTv_sure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtil.putString(SettingActivity.this, "usersData", "usersData", "");
                AppManager.getAppManager().AppExit(SettingActivity.this);
                DialogUtil.dismissDialog();
            }
        });
    }

    @OnClick(R.id.ll_setting_password)
    public void password() {
        startActiviys(PasswordActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "设置");
    }
}
