package com.smy.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;

/**
 * Created by smy on 2018/6/20.
 */
@Getter
@Setter
@ToString
@ApiModel("分页查询参数")
public class PageParam extends SortParam {
    @ApiModelProperty(value = "页码", required = true)
    @Min(value = 0, message = "页码数必须大于0")
    private int page;
    @ApiModelProperty(value = "页面大小", required = true)
    @Min(value = 0, message = "页面大小必须大于0")
    private int size;

    public Pageable pageable() {
        return PageRequest.of(page - 1, size, sort());
    }

}
