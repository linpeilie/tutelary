package cn.easii.tutelary.client.util;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.dialect.console.ConsoleLog;
import cn.easii.tutelary.deps.ch.qos.logback.classic.LoggerContext;
import cn.easii.tutelary.deps.ch.qos.logback.classic.joran.JoranConfigurator;
import cn.easii.tutelary.deps.org.slf4j.LoggerFactory;
import com.google.common.base.Strings;
import java.io.File;

public class LogUtil {

    private static final Log LOGGER = new ConsoleLog(LogUtil.class);

    private static final String LOGGING_CONFIG_PROPERTY = "tutelary.logging.config";

    public static LoggerContext initLogger(String tutelaryHomePath) {
        // 获取配置文件
        String loggingConfigProperty = System.getProperty(LOGGING_CONFIG_PROPERTY);
        String loggingFile = Strings.isNullOrEmpty(
            loggingConfigProperty) ? tutelaryHomePath + File.separator + "logback.xml" : loggingConfigProperty;
        File configFile = new File(loggingFile);
        if (!configFile.isFile()) {
            LOGGER.error("config file not exist, config file path : {}", loggingFile);
            return null;
        }
        LOGGER.info("config file path : {}", loggingFile);
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            loggerContext.reset();

            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(loggerContext);
            configurator.doConfigure(configFile.toURI().toURL());

            return loggerContext;
        } catch (Throwable e) {
            LOGGER.error("initLogger occur error", e);
        }
        return null;
    }

}
