package com.smy.test.user;

import com.smy.web.BaseResult;
import com.smy.web.ObjectResult;
import com.smy.web.PageParam;
import com.smy.web.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by smy on 2018/7/19.
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService service;

    @PostMapping("save")
    public ObjectResult<User> save(User user) {
        return new ObjectResult<>(service.save(user));
    }

    @PostMapping("update")
    public ObjectResult<User> update(User user) {
        return new ObjectResult<>(service.update(user));
    }

    @GetMapping("delete")
    public BaseResult delete(Long id) {
        service.delete(id);
        return new BaseResult();
    }

    @PostMapping("page")
    public PageResult<User> page(UserParam param, PageParam pageParam) {
        return service.page(param, pageParam);
    }


}
