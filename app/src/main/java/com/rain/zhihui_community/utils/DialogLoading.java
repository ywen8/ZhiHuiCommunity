package com.rain.zhihui_community.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.ui.view.SmileyLoadingView;

/**
 * Created by Rain on 2017/11/2.
 */

public class DialogLoading {

    private Activity activity;
    private Dialog dialog;
    private SmileyLoadingView mSmileyLoadingView;

    public DialogLoading(Activity activity) {
        this.activity = activity;
    }

    public void showLoading() {
        if (dialog == null) {
            dialog = new Dialog(activity, R.style.MyDialog);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_layout_loading, null);
            mSmileyLoadingView = (SmileyLoadingView) view.findViewById(R.id.loading_view);
            mSmileyLoadingView.setOnAnimPerformCompletedListener(new SmileyLoadingView.OnAnimPerformCompletedListener() {
                @Override
                public void onCompleted() {
                    mSmileyLoadingView.setVisibility(View.INVISIBLE);
                }
            });
            mSmileyLoadingView.start();
            dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            dialog.show();
        }
    }
    public void dismissLoading(){
        mSmileyLoadingView.stop(false);
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
