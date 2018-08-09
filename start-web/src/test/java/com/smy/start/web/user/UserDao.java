package com.smy.start.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * @author smy
 */
@Repository
public class UserDao {
    @Autowired
    private EntityManager entityManager;

    public void changeState2(int id, int state) {
        entityManager.createQuery("update User set state=:state where id=:id")
                .setParameter("id", id)
                .setParameter("state", state)
                .executeUpdate();
    }

    public void changeState3(int id, int state) {
        entityManager.createNativeQuery("UPDATE user SET state=:state WHERE id=:id")
                .setParameter("id", id)
                .setParameter("state", state)
                .executeUpdate();
    }
}
