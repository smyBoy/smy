package com.smy.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by smy on 2018/6/20.
 */
@Getter
@Setter
@ToString
@ApiModel("分页查询参数")
public class PageParam extends SortParam {
    @ApiModelProperty(value = "页码")
    private int page;
    @ApiModelProperty(value = "页面大小")
    private int size;

    public Pageable pageable() {
        return PageRequest.of(page - 1, size, sort());
    }

}
