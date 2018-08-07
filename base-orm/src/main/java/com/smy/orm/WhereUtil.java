package com.smy.orm;


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
 * 查询工具类。
 *
 * @author smy
 */
public class WhereUtil {


    public static List<CascadeData> cascadeData(Class c) {
        List<CascadeData> list = new ArrayList<>();
        Cascade cascade = (Cascade) c.getAnnotation(Cascade.class);
        if (cascade != null) {
            list.add(new CascadeData(cascade.mainField(), cascade.join(), cascade.joinField()));
        }
        Cascade.Cascades cascades = (Cascade.Cascades) c.getAnnotation(Cascade.Cascades.class);
        if (cascades != null) {
            for (Cascade cascade1 : cascades.value()) {
                list.add(new CascadeData(cascade1.mainField(), cascade1.join(), cascade1.joinField()));
            }
        }
        return list;
    }

    public static List<Predicate> predicates(CriteriaBuilder builder, Root root, SimpleQuery.RootFactory factory, WhereBuilder where) {
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

    public static Predicate createWhere(CriteriaBuilder builder, Root root, WhereData whereData) {
        return createWhere(builder, root, whereData.getType(), whereData.getName(), whereData.getValue());
    }

    private static Predicate createWhere(CriteriaBuilder builder, Root root, WhereType type, String name, Object value) {
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
