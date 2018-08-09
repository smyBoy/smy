package com.smy.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 基本返回值。
 *
 * @author smy
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("基本返回值")
public class BaseResult {
    @ApiModelProperty("成功标识")
    private boolean success = true;
    @ApiModelProperty("结果备注")
    private String message;

    public BaseResult(String message) {
        this.message = message;
    }
}
