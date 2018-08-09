package com.smy.start.web.user;

import com.smy.web.BaseResult;
import com.smy.web.ObjectResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author smy
 */
@RestController
@RequestMapping("user")
@Api(description = "用户管理")
public class UserController {
    @Resource
    private UserService service;

    @ApiOperation("添加")
    @PostMapping("add")
    public ObjectResult<User> add(User user) {
        return new ObjectResult<>(service.add(user));
    }

    @ApiOperation("修改")
    @PostMapping("update")
    public ObjectResult<User> update(User user) {
        return new ObjectResult<>(service.update(user));
    }

    @ApiOperation("修改状态-jpa")
    @PostMapping("change/state1")
    public BaseResult changeState1(int id, int state) {
        service.changeState1(id, state);
        return new BaseResult("修改状态成功");
    }

    @ApiOperation("修改状态-jsql")
    @PostMapping("change/state2")
    public BaseResult changeState2(int id, int state) {
        service.changeState2(id, state);
        return new BaseResult("修改状态成功");
    }

    @ApiOperation("修改状态-sql")
    @PostMapping("change/state3")
    public BaseResult changeState3(int id, int state) {
        service.changeState3(id, state);
        return new BaseResult("修改状态成功");
    }

    @ApiOperation("用户列表")
    @GetMapping("list")
    public ObjectResult<List<User>> list() {
        return new ObjectResult<>(service.list());
    }
}
