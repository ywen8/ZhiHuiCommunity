package com.rain.zhihui_community.ui.view.pulltorefresh.base;

import android.content.Context;
import android.view.View;

import com.rain.zhihui_community.ui.view.pulltorefresh.PullToRefreshListView;
import com.rain.zhihui_community.ui.view.pulltorefresh.PullToRefreshRecyclerView;


/**
 * Created by Mao Jiqing on 2016/10/10.
 */
public class PullToRefreshView extends View {

    public static final int LISTVIEW = 0;
    public static final int RECYCLERVIEW = 1;

    public PullToRefreshView(Context context) {
        super(context);
    }

    public View getSlideView(int slideViewType) {
        View baseView = null;
        switch (slideViewType) {
            case LISTVIEW:
                baseView = new PullToRefreshListView(getContext());
                break;
            case RECYCLERVIEW:
                baseView = new PullToRefreshRecyclerView(getContext());
                break;
            default:
                baseView = null;
                break;
        }
        return baseView;
    }
}
