package com.smy.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 对象工具。
 *
 * @author smy
 */
public class ObjectUtil {

    public static <T> List<ChangeData> update(T original, T update) {
        List<ChangeData> changeDataList = new ArrayList<>();
        List<Field> fields = getFields(original.getClass());
        Object o, u;
        for (Field field : fields) {
            if (field.getAnnotation(ChangeIgnore.class) != null) {
                continue;
            }
            field.setAccessible(true);
            o = getValue(field, original);
            u = getValue(field, update);
            if (o == u || (u != null && u.equals(o))) {
                continue;
            }
            setValue(field, original, u);
            changeDataList.add(new ChangeData(field.getName(), o, u));
        }
        return changeDataList;
    }

    public static <T> List<T> newInstance(Class<T> c, List<Object[]> objectList) {
        if (objectList == null) {
            return null;
        }
        if (objectList.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            Constructor<T> constructor = findConstructor(c, objectList.get(0));
            List<T> list = new ArrayList<>();
            for (Object[] objects : objectList) {
                list.add(constructor.newInstance(objects));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Class<T> c, Object... args) {
        try {
            if (args.length == 0) {
                return c.newInstance();
            }
            return findConstructor(c, args).newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Constructor<T> findConstructor(Class<T> c, Object... args) {
        Constructor[] cs = c.getConstructors();
        Class[] parameterTypes;
        Object arg;
        boolean flag;
        for (Constructor<T> constructor : cs) {
            parameterTypes = constructor.getParameterTypes();
            if (args.length != parameterTypes.length) {
                continue;
            }
            flag = true;
            for (int i = 0; i < args.length; i++) {
                arg = args[i];
                if (arg != null && arg.getClass() != parameterTypes[i]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return constructor;
            }
        }
        throw new RuntimeException("no Constructor format");
    }

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

    @SuppressWarnings("unchecked")
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
