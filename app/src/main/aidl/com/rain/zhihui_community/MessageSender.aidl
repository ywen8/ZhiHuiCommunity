// MessageSender.aidl
package com.rain.zhihui_community;
import com.rain.zhihui_community.entity.GroupChatDB;
import com.rain.zhihui_community.MessageReceiver;
// Declare any non-default types here with import statements

interface MessageSender {

        void sendMessage(in GroupChatDB groupChatDB);

        void registerReceiveListener(MessageReceiver messageReceiver);

        void unregisterReceiveListener(MessageReceiver messageReceiver);
}
