package com.tutelary.client.enhance.listener;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.tutelary.client.ScheduledExecutors;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AdviceListenerManager {

    private static final Log LOGGER = LogFactory.get(AdviceListenerManager.class);

    private static final FakeBootstrapClassLoader FAKE_BOOTSTRAP_CLASS_LOADER = new FakeBootstrapClassLoader();

    private static final Map<ClassLoader, Multimap<String, AdviceListener>> MAP = new ConcurrentHashMap<>();

    private static final Set<Long> UNREGISTER_ADVICE_IDS = ConcurrentHashMap.newKeySet();

    public AdviceListenerManager() {
        ScheduledExecutors.scheduleWithFixedDelay(() -> {
            if (UNREGISTER_ADVICE_IDS.size() == 0) {
                return;
            }
            for (Multimap<String, AdviceListener> multimap : MAP.values()) {
                Set<String> keySet = multimap.keySet();
                for (String key : keySet) {
                    Set<Long> removedIds = null;
                    Collection<AdviceListener> adviceListeners = multimap.get(key);
                    for (AdviceListener adviceListener : adviceListeners) {
                        if (UNREGISTER_ADVICE_IDS.remove(adviceListener.id())) {
                            removedIds = Optional.ofNullable(removedIds).orElse(new HashSet<>());
                            removedIds.add(adviceListener.id());
                        }
                    }
                    if (removedIds != null && !removedIds.isEmpty()) {
                        Set<Long> finalRemovedIds = removedIds;
                        Set<AdviceListener> newSet = adviceListeners.stream()
                                .filter(adviceListener -> !finalRemovedIds.contains(adviceListener.id()))
                                .collect(Collectors.toSet());
                        multimap.putAll(key, newSet);
                    }
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public static void unregisterAdviceListener(AdviceListener listener) {
        UNREGISTER_ADVICE_IDS.add(listener.id());
    }

    public static void registerAdviceListener(ClassLoader classLoader, String className, String methodName, String methodDesc, AdviceListener adviceListener) {
        String key = key(className, methodName, methodDesc);
        registerAdviceListener(classLoader, key, adviceListener);
    }

    public static void registerTraceAdviceListener(ClassLoader classLoader, String className, String owner, String methodName, String methodDesc, AdviceListener adviceListener) {
        registerAdviceListener(classLoader, keyForTrace(className, owner, methodName, methodDesc), adviceListener);
    }

    private static void registerAdviceListener(ClassLoader classLoader, String key, AdviceListener adviceListener) {
        ClassLoader cl = wrap(classLoader);
        Multimap<String, AdviceListener> listenerMultimap = MAP.get(cl);
        if (listenerMultimap == null) {
            MAP.putIfAbsent(cl, MultimapBuilder.treeKeys().hashSetValues().build());
            listenerMultimap = MAP.get(cl);
        }
        if (listenerMultimap.put(key, adviceListener)) {
            LOGGER.debug("register advice listener, classLoader : {}, key : {}, adviceListener : {}", cl.getClass().getName(), key, adviceListener.getClass().getName());
        }
    }

    public static List<AdviceListener> queryAdviceListeners(ClassLoader classLoader, String className, String methodName, String methodDesc) {
        return queryAdviceListeners(classLoader, key(className, methodName, methodDesc));
    }

    public static List<AdviceListener> queryTraceAdviceListeners(ClassLoader classLoader, String className, String owner, String methodName, String methodDesc) {
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
