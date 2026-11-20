package com.holdtime.ops.module.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class StrUtils {
    public static <T> void trimObjAttr(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                field.setAccessible(true); // 设置可访问私有字段
                try {
                    String value = (String) field.get(object);
                    if (value != null) {
                        field.set(object, value.trim());
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
