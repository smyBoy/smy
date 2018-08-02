package com.smy.orm;

import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smy on 2018/8/2.
 */
public class CascadeBuilder extends WhereBuilder {
    private List<CascadeData> dataList;

    private Map<Class, List<WhereData>> whereDataMap = new HashMap<>();

    public CascadeBuilder(List<CascadeData> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new RuntimeException("级联查询的级联数据不可为空");
        }
        this.dataList = dataList;
    }

    public CascadeBuilder(Class paramClass) {
        this(WhereUtil.cascadeData(paramClass));
    }

    public CascadeBuilder add(Class c, WhereData whereData) {
        List<WhereData> list = whereDataMap.get(c);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        list.add(whereData);
        whereDataMap.put(c, list);
        return this;
    }

    @Override
    public List<Predicate> createPredicates(CriteriaBuilder b, Root r) {
        List<Predicate> list = super.createPredicates(b, r);

        return list;
    }
}
