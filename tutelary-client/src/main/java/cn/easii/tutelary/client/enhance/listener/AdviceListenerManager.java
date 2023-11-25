package cn.easii.tutelary.client.enhance.listener;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AdviceListenerManager {

    private static final Log LOGGER = LogFactory.get(AdviceListenerManager.class);

    private static final FakeBootstrapClassLoader FAKE_BOOTSTRAP_CLASS_LOADER = new FakeBootstrapClassLoader();

    private static final Map<ClassLoader, Multimap<String, AdviceListener>> MAP = new ConcurrentHashMap<>();

    public static void registerAdviceListener(ClassLoader classLoader,
        String className,
        String methodName,
        String methodDesc,
        AdviceListener adviceListener) {
        String key = key(className, methodName, methodDesc);
        registerAdviceListener(classLoader, key, adviceListener);
    }

    public static void registerTraceAdviceListener(ClassLoader classLoader,
        String className,
        String owner,
        String methodName,
        String methodDesc,
        AdviceListener adviceListener) {
        registerAdviceListener(classLoader, keyForTrace(className, owner, methodName, methodDesc), adviceListener);
    }

    private static void registerAdviceListener(ClassLoader classLoader, String key, AdviceListener adviceListener) {
        ClassLoader cl = wrap(classLoader);
        Multimap<String, AdviceListener> listenerMultimap = MAP.get(cl);
        if (listenerMultimap == null) {
            MAP.putIfAbsent(cl, MultimapBuilder.treeKeys().hashSetValues().build());
            listenerMultimap = MAP.get(cl);
        }
        synchronized (key.intern()) {
            if (listenerMultimap.put(key, adviceListener)) {
                LOGGER.debug(
                    "register advice listener, classLoader : {}, key : {}, adviceListener : {}",
                    cl.getClass().getName(),
                    key, adviceListener.getClass().getName()
                );
            }
        }
    }

    public static List<AdviceListener> queryAdviceListeners(ClassLoader classLoader,
        String className,
        String methodName,
        String methodDesc) {
        return queryAdviceListeners(classLoader, key(className, methodName, methodDesc));
    }

    public static List<AdviceListener> queryTraceAdviceListeners(ClassLoader classLoader,
        String className,
        String owner,
        String methodName,
        String methodDesc) {
        return queryAdviceListeners(classLoader, keyForTrace(className, owner, methodName, methodDesc));
    }

    private static List<AdviceListener> queryAdviceListeners(ClassLoader classLoader, String key) {
        ClassLoader cl = wrap(classLoader);
        Multimap<String, AdviceListener> listenerMultimap = MAP.get(cl);
        if (listenerMultimap != null) {
            return new ArrayList<>(listenerMultimap.get(key));
        }
        return Collections.emptyList();
    }

    public static void removeListener(AdviceListener adviceListener) {
        for (Multimap<String, AdviceListener> adviceListenerMultimap : MAP.values()) {
            Set<String> keys = new HashSet<>();
            for (String key : adviceListenerMultimap.keys()) {
                final Collection<AdviceListener> adviceListeners = adviceListenerMultimap.get(key);
                if (adviceListeners.contains(adviceListener)) {
                    keys.add(key);
                }
            }
            for (String key : keys) {
                synchronized (key.intern()) {
                    adviceListenerMultimap.remove(key, adviceListener);
                }
            }
        }
    }

    private static String key(String className, String methodName, String methodDesc) {
        className = className.replace('/', '.');
        return className + methodName + methodDesc;
    }

    private static String keyForTrace(String className, String owner, String methodName, String methodDesc) {
        className = className.replace('/', '.');
        return className + owner + methodName + methodDesc;
    }

    private static ClassLoader wrap(ClassLoader classLoader) {
        return classLoader == null ? FAKE_BOOTSTRAP_CLASS_LOADER : classLoader;
    }

    private static class FakeBootstrapClassLoader extends ClassLoader {
    }

}
