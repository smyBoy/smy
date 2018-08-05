package com.smy.orm;

import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by smy on 2018/5/8.
 */
public interface CommonDao {

    <T> T save(T t);

    void save(Collection list);

    <T> T update(T t);

    void update(Collection list);

    void delete(Object t);

    void delete(Collection list);

    default void delete(Class c, Serializable id) {
        Object o = find(c, id);
        if (o != null) {
            delete(o);
        }
    }

    default void delete(Class c, Collection<? extends Serializable> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        ids.forEach(id -> delete(c, id));
    }

    <T> T find(Class<T> c, Serializable id);

    <T> List<T> list(Class<T> c, WhereBuilder where, Sort sort, Integer start, Integer size);

    default <T> List<T> list(Class<T> c, WhereBuilder where, Sort sort) {
        return list(c, where, sort, null, null);
    }

    default <T> List<T> list(Class<T> c, WhereBuilder where) {
        return list(c, where, Sort.unsorted());
    }

    default <T> List<T> list(Class<T> c) {
        return list(c, new WhereBuilder());
    }


}
