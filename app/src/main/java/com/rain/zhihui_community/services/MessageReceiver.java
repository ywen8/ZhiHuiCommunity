package com.rain.zhihui_community.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rain.zhihui_community.ui.activity.DialogActivity;
import com.rain.zhihui_community.ui.activity.wallet.RedPacketActivity;
import com.rain.zhihui_community.utils.Logger;
import com.rain.zhihui_community.utils.rxBus.RxBus;

import cn.jpush.android.api.JPushInterface;

public class MessageReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";
    public static Handler mHandler ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d(TAG, "JPush用户注册成功");
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Logger.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的通知");
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.d(TAG, "用户点击打开了通知");
        } else {
            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void processCustomMessage(final Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        final String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (message.equals("您的账号在别处登录!如有疑问请联系客服!")) {
            context.startActivity(new Intent(context, DialogActivity.class));
        } else if (message.equals("您有一个红包可以领取")) {
            context.startActivity(new Intent(context, RedPacketActivity.class));
            RxBus.getInstance().post("1");
        }
    }
}
