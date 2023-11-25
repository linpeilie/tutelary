package cn.easii.tutelary.client.command.logger;

import cn.easii.tutelary.client.constants.LoggerConstant;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AsyncAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class Log4j2Helper {

    private static final Log LOG = LogFactory.get(Log4j2Helper.class);

    private static boolean Log4j2 = false;

    private static Field configField = null;

    static {
        ThrowableUtil.ignoringExceptionsExecute(() -> {
            final Class<?> clazz = Log4j2Helper.class.getClassLoader().loadClass("org.apache.logging.log4j.Logger");
            if (clazz.getClassLoader().equals(Log4j2Helper.class.getClassLoader())) {
                LOG.info("log4j2 enabled......");
                Log4j2 = true;
            }
            configField = LoggerConfig.class.getDeclaredField("config");
            configField.setAccessible(true);
        });
    }

    public List<Map<String, Object>> getLoggers(String name, boolean includeNoAppenders) {
        if (!Log4j2) {
            return Collections.emptyList();
        }

        Configuration configuration = getLoggerContext().getConfiguration();

        if (StrUtil.isNotEmpty(name)) {
            LoggerConfig loggerConfig = configuration.getLoggerConfig(name);
            if (loggerConfig == null) {
                return Collections.emptyList();
            }
            if (!name.equalsIgnoreCase("root") && StrUtil.isEmpty(loggerConfig.getName())) {
                return Collections.emptyList();
            }
            return ListUtil.toList(getLogger(loggerConfig));
        } else {

            Map<String, LoggerConfig> loggers = configuration.getLoggers();
            if (MapUtil.isNotEmpty(loggers)) {
                return loggers.values()
                    .stream()
                    .filter(
                        loggerConfig -> includeNoAppenders || CollectionUtil.isNotEmpty(loggerConfig.getAppenders()))
                    .map(Log4j2Helper::getLogger)
                    .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    private static Map<String, Object> getLogger(LoggerConfig loggerConfig) {
        Map<String, Object> map = new HashMap<>();

        map.put(LoggerConstant.name, loggerConfig.getName());
        map.put(LoggerConstant.clazz, loggerConfig.getClass().getName());
        Optional.ofNullable(loggerConfig.getClass().getProtectionDomain().getCodeSource())
            .ifPresent(codeSource -> map.put(LoggerConstant.codeSource, codeSource.getLocation().toString()));

        Optional.ofNullable(configField).ifPresent(config -> {
            ThrowableUtil.ignoringExceptionsExecute(() -> {
                final Object configObj = config.get(loggerConfig);
                LOG.info("config obj : {}", configObj);
            });
        });
        map.put(LoggerConstant.additivity, loggerConfig.isAdditive());
        Optional.ofNullable(loggerConfig.getLevel())
            .ifPresent(level -> map.put(LoggerConstant.level, level.toString()));

        List<Map<String, Object>> loggerAppenders = getLoggerAppenders(loggerConfig);
        map.put(LoggerConstant.appenders, loggerAppenders);

        return map;
    }

    private static List<Map<String, Object>> getLoggerAppenders(LoggerConfig loggerConfig) {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Appender> appenders = loggerConfig.getAppenders();

        for (Map.Entry<String, Appender> entry : appenders.entrySet()) {
            Map<String, Object> map = new HashMap<>();

            Appender appender = entry.getValue();

            map.put(LoggerConstant.AppendersConstant.name, appender.getName());
            map.put(LoggerConstant.AppendersConstant.clazz, appender.getClass().getName());

            if (appender instanceof FileAppender) {
                map.put(LoggerConstant.AppendersConstant.file, ((FileAppender) appender).getFileName());
            } else if (appender instanceof ConsoleAppender) {
                map.put(LoggerConstant.AppendersConstant.target, ((ConsoleAppender) appender).getTarget());
            } else if (appender instanceof AsyncAppender) {
                AsyncAppender asyncAppender = ((AsyncAppender) appender);
                String[] appenderRefStrings = asyncAppender.getAppenderRefStrings();

                map.put(LoggerConstant.AppendersConstant.blocking, asyncAppender.isBlocking());
                map.put(LoggerConstant.AppendersConstant.appenderRef, Arrays.asList(appenderRefStrings));
            }

            list.add(map);
        }

        return list;
    }

    private static LoggerContext getLoggerContext() {
        return (LoggerContext) LogManager.getContext(false);
    }

    public static Boolean updateLevel(String name, String level) {
        if (Log4j2) {
            Level l = Level.getLevel(level);
            if (l == null) {
                return Boolean.FALSE;
            }
            LoggerConfig loggerConfig = getLoggerConfig(name);
            if (loggerConfig == null) {
                loggerConfig = new LoggerConfig(name, l, true);
                getLoggerContext().getConfiguration().addLogger(name, loggerConfig);
            } else {
                loggerConfig.setLevel(l);
            }
            getLoggerContext().updateLoggers();
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static LoggerConfig getLoggerConfig(String name) {
        if (StrUtil.isEmpty(name) || LoggerConfig.ROOT.equalsIgnoreCase(name)) {
            name = LogManager.ROOT_LOGGER_NAME;
        }
        return getLoggerContext().getConfiguration().getLoggers().get(name);
    }

}
