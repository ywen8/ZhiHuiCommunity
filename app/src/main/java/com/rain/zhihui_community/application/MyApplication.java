package com.rain.zhihui_community.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.rain.zhihui_community.db.BaseManager;
import com.rain.zhihui_community.utils.TimeUtils;
import com.rain.zhihui_community.utils.crash.Cockroach;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Rain on 2017/10/26.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (getProcessName().equals("com.rain.zhihui_community")) {
            Log.i("process name", getProcessName());
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        BaseManager.initOpenHelper(this);
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Cockroach", thread + "\n" + throwable.toString());
                        throwable.printStackTrace();
//                        send(MyApplication.this, thread + "\n" + throwable.toString());
                    }
                });
            }
        });


    }

    //取得进程名
    private String getProcessName() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == Process.myPid()) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public void send(Context context, String debug) {
        int time = TimeUtils.getYear() + TimeUtils.getMonth() + TimeUtils.getDay() + TimeUtils.getHour() + TimeUtils.getMinute();
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:2181266134@qq.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, "日志提交(" + time + ")");
        data.putExtra(Intent.EXTRA_TEXT, debug);
        context.startActivity(data);
    }
}
