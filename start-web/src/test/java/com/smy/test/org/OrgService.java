package com.smy.test.org;

import com.smy.orm.CommonDao;
import com.smy.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by smy on 2018/7/23.
 */
@Service
public class OrgService {
    @Autowired
    private CommonDao commonDao;

    @Transactional
    public Org save(Org org) {
        return commonDao.save(org);
    }

    @Transactional
    public Org update(Org org) {
        Org original = commonDao.find(Org.class, org.getId());
        ObjectUtil.update(original,org);
        return commonDao.update(original);
    }

    @Transactional
    public Org change(Long id) {
        Org original = commonDao.find(Org.class, id);
        original.setName(UUID.randomUUID().toString().substring(16));
        return commonDao.update(original);
    }
}
