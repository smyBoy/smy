package com.smy.orm;

import java.lang.annotation.*;

/**
 * 级联查询标签。
 *
 * @author smy
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = Cascade.Cascades.class)
public @interface Cascade {

    String mainField();

    Class join();

    String joinField();

    /**
     *级联查询多注解支持。
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Cascades {
        Cascade[] value();
    }
}
