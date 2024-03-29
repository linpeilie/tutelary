package cn.easii.tutelary.common.thread;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;

public class LoggingUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Log LOG = LogFactory.get();

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOG.info("线程 : {} 发生未捕获异常", t.getName(), e);
    }
}
