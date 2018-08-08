package com.smy.orm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 通用Dao。
 *
 * @author smy
 */
public interface CommonDao {
    /**
     * 保存。
     * 如果ID数据库自生成，保存时ID需为null，返回值有数据库ID。
     *
     * @param t   对象
     * @param <T> 对象泛型约束
     * @return 当前对象
     */
    <T> T save(T t);

    /**
     * 批量保存。
     *
     * @param list 对象集合
     * @see #save(Object)
     */
    void save(Collection list);

    /**
     * 更新。
     * ID存在，其他字段覆盖原始数据。
     * ID为null且ID数据库生成，保存数据。
     *
     * @param t   对象
     * @param <T> 对象泛型约束
     * @return 当前对象
     */
    <T> T update(T t);

    /**
     * 批量保存。
     *
     * @param list 对象集合
     * @see #update(Object)
     */
    void update(Collection list);

    /**
     * 条件更新。
     *
     * @param c      对象类型
     * @param setMap 更新值
     * @param where  条件
     * @return 成功更新条数
     */
    int update(Class c, Map<String, ?> setMap, SimpleQuery where);

    /**
     * 删除。根据ID删除对象。
     *
     * @param t 对象
     */
    void delete(Object t);

    /**
     * 批量删除
     *
     * @param list 对象集合
     */
    void delete(Collection list);

    /**
     * 删除。
     *
     * @param c  对象类型
     * @param id 对象ID
     */
    default void delete(Class c, Serializable id) {
        Object o = find(c, id);
        if (o != null) {
            delete(o);
        }
    }

    /**
     * 批量删除。
     *
     * @param c   对象类型
     * @param ids 对象ID集合
     */
    default void delete(Class c, Collection<? extends Serializable> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        ids.forEach(id -> delete(c, id));
    }

    /**
     * 条件删除。
     *
     * @param c     对象类型
     * @param where 条件
     * @return 成功删除条数
     */
    int delete(Class c, SimpleQuery where);

    /**
     * 查询对象。
     *
     * @param c   对象类型
     * @param id  对象ID
     * @param <T> 对象泛型约束
     * @return 对象
     */
    <T> T find(Class<T> c, Serializable id);

    /**
     * 条件获取。
     *
     * @param c     对象类型
     * @param where 条件
     * @param <T>   泛型约束
     * @return 对象
     */
    default <T> T find(Class<T> c, SimpleQuery where) {
        List<T> list = list(c, where, Sort.unsorted(), null, null);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() == 0) {
            return null;
        } else {
            throw new RuntimeException(list.size() + " result for find one");
        }
    }

    /**
     * 查询对象列表。
     *
     * @param c     对象类型
     * @param where 查询条件
     * @param sort  排序条件
     * @param start 开始位置
     * @param size  查询数量
     * @param <T>   对象泛型约束
     * @return 对象列表
     */
    <T> List<T> list(Class<T> c, SimpleQuery where, Sort sort, Integer start, Integer size);

    /**
     * 查询对象列表。
     *
     * @param c     对象类型
     * @param where 查询条件
     * @param sort  排序条件
     * @param <T>   对象泛型约束
     * @return 对象列表
     */
    default <T> List<T> list(Class<T> c, SimpleQuery where, Sort sort) {
        return list(c, where, sort, null, null);
    }

    /**
     * 查询对象列表。
     *
     * @param c     对象类型
     * @param where 查询条件
     * @param <T>   对象泛型约束
     * @return 对象列表
     */
    default <T> List<T> list(Class<T> c, SimpleQuery where) {
        return list(c, where, Sort.unsorted());
    }

    /**
     * 查询对象列表。
     *
     * @param c   对象类型
     * @param <T> 对象泛型约束
     * @return 对象列表
     */
    default <T> List<T> list(Class<T> c) {
        return list(c, new WhereBuilder());
    }

    /**
     * 简单计数。
     *
     * @param c     对象类型
     * @param where 条件
     * @return 数量
     */
    int count(Class c, SimpleQuery where);

    /**
     * 分页查询
     *
     * @param c        对象类型
     * @param where    条件
     * @param pageable spring-jpa分页参数
     * @param <T>      泛型约束
     * @return spring-jpa分页返回值
     */
    default <T> Page<T> page(Class<T> c, SimpleQuery where, Pageable pageable) {
        return new PageImpl<>(
                list(c, where, pageable.getSort(), (int) pageable.getOffset(), pageable.getPageSize()),
                pageable,
                count(c, where));
    }


}
