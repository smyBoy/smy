package com.smy.start.web.user;

import com.smy.start.web.BaseIDEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author smy
 */
@Getter
@Setter
@ToString
@Entity
@Table
@ApiModel("用户管理")
public class User extends BaseIDEntity {
    @ApiModelProperty("名称")
    @Column(columnDefinition = "varchar(40) COMMENT '名称'")
    private String name;
    @ApiModelProperty("出生日期")
    @Column(columnDefinition = "date COMMENT '出生日期'")
    private Date birthday;
}
