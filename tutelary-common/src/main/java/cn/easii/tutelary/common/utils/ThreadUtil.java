package cn.easii.tutelary.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.sun.management.ThreadMXBean;
import java.util.List;
import java.util.Objects;

public class ThreadUtil {

    private static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    public static ThreadGroup getRoot() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup parent;
        while ((parent = group.getParent()) != null) {
            group = parent;
        }
        return group;
    }

    public static List<Thread> getThreads() {
        ThreadGroup root = getRoot();
        Thread[] threads = new Thread[root.activeCount()];
        while (root.enumerate(threads, true) == threads.length) {
            threads = new Thread[threads.length * 2];
        }
        List<Thread> list = CollectionUtil.newArrayList(threads);
        list.removeIf(Objects::isNull);
        return list;
    }

    public static void safeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    /**
     * TCCL : ThreadContextClassLoader
     */
    public static String getTCCL(Thread thread) {
        ClassLoader contextClassLoader = thread.getContextClassLoader();
        if (contextClassLoader == null) {
            return "null";
        } else {
            return contextClassLoader.getClass().getName() + "@" + ClassUtil.classLoaderHash(contextClassLoader);
        }
    }

}
