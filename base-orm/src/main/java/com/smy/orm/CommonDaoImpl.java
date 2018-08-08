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
import java.util.Map;

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
    public int update(Class c, Map<String, ?> setMap, SimpleQuery where) {
        if (CollectionUtils.isEmpty(setMap)) {
            throw new RuntimeException("update data is empty!");
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate update = builder.createCriteriaUpdate(c);
        Root root = update.from(c);
        setMap.forEach((k, v) -> {
            update.set(k, v);
        });
        List<Predicate> list = where.createPredicates(builder, root, update::from);
        if (!CollectionUtils.isEmpty(list)) {
            update.where(list.toArray(new Predicate[list.size()]));
        }
        return entityManager.createQuery(update).executeUpdate();
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
    public int delete(Class c, SimpleQuery where) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete delete = builder.createCriteriaDelete(c);
        Root root = delete.from(c);
        List<Predicate> list = where.createPredicates(builder, root, delete::from);
        if (!CollectionUtils.isEmpty(list)) {
            delete.where(list.toArray(new Predicate[list.size()]));
        }
        return entityManager.createQuery(delete).executeUpdate();
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

    @Override
    public int count(Class c, SimpleQuery where) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root root = query.from(c);
        query.select(builder.countDistinct(root));
        List<Predicate> pList = where.createPredicates(builder, root, query::from);
        if (!CollectionUtils.isEmpty(pList)) {
            query.where(pList.toArray(new Predicate[pList.size()]));
        }
        return entityManager.createQuery(query).getSingleResult().intValue();
    }

}
