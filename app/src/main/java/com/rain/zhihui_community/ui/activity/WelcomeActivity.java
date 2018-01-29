package com.rain.zhihui_community.ui.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.google.gson.Gson;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.Persons;
import com.rain.zhihui_community.entity.UpDataApk;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.CallBack;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.activity.login.LoginActivity;
import com.rain.zhihui_community.utils.DeviceidUtil;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.WinToast;

import java.io.File;

import butterknife.ButterKnife;

public class WelcomeActivity extends BaseAppAvtivity {

    Handler handler = new Handler();
    private boolean isOne = false;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private long preProgress = 0;
    private int NOTIFY_ID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        updataApp();
    }

    private void goApp() {
        isOne = SharedPreferencesUtil.getBoolean(this, "isOne", false);
        final String userData = SharedPreferencesUtil.getString(this, "usersData", "usersData", "");
        Runnable updateThread = new Runnable() {
            @Override
            public void run() {
                if (isOne) {
                    if (userData != null && !userData.equals("")) {
                        Gson gson = new Gson();
                        Persons userDatas = gson.fromJson(userData, Persons.class);
                        GloData.setPersons(userDatas);
                        startActiviys(MainActivity.class);
                        finish();
                    } else {
                        startActiviys(LoginActivity.class);
                        finish();
                    }
                    finish();
                } else {
                    SharedPreferencesUtil.putBoolean(WelcomeActivity.this, "isOne", true);
                    startActiviys(GuidanceActivity.class);
                    finish();
                }
            }
        };
        handler.postDelayed(updateThread, 500);
    }

    /**
     * 检测APP是否要更新
     */
    private void updataApp() {
        final String versionName = DeviceidUtil.getAppVersionName(this);
        RetrofitClient.getInstance(this).createBaseApi().updata(new BaseSubscriber<UpDataApk>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (e.State == 1008) {
                    WinToast.makeText(WelcomeActivity.this, "获取版本号失败");
                }
            }

            @Override
            public void onNext(final UpDataApk upDataApk) {
                super.onNext(upDataApk);
                String version = upDataApk.getVnumber();
                if (versionName.equals(version)) {
                    goApp();
                } else {
                    DialogUtil.upDataDialog(WelcomeActivity.this, upDataApk.getVnumber(), versionName, upDataApk.getVdetails());
                    DialogUtil.getTv_sure().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtil.dismissDialog();
                            upApp(upDataApk);
                        }
                    });

                }
            }
        });
    }

    /**
     * 版本不一样开始更新
     *
     * @param upDataApk
     */
    private void upApp(final UpDataApk upDataApk) {

        RetrofitClient.getInstance(this).createBaseApi().download(upDataApk.getDownload(), new CallBack() {
            @Override
            public void onStart() {
                super.onStart();
                initNotification();
//                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {
//                progressbar.setVisibility(View.GONE);
                cancelNotification();
            }

            @SuppressLint("NewApi")
            @Override
            public void onProgress(long fileSizeDownloaded) {
                super.onProgress(fileSizeDownloaded);
                updateNotification(fileSizeDownloaded);
            }

            @Override
            public void onSucess(String path, String name, long fileSize) {
                cancelNotification();
                File file = new File(path);
                installApk(file);
            }
        });
    }

    /**
     * 安装软件
     *
     * @param file
     */
    private void installApk(File file) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(this, "com.rain.zhihui_community.fileprovider", file);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Uri uri = Uri.fromFile(file);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        // 执行意图进行安装
        startActivity(install);
    }

    /**
     * 初始化Notification通知
     */
    public void initNotification() {
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.logo)// 设置通知的图标
                .setContentText("0%")// 进度Text
                .setContentTitle("智慧平安社区")// 标题
                .setProgress(100, 0, false);// 设置进度条
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);// 获取系统通知管理器
        notificationManager.notify(NOTIFY_ID, builder.build());// 发送通知
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, (int) progress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }

}
