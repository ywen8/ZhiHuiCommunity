package com.rain.zhihui_community.ui.activity.group_chat;

import com.rain.zhihui_community.base.BasePresenter;
import com.rain.zhihui_community.entity.GroupChatDB;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Rain on 2017/10/26.
 */

public class ChatPresenter extends BasePresenter<ChatModule, GroupChatActivity> implements ChatContract.IChatSendPresenter, ChatContract.IChatReceivePresenter {

    public void send() {
        module.send(view.getSendmap(), this);
    }

    public void receive() {
        module.receive(view.getReceivemap(), this);
    }


    @Override
    public void getSendResult(ResponseBody responseBody) {
        view.onSendResult(responseBody);
    }

    @Override
    public void onSendError(String error) {
        view.showToast(error);
    }

    @Override
    public void getReceiveResult(List<GroupChatDB> chatList) {
        view.onReceiveResult(chatList);
    }

    @Override
    public void onReceiveError(String error) {
        view.showToast(error);
    }
}
