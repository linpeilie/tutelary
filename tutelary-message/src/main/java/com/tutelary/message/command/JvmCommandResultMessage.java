package com.tutelary.message.command;

import com.tutelary.common.CommandResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode (callSuper = true)
public class JvmCommandResultMessage extends CommandResult {

    private RuntimeInfo runtime;

    private ClassLoading classLoading;

    private Compilation compilation;

    private List<GarbageCollector> garbageCollectors;

    private List<MemoryManager> memoryManagers;

    private MemoryInfo memory;

    private OperatingSystem operatingSystem;

    private ThreadInfo thread;

}
