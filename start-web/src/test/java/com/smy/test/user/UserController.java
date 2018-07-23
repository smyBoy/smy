package com.smy.test.user;

import com.smy.web.BaseResult;
import com.smy.web.ObjectResult;
import com.smy.web.PageParam;
import com.smy.web.PageResult;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.TimeZone;

/**
 * Created by smy on 2018/7/19.
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService service;

    @InitBinder
    public void intDate(WebDataBinder dataBinder) {
        DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd HH:mm:ss");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        dataBinder.addCustomFormatter(dateFormatter);
    }


    @GetMapping("test")
    public ObjectResult<User> test(User user) {
        return new ObjectResult<>(user);
    }

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
    public PageResult<User> page(UserParam param,@Validated PageParam pageParam) {
        return service.page(param, pageParam);
    }


}
