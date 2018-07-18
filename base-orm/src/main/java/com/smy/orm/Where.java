package com.smy.orm;

import java.lang.annotation.*;

/**
 * Created by smy on 2018/6/1.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Where {

    String value() default "";

    WhereType type() default WhereType.eq;
}
