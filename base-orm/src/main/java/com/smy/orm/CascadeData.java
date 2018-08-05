package com.smy.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by smy on 2018/8/5.
 */
@AllArgsConstructor
@Getter
@ToString
public class CascadeData {
    private String mainField;
    private Class join;
    private String joinField;
}
