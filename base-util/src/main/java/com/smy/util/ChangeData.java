package com.smy.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by smy on 2018/7/23.
 */
@Getter
@ToString
@AllArgsConstructor
public class ChangeData {
    private String name;
    private Object original;
    private Object update;
}
