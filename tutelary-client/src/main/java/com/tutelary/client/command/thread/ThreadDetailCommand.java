package com.tutelary.client.command.thread;

import cn.hutool.core.util.ArrayUtil;
import com.sun.management.ThreadMXBean;
import com.tutelary.client.command.Command;
import com.tutelary.client.command.ManagementFactory;
import com.tutelary.client.converter.ManagementConverter;
import com.tutelary.message.command.param.ThreadDetailParam;
import com.tutelary.message.command.result.ThreadDetail;

import java.lang.management.ThreadInfo;
import java.util.Arrays;

public class ThreadDetailCommand implements Command<ThreadDetail> {

    private final ThreadDetailParam param;

    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    public ThreadDetailCommand(ThreadDetailParam param) {
        this.param = param;
    }

    @Override
    public ThreadDetail execute() {
        long[] ids = {param.getId()};
        ThreadInfo[] threadInfoArr = threadMXBean.getThreadInfo(ids, false, false);

        if (threadInfoArr == null || threadInfoArr.length < 1 || threadInfoArr[0]  == null) {
            ThreadDetail threadDetail = new ThreadDetail();
            threadDetail.failed("thread not exits! id : " + param.getId());
            return threadDetail;
        }
        
        return ManagementConverter.CONVERTER.threadInfoToDetail(threadInfoArr[0]);
    }

    @Override
    public void terminated() {

    }
}
