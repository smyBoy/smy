package com.smy.test.user;

import com.smy.orm.Cascade;
import com.smy.orm.Where;
import com.smy.orm.WhereType;
import com.smy.orm.WhereUtil;
import com.smy.test.org.Org;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by smy on 2018/8/2.
 */
@Getter
@Setter
@ToString
@ApiModel("用户查询参数")
@Cascade(mainField = "orgId", join = Org.class, joinField = "id")
@Cascade(mainField = "orgId", join = Org.class, joinField = "id")
public class UserParam2 {
    @Where(type = WhereType.like)
    private String name;
    @Where(type = WhereType.like)
    private String username;
    @Where(join = Org.class, type = WhereType.like)
    private String orgName;

}
