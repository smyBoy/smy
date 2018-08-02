package com.smy.orm;

import java.lang.annotation.*;

/**
 * Created by smy on 2018/8/2.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = Cascade.Cascades.class)
public @interface Cascade {

    String mainField();

    Class join();

    String joinField();

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Cascades {
        Cascade[] value();
    }
}
