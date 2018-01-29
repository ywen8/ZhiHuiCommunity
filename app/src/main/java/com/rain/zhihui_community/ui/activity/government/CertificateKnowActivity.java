package com.rain.zhihui_community.ui.activity.government;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.ui.adapter.CertificatesAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CertificateKnowActivity extends BaseAppAvtivity {


    public String[] groupStrings = {"护照", "往来港澳通行证", "往来台湾通行证", "台湾居民", "外国人"};
    public String[][] childStrings = {
            {"护照"},
            {"往来港澳通行证（仅申请证件）", "往来港澳通及签注（商务）", "往来港澳通及签注（探亲）", "往来港澳通及签注（逗留）", "往来港澳通及签注（团队旅游）",},
            {"大陆居民往来台湾通行证（仅申请证件）", "大陆居民往来台湾通行证及签注（定居）", "大陆居民往来台湾通行证及签注（探亲）", "大陆居民往来台湾通行证及签注（商务）", "大陆居民往来台湾通行证及签注（学习）", "大陆居民往来台湾通行证及签注（团队旅游）", "大陆居民往来台湾通行证及签注（应邀赴台）"},
            {"证件"},
            {"签证", "居留证件", "停留证件"}
    };
    @BindView(R.id.expand_list)
    ExpandableListView expandableListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certifcate_know);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.TitleItemView(this, "办证须知");
        expandableListView.setAdapter(new CertificatesAdapter(this, groupStrings, childStrings));
        //        设置分组项的点击监听事件
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                return false;
            }
        });
    }
}
