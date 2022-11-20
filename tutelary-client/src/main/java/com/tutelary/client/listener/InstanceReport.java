package com.tutelary.client.listener;

import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.NamedThreadFactory;
import com.tutelary.client.command.overview.OverviewCommand;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.event.AbstractChannelEventListener;
import com.tutelary.message.InstanceInfoReportRequestMessage;
import com.tutelary.message.command.result.Overview;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InstanceReport extends AbstractChannelEventListener {

    private static final Log LOG = LogFactory.get(InstanceReport.class);

    private ScheduledExecutorService scheduledExecutorService;

    private volatile Channel channel;

    @Override
    public void onClientRegistered(ChannelHandlerContext ctx) {
        channel = ctx.channel();
        if (scheduledExecutorService == null) {
            scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
                    new NamedThreadFactory("instance-report"));
            scheduledExecutorService.scheduleWithFixedDelay(() -> {
                if (ClientBootstrap.registered) {
                    OverviewCommand overviewCommand = new OverviewCommand();
                    Overview overview = overviewCommand.execute();
                    InstanceInfoReportRequestMessage instanceInfoReportRequestMessage = new InstanceInfoReportRequestMessage();
                    instanceInfoReportRequestMessage.setInstanceId(ClientBootstrap.instanceId);
                    instanceInfoReportRequestMessage.setOverview(overview);
                    instanceInfoReportRequestMessage.setCurrentTime(System.currentTimeMillis());
                    LOG.debug("report overview : {}", instanceInfoReportRequestMessage);
                    channel.writeAndFlush(instanceInfoReportRequestMessage);
                }
            }, 0, 1, TimeUnit.MINUTES);
        }
    }

}
