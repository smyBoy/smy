package com.smy.orm;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 简单查询接口。
 *
 * @author smy
 * @see WhereBuilder
 * @see WhereData
 */
public interface SimpleQuery {

    /**
     * 生成jpa查询条件。
     *
     * @param b       jpa查询构造。
     * @param r       jpa查询默认对象。
     * @param factory jpa查询对象生成工厂，用于级联查询。
     * @return jpa查询条件
     */
    List<Predicate> createPredicates(CriteriaBuilder b, Root r, RootFactory factory);

    /**
     * jpa对象生成工厂。用于级联查询生成jpa查询条件。
     */
    interface RootFactory {
        /**
         * 生成jpa查询对象。
         *
         * @param c 查询实体
         * @return jpa查询对象
         */
        Root root(Class c);
    }
}
