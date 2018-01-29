package com.rain.zhihui_community.ui.activity.village;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseActivity;
import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.ui.activity.village.addhousing.AddHousingActivity;
import com.rain.zhihui_community.ui.adapter.HousingAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.type;

public class HousingActivity extends BaseActivity<HousingContract.IHousingView, HousingPresenter> implements HousingContract.IHousingView {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private HousingAdapter adapter;
    @BindView(R.id.rl_none)
    RelativeLayout rl_none;
    private String uid;

    private boolean select_housing = false;
    private boolean save_housing = false;
    private MyCommunity myCommunity;

    @OnClick(R.id.rl_add_housing)
    public void add() {
        startActiviys(AddHousingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        select_housing = getIntent().getBooleanExtra("select_housing", false);
        save_housing = getIntent().getBooleanExtra("save_housing", false);
        if (select_housing)
            TitleUtls.setIsShow(false);
        else
            TitleUtls.setIsShow(true);
        TitleUtls.TitleItemView(this, "我的社区");
        persons = GloData.getPersons().getUser();
        myHousing = GloData.getPersons().getMiddleueDTOS();
//        if (null != myHousing) {
//            Log.i(TAG, "onHousingResult: 检测本地是否有小区" + myHousing.toString());
//        }
        initData();
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
    protected void onRestart() {
        super.onRestart();
        initData();
        myHousing = GloData.getPersons().getMiddleueDTOS();
//        if (null != myHousing) {
//            Log.i(TAG, "onHousingResult: 检测本地是否有小区" + myHousing.toString());
//        }
    }

    @Override
    protected HousingPresenter initPresenter() {
        return new HousingPresenter();
    }

    @Override
    protected BaseModule initModule() {
        return new HousingModule(this);
    }

    private void initData() {
        presenter.housing();
    }

    @Override
    public String getUid() {
        return persons.getId();
    }

    @Override
    public String getid() {
        return uid;
    }

    @Override
    public void onHousingResult(final List<MyCommunity> communityList) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HousingAdapter(this, communityList, select_housing);
        mRecyclerView.setAdapter(adapter);
        adapter.setmListener(new HousingAdapter.MyDeleteClickListener() {
            @Override
            public void onItemClick(View view, final MyCommunity community, final int position) {
                uid = community.getId();
                DialogUtil.deleteDialog(HousingActivity.this, "确定删除该小区吗?");
                DialogUtil.getTv_cancle().setText("取消");
                DialogUtil.getTv_sure().setText("删除");
                DialogUtil.getTv_sure().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < myHousing.size(); i++) {
                            if (community.getId().equals(myHousing.get(i).getId())) {
                                myCommunity = myHousing.get(i);
                            }
                        }
                        if (myCommunity.isSelect()) {
                            WinToast.toast(HousingActivity.this, "当前小区为默认小区不能删除");
                            DialogUtil.dismissDialog();
                            return;
                        }
//                        Log.i(TAG, "onHousingResult: 删除小区后更改本地数据1" + myCommunity.toString());
                        myHousing.remove(myCommunity);
                        presenter.delete();
                        GloData.getPersons().setMiddleueDTOS(myHousing);
//                        Log.i(TAG, "onHousingResult: 删除小区后更改本地数据2" + myHousing.toString());
                        SharedPreferencesUtil.putString(HousingActivity.this, "usersData", "usersData", new Gson().toJson(GloData.getPersons()));
                        adapter.removeItem(position);
                        DialogUtil.dismissDialog();
                    }
                });
            }
        });
        adapter.setOnItemViewListener(new HousingAdapter.OnItemViewListener() {
            @Override
            public void OnItemListener(View view, MyCommunity myCommunity) {
                if (select_housing) {

                    if (save_housing) {
                        for (int i = 0; i < myHousing.size(); i++) {
                            MyCommunity com = myHousing.get(i);
                            if (myCommunity.getCid().equals(com.getCid())) {
                                com.setSelect(true);
                                GloData.getPersons().setMiddleueDTOS(myHousing);
//                                Log.i(TAG, "onHousingResult: 修改当前默认小区时存在本地的小区" + myHousing.toString());
                                SharedPreferencesUtil.putString(HousingActivity.this, "usersData", "usersData", new Gson().toJson(GloData.getPersons()));
                            } else {
                                com.setSelect(false);
                            }
                        }
                    }
                    Intent intent = new Intent();
                    intent.putExtra("myCommunity", myCommunity);
                    setResult(type, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onDeleteResult(String result) {
        if (result.contains("1000")) {
            WinToast.toast(this, "删除成功");
            Log.i(TAG, "onDeleteResult:删除之后的本地小区 " + myHousing.toString());
            presenter.housing();
        } else {
            WinToast.toast(this, "删除失败");
            presenter.housing();
        }
    }
}
