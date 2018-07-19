package com.smy.test.user;

import com.smy.orm.Where;
import com.smy.orm.WhereType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by smy on 2018/7/19.
 */
@Getter
@Setter
@ToString
@ApiModel("用户查询参数")
public class UserParam {
    @Where(type = WhereType.like)
    private String name;
    @Where(type = WhereType.like)
    private String username;
}
