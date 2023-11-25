package cn.easii.tutelary.client.loader;

import cn.easii.tutelary.client.ClientBootstrap;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClassLoaderWrapper {

    public static ClassLoader getAgentClassLoader() {
        return ClientBootstrap.class.getClassLoader();
    }

    public static ClassLoader getApplicationClassLoader() {
        return ClientBootstrap.class.getClassLoader().getParent();
    }

    public static List<Class> getApplicationLoadedClasses(Instrumentation inst) {
        ClassLoader classLoader = getApplicationClassLoader();
        return Arrays.stream(inst.getAllLoadedClasses())
            .filter(clazz -> clazz.getClassLoader() != null)
            .filter(
                clazz -> classLoader.getClass().getName().equals(clazz.getClassLoader().getClass().getName()))
            .collect(Collectors.toList());
    }

}
