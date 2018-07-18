package com.smy.orm;


import com.smy.util.ObjectUtil;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by smy on 2018/6/1.
 */
class WhereUtil {

    static List<String> addWhere(List<Predicate> list, CriteriaBuilder builder, Root root, Object where) {
        List<String> keys = new ArrayList<>();
        if (where == null) {
            return keys;
        }
        ObjectUtil.getFields(where.getClass()).forEach(f -> {
            Where q = f.getAnnotation(Where.class);
            if (q == null) {
                return;
            }
            String name = "".equals(q.value()) ? f.getName() : q.value();
            Object value = ObjectUtil.getValue(f, where);
            addWhere(list, builder, root, q.type(), name, value);
            keys.add(name);
        });
        return keys;
    }

    static void addWhere(List<Predicate> list, CriteriaBuilder builder, Root root, WhereType type, String name, Object value) {
        if (value == null || "".equals(value)) {
            if (WhereType.eqOrNull.equals(type)) {
                list.add(builder.isNull(root.get(name)));
            }
            return;
        }
        switch (type) {
            case eq:
                list.add(builder.equal(root.get(name), value));
                break;
            case like:
                list.add(builder.like(root.get(name), "%" + value + "%"));
                break;
            case leftLike:
                list.add(builder.like(root.get(name), value + "%"));
                break;
            case ge:
                list.add(builder.greaterThanOrEqualTo(root.get(name), (Comparable) value));
                break;
            case gt:
                list.add(builder.greaterThan(root.get(name), (Comparable) value));
                break;
            case le:
                list.add(builder.lessThanOrEqualTo(root.get(name), (Comparable) value));
                break;
            case lt:
                list.add(builder.lessThan(root.get(name), (Comparable) value));
                break;
            case in:
                CriteriaBuilder.In in = builder.in(root.get(name));
                if (value instanceof Collection && !CollectionUtils.isEmpty((Collection<?>) value)) {
                    ((Collection) value).forEach(in::value);
                    list.add(in);
                    break;
                }
            case locate:
                list.add(builder.gt(builder.locate(new LiteralExpression((CriteriaBuilderImpl) builder, value), root.get(name)), Integer.valueOf(0)));
                break;
            default:
                throw new RuntimeException("error " + type + " for " + name);
        }
    }

    static List<Order> createOrder(Root root, Sort sort) {
        List<Order> list = new ArrayList<>();
        if (sort != null && sort.isSorted()) {
            sort.stream().forEach(order -> {
                list.add(new OrderImpl(root.get(order.getProperty()), order.isAscending()));
            });
        }
        return list;
    }
}
