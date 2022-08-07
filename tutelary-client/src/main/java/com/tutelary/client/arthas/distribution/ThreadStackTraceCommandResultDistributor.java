package com.tutelary.client.arthas.distribution;

import com.taobao.arthas.core.command.model.ResultModel;
import com.taobao.arthas.core.command.model.ThreadModel;
import com.tutelary.client.arthas.converter.ArthasModelConverter;
import com.tutelary.message.command.ThreadStackTraceCommandResultMessage;

import java.lang.management.ThreadInfo;

public class ThreadStackTraceCommandResultDistributor
    extends AbstractResultDistributor<ThreadStackTraceCommandResultMessage> {

    public ThreadStackTraceCommandResultDistributor() {
        super(new ThreadStackTraceCommandResultMessage());
    }

    @Override
    protected void appendCommandResult(ResultModel resultModel) {
        ThreadModel threadModel = (ThreadModel)resultModel;
        ThreadInfo threadInfo = threadModel.getThreadInfo();
        resultMessage.setThreadInfo(ArthasModelConverter.CONVERTER.threadInfoToThreadDetailInfo(threadInfo));
    }
}
