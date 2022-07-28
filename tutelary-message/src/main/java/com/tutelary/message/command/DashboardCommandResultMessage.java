package com.tutelary.message.command;

import com.tutelary.common.CommandResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DashboardCommandResultMessage extends CommandResult {

    private DashboardRuntime runtime;
    private List<GarbageCollector> garbageCollectors;
    private List<ThreadDetailInfo> threads;
    private DashboardMemoryInfo memoryInfo;

}
