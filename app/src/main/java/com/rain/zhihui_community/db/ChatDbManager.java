package com.rain.zhihui_community.db;

import com.rain.zhihui_community.entity.GroupChatDB;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Rain on 2017/10/26.
 */

public class ChatDbManager extends BaseManager<GroupChatDB, Long> {


    @Override
    public AbstractDao<GroupChatDB, Long> getAbstractDao() {
        return daoSession.getChatMessageBeanDao();
    }
}
