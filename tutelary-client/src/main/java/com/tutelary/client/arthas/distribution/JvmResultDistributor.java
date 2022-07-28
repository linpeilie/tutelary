package com.tutelary.client.arthas.distribution;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.common.collect.Lists;
import com.taobao.arthas.core.command.model.JvmItemVO;
import com.taobao.arthas.core.command.model.JvmModel;
import com.taobao.arthas.core.command.model.ResultModel;
import com.taobao.arthas.core.command.model.StatusModel;
import com.taobao.arthas.core.distribution.ResultDistributor;
import com.tutelary.common.CommandResult;
import com.tutelary.common.utils.StringUtils;
import com.tutelary.message.command.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class JvmResultDistributor extends AbstractResultDistributor<JvmCommandResultMessage> {

    private static final Log LOG = LogFactory.get();

    private final JvmCommandResultMessage commandResult = new JvmCommandResultMessage();

    @Override
    public void appendResult(ResultModel result) {
        LOG.info("[ PackageResultDistributor ] append result : {}", result);
        if (result instanceof StatusModel) {
            handleStatusModel(result);
        } else if (result instanceof JvmModel) {
            handleJvmModel(result);
        }
    }

    private void handleStatusModel(ResultModel resultModel) {
        StatusModel statusModel = (StatusModel)resultModel;
        commandResult.setStatus(statusModel.getStatusCode());
        commandResult.setMessage(statusModel.getMessage());
    }

    private void handleJvmModel(ResultModel resultModel) {
        JvmModel jvmModel = (JvmModel)resultModel;
        Map<String, List<JvmItemVO>> jvmInfoMap = jvmModel.getJvmInfo();
        analysisRuntimeInfo(jvmInfoMap.get("RUNTIME"));
        analysisClassLoading(jvmInfoMap.get("CLASS-LOADING"));
        analysisCompilation(jvmInfoMap.get("COMPILATION"));
        analysisGarbageCollectors(jvmInfoMap.get("GARBAGE-COLLECTORS"));
        analysisMemoryManagers(jvmInfoMap.get("MEMORY-MANAGERS"));
        analysisMemory(jvmInfoMap.get("MEMORY"));
        analysisOperatingSystem(jvmInfoMap.get("OPERATING-SYSTEM"));
        analysisThread(jvmInfoMap.get("THREAD"));
    }

    private void analysisThread(List<JvmItemVO> list) {
        ThreadInfo threadInfo = new ThreadInfo();
        CollectionUtil.forEach(list, (value, index) -> {
            switch (value.getName()) {
                case "COUNT":
                    threadInfo.setCount(NumberUtil.parseInt(StringUtils.toString(value.getValue())));
                    break;
                case "DAEMON-COUNT":
                    threadInfo.setDaemonCount(NumberUtil.parseInt(StringUtils.toString(value.getValue())));
                    break;
                case "PEAK-COUNT":
                    threadInfo.setPeakCount(NumberUtil.parseInt(StringUtils.toString(value.getValue())));
                    break;
                case "STARTED-COUNT":
                    threadInfo.setStartedCount(NumberUtil.parseInt(StringUtils.toString(value.getValue())));
                    break;
                case "DEADLOCK-COUNT":
                    threadInfo.setDeadlockCount(NumberUtil.parseInt(StringUtils.toString(value.getValue())));
                    break;
                default:
                    break;
            }
        });
        commandResult.setThread(threadInfo);
    }

    private void analysisOperatingSystem(List<JvmItemVO> list) {
        OperatingSystem operatingSystem = new OperatingSystem();
        CollectionUtil.forEach(list, (value, index) -> {
            switch (value.getName()) {
                case "OS":
                    operatingSystem.setOs(StringUtils.toString(value.getValue()));
                    break;
                case "ARCH":
                    operatingSystem.setArch(StringUtils.toString(value.getValue()));
                    break;
                case "PROCESSORS-COUNT":
                    operatingSystem.setProcessorsCount(NumberUtil.parseInt(StringUtils.toString(value.getValue())));
                    break;
                case "LOAD-AVERAGE":
                    operatingSystem.setLoadAverage(NumberUtil.parseDouble(StringUtils.toString(value.getValue())));
                    break;
                case "VERSION":
                    operatingSystem.setVersion(StringUtils.toString(value.getValue()));
                    break;
                default:
                    break;
            }
        });
        commandResult.setOperatingSystem(operatingSystem);
    }

    private void analysisMemory(List<JvmItemVO> list) {
        MemoryInfo memoryInfo = new MemoryInfo();
        CollectionUtil.forEach(list, (value, index) -> {
            switch (value.getName()) {
                case "HEAP-MEMORY-USAGE":
                    MemoryUsage memoryUsage = JSONUtil.toBean(JSONUtil.toJsonStr(value.getValue()), MemoryUsage.class);
                    memoryUsage.setName(value.getName());
                    memoryInfo.setHeapMemoryUsage(memoryUsage);
                    break;
                case "NO-HEAP-MEMORY-USAGE":
                    MemoryUsage noHeapMemoryUsage =
                        JSONUtil.toBean(JSONUtil.toJsonStr(value.getValue()), MemoryUsage.class);
                    noHeapMemoryUsage.setName(value.getName());
                    memoryInfo.setHeapMemoryUsage(noHeapMemoryUsage);
                    break;
                case "PENDING-FINALIZE-COUNT":
                    memoryInfo.setPendingFinalizeCount(NumberUtil.parseLong(StringUtils.toString(value.getValue())));
                    break;
                default:
                    break;
            }
        });
        commandResult.setMemory(memoryInfo);
    }

    private void analysisMemoryManagers(List<JvmItemVO> list) {
        List<MemoryManager> memoryManagers = new ArrayList<>();
        CollectionUtil.forEach(list, (value, index) -> {
            Object val = value.getValue();
            if (val != null) {
                Map<String, Object> map = (Map<String, Object>)val;
                MemoryManager memoryManager = new MemoryManager();
                memoryManager.setName(value.getName());
                memoryManager.setMemoryPoolNames((List<String>)value.getValue());
                memoryManagers.add(memoryManager);
            }
        });
        commandResult.setMemoryManagers(memoryManagers);
    }

    private void analysisGarbageCollectors(List<JvmItemVO> list) {
        List<GarbageCollector> garbageCollectors = new ArrayList<>();
        CollectionUtil.forEach(list, (value, index) -> {
            Object val = value.getValue();
            if (val != null) {
                Map<String, Object> psMap = (Map<String, Object>)val;
                GarbageCollector garbageCollector = new GarbageCollector();
                garbageCollector.setName(value.getName());
                garbageCollector.setCollectionCount(
                    NumberUtil.parseLong(StringUtils.toString(psMap.get("collectionCount"))));
                garbageCollector.setCollectionTime(
                    NumberUtil.parseLong(StringUtils.toString(psMap.get("collectionTime"))));
                garbageCollectors.add(garbageCollector);
            }
        });
        commandResult.setGarbageCollectors(garbageCollectors);
    }

    private void analysisCompilation(List<JvmItemVO> list) {
        Compilation compilation = new Compilation();
        CollectionUtil.forEach(list, (value, index) -> {
            switch (value.getName()) {
                case "NAME":
                    compilation.setName(StringUtils.toString(value.getValue()));
                    break;
                case "TOTAL-COMPILE-TIME":
                    compilation.setTotalCompileTime(NumberUtil.parseLong(StringUtils.toString(value.getValue())));
                    break;
                default:
                    break;
            }
        });
        commandResult.setCompilation(compilation);
    }

    private void analysisClassLoading(List<JvmItemVO> classLoadingList) {
        ClassLoading classLoading = new ClassLoading();
        CollectionUtil.forEach(classLoadingList, (value, index) -> {
            switch (value.getName()) {
                case "LOADED-CLASS-COUNT":
                    classLoading.setLoadedClassCount(NumberUtil.parseInt(StringUtils.toString(value.getValue())));
                    break;
                case "TOTAL-LOADED-CLASS-COUNT":
                    classLoading.setTotalLoadedClassCount(NumberUtil.parseLong(StringUtils.toString(value.getValue())));
                    break;
                case "UNLOADED-CLASS-COUNT":
                    classLoading.setUnloadedClassCount(NumberUtil.parseLong(StringUtils.toString(value.getValue())));
                    break;
                case "IS-VERBOSE":
                    classLoading.setVerbose(Boolean.parseBoolean(StringUtils.toString(value.getValue())));
                    break;
                default:
                    break;
            }
        });
        commandResult.setClassLoading(classLoading);
    }

    private void analysisRuntimeInfo(List<JvmItemVO> runtimeItemList) {
        RuntimeInfo runtimeInfo = new RuntimeInfo();
        CollectionUtil.forEach(runtimeItemList, (value, index) -> {
            switch (value.getName()) {
                case "MACHINE-NAME":
                    runtimeInfo.setMachineName(StringUtils.toString(value.getValue()));
                    break;
                case "JVM-START-TIME":
                    runtimeInfo.setJvmStartTime(StringUtils.toString(value.getValue()));
                    break;
                case "MANAGEMENT-SPEC-VERSION":
                    runtimeInfo.setManagementSpecVersion(StringUtils.toString(value.getValue()));
                    break;
                case "SPEC-NAME":
                    runtimeInfo.setSpecName(StringUtils.toString(value.getValue()));
                    break;
                case "SPEC-VENDOR":
                    runtimeInfo.setSpecVendor(StringUtils.toString(value.getValue()));
                    break;
                case "SPEC-VERSION":
                    runtimeInfo.setSpecVersion(StringUtils.toString(value.getValue()));
                    break;
                case "VM-NAME":
                    runtimeInfo.setVmName(StringUtils.toString(value.getValue()));
                    break;
                case "VM-VENDOR":
                    runtimeInfo.setVmVendor(StringUtils.toString(value.getValue()));
                    break;
                case "VM-VERSION":
                    runtimeInfo.setVmVersion(StringUtils.toString(value.getValue()));
                    break;
                case "INPUT-ARGUMENTS":
                    Object inputArguments = value.getValue();
                    if (inputArguments instanceof List) {
                        runtimeInfo.setInputArguments((List<String>)inputArguments);
                    }
                    break;
                default:
                    break;
            }
        });
        commandResult.setRuntime(runtimeInfo);
    }

    @Override
    public JvmCommandResultMessage getResult() {
        return commandResult;
    }
}
