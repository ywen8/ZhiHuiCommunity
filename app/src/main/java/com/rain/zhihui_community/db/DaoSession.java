package com.rain.zhihui_community.db;

import com.rain.zhihui_community.entity.GroupChatDB;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

/**
 * Created by Rain on 2017/10/26.
 */

public class DaoSession extends AbstractDaoSession {
    private final DaoConfig chatMessageBeanDaoConfig;

    private final ChatMessageBeanDao chatMessageBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);
        chatMessageBeanDaoConfig = daoConfigMap.get(ChatMessageBeanDao.class).clone();
        chatMessageBeanDaoConfig.initIdentityScope(type);

        chatMessageBeanDao = new ChatMessageBeanDao(chatMessageBeanDaoConfig, this);

        registerDao(GroupChatDB.class, chatMessageBeanDao);
    }
    public void clear() {
        chatMessageBeanDaoConfig.clearIdentityScope();
    }

    public ChatMessageBeanDao getChatMessageBeanDao() {
        return chatMessageBeanDao;
    }
}
