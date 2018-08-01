package com.smy.orm;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2018/7/30.
 */
public class CascadeBuilder extends WhereBuilder {

    private Map<Class, Cascade> cascadeMap = new HashMap<>();

    private Map<Class, List<WhereData>> whereDataMap = new HashMap<>();

    public CascadeBuilder(Map<Class, Cascade> cascadeMap) {
        this.cascadeMap = cascadeMap;
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


}
