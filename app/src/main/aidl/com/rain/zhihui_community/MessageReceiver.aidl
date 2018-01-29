// MessageReceiver.aidl
package com.rain.zhihui_community;
import com.rain.zhihui_community.entity.GroupChatDB;

// Declare any non-default types here with import statements

interface MessageReceiver {
    void onMessageReceived(in GroupChatDB groupChatDB);
}
