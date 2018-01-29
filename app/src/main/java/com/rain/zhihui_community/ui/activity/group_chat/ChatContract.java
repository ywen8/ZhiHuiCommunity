package com.rain.zhihui_community.ui.activity.group_chat;

import com.rain.zhihui_community.base.BaseContract;
import com.rain.zhihui_community.entity.GroupChatDB;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Rain on 2017/10/26.
 */

public class ChatContract {

    public interface IChatView extends BaseContract.IBaseView {

        Map<String, String> getSendmap();

        void onSendResult(ResponseBody responseBody);


        Map<String, String> getReceivemap();

        void onReceiveResult(List<GroupChatDB> chatList);
    }

    public interface IChatSendPresenter extends BaseContract.IBasePresenter {

        void getSendResult(ResponseBody responseBody);

        void onSendError(String error);
    }

    public interface IChatReceivePresenter extends BaseContract.IBasePresenter {
        void getReceiveResult(List<GroupChatDB> chatList);

        void onReceiveError(String error);
    }

    public interface IChatMoudle extends BaseContract.IBaseModule {
        void send(Map<String, String> map, IChatSendPresenter iChatSendPresenter);

        void receive(Map<String, String> map, IChatReceivePresenter iChatReceivePresenter);
    }
}
