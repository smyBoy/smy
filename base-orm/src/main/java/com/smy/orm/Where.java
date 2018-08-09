package com.smy.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询标签。
 *
 * @author smy
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Where {

    Class join() default void.class;

    String value() default "";

    WhereType type() default WhereType.eq;
}
