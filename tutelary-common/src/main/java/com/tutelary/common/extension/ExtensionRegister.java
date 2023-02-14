package com.tutelary.common.extension;

import com.tutelary.common.utils.ClassUtil;
import java.util.List;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

public class ExtensionRegister {

    public static void doRegistration(ExtensionPointI extensionPoint) {
        Class<?> extensionClz = extensionPoint.getClass();
        if (AopUtils.isAopProxy(extensionPoint)) {
            extensionClz = ClassUtils.getUserClass(extensionPoint);
        }
        Extension extensionAnn = AnnotationUtils.findAnnotation(extensionClz, Extension.class);
        int commandCode = extensionAnn.commandCode();
        List<Class<?>> interfaces = ClassUtil.getAllInterfaces(extensionClz);
        for (Class<?> anInterface : interfaces) {
            if (!ExtensionPointI.class.isAssignableFrom(anInterface)) {
                continue;
            }
            if (anInterface == ExtensionPointI.class) {
                continue;
            }
            ExtensionCoordinate extensionCoordinate = ExtensionCoordinate.valueOf(anInterface, commandCode);
            ExtensionPointI preVal = ExtensionRepository.put(extensionCoordinate, extensionPoint);
            if (preVal != null) {
                throw new RuntimeException("Double registration is not allowed : " + extensionCoordinate);
            }
        }
    }

}
