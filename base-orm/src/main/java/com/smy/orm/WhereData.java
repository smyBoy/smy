package com.smy.orm;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 单一查询条件。
 *
 * @author smy
 */
@Getter
@ToString
public class WhereData implements SimpleQuery {
    WhereData(WhereType type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    private WhereType type;

    private String name;

    private Object value;

    public static WhereData eq(String name, Object value) {
        return new WhereData(WhereType.eq, name, value);
    }

    public static WhereData like(String name, String value) {
        return new WhereData(WhereType.like, name, value);
    }

    public static WhereData leftLike(String name, String value) {
        return new WhereData(WhereType.leftLike, name, value);
    }

    public static WhereData gt(String name, Comparable value) {
        return new WhereData(WhereType.gt, name, value);
    }

    public static WhereData ge(String name, Comparable value) {
        return new WhereData(WhereType.ge, name, value);
    }

    public static WhereData lt(String name, Comparable value) {
        return new WhereData(WhereType.like, name, value);
    }

    public static WhereData le(String name, Comparable value) {
        return new WhereData(WhereType.like, name, value);
    }

    public static WhereData in(String name, Collection value) {
        return new WhereData(WhereType.in, name, value);
    }

    public static WhereData in(String name, Object... value) {
        Collection collection = new ArrayList();
        for (Object o : value) {
            collection.add(o);
        }
        return in(name, collection);
    }

    public static WhereData locate(String name, String value) {
        return new WhereData(WhereType.locate, name, value);
    }

    @Override
    public List<Predicate> createPredicates(CriteriaBuilder b, Root r, RootFactory factory) {
        return Arrays.asList(WhereUtil.createWhere(b, r, this));
    }
}
