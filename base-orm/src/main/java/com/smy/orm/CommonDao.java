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
 * Created by smy on 2018/5/8.
 *
 * @see SimpleQuery
 */
public interface CommonDao {

    <T> T save(T t);

    void save(Collection list);

    <T> T update(T t);

    void update(Collection list);

    int update(Class c, Map<String, ?> setMap, SimpleQuery where);

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

    int delete(Class c, SimpleQuery where);

    <T> T find(Class<T> c, Serializable id);

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

    <T> List<T> list(Class<T> c, SimpleQuery where, Sort sort, Integer start, Integer size);

    default <T> List<T> list(Class<T> c, SimpleQuery where, Sort sort) {
        return list(c, where, sort, null, null);
    }

    default <T> List<T> list(Class<T> c, SimpleQuery where) {
        return list(c, where, Sort.unsorted());
    }

    default <T> List<T> list(Class<T> c) {
        return list(c, (b, r) -> null);
    }

    <T> int count(Class<T> c, SimpleQuery where);

    default <T> Page<T> page(Class<T> c, SimpleQuery where, Pageable pageable) {
        return new PageImpl<>(
                list(c, where, pageable.getSort(), (int) pageable.getOffset(), pageable.getPageSize()),
                pageable,
                count(c, where));
    }
}
