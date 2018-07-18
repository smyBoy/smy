package com.smy.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

/**
 * Created by smy on 2018/6/20.
 */
@Getter
@Setter
@ToString
@ApiModel("排序参数")
public class SortParam {
    @ApiModelProperty("排序字段")
    private String sort;
    @ApiModelProperty(value = "排序规则", allowableValues = "asc,desc")
    private String order = "asc";

    public Sort sort() {
        if (StringUtils.isEmpty(sort)) {
            return null;
        }
        return Sort.by(Sort.Direction.fromString(order), sort);
    }
}
