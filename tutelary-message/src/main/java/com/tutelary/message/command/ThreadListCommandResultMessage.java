package com.tutelary.message.command;

import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResult;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.ThreadInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode (callSuper = true)
@Command(CommandEnum.ARTHAS_THREAD_LIST)
public class ThreadListCommandResultMessage extends CommandResult {

    private int newCount;

    private int runnableCount;

    private int blockedCount;

    private int waitingCount;

    private int timedWaitingCount;

    private int terminatedCount;

    private List<ThreadInfo> threadList;

}
