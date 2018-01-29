package com.rain.zhihui_community.services;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.rain.zhihui_community.MessageReceiver;
import com.rain.zhihui_community.MessageSender;
import com.rain.zhihui_community.entity.GroupChatDB;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageService extends Service {

    private static final String TAG = "MESSAGE_SERVICE";
    //RemoteCallbackList专门用来管理多进程回调接口
    private RemoteCallbackList<MessageReceiver> listenerList = new RemoteCallbackList<>();
    private AtomicBoolean serviceStop = new AtomicBoolean(false);
    private Map<String, String> receiveMap;

    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (checkCallingOrSelfPermission("com.rain.zhihui_community.permission.REMOTE_SERVICE_PERMISSION") == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return messageSender;
    }

    IBinder messageSender = new MessageSender.Stub() {

        @Override
        public void sendMessage(GroupChatDB groupChatDB) throws RemoteException {
            Log.i(TAG, "sendMessage: " + groupChatDB.toString());
        }

        @Override
        public void registerReceiveListener(MessageReceiver messageReceiver) throws RemoteException {
            listenerList.register(messageReceiver);
        }

        @Override
        public void unregisterReceiveListener(MessageReceiver messageReceiver) throws RemoteException {
            listenerList.unregister(messageReceiver);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            /**
             * 包名验证方式
             */
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (packageName == null || !packageName.startsWith("com.rain.zhihui_community")) {
                Log.d("onTransact", "拒绝调用：" + packageName);
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        receiveMap = (Map<String, String>) intent.getSerializableExtra("map");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new FakeTCPTask()).start();

    }

    //模拟长连接，通知客户端有新消息到达
    class FakeTCPTask implements Runnable {

        @Override
        public void run() {
            while (!serviceStop.get()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RetrofitClient.getInstance(MessageService.this).createBaseApi().receive(receiveMap, new BaseSubscriber<List<GroupChatDB>>(MessageService.this) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                    }

                    @Override
                    public void onNext(List<GroupChatDB> groupChatDBs) {
                        super.onNext(groupChatDBs);
                        Log.i(TAG, "onNext: " + groupChatDBs.toString());
                        receiveMap.put("time", groupChatDBs.get(0).getCreatetime());
                        for (GroupChatDB groupChatDB :
                                groupChatDBs) {
                            /**
                             * RemoteCallbackList的遍历方式
                             * beginBroadcast和finishBroadcast一定要配对使用
                             */
                            final int listenerCount = listenerList.beginBroadcast();
                            Log.d(TAG, "listenerCount == " + listenerCount);
                            for (int i = 0; i < listenerCount; i++) {
                                MessageReceiver messageReceiver = listenerList.getBroadcastItem(i);
                                if (messageReceiver != null) {
                                    try {
                                        messageReceiver.onMessageReceived(groupChatDB);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            listenerList.finishBroadcast();
                        }

                    }
                });
            }
        }
    }

    @Override
    public void onDestroy() {
        serviceStop.set(true);
        super.onDestroy();
    }
}
