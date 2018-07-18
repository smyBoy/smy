package com.smy.orm;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by smy on 2018/6/15.
 *
 * @see WhereBuilder
 * @see WhereData
 */
public interface SimpleQuery {

    List<Predicate> createPredicates(CriteriaBuilder b, Root r);
}
