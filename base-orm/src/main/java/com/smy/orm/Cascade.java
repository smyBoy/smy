package com.smy.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Admin on 2018/7/30.
 */
@Getter
@AllArgsConstructor
public class Cascade {
    private String mainField;
    private String joinField;
}
