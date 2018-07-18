package com.smy.orm;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2018/6/6.
 */
@Getter(AccessLevel.PACKAGE)
@ToString
public class WhereBuilder implements SimpleQuery {

    private List where = new ArrayList();

    private List<WhereData> defaultWhere = new ArrayList<>();

    public WhereBuilder addDefault(WhereData data) {
        defaultWhere.add(data);
        return this;
    }

    public WhereBuilder add(WhereData data) {
        where.add(data);
        return this;
    }

    public WhereBuilder add(Object data) {
        where.add(data);
        return this;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaBuilder b, Root r) {
        List<Predicate> list = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        WhereData whereData;
        for (Object data : where) {
            if (data instanceof WhereData) {
                whereData = (WhereData) data;
                WhereUtil.addWhere(list, b, r, whereData.getType(), whereData.getName(), whereData.getValue());
                keys.add(whereData.getName());
            } else {
                keys.addAll(WhereUtil.addWhere(list, b, r, data));
            }
        }
        for (WhereData data : defaultWhere) {
            if (!keys.contains(data.getName())) {
                WhereUtil.addWhere(list, b, r, data.getType(), data.getName(), data.getValue());
            }
        }
        return list;
    }
}
