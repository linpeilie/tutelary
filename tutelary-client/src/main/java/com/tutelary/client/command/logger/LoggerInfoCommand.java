package com.tutelary.client.command.logger;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Multimap;
import com.tutelary.client.command.Command;
import com.tutelary.client.converter.LoggerInfoConverter;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.message.command.domain.LoggerInfo;
import com.tutelary.message.command.param.LoggerInfoRequest;
import com.tutelary.message.command.result.LoggerInfoResponse;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoggerInfoCommand extends AbstractLoggerCommand implements Command<LoggerInfoResponse> {

    private static final Log log = LogFactory.get(LoggerInfoCommand.class);

    private final LoggerInfoRequest request;

    public LoggerInfoCommand(final LoggerInfoRequest request, final Instrumentation inst) {
        super(inst);
        this.request = request;
    }

    @Override
    public LoggerInfoResponse execute() {
        List<LoggerInfo> loggers = new ArrayList<>();
        Multimap<ClassLoader, LoggerType> classLoaderLoggers = loadClassLoaderLoggers();

        for (Map.Entry<ClassLoader, LoggerType> entry : classLoaderLoggers.entries()) {
            final ClassLoader classloader = entry.getKey();

            if (classloader == null) {
                continue;
            }

            if (StrUtil.isNotEmpty(request.getClassLoaderHashCode())
                && !request.getClassLoaderHashCode().equals(ClassUtil.classLoaderHash(classloader))) {
                continue;
            }

            final String loggerHelperClassName = loggerHelperMap.get(entry.getValue());

            loggers.addAll(loggerInfo(classloader, loggerHelperClassName));
        }

        final LoggerInfoResponse loggerResponse = new LoggerInfoResponse();
        loggerResponse.setLoggers(loggers);
        return loggerResponse;
    }

    @SuppressWarnings("unchecked")
    private List<LoggerInfo> loggerInfo(ClassLoader classLoader, String className) {
        // load class
        try {
            Class<?> loggerHelperClass = classLoader.loadClass(className);
            Method getLoggersMethod = loggerHelperClass.getMethod("getLoggers", String.class, boolean.class);
            List<Map<String, Object>> loggers = (List<Map<String, Object>>) getLoggersMethod.invoke(null, request.getName(), request.isIncludeNoAppender());
            List<LoggerInfo> loggerInfos = loggers.stream()
                .map(LoggerInfoConverter.CONVERTER::map2LoggerInfo)
                .collect(Collectors.toList());
            loggerInfos.forEach(loggerInfo -> {
                loggerInfo.setClassLoader(classLoader.getClass().getName());
                loggerInfo.setClassLoaderHash(ClassUtil.classLoaderHash(classLoader));
            });
            return loggerInfos;
        } catch (ClassNotFoundException e) {
            log.error("[LoggerCommand] logger helper class not found, class name : {}", className);
        } catch (InvocationTargetException e) {
            log.error("[LoggerCommand] exception occurred during invoking getLoggers method", e);
        } catch (NoSuchMethodException e) {
            log.error("[LoggerCommand] there is no getLoggers method, LoggerHelper name : {}", className);
        } catch (IllegalAccessException e) {
            log.error("[LoggerCommand] the getLoggers method access exception, LoggerHelper name : {}", className);
        }
        return Collections.emptyList();
    }

    @Override
    public void terminated() {

    }
}
