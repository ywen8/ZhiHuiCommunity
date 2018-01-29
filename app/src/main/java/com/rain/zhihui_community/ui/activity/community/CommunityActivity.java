package com.rain.zhihui_community.ui.activity.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseActivity;
import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.UnitNumber;
import com.rain.zhihui_community.ui.adapter.CommAdapter;
import com.rain.zhihui_community.ui.adapter.UnitAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


public class CommunityActivity extends BaseActivity<CommunityContract.ICommunityView, CommunityPresenter> implements CommunityContract.ICommunityView {

    @BindView(R.id.lv_community)
    ListView mListView;

    private int type = 1;
    private CommAdapter commAdapter;
    private UnitAdapter unitAdapter;
    private String commid;


    @OnItemClick(R.id.lv_community)
    public void itemClick(int position) {
        if (type == 1) {
            Community community = (Community) commAdapter.getItem(position);
            Intent intent = new Intent();
            intent.putExtra("community", community);
            setResult(type, intent);
            finish();
        } else if (type == 2) {
            UnitNumber unitNumber = (UnitNumber) unitAdapter.getItem(position);
            Intent intent = new Intent();
            intent.putExtra("unitNumber", unitNumber);
            setResult(type, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        type = getIntent().getIntExtra("type", 1);
        if (type == 2) {
            TitleUtls.TitleItemView(this, "选择单元门");
            commid = getIntent().getStringExtra("commid");
        } else if (type == 1) {
            TitleUtls.TitleItemView(this, "选择小区");
        }
        initData();
    }

    private void initData() {
        if (type == 1) {
            presenter.community();
        } else if (type == 2) {
            presenter.getUnit();
        }
    }

    @Override
    protected CommunityPresenter initPresenter() {
        return new CommunityPresenter();
    }

    @Override
    protected BaseModule initModule() {
        return new CommunityModule(this);
    }

    @Override
    public String getCommid() {
        return commid;
    }

    @Override
    public void onCommunityResult(List<Community> communityList) {
        Log.i(TAG, "onCommunityResult: " + communityList.toString());
        commAdapter = new CommAdapter(this, communityList);
        mListView.setAdapter(commAdapter);
    }

    @Override
    public void onUnitNumberResult(List<UnitNumber> unitNumberList) {
        Log.i(TAG, "onUnitNumberResult: " + unitNumberList.toString());
        unitAdapter = new UnitAdapter(this, unitNumberList);
        mListView.setAdapter(unitAdapter);
    }
}
