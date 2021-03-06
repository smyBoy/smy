package com.smy.start.web;

import com.smy.web.ObjectResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页查询返回值。
 *
 * @author smy
 */
@Getter
@Setter
@ToString
@ApiModel("分页查询返回值")
public class PageResult<T> extends ObjectResult<List<T>> {
    @ApiModelProperty("总数")
    private long total;

    public static <T> PageResult<T> page(Page<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setTotal(page.getTotalElements());
        result.setData(page.getContent());
        return result;
    }
}
