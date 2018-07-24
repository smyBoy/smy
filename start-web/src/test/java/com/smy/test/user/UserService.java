package com.smy.test.user;

import com.smy.orm.CommonDao;
import com.smy.orm.WhereBuilder;
import com.smy.web.ObjectResult;
import com.smy.web.PageParam;
import com.smy.web.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by smy on 2018/7/19.
 */
@Service
public class UserService {

    @Autowired
    private CommonDao commonDao;

    @Transactional
    public User save(User user) {
        return commonDao.save(user);
    }

    @Transactional
    public User update(User user) {
        return commonDao.update(user);
    }

    @Transactional
    public void delete(Long id) {
        commonDao.delete(User.class, id);
    }

    public PageResult<User> page(UserParam param, PageParam pageParam) {
        return PageResult.page(commonDao.page(User.class, new WhereBuilder().add(param), pageParam.pageable()));
    }

}
