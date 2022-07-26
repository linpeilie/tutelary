package com.tutelary;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.tutelary.common.constants.ArgumentConstants;
import com.tutelary.common.utils.FileUtils;
import com.tutelary.utils.ProcessUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tutelary {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tutelary.class);

    private final ApplicationContext applicationContext;

    private final TutelaryProperties tutelaryProperties;

    public Tutelary(ApplicationContext applicationContext, TutelaryProperties tutelaryProperties) {
        this.applicationContext = applicationContext;
        this.tutelaryProperties = tutelaryProperties;
        try {
            this.attach();
        } catch (Exception e) {
            LOGGER.error("tutelary agent start occur error", e);
        }
    }

    private void attach() throws IOException {
        String pid = applicationContext.getEnvironment().getRequiredProperty("PID");
        String applicationName = applicationContext.getEnvironment().getRequiredProperty("spring.application.name");
        LOGGER.info("current pid : {}", pid);
        String tutelaryServerUrl = tutelaryProperties.getServerUrl();
        LOGGER.debug("tutelary server url : {}", tutelaryServerUrl);
        String basePackage = tutelaryProperties.getBasePackage();
        if (StrUtil.isBlank(basePackage)) {
            Map<String, Object> applicationMap =
                    applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
            Object application = CollectionUtil.getFirst(applicationMap.values());
            basePackage = application.getClass().getPackage().getName();
        }
        LOGGER.debug("project base package : {}", basePackage);

        // 加载 tutelary-packaging
        URL tutelaryJarUrl = this.getClass().getClassLoader().getResource("tutelary-bin.zip");
        Assert.notNull(tutelaryJarUrl, "fail to load tutelary-packaging");
        URL arthasJarUrl = this.getClass().getClassLoader().getResource("arthas-bin.zip");
        Assert.notNull(arthasJarUrl, "fail to load arthas-packaging");
        File tutelaryDir = FileUtils.mkdir(FileUtil.getUserHomePath(), ".tutelary", true);
        ZipUtil.unzip(tutelaryJarUrl.openStream(), tutelaryDir, Charset.defaultCharset());
        ZipUtil.unzip(arthasJarUrl.openStream(), tutelaryDir, Charset.defaultCharset());

        String tutelaryHome = tutelaryDir.getAbsolutePath();

        // 加载 tutelary-boot.jar
        File tutelaryBootJar = new File(tutelaryHome, "tutelary-boot.jar");
        Assert.notNull(tutelaryBootJar, "can not find tutelary-boot.jar under tutelary home : {}", tutelaryHome);
        // 加载 tutelary-agent.jar
        File tutelaryAgentJar = new File(tutelaryHome, "tutelary-agent.jar");
        Assert.notNull(tutelaryAgentJar, "can not find tutelary-agent.jar under tutelary home : {}", tutelaryHome);

        // 启动 tutelary-boot
        List<String> args = new ArrayList<>();
        args.add("-jar");
        args.add(tutelaryBootJar.getAbsolutePath());
        // args
        args.add(StrUtil.DASHED + ArgumentConstants.PID);
        args.add(pid);
        args.add(StrUtil.DASHED + ArgumentConstants.APP_NAME);
        args.add(applicationName);
        args.add(StrUtil.DASHED + ArgumentConstants.TUTELARY_WORKSPACE);
        args.add(tutelaryAgentJar.getParent());
        args.add(StrUtil.DASHED + ArgumentConstants.TUTELARY_SERVER_URL);
        args.add(tutelaryServerUrl);
        args.add(StrUtil.DASHED + ArgumentConstants.BASE_PACKAGE);
        args.add(basePackage);

        ProcessUtils.startTutelaryBoot(args);
    }

}
