package com.rain.zhihui_community.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.entity.Persons;
import com.rain.zhihui_community.utils.DialogLoading;

import java.util.List;


/**
 * author : Rain
 * time : 2017/10/17 0017
 * explain :
 */

public class BaseAppAvtivity extends AppCompatActivity {
    public static final String TAG = "Rain=====";
    public Persons.UserBean persons;//个人信息
    public List<MyCommunity> myHousing;//我的小区的集合
    public DialogLoading loading = new DialogLoading(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void startActiviys(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public void startActiviys(Class c, int type) {
        Intent intent = new Intent(this, c);
        intent.putExtra("type", type);
        startActivityForResult(intent, type);
    }
}
