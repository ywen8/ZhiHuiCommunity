package com.rain.zhihui_community.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.rain.zhihui_community.entity.GroupChatDB;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/**
 * Created by Rain on 2017/10/26.
 */

public class ChatMessageBeanDao extends AbstractDao<GroupChatDB, Long> {

    public static final String TABLENAME = "ZHIHUI_SHEQU_CHAT";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property uid = new Property(1, String.class, "uid", false, "uid");
        public final static Property username = new Property(2, String.class, "username", false, "username");
        public final static Property headimg = new Property(3, String.class, "headimg", false, "headimg");
        public final static Property message = new Property(4, String.class, "messages", false, "messages");
        public final static Property createtime = new Property(5, String.class, "createtime", false, "createtime");
        public final static Property sex = new Property(6, String.class, "sex", false, "sex");
    }

    public ChatMessageBeanDao(DaoConfig config) {
        super(config);
    }

    public ChatMessageBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "ZHIHUI_SHEQU_CHAT (_id INTEGER PRIMARY KEY , uid TEXT, username TEXT ,headimg TEXT , messages TEXT, createtime TEXT , sex TEXT );");
    }

    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ZHIHUI_SHEQU_CHAT\"";
        db.execSQL(sql);
    }


    @Override
    protected GroupChatDB readEntity(Cursor cursor, int offset) {
        GroupChatDB groupChatDB = new GroupChatDB(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0),
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1),
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2),
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3),
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4),
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5),
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        return groupChatDB;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return !cursor.isNull(offset + 0) ? cursor.getLong(offset + 0) : null;
    }

    @Override
    protected void readEntity(Cursor cursor, GroupChatDB entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUsername(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setHeadimg(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMessage(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCreatetime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSex(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));

    }

    @Override
    protected void bindValues(DatabaseStatement stmt, GroupChatDB entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(2, uid);
        }
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(3, username);
        }
        String headimg = entity.getHeadimg();
        if (headimg != null) {
            stmt.bindString(4, headimg);
        }
        String messages = entity.getMessage();
        if (messages != null) {
            stmt.bindString(5, messages);
        }
        String createtime = entity.getCreatetime();
        if (createtime != null) {
            stmt.bindString(6, createtime);
        }
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }

    }

    @Override
    protected void bindValues(SQLiteStatement stmt, GroupChatDB entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(2, uid);
        }
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(3, username);
        }
        String headimg = entity.getHeadimg();
        if (headimg != null) {
            stmt.bindString(4, headimg);
        }
        String messages = entity.getMessage();
        if (messages != null) {
            stmt.bindString(5, messages);
        }
        String createtime = entity.getCreatetime();
        if (createtime != null) {
            stmt.bindString(6, createtime);
        }
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
    }

    @Override
    protected Long updateKeyAfterInsert(GroupChatDB entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    protected Long getKey(GroupChatDB entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected boolean hasKey(GroupChatDB entity) {
        return entity.getId() != null;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}
