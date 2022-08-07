package com.tutelary.message.command;

import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResult;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.DashboardMemoryInfo;
import com.tutelary.message.command.domain.DashboardRuntime;
import com.tutelary.message.command.domain.GarbageCollector;
import com.tutelary.message.command.domain.ThreadInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Command(CommandEnum.ARTHAS_DASHBOARD)
public class DashboardCommandResultMessage extends CommandResult {

    private DashboardRuntime runtime;
    private List<GarbageCollector> garbageCollectors;
    private List<ThreadInfo> threads;
    private DashboardMemoryInfo memoryInfo;

}
