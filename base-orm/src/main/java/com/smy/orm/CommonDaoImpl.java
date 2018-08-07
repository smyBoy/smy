package com.smy.orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 通用Dao实现类。
 *
 * @author smy
 */
@Repository("commonDao")
public class CommonDaoImpl implements CommonDao {

    @Autowired
    protected EntityManager entityManager;

    @Override
    public <T> T save(T t) {
        entityManager.persist(t);
        return t;
    }

    @Override
    public void save(Collection list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(entityManager::persist);
    }

    @Override
    public <T> T update(T t) {
        entityManager.merge(t);
        return t;
    }

    @Override
    public void update(Collection list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(entityManager::merge);
    }

    @Override
    public void delete(Object t) {
        entityManager.remove(t);
    }

    @Override
    public void delete(Collection list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(entityManager::remove);
    }

    @Override
    public <T> T find(Class<T> c, Serializable id) {
        return entityManager.find(c, id);
    }

    @Override
    public <T> List<T> list(Class<T> c, SimpleQuery where, Sort sort, Integer start, Integer size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(c);
        Root root = query.from(c);
        query.select(root);
        List<Predicate> pList = where.createPredicates(builder, root, query::from);
        if (!CollectionUtils.isEmpty(pList)) {
            query.where(pList.toArray(new Predicate[pList.size()]));
        }
        List<Order> oList = WhereUtil.createOrder(root, sort);
        if (!CollectionUtils.isEmpty(oList)) {
            query.orderBy(oList);
        }
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        if (start != null) {
            typedQuery.setFirstResult(start);
        }
        if (size != null) {
            typedQuery.setMaxResults(size);
        }
        return typedQuery.getResultList();
    }

}
