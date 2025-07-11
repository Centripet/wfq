package org.wfq.wufangquan.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Claims;
import net.sourceforge.pinyin4j.PinyinHelper;

public class ReflectUtils {

    /**
     * 将实体类对象转换为 Map
     *
     * @param obj 输入的实体类对象
     * @return 返回该实体类对象的字段和对应的值组成的 Map
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();

        // 确保对象不为 null
        if (obj == null) {
            return map;
        }

        // 获取类的所有字段
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);  // 使私有字段可以访问
            try {
                // 获取字段名和值并放入 Map 中
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();  // 如果访问出错，打印异常
            }
        }

        return map;
    }


//泛化实体类成员变量映射
    public static void fieldsReflection(Object source, Object target) {
        if (source == null || target == null) return;

        Method[] sourceMethods = source.getClass().getMethods();
        Method[] targetMethods = target.getClass().getMethods();

        for (Method sourceMethod : sourceMethods) {
            if (!sourceMethod.getName().startsWith("get") || sourceMethod.getParameterCount() != 0) {
                continue;
            }

            String fieldName = sourceMethod.getName().substring(3); // 去掉 "get"
            String setterName = "set" + fieldName;

            for (Method targetMethod : targetMethods) {
                if (targetMethod.getName().equals(setterName)
                        && targetMethod.getParameterCount() == 1
                        && targetMethod.getParameterTypes()[0].isAssignableFrom(sourceMethod.getReturnType())) {
                    try {
                        Object value = sourceMethod.invoke(source);
                        targetMethod.invoke(target, value);
                    } catch (Exception e) {
                        // 可加入日志记录
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

//String user_id = claims.get("user_id", String.class);
    public static void copyCommonFields(Object target, Claims source) {
        // 获取目标对象的类
        Class<?> targetClass = target.getClass();

        // 获取目标对象所有的字段
        Field[] targetFields = targetClass.getDeclaredFields();

        for (Field targetField : targetFields) {
            try {
                // 获取目标字段的名称
                String fieldName = targetField.getName();

                // 确保源对象中有相同名称的字段
                if (source.containsKey(fieldName)) {
                    // 设置访问权限，允许访问私有字段
                    targetField.setAccessible(true);

                    // 从 Claims 中获取对应字段的值
                    Object value = source.get(fieldName);

                    // 将值设置到目标对象的字段
                    targetField.set(target, value);
                }
            } catch (IllegalAccessException e) {
                System.out.println("Unable to access or set field: " + targetField.getName());
            }
        }
    }

    public static String getInitial(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        char firstChar = str.charAt(0);

        // 如果是英文字母，直接返回大写
        if (Character.isLetter(firstChar) && firstChar < 128) {
            return String.valueOf(Character.toUpperCase(firstChar));
        }

        // 如果是中文，使用拼音库获取首字母
        String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(firstChar);
        if (pinyins != null && pinyins.length > 0) {
            return String.valueOf(Character.toUpperCase(pinyins[0].charAt(0)));
        }

        // 非中文/英文字符，返回 '#'
        return "#";
    }

}
