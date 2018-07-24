package com.smy.web;

import java.lang.annotation.*;

/**
 * Created by smy on 2018/5/25.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    int value();
}
