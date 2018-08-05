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
 * Created by smy on 2018/5/8.
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
    public <T> List<T> list(Class<T> c, WhereBuilder where, Sort sort, Integer start, Integer size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(c);
        Root root = query.from(c);
        query.select(root);
        List<Predicate> pList = predicates(builder, root, join -> query.from(join), where);
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

    private static List<Predicate> predicates(CriteriaBuilder builder, Root root, RootFactory factory, WhereBuilder where) {
        List<Predicate> list = new ArrayList<>();
        List<String> hasFields = new ArrayList<>();
        where.getWhere().forEach(whereData -> {
            list.add(WhereUtil.createWhere(builder, root, whereData));
            hasFields.add(whereData.getName());
        });
        where.getDefaultWhere().forEach(whereData -> {
            if (hasFields.contains(whereData.getName())) {
                return;
            }
            list.add(WhereUtil.createWhere(builder, root, whereData));
        });
        where.getCascadeWhere().forEach((join, whereList) -> {
            if (CollectionUtils.isEmpty(whereList)) {
                return;
            }
            CascadeData cascadeData = where.getCascade().stream().filter(c -> join.equals(c.getJoin())).findFirst().orElseThrow(() -> new RuntimeException("no join for search"));
            Root root2 = factory.root(join);
            list.add(builder.equal(root.get(cascadeData.getMainField()), root2.get(cascadeData.getJoinField())));
            whereList.forEach(whereData -> {
                list.add(WhereUtil.createWhere(builder, root, whereData));
            });
        });
        return list;
    }

    public interface RootFactory {
        Root root(Class c);
    }


}
