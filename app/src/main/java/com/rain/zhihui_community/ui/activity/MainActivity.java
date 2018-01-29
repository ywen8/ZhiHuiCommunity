package com.rain.zhihui_community.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.TabEntity;
import com.rain.zhihui_community.ui.fragment.MyFragment;
import com.rain.zhihui_community.ui.fragment.home.HomeFragment;
import com.rain.zhihui_community.ui.fragment.message.MessageFragment;
import com.rain.zhihui_community.ui.fragment.neighbor.NeighborFragment;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DeviceidUtil;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends BaseAppAvtivity {

    /**
     * title文字部分
     */
    private String[] mTitles = {"首页", "消息", "邻友", "我的"};
    /**
     * 未选中图标数组
     */
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_my_unselect};
    /**
     * 选中图标数组
     */
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_my_select};
    /**
     * fragment集合
     */
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    /**
     * 图标信息对象
     */
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @BindView(R.id.yzs_base_tabLayout)
    CommonTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(MessageFragment.newInstance());
        mFragments.add(NeighborFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }


        mTabLayout.setTabData(mTabEntities, this, R.id.content, mFragments);

        mTabLayout.setCurrentTab(0);
        //设置未读消息红点
//        mTabLayout.showDot(1);
        //设置未读消息背景
//        mTabLayout.showMsg(0, 5);
        //设置自定义颜色的msg
        mTabLayout.setMsgMargin(3, 0, 3);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                Log.i(TAG, "onTabSelect: " + position);
                if (position == 0) {
                    mTabLayout.hideMsg(0);
                }
            }

            @Override
            public void onTabReselect(int position) {
//                Log.i(TAG, "onTabReselect: "+position);
                if (position == 0) {
                    mTabLayout.hideMsg(0);
                }
            }
        });

        JPushInterface.setAlias(this, DeviceidUtil.deviceid(this),
                new TagAliasCallback() {

                    @Override
                    public void gotResult(int responseCode,
                                          String alias, Set<String> tags) {
                        if (responseCode == 0) {
                            System.out.println("MyReceiver=============别名设置成功" + DeviceidUtil.deviceid(MainActivity.this));
                        }
                    }
                });
    }

}
