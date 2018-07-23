package com.smy.test.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by smy on 2018/7/19.
 */
@Getter
@Setter
@ToString
@Entity
@Table
@ApiModel("用户")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(columnDefinition = "bigint COMMENT 'ID'")
    private Long id;
    @ApiModelProperty(hidden = true)
    @Column(columnDefinition = "bigint COMMENT '创建时间'")
    private Timestamp createTime;
    @ApiModelProperty(hidden = true)
    @Column(columnDefinition = "bigint COMMENT '更新时间'")
    private Timestamp updateTime;
    @Version
    @Column(columnDefinition = "bigint COMMENT '版本号'")
    private Long version;
    @Column(columnDefinition = "varchar(30) COMMENT '名称'")
    private String name;
    @Column(columnDefinition = "varchar(30) COMMENT '登陆名'")
    private String username;
    @Column(columnDefinition = "varchar(30) COMMENT '密码'")
    private String password;

}
