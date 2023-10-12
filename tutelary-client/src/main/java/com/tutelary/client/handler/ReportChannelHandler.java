package com.tutelary.client.handler;

import com.google.auto.service.AutoService;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.client.InstanceIdHolder;
import com.tutelary.common.thread.NamedThreadFactory;
import com.tutelary.client.command.overview.OverviewCommand;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.InstanceInfoReportRequest;
import com.tutelary.message.command.domain.GarbageCollector;
import com.tutelary.message.command.result.Overview;
import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.exception.RemotingException;
import com.tutelary.remoting.api.transport.ChannelHandlerAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@AutoService(ChannelHandler.class)
public class ReportChannelHandler extends ChannelHandlerAdapter {

    private static final Log LOG = LogFactory.get(ReportChannelHandler.class);
    private static final Map<String, Long> LAST_COLLECTION_COUNT_MAP = new HashMap<>();
    private static final Map<String, Long> LAST_COLLECTION_TIME_MAP = new HashMap<>();
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void connected(Channel channel) throws RemotingException {
        if (scheduledExecutorService == null) {
            scheduledExecutorService = new ScheduledThreadPoolExecutor(
                1,
                new NamedThreadFactory("instance-report")
            );
            scheduledExecutorService.scheduleWithFixedDelay(() -> {
                if (ClientBootstrap.registered) {
                    OverviewCommand overviewCommand = new OverviewCommand();
                    Overview overview = overviewCommand.execute();
                    handleGarbageCollectors(overview.getGarbageCollectors());
                    InstanceInfoReportRequest instanceInfoReportRequest = new InstanceInfoReportRequest();
                    instanceInfoReportRequest.setInstanceId(InstanceIdHolder.getInstanceId());
                    instanceInfoReportRequest.setOverview(overview);
                    instanceInfoReportRequest.setCurrentTime(System.currentTimeMillis());
                    LOG.debug("report overview : {}", instanceInfoReportRequest);
                    ClientBootstrap.sendData(instanceInfoReportRequest);
                }
            }, 0, 1, TimeUnit.MINUTES);
        }
    }

    private void handleGarbageCollectors(List<GarbageCollector> garbageCollectors) {
        garbageCollectors.forEach(garbageCollector -> {
            String name = garbageCollector.getName();
            long collectionCount = garbageCollector.getCollectionCount();
            long collectionTime = garbageCollector.getCollectionTime();
            if (LAST_COLLECTION_COUNT_MAP.containsKey(name)) {
                Long lastCollectionCount = LAST_COLLECTION_COUNT_MAP.get(name);
                garbageCollector.setCollectionCount(collectionCount - lastCollectionCount);
            }
            if (LAST_COLLECTION_TIME_MAP.containsKey(name)) {
                Long lastCollectionTime = LAST_COLLECTION_TIME_MAP.get(name);
                garbageCollector.setCollectionTime(collectionTime - lastCollectionTime);
            }
            LAST_COLLECTION_COUNT_MAP.put(name, collectionCount);
            LAST_COLLECTION_TIME_MAP.put(name, collectionTime);
        });
    }

}
