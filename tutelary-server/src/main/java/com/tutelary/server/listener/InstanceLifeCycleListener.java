package com.tutelary.server.listener;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.tutelary.InstanceManager;
import com.tutelary.common.helper.ChannelHelper;
import com.tutelary.common.listener.ServerEventListener;
import com.tutelary.common.thread.LoggingUncaughtExceptionHandler;
import com.tutelary.event.AbstractChannelEventListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class InstanceLifeCycleListener extends AbstractChannelEventListener implements ServerEventListener {

    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, ThreadFactoryBuilder.create().setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler()).build());

    @Autowired
    private InstanceManager instanceManager;

    @Override
    public void onActive(ChannelHandlerContext ctx) {
    }

    @Override
    public void onInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        instanceManager.removeChannel(channel);
    }

}
