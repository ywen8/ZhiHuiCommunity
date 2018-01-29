package com.rain.zhihui_community.ui.activity.group_chat;

import android.content.Context;
import android.util.Log;

import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.db.ChatDbManager;
import com.rain.zhihui_community.entity.GroupChatDB;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Rain on 2017/10/26.
 */

public class ChatModule extends BaseModule implements ChatContract.IChatMoudle {

    private Context mContext;


    public ChatModule(Context mContext) {
        this.mContext = mContext;
        mChatDbManager = new ChatDbManager();
    }

    @Override
    public void send(Map<String, String> map, final ChatContract.IChatSendPresenter iChatSendPresenter) {
        RetrofitClient.getInstance(mContext).createBaseApi().send(map, new BaseSubscriber<ResponseBody>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                iChatSendPresenter.onSendError(e.Message);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                iChatSendPresenter.getSendResult(responseBody);
            }
        });

    }

    @Override
    public void receive(Map<String, String> map, final ChatContract.IChatReceivePresenter iChatReceivePresenter) {

        RetrofitClient.getInstance(mContext).createBaseApi().receive(map, new BaseSubscriber<List<GroupChatDB>>(mContext) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                iChatReceivePresenter.onReceiveError(e.Message);
            }

            @Override
            public void onNext(List<GroupChatDB> chatList) {
                super.onNext(chatList);
                iChatReceivePresenter.getReceiveResult(chatList);
                boolean isAdd = mChatDbManager.insertList(chatList);
            }
        });
    }

    public void clear() {
        mChatDbManager.clearDaoSession();
    }
}
