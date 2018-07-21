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
    /**
     * 主要参考http的状态码
     * 1XX    信息提示   标识临时响应，客户端一般显示一段时间后消失,不执行其他操作
     * 2XX    成功       表明服务器成功地接受了客户端请求
     * 200——成功
     * 3XX    重定向     客户端必须采取更多操作来实现请求
     * 4XX    客户端错误
     * 5XX    服务器错误
     * >600  业务级错误
     */
    @ApiModelProperty("成功标识")
    private int code = 200;
    @ApiModelProperty("结果备注")
    private String message;


}
