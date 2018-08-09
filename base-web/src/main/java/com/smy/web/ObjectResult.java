package com.smy.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 对象返回值。
 *
 * @author smy
 */
@Getter
@Setter
@ToString
@ApiModel("对象返回值")
@NoArgsConstructor
@AllArgsConstructor
public class ObjectResult<T> extends BaseResult {
    @ApiModelProperty("返回对象")
    private T data;
}
