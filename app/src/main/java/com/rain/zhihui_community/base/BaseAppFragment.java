package com.rain.zhihui_community.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.entity.Persons;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * author : Rain
 * time : 2017/10/17 0017
 * explain : 基于fragmentUI的基类
 */

public abstract class BaseAppFragment extends Fragment {
    private FragmentActivity activity;

    public Persons persons;
    public List<MyCommunity> myHousing;
    private View parentView;
    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;

    //标志位 fragment是否可见
    protected boolean isVisible;
    public Unbinder bind;
    public static final String TAG = "============";
    public abstract
    @LayoutRes
    int getLayoutResId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        initView(parentView);
        return parentView;
    }

    protected abstract void initView(View parentView);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    public abstract void finishCreateView(Bundle bundle);

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    public <T extends View> T $(int id) {
        return (T) parentView.findViewById(id);
    }

    public void startActiviys(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }

    public void startActiviys(Class c, int type) {
        Intent intent = new Intent(getActivity(), c);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
