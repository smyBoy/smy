package com.smy.test.user;

import com.smy.orm.WhereData;
import com.smy.orm.WhereUtil;
import com.smy.test.org.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2018/7/24.
 */
@Repository
public class UserDao {

    @Autowired
    private EntityManager entityManager;

    public List<User> test1(String username, String orgName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);
        query.select(userRoot);
        List<Predicate> pList = new ArrayList<>();
        if (!StringUtils.isEmpty(username)) {
            pList.add(WhereUtil.createWhere(builder, userRoot, WhereData.le("name", username)));
        }
        if (StringUtils.isEmpty(orgName)) {
            Root<Org> orgRoot = query.from(Org.class);
            pList.add(WhereUtil.createWhere(builder, orgRoot, WhereData.le("name", orgName)));
            pList.add(builder.equal(userRoot.get("orgId"), orgRoot.get("id")));
        }
        return entityManager.createQuery(query).getResultList();
    }

}
