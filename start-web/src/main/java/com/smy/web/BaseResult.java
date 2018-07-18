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
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("基本返回值")
public class BaseResult {
    @ApiModelProperty("成功标识")
    private boolean success = true;
    @ApiModelProperty("结果备注")
    private String message;

    public static BaseResult success(String message) {
        return new BaseResult(true, "message");
    }

    public static BaseResult fail(String message) {
        return new BaseResult(false, "message");
    }

}
