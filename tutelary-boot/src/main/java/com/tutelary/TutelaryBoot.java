package com.tutelary;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.taobao.middleware.cli.CLI;
import com.taobao.middleware.cli.CommandLine;
import com.taobao.middleware.cli.annotations.Argument;
import com.taobao.middleware.cli.annotations.CLIConfigurator;
import com.taobao.middleware.cli.annotations.Name;
import com.taobao.middleware.cli.annotations.Option;
import com.tutelary.common.config.TutelaryAgentProperties;
import com.tutelary.common.constants.ArgumentConstants;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

@Name("tutelary-boot")
public class TutelaryBoot {

    private String pid;

    private String appName;

    private String tutelaryAgentPath;

    private String tutelaryServerUrl;

    private String basePackage;

    public static void main(String[] args) throws IOException {
        System.out.println("args : " + ArrayUtil.toString(args));
        TutelaryBoot tutelaryBoot = new TutelaryBoot();
        CLI cli = CLIConfigurator.define(TutelaryBoot.class);
        CommandLine commandLine = cli.parse(Arrays.asList(args));
        CLIConfigurator.inject(commandLine, tutelaryBoot);

        TutelaryAgentProperties tutelaryAgentProperties = new TutelaryAgentProperties();
        tutelaryAgentProperties.setTutelaryServerUrl(tutelaryBoot.tutelaryServerUrl);
        tutelaryAgentProperties.setAppName(tutelaryBoot.appName);

        VirtualMachine virtualMachine = null;
        try {
            virtualMachine = VirtualMachine.attach(tutelaryBoot.pid);
            virtualMachine.loadAgent(tutelaryBoot.tutelaryAgentPath, JSONUtil.toJsonStr(tutelaryAgentProperties));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            if (virtualMachine != null) {
                virtualMachine.detach();
            }
        }
    }

    @Option(longName = ArgumentConstants.PID, required = true)
    public void setPid(String pid) {
        this.pid = pid;
    }

    @Option(longName = ArgumentConstants.APP_NAME, required = true)
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Option(longName = ArgumentConstants.TUTELARY_AGENT_PATH, required = true)
    public void setTutelaryAgentPath(String tutelaryAgentPath) {
        this.tutelaryAgentPath = tutelaryAgentPath;
    }

    @Option(longName = ArgumentConstants.TUTELARY_SERVER_URL, required = true)
    public void setTutelaryServerUrl(String tutelaryServerUrl) {
        this.tutelaryServerUrl = tutelaryServerUrl;
    }

    @Option(longName = ArgumentConstants.BASE_PACKAGE, required = true)
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
