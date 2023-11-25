package cn.easii.tutelary.client.command.logger;

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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.log4j.Appender;
import org.apache.log4j.AsyncAppender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4jHelper {

    private static final Log LOG = LogFactory.get(Log4jHelper.class);

    private static boolean Log4j = false;

    static {
        ThrowableUtil.ignoringExceptionsExecute(() -> {
            final Class<?> clazz = Log4jHelper.class.getClassLoader().loadClass("org.apache.log4j.Logger");
            if (clazz.getClassLoader().equals(Log4jHelper.class.getClassLoader())) {
                LOG.info("log4j enabled......");
                Log4j = true;
            }
        });
    }

    public static List<Map<String, Object>> getLoggers(String name, boolean includeNoAppender) {
        if (!Log4j) {
            return Collections.emptyList();
        }
        if (StrUtil.isNotEmpty(name)) {
            Logger logger = LogManager.getLoggerRepository().exists(name);
            if (logger != null) {
                return ListUtil.toList(getLogger(logger));
            }
        } else {
            List<Map<String, Object>> list = new ArrayList<>();
            Enumeration<Logger> loggers = LogManager.getLoggerRepository().getCurrentLoggers();
            Optional.ofNullable(loggers).ifPresent(loggerList -> {
                while (loggerList.hasMoreElements()) {
                    Logger logger = loggerList.nextElement();
                    if (includeNoAppender || CollectionUtil.isNotEmpty(logger.getAllAppenders())) {
                        Map<String, Object> map = getLogger(logger);
                        list.add(map);
                    }
                }
            });

            Optional.ofNullable(LogManager.getLoggerRepository().getRootLogger()).ifPresent(root -> {
                if (includeNoAppender || CollectionUtil.isNotEmpty(root.getAllAppenders())) {
                    list.add(getLogger(root));
                }
            });
            return list;

        }
        return Collections.emptyList();
    }

    private static Map<String, Object> getLogger(Logger logger) {
        Map<String, Object> map = new HashMap<>();
        map.put(LoggerConstant.name, logger.getName());
        map.put(LoggerConstant.clazz, logger.getClass().getName());
        Optional.ofNullable(logger.getClass().getProtectionDomain().getCodeSource())
            .ifPresent(codeSource -> map.put(LoggerConstant.codeSource, codeSource.getLocation()));
        map.put(LoggerConstant.additivity, logger.getAdditivity());
        Optional.ofNullable(logger.getLevel()).ifPresent(level -> map.put(LoggerConstant.level, level.toString()));
        Optional.ofNullable(logger.getEffectiveLevel())
            .ifPresent(effectiveLevel -> map.put(LoggerConstant.effectiveLevel, effectiveLevel.toString()));
        List<Map<String, Object>> loggerAppenders = getLoggerAppenders(logger.getAllAppenders());
        map.put(LoggerConstant.appenders, loggerAppenders);

        return map;
    }

    private static List<Map<String, Object>> getLoggerAppenders(Enumeration<Appender> appenders) {
        if (appenders == null) {
            return null;
        }

        List<Map<String, Object>> list = new ArrayList<>();

        while (appenders.hasMoreElements()) {
            Map<String, Object> map = new HashMap<>();

            Appender appender = appenders.nextElement();

            map.put(LoggerConstant.AppendersConstant.name, appender.getName());
            map.put(LoggerConstant.AppendersConstant.clazz, appender.getClass().getName());

            if (appender instanceof FileAppender) {
                map.put(LoggerConstant.AppendersConstant.file, ((FileAppender) appender).getFile());
            } else if (appender instanceof ConsoleAppender) {
                map.put(LoggerConstant.AppendersConstant.target, ((ConsoleAppender) appender).getTarget());
            } else if (appender instanceof AsyncAppender) {
                Optional.ofNullable(((AsyncAppender) appender).getAllAppenders())
                    .ifPresent(appendersOfAsync -> {
                        List<Map<String, Object>> asyncs = getLoggerAppenders(appendersOfAsync);
                        List<String> appenderRef = new ArrayList<>();
                        for (Map<String, Object> async : asyncs) {
                            appenderRef.add(
                                LoggerInfoConverter.CONVERTER.object2String(async.get(LoggerConstant.name)));
                            list.add(async);
                        }
                        map.put(LoggerConstant.AppendersConstant.blocking, ((AsyncAppender) appender).getBlocking());
                        map.put(LoggerConstant.AppendersConstant.appenderRef, appenderRef);
                    });
            }

            list.add(map);
        }

        return list;
    }

    public static Boolean updateLevel(String name, String level) {
        if (Log4j) {
            Level l = Level.toLevel(level);
            Logger logger = LogManager.getLoggerRepository().exists(name);
            if (logger != null) {
                logger.setLevel(l);
                return Boolean.TRUE;
            } else {
                Logger root = LogManager.getLoggerRepository().getRootLogger();
                if (root.getName().equals(name)) {
                    root.setLevel(l);
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

}
