package com.rain.zhihui_community.db;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Rain on 2017/10/26.
 */

public interface IDatabase<M, K> {

    /**
     * @param m
     * @return
     */
    boolean insert(M m);

    /**
     * 删除
     *
     * @param key
     * @return
     */
    boolean deleteByKey(K key);
    boolean delete(M m);


    boolean deleteList(List<M> mList);

    boolean deleteByKeyInTx(K... key);

    boolean deleteAll();

    /**
     * 修改
     *
     * @param m
     * @return
     */
    boolean update(M m);

    /**
     * 修改list集合
     *
     * @param mList
     * @return
     */
    boolean updataList(List<M> mList);

    /**
     * 查询
     *
     * @param key
     * @return
     */
    M selectByPrimaryKey(K key);

    /**
     * 获取全部
     *
     * @return
     */
    List<M> loadAll();

    /**
     * 分页加载
     *
     * @param page   设定当前页数
     * @param number 设定一页显示数量
     * @return
     */
    List<M> loadPages(int page, int number);

    /**
     * 获取分页数
     *
     * @param number
     * @return
     */
    long getPages(int number);

    /**
     * 刷新
     *
     * @param m
     * @return
     */
    boolean refresh(M m);

    /**
     * 清理缓存
     */
    void clearDaoSession();

    /**
     * 事务
     *
     * @param runnable
     */
    void runInTx(Runnable runnable);


    /**
     * 添加集合
     *
     * @param mList
     * @return
     */
    boolean insertList(List<M> mList);

    /**
     * 添加集合
     *
     * @param mList
     * @return
     */
    boolean insertOrReplaceList(List<M> mList);

    /**
     * 自定义查询
     *
     * @return
     */
    QueryBuilder<M> getQueryBuilder();

    /**
     * @param where
     * @param selecttionArg
     * @return
     */
    List<M> queryRaw(String where, String... selecttionArg);

    /**
     * Delete all tables and content from our database
     */
    boolean dropDatabase();
}
