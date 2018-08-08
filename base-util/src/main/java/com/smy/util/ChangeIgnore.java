package com.smy.util;

import java.lang.annotation.*;

/**
 * 变更忽略标签。
 *
 * @author smy
 * @see ObjectUtil#update(Object, Object)
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChangeIgnore {
}
