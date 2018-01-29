package com.rain.zhihui_community.ui.fragment.message;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppFragment;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.ui.activity.message.MessageActivity;
import com.rain.zhihui_community.utils.WinToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class MessageFragment extends BaseAppFragment {
    @BindView(R.id.title_text)
    TextView tv_title;

    @OnClick(R.id.ll_system)
    void system() {
        startActiviys(MessageActivity.class, 1);
    }

    @OnClick(R.id.ll_affairs)
    void affairs() {
        startActiviys(MessageActivity.class, 2);
    }

    @OnClick(R.id.ll_community)
    void community() {
        startActiviys(MessageActivity.class, 3);
    }

    @OnClick(R.id.ll_control_message)
    void device(){
        WinToast.toast(getActivity(), "暂未开通，敬请期待!");
//        startActiviys(ControlMessageActivity.class);
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
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
        tv_title.setText("消息");
        persons = GloData.getPersons();

    }
}
