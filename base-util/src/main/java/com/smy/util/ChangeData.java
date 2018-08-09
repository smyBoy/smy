package com.smy.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 数据变更记录。
 *
 * @author smy
 * @see ObjectUtil#update(Object, Object)
 */
@Getter
@ToString
@AllArgsConstructor
public class ChangeData {
    private String name;
    private Object original;
    private Object update;
}
