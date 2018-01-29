package com.rain.zhihui_community.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rain.zhihui_community.ui.fragment.login.LoginFragment;
import com.rain.zhihui_community.ui.fragment.register.RegisterFragment;

/**
 * author : Rain
 * time : 2017/10/16 0016
 * explain :
 */

public class LoginViewAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"登录", "注册"};
    private Fragment[] fragments;

    public LoginViewAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments[position] == null){
            switch (position){
                case 0:
                    fragments[position] = LoginFragment.newInstance();
                    break;
                case 1:
                    fragments[position] = RegisterFragment.newInstance();
                    break;

            }
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
