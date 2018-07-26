package com.smy.test.org;

import com.smy.test.user.User;
import com.smy.web.BaseResult;
import com.smy.web.ObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by smy on 2018/7/23.
 */
@RestController
@RequestMapping("org")
public class OrgController {

    @Autowired
    private OrgService service;

    @PostMapping("save")
    public ObjectResult<Org> save(Org org) {
        return new ObjectResult<>(service.save(org));
    }

    @PostMapping("update")
    public ObjectResult<Org> update(Org org) {
        return new ObjectResult<>(service.update(org));
    }

    @GetMapping("test")
    public ObjectResult<Org> change(Long id){
        return new ObjectResult<>(service.change(id));
    }
}
