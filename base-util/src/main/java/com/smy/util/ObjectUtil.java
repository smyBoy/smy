package com.smy.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by smy on 2018/6/1.
 */
public class ObjectUtil {

    public static List<Field> getFields(Class c) {
        return getFields(c, f -> true);
    }

    public static List<Field> getFields(Class c, Predicate<Field> predicate) {
        List<Field> fields = new ArrayList<>();
        while (c != Object.class) {
            Stream.of(c.getDeclaredFields()).filter(predicate::test).forEach(fields::add);
            c = c.getSuperclass();
        }
        return fields;
    }

    public static <T> T getValue(Field field, Object object) {
        try {
            return (T) field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Error ObjectUtil.getValue(%s,Object)", field.getName()), e);
        }
    }

    public static void setValue(Field field, Object object, Object value) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Error ObjectUtil.setValue(%s,Object,Object)", field.getName()), e);
        }
    }



}
