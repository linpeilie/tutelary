package cn.easii.tutelary.client.command.logger;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import cn.easii.tutelary.client.constants.LoggerConstant;
import cn.easii.tutelary.client.converter.LoggerInfoConverter;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

public class LogbackHelper {

    private static final Log LOG = LogFactory.get(LogbackHelper.class);

    private static boolean Logback = false;

    private static ILoggerFactory loggerFactoryInstance;

    static {
        ThrowableUtil.ignoringExceptionsExecute(() -> {
            final Class<?> clazz = LogbackHelper.class.getClassLoader().loadClass("ch.qos.logback.classic.Logger");
            if (clazz.getClassLoader().equals(LogbackHelper.class.getClassLoader())) {
                final ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
                if (loggerFactory instanceof LoggerContext) {
                    loggerFactoryInstance = loggerFactory;

                    LOG.info("logback enabled......");
                    Logback = true;
                }
            }
        });
    }

    public static List<Map<String, Object>> getLoggers(String name, boolean includeNoAppender) {
        if (Logback) {
            LoggerContext loggerContext = (LoggerContext) loggerFactoryInstance;

            if (StrUtil.isNotEmpty(name)) {
                Logger logger = loggerContext.exists(name);
                if (logger == null) {
                    return Collections.emptyList();
                }
                return ListUtil.toList(getLogger(logger));
            } else {
                final List<Logger> loggers = loggerContext.getLoggerList();
                return loggers.stream()
                    .filter(logger -> includeNoAppender || CollectionUtil.isNotEmpty(logger.iteratorForAppenders()))
                    .map(LogbackHelper::getLogger)
                    .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    private static Map<String, Object> getLogger(Logger logger) {
        Map<String, Object> map = new HashMap<>();
        map.put(LoggerConstant.name, logger.getName());
        map.put(LoggerConstant.clazz, logger.getClass().getName());
        Optional.ofNullable(logger.getClass().getProtectionDomain().getCodeSource())
            .ifPresent(codeSource -> map.put(LoggerConstant.codeSource, codeSource.getLocation()));
        map.put(LoggerConstant.additivity, logger.isAdditive());
        Optional.ofNullable(logger.getLevel()).ifPresent(level -> map.put(LoggerConstant.level, level.toString()));
        Optional.ofNullable(logger.getEffectiveLevel())
            .ifPresent(effectiveLevel -> map.put(LoggerConstant.effectiveLevel, effectiveLevel.toString()));
        map.put(LoggerConstant.appenders, getLoggerAppenders(logger.iteratorForAppenders()));

        return map;
    }

    private static List<Map<String, Object>> getLoggerAppenders(Iterator<Appender<ILoggingEvent>> appenders) {
        List<Map<String, Object>> result = new ArrayList<>();
        while (appenders.hasNext()) {
            Appender<ILoggingEvent> appender = appenders.next();

            Map<String, Object> map = new HashMap<>();

            map.put(LoggerConstant.AppendersConstant.name, appender.getName());
            map.put(LoggerConstant.AppendersConstant.clazz, appender.getClass().getName());
            if (appender instanceof FileAppender) {
                map.put(LoggerConstant.AppendersConstant.file, ((FileAppender) appender).getFile());
            } else if (appender instanceof AsyncAppender) {
                AsyncAppender asyncAppender = (AsyncAppender) appender;
                Iterator<Appender<ILoggingEvent>> iter = asyncAppender.iteratorForAppenders();
                final List<Map<String, Object>> asyncs = getLoggerAppenders(iter);

                List<String> appenderRef = new ArrayList<>();
                for (Map<String, Object> async : asyncs) {
                    appenderRef.add(LoggerInfoConverter.CONVERTER.object2String(async.get(LoggerConstant.name)));
                    result.add(async);
                }

                map.put(LoggerConstant.AppendersConstant.appenderRef, appenderRef);
                map.put(LoggerConstant.AppendersConstant.blocking, !asyncAppender.isNeverBlock());
            } else if (appender instanceof ConsoleAppender) {
                map.put(LoggerConstant.AppendersConstant.target, ((ConsoleAppender) appender).getTarget());
            }

            result.add(map);
        }
        return result;
    }

    public static Boolean updateLevel(String name, String level) {
        if (Logback) {
            Level l = Level.toLevel(level);
            LoggerContext loggerContext = (LoggerContext) loggerFactoryInstance;
            Logger logger = loggerContext.exists(name);
            if (logger != null) {
                logger.setLevel(l);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
