package com.tutelary.message.command;

import com.tutelary.common.CommandResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode (callSuper = true)
public class JvmCommandResultMessage extends CommandResult {

    private JvmRuntimeInfo runtime;

    private JvmClassLoading classLoading;

    private JvmCompilation compilation;

    private List<GarbageCollector> garbageCollectors;

    private List<JvmMemoryManager> memoryManagers;

    private MemoryInfo memory;

    private JvmOperatingSystem operatingSystem;

}
