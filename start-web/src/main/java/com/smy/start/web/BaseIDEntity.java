package com.smy.start.web;

import com.smy.util.ChangeIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * ID自增长实体。
 *
 * @author smy
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseIDEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ApiModelProperty("ID")
    @Column(columnDefinition = "int COMMENT 'ID'")
    private Integer id;
}
