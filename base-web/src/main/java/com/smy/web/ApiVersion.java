package com.smy.web;

import java.lang.annotation.*;

/**
 * 版本控制标签。
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    int value();
}
