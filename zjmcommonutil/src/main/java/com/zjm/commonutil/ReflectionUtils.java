package com.zjm.commonutil;

import com.zjm.testvo.StudentInfoVO;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 反射工具类
 */
public class ReflectionUtils {
    public static void main(String[] args) throws Exception {
        Map<Field, Method> methodMap = fieldAndSetterMethod(StudentInfoVO.class);
        Field field = getFieldTillNoSuperClass(StudentInfoVO.class, "name");
        Method method = methodMap.get(field);
        StudentInfoVO studentInfo = new StudentInfoVO();
        setRefValue(studentInfo, method, "张三");
        System.out.println(studentInfo);

    }
    private ReflectionUtils() {
    }

    /**
     * 反射设置值
     *
     * @param object 对象
     * @param method 方法
     * @param args   方法参数对象
     * @throws ReflectiveOperationException 反射操作异常
     */
    public static void setRefValue(Object object, Method method, Object... args) throws ReflectiveOperationException {
        method.invoke(object, args);
    }

    /**
     * 有setter方法的字段及其setter方法
     *
     * @param clazz Class对象
     * @return 有setter方法的 字段及其setter方法
     * @throws IntrospectionException 内省异常
     */
    public static Map<Field, Method> fieldAndSetterMethod(Class clazz) throws IntrospectionException {
        Map<Field, Method> map = new LinkedHashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getWriteMethod() != null) {
                map.put(getFieldTillNoSuperClass(clazz, propertyDescriptor.getName()), propertyDescriptor.getWriteMethod());
            }
        }
        return map;
    }

    /**
     * 无限循环向上父类直到找到属性字段
     *
     * @param clazz
     * @param field
     * @return
     */
    private static Field getFieldTillNoSuperClass(Class clazz, String field) {
        try {
            return clazz.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            return getFieldTillNoSuperClass(clazz.getSuperclass(), field);
        } catch (NullPointerException e) {
            return null;
        }
    }




}

