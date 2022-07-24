package com.tutelary.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.taobao.arthas.common.JavaVersionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessUtils.class);

    private static String JAVA_HOME;

    public static final int STATUS_OK = 0;
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_EXEC_TIMEOUT = 100;
    public static final int STATUS_EXEC_ERROR = 101;

    public static void startTutelaryBoot(List<String> args) {
        String javaHome = findJavaHome();
        if (StrUtil.isBlank(javaHome)) {
            return;
        }
        File javaFile = findJava(javaHome);
        if (javaFile == null) {
            LOGGER.error("cannot find java/java.exe under java home : {}", javaHome);
            return;
        }
        File toolsJar = findToolsJar(javaHome);
        if (JavaVersionUtils.isLessThanJava9()) {
            if (toolsJar == null || !toolsJar.exists()) {
                LOGGER.error("can not find tools.jar under java home : {}", javaHome);
                return;
            }
        }
        List<String> command = new ArrayList<>();
        command.add(javaFile.getAbsolutePath());
        if (toolsJar != null && toolsJar.exists()) {
            command.add("-Xbootclasspath/a:" + toolsJar.getAbsolutePath());
        }
        command.addAll(args);
        String returnValue = RuntimeUtil.execForStr(command.toArray(new String[0]));
        LOGGER.info("start boot jar return value : {}", returnValue);
    }

    public static String findJavaHome() {
        String javaHome = System.getProperty("java.home");
        if (!JavaVersionUtils.isLessThanJava9()) {
            return javaHome;
        }
        File toolsJar = new File(javaHome, "lib/tools.jar");
        if (!toolsJar.exists()) {
            toolsJar = new File("../lib/tools.jar");
        }
        if (!toolsJar.exists()) {
            toolsJar = new File(javaHome, "../../lib/tools.jar");
        }
        if (toolsJar.exists()) {
            return javaHome;
        }
        LOGGER.info("can not find tools.jar under java.home : {}", javaHome);
        String javaHomeEnv = System.getenv("JAVA_HOME");
        if (StrUtil.isNotBlank(javaHomeEnv)) {
            LOGGER.info("find System Env JAVA_HOME : {}", javaHomeEnv);
            toolsJar = new File(javaHomeEnv, "lib/tools.jar");
            if (!toolsJar.exists()) {
                toolsJar = new File(javaHomeEnv, "../lib/tools.jar");
            }
            if (toolsJar.exists()) {
                return javaHomeEnv;
            }
        }
        LOGGER.error("cannot find tools.jar under java home : {}", javaHomeEnv);
        return null;
    }
    
    private static File findJava(String javaHome) {
        String[] paths = {"bin/java", "bin/java.exe", "../bin/java", "../bin/java.exe"};
        for (String path : paths) {
            File javaFile = new File(javaHome, path);
            if (javaFile.exists()) {
                LOGGER.info("Found java : {}", javaFile.getAbsoluteFile());
                return javaFile;
            }
        }
        return null;
    }

    private static File findToolsJar(String javaHome) {
        if (JavaVersionUtils.isGreaterThanJava8()) {
            return null;
        }
        File toolsJar = new File(javaHome, "lib/tools.jar");
        if (!toolsJar.exists()) {
            toolsJar = new File(javaHome, "../lib/tools.jar");
        }
        if (!toolsJar.exists()) {
            toolsJar = new File(javaHome, "../../lib/tools.jar");
        }
        return toolsJar;
    }

}
