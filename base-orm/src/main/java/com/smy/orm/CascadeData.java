package com.smy.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 级联查询数据。
 *
 * @author smy
 */
@AllArgsConstructor
@Getter
@ToString
public class CascadeData {
    private String mainField;
    private Class join;
    private String joinField;
}
