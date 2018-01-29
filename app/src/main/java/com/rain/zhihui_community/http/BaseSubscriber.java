package com.rain.zhihui_community.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by Rain on 2017/6/13 0013.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;
    private boolean isNeedCahe;
    private String TAG = "================";

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "onCompleted: "+"http is Complete");
        // todo some common as  dismiss loadding
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e.getMessage());
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.RESCODE_EXCEPTION));
        }

    }

    @Override
    public void onNext(T t) {
        Log.i(TAG, "onCompleted: "+"http is onNext"+t.toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onCompleted: "+"http is start");
        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onCompleted();
        }
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable e);
}
