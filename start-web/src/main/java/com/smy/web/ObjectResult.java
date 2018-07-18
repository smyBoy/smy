package com.smy.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created by smy on 2018/6/20.
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
