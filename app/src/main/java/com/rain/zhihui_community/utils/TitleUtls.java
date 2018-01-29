package com.rain.zhihui_community.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rain.zhihui_community.R;

/**
 * author : Rain
 * time : 2017/10/17 0017
 * explain :
 */

public class TitleUtls {

    public static Toolbar toolbar;
    public static TextView txtview;
    public static RelativeLayout rl_back, rl_add_housing;
    public static boolean isShow = false;

    /**
     * @param activity
     * @param title
     */
    public static void TitleView(final AppCompatActivity activity, String title) {
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        txtview = (TextView) activity.findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        txtview.setText(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
    /**
     * @param activity
     * @param title
     */
    public static void TitleItemView(final AppCompatActivity activity, String title) {
        rl_back = (RelativeLayout) activity.findViewById(R.id.rl_back);
        txtview = (TextView) activity.findViewById(R.id.tl_toolbar_title);
        rl_add_housing = (RelativeLayout) activity.findViewById(R.id.rl_add_housing);
        if (isShow) rl_add_housing.setVisibility(View.VISIBLE);
        txtview.setText(title);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    public static void TitleTextView(final AppCompatActivity activity, String title) {
        rl_back = (RelativeLayout) activity.findViewById(R.id.rl_back);
        txtview = (TextView) activity.findViewById(R.id.tl_toolbar_title);
        rl_add_housing = (RelativeLayout) activity.findViewById(R.id.rl_add_housing);
        txtview.setText(title);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    public static void setIsShow(boolean isShow) {
        TitleUtls.isShow = isShow;
    }
}
