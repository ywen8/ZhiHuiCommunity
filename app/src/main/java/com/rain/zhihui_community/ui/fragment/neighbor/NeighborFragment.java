package com.rain.zhihui_community.ui.fragment.neighbor;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppFragment;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.Neighbor;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.activity.group_chat.GroupChatActivity;
import com.rain.zhihui_community.ui.activity.village.addhousing.AddHousingActivity;
import com.rain.zhihui_community.ui.adapter.NeighborAdapter;
import com.rain.zhihui_community.utils.WinToast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class NeighborFragment extends BaseAppFragment {
    @BindView(R.id.title_text)
    TextView tv_title;
    @BindView(R.id.rl_none)
    RelativeLayout rl_none;
    private NeighborAdapter adapter;

    @BindView(R.id.lv_neighbor)
    ListView lv_neighbor;

    @OnItemClick(R.id.lv_neighbor)
    void neighbor(int position) {
        Neighbor neighbor = (Neighbor) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), GroupChatActivity.class);
        intent.putExtra("neighbor", neighbor);
        startActivity(intent);
    }

    @OnClick(R.id.tv_add_housing)
    void addhousing() {
        startActiviys(AddHousingActivity.class);
    }

    public static NeighborFragment newInstance() {
        return new NeighborFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_neighbor;
    }

    @Override
    protected void initView(View parentView) {

    }

    @Override
    public void finishCreateView(Bundle bundle) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!isVisible || !isPrepared) {
            return;
        }
        isPrepared = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        tv_title.setText("邻友");
        persons = GloData.getPersons();
        adapter = new NeighborAdapter(getActivity());
        lv_neighbor.setAdapter(adapter);
        RetrofitClient.getInstance(getActivity()).createBaseApi().neighbor(persons.getUser().getId(), new BaseSubscriber<List<Neighbor>>(getActivity()) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
//                WinToast.toast(getActivity(), e.Message);
                lv_neighbor.setVisibility(View.GONE);
                rl_none.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(List<Neighbor> neighbors) {
                super.onNext(neighbors);
                lv_neighbor.setVisibility(View.VISIBLE);
                rl_none.setVisibility(View.GONE);
                adapter.setList(neighbors);
            }
        });
    }
}
