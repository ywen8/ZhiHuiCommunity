package com.rain.zhihui_community.ui.activity.house;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseActivity;
import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.MyHouse;
import com.rain.zhihui_community.ui.adapter.HouseAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HouseActivity extends BaseActivity<HouseContract.IHouseView, HousePresenter> implements HouseContract.IHouseView {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    HouseAdapter adapter;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "房源管理");
        persons = GloData.getPersons().getUser();

        presenter.house();
    }

    @Override
    public void showNullLayout() {
        super.showNullLayout();
        rl_none.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideNullLayout() {
        super.hideNullLayout();
        rl_none.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected HousePresenter initPresenter() {
        return new HousePresenter();
    }

    @Override
    protected BaseModule initModule() {
        return new HouseModule(this);
    }

    @Override
    public String getUid() {
        return persons.getId();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void onHouseResult(List<MyHouse> request) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HouseAdapter(this, request);
        mRecyclerView.setAdapter(adapter);
        adapter.setmListener(new HouseAdapter.MyDeleteClickListener() {
            @Override
            public void onItemClick(View view, MyHouse myHouse, final int position) {
                id = myHouse.getId();
                DialogUtil.deleteDialog(HouseActivity.this, "确定删除所发布的小区信息吗?");
                DialogUtil.getTv_sure().setText("删除");
                DialogUtil.getTv_cancle().setText("取消");
                DialogUtil.getTv_sure().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.delete();
                        adapter.removeItem(position);
                        DialogUtil.dismissDialog();
                    }
                });
            }
        });
    }

    @Override
    public void onDeleteHouseResult(String result) {
        if (result.contains("1000")) {
            WinToast.toast(this, "删除成功");
            presenter.house();
        } else {
            WinToast.toast(this, "删除失败");
            presenter.house();
        }
    }
}
