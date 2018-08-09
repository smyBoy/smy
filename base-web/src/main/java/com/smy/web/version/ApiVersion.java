package com.smy.web.version;

import java.lang.annotation.*;

/**
 * 版本控制标签。
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {
    int value();
}
