package com.smy.orm;

import com.smy.util.ObjectUtil;
import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询条件构造/实现。
 *
 * @author smy
 */
@Getter(AccessLevel.PACKAGE)
public class WhereBuilder implements SimpleQuery {

    private List<WhereData> where = new ArrayList<>();

    private List<WhereData> defaultWhere = new ArrayList<>();

    private List<CascadeData> cascade = new ArrayList<>();

    private Map<Class, List<WhereData>> cascadeWhere = new HashMap<>();

    public WhereBuilder add(Object data) {
        if (data == null) {
            return this;
        }
        WhereUtil.cascadeData(data.getClass()).forEach(this::cascade);
        ObjectUtil.getFields(data.getClass()).forEach(f -> {
            Where q = f.getAnnotation(Where.class);
            if (q == null) {
                return;
            }
            f.setAccessible(true);
            Object value = ObjectUtil.getValue(f, data);
            if (value == null) {
                return;
            }
            String name = "".equals(q.value()) ? f.getName() : q.value();
            if (void.class.equals(q.join())) {
                add(new WhereData(q.type(), name, value));
            } else {
                add(q.join(), new WhereData(q.type(), name, value));
            }
        });
        return this;
    }

    public WhereBuilder cascade(CascadeData data) {
        cascade.add(data);
        return this;
    }

    public WhereBuilder add(WhereData data) {
        where.add(data);
        return this;
    }

    public WhereBuilder addDefault(WhereData data) {
        defaultWhere.add(data);
        return this;
    }

    public WhereBuilder add(Class join, WhereData data) {
        List<WhereData> list = cascadeWhere.get(join);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(data);
        cascadeWhere.put(join, list);
        return this;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaBuilder b, Root r, RootFactory factory) {
        return WhereUtil.predicates(b, r, factory, this);
    }
}
