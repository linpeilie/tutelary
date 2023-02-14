package com.tutelary;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.tutelary.utils.ProcessUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

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

    public static File mkdir(String parentPath, String childPath, boolean clean) {
        File tutelaryDir = new File(parentPath, childPath);
        if (tutelaryDir.exists()) {
            if (clean) {
                FileUtil.clean(tutelaryDir);
            }
            return tutelaryDir;
        }
        if (tutelaryDir.mkdir()) {
            return tutelaryDir;
        }
        return null;
    }

    private void attach() throws IOException {
        String pid = applicationContext.getEnvironment().getRequiredProperty("PID");
        String applicationName = applicationContext.getEnvironment().getRequiredProperty("spring.application.name");
        LOGGER.info("current pid : {}", pid);
        String tutelaryServerUrl = tutelaryProperties.getServerUrl();
        LOGGER.debug("tutelary server url : {}", tutelaryServerUrl);

        // 加载 tutelary-packaging
        URL tutelaryJarUrl = this.getClass().getClassLoader().getResource("tutelary-bin.zip");
        Assert.notNull(tutelaryJarUrl, "fail to load tutelary-packaging");
        File tutelaryDir = mkdir(FileUtil.getUserHomePath(), ".tutelary", true);
        ZipUtil.unzip(tutelaryJarUrl.openStream(), tutelaryDir, Charset.defaultCharset());

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
        args.add(StrUtil.DASHED + "pid");
        args.add(pid);
        args.add(StrUtil.DASHED + "app-name");
        args.add(applicationName);
        args.add(StrUtil.DASHED + "tutelary-workspace");
        args.add(tutelaryAgentJar.getParent());
        args.add(StrUtil.DASHED + "tutelary-server-url");
        args.add(tutelaryServerUrl);

        ProcessUtils.startTutelaryBoot(args);
    }

}
