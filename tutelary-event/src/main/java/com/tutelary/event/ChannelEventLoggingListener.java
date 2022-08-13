package com.tutelary.event;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.netty.handler.logging.LogLevel;

public class ChannelEventLoggingListener extends AbstractChannelEventListener {

    private static final Log LOG = LogFactory.get();

    private final LogLevel logLevel;

    public ChannelEventLoggingListener(LogLevel logLevel) {
        this.logLevel = logLevel;
    }



}
