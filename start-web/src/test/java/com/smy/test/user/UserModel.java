package com.smy.test.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by smy on 2018/7/23.
 */
@Getter
@Setter
@ToString
@ApiModel("用户模型")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Long id;
    private String name;
    private String username;
    private String orgId;
    private String orgName;
}
