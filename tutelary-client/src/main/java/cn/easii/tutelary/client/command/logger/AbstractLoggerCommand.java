package cn.easii.tutelary.client.command.logger;

import cn.easii.tutelary.common.utils.ThrowableUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import java.lang.instrument.Instrumentation;

public abstract class AbstractLoggerCommand {

    private final Instrumentation inst;

    public AbstractLoggerCommand(Instrumentation inst) {
        this.inst = inst;
    }

    private static Multimap<ClassLoader, LoggerType> classLoaderLoggerMap;

    protected static final ImmutableMap<LoggerType, String> loggerHelperMap;

    static {
        loggerHelperMap = ImmutableMap.<LoggerType, String>builder()
            .put(LoggerType.LOGBACK, "cn.easii.tutelary.client.command.logger.LogbackHelper")
            .build();
    }

    protected Multimap<ClassLoader, LoggerType> loadClassLoaderLoggers() {
        if (classLoaderLoggerMap != null) {
            return classLoaderLoggerMap;
        }
        synchronized (LoggerInfoCommand.class) {
            if (classLoaderLoggerMap != null) {
                return classLoaderLoggerMap;
            }
            SetMultimap<ClassLoader, LoggerType> multimap =
                MultimapBuilder.hashKeys().enumSetValues(LoggerType.class).build();

            final Class<?>[] allLoadedClasses = inst.getAllLoadedClasses();
            for (Class<?> clazz : allLoadedClasses) {
                final ClassLoader classLoader = clazz.getClassLoader();
                if (classLoader == null) {
                    continue;
                }
                final String className = clazz.getName();
                if ("org.apache.log4j.Logger".equals(className)) {
                    ThrowableUtil.ignoringExceptionsExecute(() -> {
                        if (classLoader.getResource("org/apache/log4j/AsyncAppender.class") != null) {
                            multimap.put(classLoader, LoggerType.LOG4J);
                        }
                    });
                } else if ("ch.qos.logback.classic.Logger".equals(className)) {
                    ThrowableUtil.ignoringExceptionsExecute(() -> {
                        if (classLoader.getResource("ch/qos/logback/core/Appender.class") != null) {
                            multimap.put(classLoader, LoggerType.LOGBACK);
                        }
                    });
                } else if ("org.apache.logging.log4j.Logger".equals(className)) {
                    ThrowableUtil.ignoringExceptionsExecute(() -> {
                        if (classLoader.getResource("org/apache/logging/log4j/core/LoggerContext.class") != null) {
                            multimap.put(classLoader, LoggerType.LOG4J2);
                        }
                    });
                }
            }
            classLoaderLoggerMap = multimap;
        }
        return classLoaderLoggerMap;
    }

}
