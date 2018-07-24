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
public class WhereUtil {

    public static List<String> addWhere(List<Predicate> list, CriteriaBuilder builder, Root root, Object where) {
        List<String> keys = new ArrayList<>();
        if (where == null) {
            return keys;
        }
        ObjectUtil.getFields(where.getClass()).forEach(f -> {
            Where q = f.getAnnotation(Where.class);
            if (q == null) {
                return;
            }
            f.setAccessible(true);
            String name = "".equals(q.value()) ? f.getName() : q.value();
            Object value = ObjectUtil.getValue(f, where);
            Predicate predicate = createWhere(builder, root, q.type(), name, value);
            if (predicate != null) {
                list.add(predicate);
                keys.add(name);
            }

        });
        return keys;
    }

    public static boolean addWhere(List<Predicate> list, CriteriaBuilder builder, Root root, WhereData whereData) {
        Predicate predicate = createWhere(builder, root, whereData);
        if (predicate == null) {
            return false;
        } else {
            list.add(predicate);
            return true;
        }
    }

    public static Predicate createWhere(CriteriaBuilder builder, Root root, WhereData whereData) {
        return createWhere(builder, root, whereData.getType(), whereData.getName(), whereData.getValue());
    }

    private static Predicate createWhere(CriteriaBuilder builder, Root root, WhereType type, String name, Object value) {
        if (value == null || "".equals(value)) {
            if (WhereType.eqOrNull.equals(type)) {
                return builder.isNull(root.get(name));
            }
            return null;
        }
        switch (type) {
            case eq:
                return builder.equal(root.get(name), value);
            case like:
                return builder.like(root.get(name), "%" + value + "%");
            case leftLike:
                return builder.like(root.get(name), value + "%");
            case ge:
                return builder.greaterThanOrEqualTo(root.get(name), (Comparable) value);
            case gt:
                return builder.greaterThan(root.get(name), (Comparable) value);
            case le:
                return builder.lessThanOrEqualTo(root.get(name), (Comparable) value);
            case lt:
                return builder.lessThan(root.get(name), (Comparable) value);
            case in:
                CriteriaBuilder.In in = builder.in(root.get(name));
                if (value instanceof Collection && !CollectionUtils.isEmpty((Collection<?>) value)) {
                    ((Collection) value).forEach(in::value);
                    return in;
                }
            case locate:
                return builder.gt(builder.locate(new LiteralExpression((CriteriaBuilderImpl) builder, value), root.get(name)), Integer.valueOf(0));
            default:
                throw new RuntimeException("error " + type + " for " + name);
        }
    }

    public static List<Order> createOrder(Root root, Sort sort) {
        List<Order> list = new ArrayList<>();
        if (sort != null && sort.isSorted()) {
            sort.stream().forEach(order -> {
                list.add(new OrderImpl(root.get(order.getProperty()), order.isAscending()));
            });
        }
        return list;
    }
}
