package com.tutelary.client.arthas.distribution;

import com.taobao.arthas.core.command.model.ResultModel;
import com.taobao.arthas.core.command.model.ThreadModel;
import com.tutelary.client.arthas.converter.ArthasModelConverter;
import com.tutelary.message.command.ThreadListCommandResultMessage;

import java.util.Map;

public class ThreadListCommandResultDistributor extends AbstractResultDistributor<ThreadListCommandResultMessage> {

    public ThreadListCommandResultDistributor() {
        super(new ThreadListCommandResultMessage());
    }

    @Override
    protected void appendCommandResult(ResultModel resultModel) {
        ThreadModel threadModel = (ThreadModel)resultModel;
        resultMessage.setThreadList(
            ArthasModelConverter.CONVERTER.threadVosToThreadDetailInfoList(threadModel.getThreadStats()));
        Map<Thread.State, Integer> threadStateCountMap = threadModel.getThreadStateCount();
        resultMessage.setNewCount(threadStateCountMap.getOrDefault(Thread.State.NEW, 0));
        resultMessage.setRunnableCount(threadStateCountMap.getOrDefault(Thread.State.RUNNABLE, 0));
        resultMessage.setBlockedCount(threadStateCountMap.getOrDefault(Thread.State.BLOCKED, 0));
        resultMessage.setWaitingCount(threadStateCountMap.getOrDefault(Thread.State.WAITING, 0));
        resultMessage.setTimedWaitingCount(threadStateCountMap.getOrDefault(Thread.State.TIMED_WAITING, 0));
        resultMessage.setTerminatedCount(threadStateCountMap.getOrDefault(Thread.State.TERMINATED, 0));
    }
}
