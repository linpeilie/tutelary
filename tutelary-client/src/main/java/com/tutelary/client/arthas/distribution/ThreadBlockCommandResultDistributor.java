package com.tutelary.client.arthas.distribution;

import com.taobao.arthas.core.command.model.BlockingLockInfo;
import com.taobao.arthas.core.command.model.ResultModel;
import com.taobao.arthas.core.command.model.ThreadModel;
import com.tutelary.client.arthas.converter.ArthasModelConverter;
import com.tutelary.message.command.BlockingThreadCommandResultMessage;

public class ThreadBlockCommandResultDistributor extends AbstractResultDistributor<BlockingThreadCommandResultMessage> {

    public ThreadBlockCommandResultDistributor() {
        super(new BlockingThreadCommandResultMessage());
    }

    @Override
    protected void appendCommandResult(ResultModel resultModel) {
        ThreadModel threadModel = (ThreadModel)resultModel;
        BlockingLockInfo blockingLockInfo = threadModel.getBlockingLockInfo();
        resultMessage.setLockingThreadCount(blockingLockInfo.getBlockingThreadCount());
        resultMessage.setLockIdentityHashCode(blockingLockInfo.getLockIdentityHashCode());
        resultMessage.setThreadInfo(
            ArthasModelConverter.CONVERTER.threadInfoToThreadDetailInfo(blockingLockInfo.getThreadInfo()));
    }
}
