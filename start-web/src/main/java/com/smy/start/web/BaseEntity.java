package com.smy.start.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * 基础实体映射。
 *
 * @author smy
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity {
    @ApiModelProperty(value = "创建时间")
    @Column(columnDefinition = "datetime COMMENT '创建时间'")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    @Column(columnDefinition = "datetime COMMENT '更新时间'")
    private Date updateTime;
    @ApiModelProperty(value = "状态")
    @Column(columnDefinition = "int(2) COMMENT '状态'")
    private int state;

    @PrePersist
    public void persist() {
        createTime = new Date();
        updateTime = createTime;
    }

    @PreUpdate
    public void update() {
        updateTime = new Date();
    }
}
