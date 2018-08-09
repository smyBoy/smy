package com.smy.start.web.user;

import com.smy.orm.CommonDao;
import com.smy.orm.WhereData;
import com.smy.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author smy
 */
@Service
public class UserService {
    @Autowired
    private CommonDao commonDao;

    @Resource
    private UserDao dao;

    @Transactional
    public User add(User user) {
        return commonDao.save(user);
    }

    @Transactional
    public User update(User user) {
        User original = commonDao.find(User.class, user.getId());
        ObjectUtil.update(original, user);
        return commonDao.update(original);
    }

    public List<User> list() {
        return commonDao.list(User.class);
    }

    @Transactional
    public void changeState1(int id, int state) {
        Map<String, Object> set = new HashMap<>();
        set.put("state", state);
        commonDao.update(User.class, set, WhereData.eq("id", id));
    }

    @Transactional
    public void changeState2(int id, int state) {
        dao.changeState2(id,state);
    }

    @Transactional
    public void changeState3(int id, int state) {
        dao.changeState3(id,state);
    }
}
