package com.tutelary.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.TypeUtil;
import com.tutelary.common.utils.matcher.EqualsMatcher;
import com.tutelary.common.utils.matcher.Matcher;
import com.tutelary.common.utils.matcher.RegexMatcher;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtil {

    /**
     * find class by qualified class name
     * @param inst  {@link Instrumentation}
     * @param targetQualifiedClassName  qualified class name
     */
    public static Class<?> searchClass(Instrumentation inst, String targetQualifiedClassName) {
        Matcher<String> matcher = new EqualsMatcher<>(targetQualifiedClassName);
        final Set<Class<?>> classes = searchClass(inst, matcher);
        if (CollectionUtil.isEmpty(classes)) {
            return null;
        }
        return classes.iterator().next();
    }

    public static Set<Class<?>> fuzzySearchClass(Instrumentation inst, String classPattern, String classloader) {
        final Set<Class<?>> classes = fuzzySearchClass(inst, classPattern);
        return filter(classes, classloader);
    }

    private static Set<Class<?>> filter(final Set<Class<?>> classes, final String classloader) {
        if (classloader == null) {
            return classes;
        }
        if (classes != null) {
            return classes.stream().filter(clazz -> {
                return clazz != null && Integer.toHexString(clazz.getClassLoader().hashCode()).equals(classloader);
            }).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public static Set<Class<?>> fuzzySearchClass(Instrumentation inst, String classPattern) {
        if (!classPattern.contains("$$Lambda")) {
            classPattern = classPattern.replace("/", ".");
        }
        Matcher matcher = new RegexMatcher(classPattern);
        return searchClass(inst, matcher);
    }

    public static Set<Class<?>> searchClass(Instrumentation inst, Matcher<String> classNameMatcher) {
        return searchClass(inst, classNameMatcher, Integer.MAX_VALUE);
    }

    public static Set<Class<?>> searchClass(Instrumentation inst, Matcher<String> classNameMatcher, int limit) {
        if (classNameMatcher == null) {
            return Collections.emptySet();
        }
        final Set<Class<?>> classes = new HashSet<>();
        for (Class clazz : inst.getAllLoadedClasses()) {
            if (clazz == null) {
                continue;
            }
            if (classNameMatcher.matches(clazz.getName())) {
                classes.add(clazz);
            }
            if (classes.size() >= limit) {
                break;
            }
        }
        return classes;
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

    public static String classLoaderHash(Class<?> clazz) {
        if (clazz == null) {
            return "null";
        }
        return classLoaderHash(clazz.getClassLoader());
    }

    public static String classLoaderHash(ClassLoader classLoader) {
        if (classLoader == null) {
            return "null";
        }
        return Integer.toHexString(classLoader.hashCode());
    }

}
