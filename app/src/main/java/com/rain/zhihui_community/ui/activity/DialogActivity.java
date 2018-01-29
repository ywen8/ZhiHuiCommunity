package com.rain.zhihui_community.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.ui.activity.login.LoginActivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;

public class DialogActivity extends BaseAppAvtivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        DialogUtil.dialog(DialogActivity.this, "您的账号在别处登录!如有疑问请联系客服!");
        DialogUtil.getTv_cancle().setText("返回登录");
        DialogUtil.getTv_cancle().setTextColor(getResources().getColor(R.color.dialog_text_press));
        DialogUtil.getTv_cancle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActiviys(LoginActivity.class);
                SharedPreferencesUtil.putString(DialogActivity.this, "usersData", "usersData", "");
                AppManager.getAppManager().AppExit(DialogActivity.this);
                DialogUtil.dismissDialog();
            }
        });
    }
}
