package com.smy.util;

import java.lang.annotation.*;

/**
 * Created by smy on 2018/7/23.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChangeIgnore {
}
