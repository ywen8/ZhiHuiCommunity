package com.rain.zhihui_community.ui.view.menuview.Interface;

import android.view.View;

import java.util.List;

/**
 * @author Rain
 * @date 2017/6/1
 */
public interface IDynamicSore<T> {
    void setGridView(View view, int type, List<T> data);
}
