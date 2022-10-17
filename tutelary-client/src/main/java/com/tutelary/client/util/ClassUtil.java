package com.tutelary.client.util;

import java.lang.instrument.Instrumentation;

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

}
