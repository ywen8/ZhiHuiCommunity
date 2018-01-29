package com.rain.zhihui_community.ui.activity.lethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseActivity;
import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.MyHouse;
import com.rain.zhihui_community.ui.activity.community.CommunityActivity;
import com.rain.zhihui_community.ui.activity.lethouse.issue.IssueHouseActivity;
import com.rain.zhihui_community.ui.adapter.HouseAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LetHouseActivity extends BaseActivity<LetContract.ILetView, LetPresenter> implements LetContract.ILetView {

    @BindView(R.id.tv_lethouse_name)
    TextView tv_house_name;
    @BindView(R.id.tv_right_title)
    TextView right_title;
    @BindView(R.id.rl_add_housing)
    RelativeLayout rl_issue;
    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private HouseAdapter adapter;
    private String commid;
    private Community community;

    @OnClick(R.id.rl_add_housing)
    void releaseHousing() {
        startActiviys(IssueHouseActivity.class, 2);
    }

    @OnClick(R.id.ll_houing_select)
    void select_housing() {
        startActiviys(CommunityActivity.class, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_let_house);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.TitleTextView(this, "出租屋");
        right_title.setText("发布");
        myHousing = GloData.getPersons().getMiddleueDTOS();
        if (null == myHousing || myHousing.size() == 0) {
            rl_issue.setEnabled(false);
            right_title.setTextColor(getResources().getColor(R.color.wathet));
        } else {
            rl_issue.setEnabled(true);
            right_title.setTextColor(getResources().getColor(R.color.white));
        }
        presenter.community();
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
    protected LetPresenter initPresenter() {
        return new LetPresenter();
    }

    @Override
    protected BaseModule initModule() {
        return new LetModule(this);
    }

    @Override
    public String getCommid() {
        return commid;
    }

    @Override
    public void onCommunityResult(List<Community> communityList) {
        commid = communityList.get(0).getCommid();
        tv_house_name.setText(communityList.get(0).getCommname());
        presenter.house();
    }

    @Override
    public void onHouseResult(List<MyHouse> request) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HouseAdapter(this, request);
        adapter.setDelete(false);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    community = (Community) data.getSerializableExtra("community");
                    tv_house_name.setText(community.getCommname());
                    commid = community.getCommid();
                    presenter.house();
                }
                break;
            case 2:
                if (data != null) {
                    int type = data.getIntExtra("issue", 0);
                    if (type == 2)
                        presenter.house();
                }
                break;
        }
    }
}
