package com.tutelary.common.utils;

import cn.hutool.core.util.TypeUtil;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class ClassUtil {

    public static Class<?> searchClass(Instrumentation inst, String targetClass) {
        Class[] loadedClasses = inst.getAllLoadedClasses();
        for (Class loadedClass : loadedClasses) {
            if (loadedClass == null) {
                continue;
            }
            if (loadedClass.getName().equals(targetClass)) {
                return loadedClass;
            }
        }
        return null;
    }

    public static boolean isLambdaClass(Class<?> clazz) {
        return clazz.getName().contains("$$Lambda$");
    }

    public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
        if (cls == null) {
            return null;
        }

        final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<>();
        getAllInterfaces(cls, interfacesFound);

        return new ArrayList<>(interfacesFound);
    }

    private static void getAllInterfaces(Class<?> cls, final HashSet<Class<?>> interfacesFound) {
        while (cls != null) {
            final Class<?>[] interfaces = cls.getInterfaces();

            for (final Class<?> i : interfaces) {
                if (interfacesFound.add(i)) {
                    getAllInterfaces(i, interfacesFound);
                }
            }

            cls = cls.getSuperclass();
        }
    }

    public static <T> Class<T> getGenericsBySuperClass(Class<?> clazz, Class<?> genericsSuperClass) {
        Type[] typeArguments = TypeUtil.getTypeArguments(clazz);
        for (Type typeArgument : typeArguments) {
            if (genericsSuperClass.isAssignableFrom(TypeUtil.getClass(typeArgument))) {
                return (Class<T>) TypeUtil.getClass(typeArgument);
            }
        }
        throw new RuntimeException("get generics by super class failed, generics super class : "
                                   + genericsSuperClass.getSimpleName() + ", current class : " + clazz.getSimpleName());
    }

}
